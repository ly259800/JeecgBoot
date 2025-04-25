import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '推广人ID',
    align:"center",
    dataIndex: 'customerId'
   },
   {
    title: '推广人手机号',
    align:"center",
    dataIndex: 'customerPhone'
   },
   {
    title: '面试人ID',
    align:"center",
    dataIndex: 'interviewId'
   },
   {
    title: '面试人手机号',
    align:"center",
    dataIndex: 'interviewPhone'
   },
   {
    title: '面试人名称',
    align:"center",
    dataIndex: 'interviewName'
   },
   {
    title: '佣金',
    align:"center",
    dataIndex: 'commission'
   },
   {
    title: '审核状态',
    align:"center",
    dataIndex: 'auditStatus',
     customRender: ({ text }) => {
       return render.renderDict(text, 'yn');
     },
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "推广人手机号",
    field: 'customerPhone',
    component: 'Input',
    //colProps: {span: 6},
  },
  {
    label: "审核状态",
    field: 'auditStatus',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode:"yn"
    },
  },



];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '推广人ID',
    field: 'customerId',
    component: 'Input',
  },
  {
    label: '推广人手机',
    field: 'customerPhone',
    component: 'Input',
  },
  {
    label: '面试人ID',
    field: 'interviewId',
    component: 'Input',
  },
  {
    label: '面试人手机号',
    field: 'interviewPhone',
    component: 'Input',
  },
  {
    label: '面试人名称',
    field: 'interviewName',
    component: 'Input',
  },
  {
    label: '佣金',
    field: 'commission',
    component: 'InputNumber',
  },
  {
    label: '审核状态',
    field: 'auditStatus',
    component: 'InputNumber',
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
  customerId: {title: '推广人ID',order: 0,view: 'text', type: 'string',},
  customerPhone: {title: '推广人手机',order: 1,view: 'text', type: 'string',},
  interviewId: {title: '面试人ID',order: 2,view: 'text', type: 'string',},
  interviewPhone: {title: '面试人手机号',order: 3,view: 'text', type: 'string',},
  interviewName: {title: '面试人名称',order: 4,view: 'text', type: 'string',},
  commission: {title: '佣金',order: 5,view: 'number', type: 'number',},
  auditStatus: {title: '审核状态',order: 6,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
