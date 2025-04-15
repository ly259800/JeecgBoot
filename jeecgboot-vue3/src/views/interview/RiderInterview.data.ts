import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
import {getAllSiteList} from "@/views/interview/RiderInterview.api";
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
    title: '工作地点',
    align:"center",
    dataIndex: 'jobPosition_dictText',
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
   },{
    title: '是否需要电动车',
    align:"center",
    dataIndex: 'electricVehicle',
    customRender: ({ text }) => {
      return render.renderDict(text, 'yn');
    },
  },
  {
    title: '面试时间',
    align:"center",
    dataIndex: 'interviewDate'
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
    title: '推广人手机号',
    align:"center",
    dataIndex: 'referencePhone'
  },
   {
    title: '数据来源',
    align:"center",
    dataIndex: 'source'
   },
   {
    title: '处理状态',
    align:"center",
    dataIndex: 'status',
     customRender: ({ text }) => {
       return render.renderDict(text, 'yn');
     },
   },
   {
    title: '入职状态',
    align:"center",
    dataIndex: 'passStatus',
     customRender: ({ text }) => {
       return render.renderDict(text, 'yn');
     },
   },{
    title: '备注',
    align:"center",
    dataIndex: 'memo'
  }
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
    label: "推广人手机号",
    field: 'referencePhone',
    component: 'Input',
    //colProps: {span: 6},
  },
	{
      label: "处理状态",
      field: 'status',
      component: 'JSelectMultiple',
      componentProps:{
        dictCode:"yn"
      },
      //colProps: {span: 6},
 	},
	{
      label: "入职状态",
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
    label: '站点',
    field: 'siteId',
    component: 'ApiSelect',
    componentProps: {
      //mode: 'multiple',
      api: getAllSiteList,
      numberToString: true,
      labelField: 'name',
      valueField: 'id',
      immediate: false,
      showSearch: true, // 启用搜索功能
      filterOption: (input: string, option: any) => {
        // 本地筛选逻辑：匹配 name 字段
        return option.label.toLowerCase().includes(input.toLowerCase());
      },
    },
  },{
    label: '备注',
    field: 'memo',
    component: 'Input',
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
  status: {title: '处理状态',order: 12,view: 'number', type: 'number',dictCode: 'yn',},
  passStatus: {title: '入职状态',order: 13,view: 'number', type: 'number',dictCode: 'yn',},
  referencePhone: {title: '推广人手机号',order: 14,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
