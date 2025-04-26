import {defHttp} from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  signList = '/interview/riderInterview/list?entrance=1',
  interviewList = '/interview/riderInterview/list',
  save='/interview/riderInterview/add',
  edit='/interview/riderInterview/edit',
  deleteOne = '/interview/riderInterview/delete',
  deleteBatch = '/interview/riderInterview/deleteBatch',
  passBatch = '/interview/riderInterview/passBatch',
  settleBatch = '/interview/riderInterview/settleBatch',
  importExcel = '/interview/riderInterview/importExcel',
  exportXls = '/interview/riderInterview/exportXls',
  allSiteList = '/site/riderSite/queryList',
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
 * 报名列表接口
 * @param params
 */
export const signList = (params) =>
  defHttp.get({url: Api.signList, params});

/**
 * 面试列表接口
 * @param params
 */
export const interviewList = (params) =>
  defHttp.get({url: Api.interviewList, params});

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
 * 批量入职
 * @param params
 */
export const batchPass = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认入职',
    content: '是否确认选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.post({url: Api.passBatch, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}

/**
 * 批量结算
 * @param params
 */
export const batchSettle = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认结算',
    content: '是否确认选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.post({url: Api.settleBatch, data: params}, {joinParamsToUrl: true}).then(() => {
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


/**
 * 获取全部站点
 */
export const getAllSiteList = (params) => defHttp.get({ url: Api.allSiteList, params });
