<template>
  <div>
    <!--引用表格-->
   <BasicTable @register="registerTable" :rowSelection="rowSelection">
     <!--插槽:table标题-->
      <template #tableTitle>
          <a-button type="primary" v-auth="'post:family_post:add'" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button type="primary" v-auth="'post:family_post:publishBatch'" @click="handlePublishStatus" preIcon="ant-design:plus-outlined">确认发布</a-button>
        <a-button type="primary" v-auth="'post:family_post:cancelBatch'" @click="handleCancelStatus" preIcon="ant-design:plus-outlined">确认下架</a-button>
<!--          <a-button  type="primary" v-auth="'post:family_post:exportXls'" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
          <j-upload-button type="primary" v-auth="'post:family_post:importExcel'" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>-->

          <a-dropdown v-if="selectedRowKeys.length > 0">
              <template #overlay>
                <a-menu>
                  <a-menu-item key="1" @click="batchHandleDelete">
                    <Icon icon="ant-design:delete-outlined"></Icon>
                    删除
                  </a-menu-item>
                </a-menu>
              </template>
              <a-button v-auth="'post:family_post:deleteBatch'">批量操作
                <Icon icon="mdi:chevron-down"></Icon>
              </a-button>
        </a-dropdown>
        <!-- 高级查询 -->
        <super-query :config="superQueryConfig" @search="handleSuperQuery" />
      </template>
       <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
      </template>
      <!--字段回显插槽-->
      <template v-slot:bodyCell="{ column, record, index, text }">
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <PostModal @register="registerModal" @success="handleSuccess"></PostModal>

    <!-- 表单详情区域 -->
    <PostDetailModal @register="registerDetailModal" @success="handleSuccess"></PostDetailModal>
  </div>
</template>

<script lang="ts" name="post-post" setup>
  import {ref, reactive, computed, unref} from 'vue';
  import {BasicTable, useTable, TableAction} from '/@/components/Table';
  import {useModal} from '/@/components/Modal';
  import { useListPage } from '/@/hooks/system/useListPage'
  import PostModal from './components/PostModal.vue'
  import {columns, searchFormSchema, superQuerySchema} from './Post.data';
  import {list, deleteOne, batchDelete, getImportUrl, getExportUrl, batchPublish, batchCancel} from './Post.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import { useUserStore } from '/@/store/modules/user';
  import { loadCategoryData } from '/@/api/common/api'
  import { getAuthCache, setAuthCache } from '/@/utils/auth';
  import { DB_DICT_DATA_KEY } from '/@/enums/cacheEnum';
  import PostDetailModal from "@/views/post/components/PostDetailModal.vue";
  import {defHttp} from "@/utils/http/axios";
  import {batchPass} from "@/views/interview/RiderInterview.api";
  import {useMessage} from "@/hooks/web/useMessage";
  const queryParam = reactive<any>({});
  const checkedKeys = ref<Array<string | number>>([]);
  const userStore = useUserStore();
  //注册model
  const [registerModal, {openModal}] = useModal();
  const [registerDetailModal, { openModal: openDetailModal }] = useModal();
  //注册table数据
  const { prefixCls,tableContext,onExportXls,onImportXls } = useListPage({
      tableProps:{
           title: '岗位管理',
           api: list,
           columns,
           canResize:false,
           formConfig: {
              //labelWidth: 120,
              schemas: searchFormSchema,
              autoSubmitOnEnter:true,
              showAdvancedButton:true,
              fieldMapToNumber: [
              ],
              fieldMapToTime: [
              ],
            },
           actionColumn: {
               width: 120,
               fixed:'right'
            },
            beforeFetch: (params) => {
              return Object.assign(params, queryParam);
            },
      },
       exportConfig: {
            name:"岗位管理",
            url: getExportUrl,
            params: queryParam,
          },
          importConfig: {
            url: getImportUrl,
            success: handleSuccess
          },
  })

  const [registerTable, {reload},{ rowSelection, selectedRowKeys }] = tableContext

  // 高级查询配置
  const superQueryConfig = reactive(superQuerySchema);

  const { createMessage } = useMessage();

  /**
   * 高级查询事件
   */
  function handleSuperQuery(params) {
    Object.keys(params).map((k) => {
      queryParam[k] = params[k];
    });
    reload();
  }
   /**
    * 新增事件
    */
  function handleAdd() {
     openModal(true, {
       isUpdate: false,
       showFooter: true,
     });
  }
   /**
    * 编辑事件
    */
  function handleEdit(record: Recordable) {
     openModal(true, {
       record,
       isUpdate: true,
       showFooter: true,
     });
   }

  /**
   * 编辑详情事件
   */
  async function handleDetailEdit(record: Recordable) {
    const queryByPostIdUrl = '/post/postDetail/queryByPostId';
    let params = {postId: record.id};
    const detailRecord = await defHttp.get({url: queryByPostIdUrl, params});

    openDetailModal(true, {
      postId: record.id,// 始终传递 postId
      record: detailRecord,
      isUpdate: true,
      showFooter: true,
    });
  }

  async function handlePublishStatus() {
    if (selectedRowKeys.value.length === 0) {
      createMessage.warning('请至少选择一条记录');
      return;
    }
    await batchPublish({ids: selectedRowKeys.value}, handleSuccess);
  }

  async function handleCancelStatus() {
    if (selectedRowKeys.value.length === 0) {
      createMessage.warning('请至少选择一条记录');
      return;
    }
    await batchCancel({ids: selectedRowKeys.value}, handleSuccess);
  }

   /**
    * 详情
   */
  function handleDetail(record: Recordable) {
     openModal(true, {
       record,
       isUpdate: true,
       showFooter: false,
     });
   }
   /**
    * 删除事件
    */
  async function handleDelete(record) {
     await deleteOne({id: record.id}, handleSuccess);
   }
   /**
    * 批量删除事件
    */
  async function batchHandleDelete() {
     await batchDelete({ids: selectedRowKeys.value}, handleSuccess);
   }
   /**
    * 成功回调
    */
  function handleSuccess() {
      (selectedRowKeys.value = []) && reload();
   }
   /**
      * 操作栏
      */
  function getTableAction(record){
       return [
         {
           label: '编辑',
           onClick: handleEdit.bind(null, record),
           auth: 'post:family_post:edit'
         },
         {
           label: '设置岗位详情',
           onClick: handleDetailEdit.bind(null, record),
           auth: 'post:family_post_detail:edit'
         }
       ]
   }
     /**
        * 下拉操作栏
        */
  function getDropDownAction(record){
       return [
         {
           label: '查看详情',
           onClick: handleDetail.bind(null, record),
         }, {
           label: '删除',
           popConfirm: {
             title: '是否确认删除',
             confirm: handleDelete.bind(null, record),
             placement: 'topLeft',
           },
           auth: 'post:family_post:delete'
         }
       ]
   }


   /**
    * 初始化字典配置
   */
    function initDictConfig(){
       loadCategoryData({code:''}).then((res) => {
         if (res) {
             const allDictDate = userStore.getAllDictItems;
             if(!allDictDate['']){
               userStore.setAllDictItems({...allDictDate,'':res});
             }
         }
       })
   }
   initDictConfig();
</script>

<style lang="less" scoped>
  :deep(.ant-picker),:deep(.ant-input-number){
    width: 100%;
  }
</style>
