import {defHttp} from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/site/riderSite/list',
  save='/site/riderSite/add',
  edit='/site/riderSite/edit',
  deleteOne = '/site/riderSite/delete',
  deleteBatch = '/site/riderSite/deleteBatch',
  updateProfitBatch = '/site/riderSite/updateProfitBatch',
  updateProfitAll = '/site/riderSite/updateProfitAll',
  importExcel = '/site/riderSite/importExcel',
  exportXls = '/site/riderSite/exportXls',
}
/**
 * 导出api
 * @param params
 */
export const getExportUrl = Api.exportXls;
/**
 * 导入api
 */
export const getImportUrl = Api.importExcel;
/**
 * 列表接口
 * @param params
 */
export const list = (params) =>
  defHttp.get({url: Api.list, params});

/**
 * 删除单个
 */
export const deleteOne = (params,handleSuccess) => {
  return defHttp.delete({url: Api.deleteOne, params}, {joinParamsToUrl: true}).then(() => {
    handleSuccess();
  });
}
/**
 * 批量删除
 * @param params
 */
export const batchDelete = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({url: Api.deleteBatch, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}

/**
 * 批量更新利润
 * @param params
 */
export const updateProfit = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认更新',
    content: '是否更新选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.post({url: Api.updateProfitBatch, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}

/**
 * 更新全部利润
 * @param params
 */
export const updateAllProfit = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认更新',
    content: '是否更新全部数据的利润',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.post({url: Api.updateProfitAll, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}

/**
 * 保存或者更新
 * @param params
 */
export const saveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.edit : Api.save;
  return defHttp.post({url: url, params});
}
