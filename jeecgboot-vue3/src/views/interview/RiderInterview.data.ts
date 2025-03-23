import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '姓名',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '性别',
    align:"center",
    dataIndex: 'sex',
     customRender: ({ text }) => {
       return render.renderDict(text, 'sex');
     },
   },
   {
    title: '年龄',
    align:"center",
    dataIndex: 'age'
   },
   {
    title: '手机号码',
    align:"center",
    dataIndex: 'phone'
   },
   {
    title: '报道城市',
    align:"center",
    dataIndex: 'city'
   },
   {
    title: '是否全职',
    align:"center",
    dataIndex: 'jobType',
     customRender: ({ text }) => {
       return render.renderDict(text, 'yn');
     },
   },
   {
    title: '是否需要住宿',
    align:"center",
    dataIndex: 'accommodation',
     customRender: ({ text }) => {
       return render.renderDict(text, 'yn');
     },
   },
   {
    title: '是否需要购买社保',
    align:"center",
    dataIndex: 'socialSecurity',
     customRender: ({ text }) => {
       return render.renderDict(text, 'yn');
     },
   },
   {
    title: '报道站点',
    align:"center",
    dataIndex: 'siteName'
   },
   {
    title: '期望区域地址',
    align:"center",
    dataIndex: 'expectRegion',
   },
   {
    title: '推广人',
    align:"center",
    dataIndex: 'reference'
   },
   {
    title: '数据来源',
    align:"center",
    dataIndex: 'source'
   },
   {
    title: '面试状态',
    align:"center",
    dataIndex: 'status',
     customRender: ({ text }) => {
       return render.renderDict(text, 'yn');
     },
   },
   {
    title: '通过状态',
    align:"center",
    dataIndex: 'passStatus',
     customRender: ({ text }) => {
       return render.renderDict(text, 'yn');
     },
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "姓名",
      field: 'name',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "手机号码",
      field: 'phone',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "报道站点",
      field: 'siteName',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "面试状态",
      field: 'status',
      component: 'JSelectMultiple',
      componentProps:{
        dictCode:"yn"
      },
      //colProps: {span: 6},
 	},
	{
      label: "通过状态",
      field: 'passStatus',
      component: 'JSelectMultiple',
      componentProps:{
        dictCode:"yn"
      },
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '姓名',
    field: 'name',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入姓名!'},
          ];
     },
  },
  {
    label: '性别',
    field: 'sex',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"sex",
        type: "radio"
     },
  },
  {
    label: '年龄',
    field: 'age',
    component: 'InputNumber',
  },
  {
    label: '手机号码',
    field: 'phone',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入手机号码!'},
                 { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
          ];
     },
  },
  {
    label: '报道城市',
    field: 'city',
    component: 'Input',
  },
  {
    label: '是否全职',
    field: 'jobType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"yn",
        type: "radio"
     },
  },
  {
    label: '是否需要住宿',
    field: 'accommodation',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"yn",
        type: "radio"
     },
  },
  {
    label: '是否需要购买社保',
    field: 'socialSecurity',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"yn",
        type: "radio"
     },
  },
  {
    label: '报道站点',
    field: 'siteName',
    component: 'Input',
  },
  {
    label: '期望区域地址',
    field: 'expectRegion',
    component: 'JAreaLinkage',
    componentProps: {
      saveCode: 'region',
    },
  },
  {
    label: '推广人',
    field: 'reference',
    component: 'Input',
  },
  {
    label: '数据来源',
    field: 'source',
    component: 'Input',
  },
  {
    label: '面试状态',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"yn",
        type: "radio"
     },
  },
  {
    label: '通过状态',
    field: 'passStatus',
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
  name: {title: '姓名',order: 0,view: 'text', type: 'string',},
  sex: {title: '性别',order: 1,view: 'number', type: 'number',dictCode: 'sex',},
  age: {title: '年龄',order: 2,view: 'number', type: 'number',},
  phone: {title: '手机号码',order: 3,view: 'text', type: 'string',},
  city: {title: '报道城市',order: 4,view: 'text', type: 'string',},
  jobType: {title: '是否全职',order: 5,view: 'number', type: 'number',dictCode: 'yn',},
  accommodation: {title: '是否需要住宿',order: 6,view: 'number', type: 'number',dictCode: 'yn',},
  socialSecurity: {title: '是否需要购买社保',order: 7,view: 'number', type: 'number',dictCode: 'yn',},
  siteName: {title: '报道站点',order: 8,view: 'text', type: 'string',},
  expectRegion: {title: '期望区域地址',order: 9,view: 'pca', type: 'string',},
  reference: {title: '推广人',order: 10,view: 'text', type: 'string',},
  source: {title: '数据来源',order: 11,view: 'text', type: 'string',},
  status: {title: '面试状态',order: 12,view: 'number', type: 'number',dictCode: 'yn',},
  passStatus: {title: '通过状态',order: 13,view: 'number', type: 'number',dictCode: 'yn',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
