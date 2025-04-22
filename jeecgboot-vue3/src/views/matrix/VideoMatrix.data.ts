import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '我的视频',
    align:"center",
    dataIndex: 'videoUrl',
   },
   {
    title: '视频描述',
    align:"center",
    dataIndex: 'content',
   },
   {
    title: '发布抖音',
    align:"center",
    dataIndex: 'releaseTiktok_dictText'
   },
   {
    title: '发布快手',
    align:"center",
    dataIndex: 'releaseKwai_dictText'
   },
   {
    title: '发布视频号',
    align:"center",
    dataIndex: 'releaseWechat_dictText'
   },
   {
    title: '发布小红书',
    align:"center",
    dataIndex: 'releaseRednote_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '我的视频',
    field: 'videoUrl',
    component: 'JUpload',
    componentProps:{
     },
  },
  {
    label: '视频描述',
    field: 'content',
    component: 'JEditor',
  },
  {
    label: '发布抖音',
    field: 'releaseTiktok',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"yn",
        type: "radio"
     },
  },
  {
    label: '发布快手',
    field: 'releaseKwai',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"yn",
        type: "radio"
     },
  },
  {
    label: '发布视频号',
    field: 'releaseWechat',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"yn",
        type: "radio"
     },
  },
  {
    label: '发布小红书',
    field: 'releaseRednote',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"yn",
        type: "radio"
     },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  videoUrl: {title: '我的视频',order: 0,view: 'file', type: 'string',},
  content: {title: '视频描述',order: 1,view: 'umeditor', type: 'string',},
  releaseTiktok: {title: '发布抖音',order: 2,view: 'number', type: 'number',dictCode: 'yn',},
  releaseKwai: {title: '发布快手',order: 3,view: 'number', type: 'number',dictCode: 'yn',},
  releaseWechat: {title: '发布视频号',order: 4,view: 'number', type: 'number',dictCode: 'yn',},
  releaseRednote: {title: '发布小红书',order: 5,view: 'number', type: 'number',dictCode: 'yn',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}