<template>
  <div>
    <!--引用表格-->
   <BasicTable @register="registerTable" :rowSelection="rowSelection">
     <!--插槽:table标题-->
      <template #tableTitle>
<!--          <a-button type="primary" v-auth="'interview:rider_interview:add'" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>-->
          <a-button type="primary" v-auth="'interview:rider_interview:passBatch'" @click="handlePassStatus" preIcon="ant-design:plus-outlined">确认入职</a-button>
          <a-button  type="primary" v-auth="'interview:rider_interview:exportXls'" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
<!--          <j-upload-button type="primary" v-auth="'interview:rider_interview:importExcel'" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>-->
          <a-dropdown v-if="selectedRowKeys.length > 0">
              <template #overlay>
                <a-menu>
                  <a-menu-item key="1" @click="batchHandleDelete">
                    <Icon icon="ant-design:delete-outlined"></Icon>
                    删除
                  </a-menu-item>
                </a-menu>
              </template>
              <a-button v-auth="'interview:rider_interview:deleteBatch'">批量操作
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
        <template v-if="column.dataIndex==='expectRegion'">
          <!--省市区字段回显插槽-->
          {{ getAreaTextByCode(text) }}
        </template>
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <RiderInterviewModal @register="registerModal" @success="handleSuccess"></RiderInterviewModal>
  </div>
</template>

<script lang="ts" name="interview-riderInterview" setup>
  import {ref, reactive, computed, unref} from 'vue';
  import {BasicTable, useTable, TableAction} from '/@/components/Table';
  import {useModal} from '/@/components/Modal';
  import { useListPage } from '/@/hooks/system/useListPage'
  import RiderInterviewModal from './components/RiderInterviewModal.vue'
  import {columns, searchFormSchema, superQuerySchema} from './RiderInterview.data';
  import {
    signList,
    deleteOne,
    batchDelete,
    getImportUrl,
    getExportUrl,
    batchPass
  } from './RiderInterview.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import { useUserStore } from '/@/store/modules/user';
  import { getAreaTextByCode } from '/@/components/Form/src/utils/Area';
  import {useMessage} from "@/hooks/web/useMessage";
  const queryParam = reactive<any>({});
  const checkedKeys = ref<Array<string | number>>([]);
  const userStore = useUserStore();
  //注册model
  const [registerModal, {openModal}] = useModal();
  //注册table数据
  const { prefixCls,tableContext,onExportXls,onImportXls } = useListPage({
      tableProps:{
           title: '报名管理',
           api: signList,
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
            name:"报名管理",
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

  async function handlePassStatus() {
    if (selectedRowKeys.value.length === 0) {
      createMessage.warning('请至少选择一条记录');
      return;
    }
    await batchPass({ids: selectedRowKeys.value}, handleSuccess);
  }

  /**
   * 跟踪维护
   */
  function handleStatus(record: Recordable) {
    openModal(true, {
      record,
      isUpdate: true,
      showFooter: true,
    });
  }

  /**
   * 跟踪维护
   */
  function handleSite(record: Recordable) {
    openModal(true, {
      record,
      isUpdate: true,
      showFooter: true,
    });
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
           label: '跟踪维护',
           onClick: handleEdit.bind(null, record),
           auth: 'interview:rider_interview:edit'
         }
       ]
   }
     /**
        * 下拉操作栏
        */
  function getDropDownAction(record){
       return [
         {
           label: '删除',
           popConfirm: {
             title: '是否确认删除',
             confirm: handleDelete.bind(null, record),
             placement: 'topLeft',
           },
           auth: 'interview:rider_interview:delete'
         }
       ]
   }


</script>

<style lang="less" scoped>
  :deep(.ant-picker),:deep(.ant-input-number){
    width: 100%;
  }
</style>
