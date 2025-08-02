import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
import {getAllSiteList} from "@/views/interview/RiderInterview.api";
import {h} from "vue";
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '数据来源',
    align:"center",
    dataIndex: 'source'
  },
  {
    title: '入职状态',
    align:"center",
    dataIndex: 'passStatus',
    customRender: ({ text }) => {
      return render.renderDict(text, 'yn');
    },
  },
  {
    title: '结算状态',
    align:"center",
    dataIndex: 'settleStatus',
    customRender: ({ text }) => {
      return render.renderDict(text, 'yn');
    },
  },
  {
    title: '推广人手机号',
    align:"center",
    dataIndex: 'referencePhone'
  },
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
    title: '手机号码',
    align:"center",
    dataIndex: 'phone'
   },{
    title: '岗位',
    align:"center",
    dataIndex: 'siteName'
  },{
    title: '付费价格',
    align:"center",
    dataIndex: 'price'
  },
  {
    title: '是否支付',
    align:"center",
    dataIndex: 'payStatus',
    customRender: ({ text }) => {
      return render.renderDict(text, 'yn');
    },
  },
  {
    title: '是否签署',
    align:"center",
    dataIndex: 'signStatus',
    customRender: ({ text }) => {
      return render.renderDict(text, 'yn');
    },
  },
  {
    title: '是否培训',
    align:"center",
    dataIndex: 'trainingStatus',
    customRender: ({ text }) => {
      return render.renderDict(text, 'yn');
    },
  },
  {
    title: '是否岗位确认',
    align:"center",
    dataIndex: 'confirmStatus',
    customRender: ({ text }) => {
      return render.renderDict(text, 'yn');
    },
  },{
    title: '招聘老师',
    align:"center",
    dataIndex: 'trainingTeacher'
  },{
    title: '分配区域',
    align:"center",
    dataIndex: 'expectRegion'
  },{
    title: '酒店名称',
    align:"center",
    dataIndex: 'jobPosition'
  },{
    title: '安置单',
    align:"center",
    dataIndex: 'signTaskUrl',
    customRender: ({ text }) => {
      if (!text) {
        return '';
      }
      return h('a', {
        href: text,
        target: '_blank',
        rel: 'noopener noreferrer'
      }, '查看安置单');
    },
  },{
    title: '视频',
    align:"center",
    dataIndex: 'videoUrl',
    customRender: ({ text }) => {
      if (!text) {
        return '';
      }
      return h('a', {
        href: text,
        target: '_blank',
        rel: 'noopener noreferrer'
      }, '查看视频');
    },
  },
  {
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
    label: "推广人手机号",
    field: 'referencePhone',
    component: 'Input',
    //colProps: {span: 6},
  },
	{
      label: "处理状态",
      field: 'status',
      component: 'JDictSelectTag',
      componentProps:{
        dictCode:"yn"
      },
      //colProps: {span: 6},
 	},
	{
      label: "入职状态",
      field: 'passStatus',
      component: 'JDictSelectTag',
      componentProps:{
        dictCode:"yn"
      },
      //colProps: {span: 6},
 	},
  {
    label: "结算状态",
    field: 'settleStatus',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode:"yn"
    },
    //colProps: {span: 6},
  },
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '分配区域',
    field: 'expectRegion',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
        { required: true, message: '请输入分配区域!'},
      ];
    },
  },
  {
    label: '酒店名称',
    field: 'jobPosition',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
        { required: true, message: '请输入酒店名称!'},
      ];
    },
  },
  {
    label: '视频文件',
    field: 'videoUrl',
    component: 'JUpload',
    componentProps:{
      maxCount:1
    },
  },
  {
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
