<template>
  <div>
    <!--引用表格-->
   <BasicTable @register="registerTable" :rowSelection="rowSelection">
     <!--插槽:table标题-->
      <template #tableTitle>
          <a-button type="primary" v-auth="'site:rider_site:add'" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
          <a-button type="primary" v-auth="'site:rider_site:updateProfitBatch'" @click="openProfitBatchModal" preIcon="ant-design:plus-outlined">设置利润</a-button>
        <a-button type="primary" v-auth="'site:rider_site:updateProfitAll'" @click="openProfitAllModal" preIcon="ant-design:plus-outlined">设置全部利润</a-button>
        <a-button  type="primary" v-auth="'site:rider_site:exportXls'" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
          <j-upload-button type="primary" v-auth="'site:rider_site:importExcel'" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>
          <a-dropdown v-if="selectedRowKeys.length > 0">
              <template #overlay>
                <a-menu>
                  <a-menu-item key="1" @click="batchHandleDelete">
                    <Icon icon="ant-design:delete-outlined"></Icon>
                    删除
                  </a-menu-item>
                </a-menu>
              </template>
              <a-button v-auth="'site:rider_site:deleteBatch'">批量操作
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

    <!-- 批量设置利润弹框 -->
    <a-modal
      v-model:visible="showBatchProfitModal"
      title="批量设置利润"
      @ok="handleBatchProfitSubmit"
      @cancel="showBatchProfitModal = false"
    >
      <a-form layout="vertical">
        <a-form-item label="利润百分比">
          <a-input-number
            v-model:value="batchProfitValue"
            :min="0"
            :max="100"
            :precision="0"
            style="width: 100%"
            placeholder="请输入利润百分比"
          />
        </a-form-item>
      </a-form>
    </a-modal>

    <!-- 设置全部利润弹框 -->
    <a-modal
      v-model:visible="showAllProfitModal"
      title="设置全部利润"
      @ok="handleAllProfitSubmit"
      @cancel="showAllProfitModal = false"
    >
      <a-form layout="vertical">
        <a-form-item label="利润百分比">
          <a-input-number
            v-model:value="allProfitValue"
            :min="0"
            :max="100"
            :precision="0"
            style="width: 100%"
            placeholder="请输入利润百分比"
          />
        </a-form-item>
      </a-form>
    </a-modal>

    <!-- 表单区域 -->
    <RiderSiteModal @register="registerModal" @success="handleSuccess"></RiderSiteModal>
  </div>
</template>

<script lang="ts" name="site-riderSite" setup>
import {ref, reactive, computed, unref, nextTick} from 'vue';
  import {BasicTable, useTable, TableAction} from '/@/components/Table';
  import {useModal} from '/@/components/Modal';
  import { useListPage } from '/@/hooks/system/useListPage'
  import RiderSiteModal from './components/RiderSiteModal.vue'
  import {columns, searchFormSchema, superQuerySchema} from './RiderSite.data';
  import {
    list,
    deleteOne,
    batchDelete,
    updateProfit,
    getImportUrl,
    getExportUrl,
    updateAllProfit
  } from './RiderSite.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import { useUserStore } from '/@/store/modules/user';
import {useMessage} from "@/hooks/web/useMessage";

  const queryParam = reactive<any>({});
  const checkedKeys = ref<Array<string | number>>([]);
  const userStore = useUserStore();
  //注册model
  const [registerModal, {openModal}] = useModal();
  //注册table数据
  const { prefixCls,tableContext,onExportXls,onImportXls } = useListPage({
      tableProps:{
           title: '站点管理',
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
            name:"站点管理",
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

  // 添加以下状态和函数
  const showBatchProfitModal = ref(false);
  const showAllProfitModal = ref(false);
  const batchProfitValue = ref<number>(0);
  const allProfitValue = ref<number>(0);

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
           auth: 'site:rider_site:edit'
         }
       ]
   }
     /**
        * 下拉操作栏
        */
  function getDropDownAction(record){
       return [
         {
           label: '详情',
           onClick: handleDetail.bind(null, record),
         }, {
           label: '删除',
           popConfirm: {
             title: '是否确认删除',
             confirm: handleDelete.bind(null, record),
             placement: 'topLeft',
           },
           auth: 'site:rider_site:delete'
         }
       ]
   }


// 打开批量设置利润弹框
function openProfitBatchModal() {
  if (selectedRowKeys.value.length === 0) {
    createMessage.warning('请至少选择一条记录');
    return;
  }
  batchProfitValue.value = 0;
  showBatchProfitModal.value = true;
}

// 打开设置全部利润弹框
function openProfitAllModal() {
  allProfitValue.value = 0;
  showAllProfitModal.value = true;
}

// 批量设置利润提交
async function handleBatchProfitSubmit() {
  try {
    await updateProfit({
      ids: selectedRowKeys.value,
      profit: batchProfitValue.value
    }, handleSuccess);
    showBatchProfitModal.value = false;
  } catch (error) {
    console.error('批量设置利润失败', error);
    createMessage.error('批量设置利润失败');
  }
}

// 设置全部利润提交
async function handleAllProfitSubmit() {
  try {
    await updateAllProfit({
      profit: allProfitValue.value
    }, handleSuccess);
    showAllProfitModal.value = false;
  } catch (error) {
    console.error('设置全部利润失败', error);
    createMessage.error('设置全部利润失败');
  }
}

</script>

<style lang="less" scoped>
  :deep(.ant-picker),:deep(.ant-input-number){
    width: 100%;
  }
</style>
