import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '客户ID',
    align:"center",
    dataIndex: 'customerId'
   },
   {
    title: '商户订单号',
    align:"center",
    dataIndex: 'outTradeNo'
   },
   {
    title: '订单状态',
    align:"center",
    dataIndex: 'orderState_dictText'
   },
   {
    title: '订单类型',
    align:"center",
    dataIndex: 'orderType_dictText'
   },
   {
    title: '订单总金额',
    align:"center",
    dataIndex: 'totalAmount'
   },
   {
    title: '实际支付金额',
    align:"center",
    dataIndex: 'actualAmount'
   },
   {
    title: '商品描述',
    align:"center",
    dataIndex: 'description'
   },
   {
    title: '支付人ID',
    align:"center",
    dataIndex: 'payId'
   },
   {
    title: '支付人名称',
    align:"center",
    dataIndex: 'payer'
   },
   {
    title: '支付完成时间',
    align:"center",
    dataIndex: 'successTime'
   },
   {
    title: '取消人ID',
    align:"center",
    dataIndex: 'cancelId'
   },
   {
    title: '取消人',
    align:"center",
    dataIndex: 'canceler'
   },
   {
    title: '取消时间',
    align:"center",
    dataIndex: 'cancelTime'
   },
   {
    title: '支付方式',
    align:"center",
    dataIndex: 'paymentMethod_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '客户ID',
    field: 'customerId',
    component: 'Input',
  },
  {
    label: '商户订单号',
    field: 'outTradeNo',
    component: 'Input',
  },
  {
    label: '订单状态',
    field: 'orderState',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"order_state"
     },
  },
  {
    label: '订单类型',
    field: 'orderType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"order_type"
     },
  },
  {
    label: '订单总金额',
    field: 'totalAmount',
    component: 'Input',
  },
  {
    label: '实际支付金额',
    field: 'actualAmount',
    component: 'Input',
  },
  {
    label: '商品描述',
    field: 'description',
    component: 'Input',
  },
  {
    label: '支付人ID',
    field: 'payId',
    component: 'Input',
  },
  {
    label: '支付人名称',
    field: 'payer',
    component: 'Input',
  },
  {
    label: '支付完成时间',
    field: 'successTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: '取消人ID',
    field: 'cancelId',
    component: 'Input',
  },
  {
    label: '取消人',
    field: 'canceler',
    component: 'Input',
  },
  {
    label: '取消时间',
    field: 'cancelTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: '支付方式',
    field: 'paymentMethod',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
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
  customerId: {title: '客户ID',order: 0,view: 'text', type: 'string',},
  outTradeNo: {title: '商户订单号',order: 1,view: 'text', type: 'string',},
  orderState: {title: '订单状态',order: 2,view: 'number', type: 'number',dictCode: 'order_state',},
  orderType: {title: '订单类型',order: 3,view: 'number', type: 'number',dictCode: 'order_type',},
  totalAmount: {title: '订单总金额',order: 4,view: 'text', type: 'string',},
  actualAmount: {title: '实际支付金额',order: 5,view: 'text', type: 'string',},
  description: {title: '商品描述',order: 6,view: 'text', type: 'string',},
  payId: {title: '支付人ID',order: 7,view: 'text', type: 'string',},
  payer: {title: '支付人名称',order: 8,view: 'text', type: 'string',},
  successTime: {title: '支付完成时间',order: 9,view: 'datetime', type: 'string',},
  cancelId: {title: '取消人ID',order: 10,view: 'text', type: 'string',},
  canceler: {title: '取消人',order: 11,view: 'text', type: 'string',},
  cancelTime: {title: '取消时间',order: 12,view: 'datetime', type: 'string',},
  paymentMethod: {title: '支付方式',order: 13,view: 'number', type: 'number',dictCode: '',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}