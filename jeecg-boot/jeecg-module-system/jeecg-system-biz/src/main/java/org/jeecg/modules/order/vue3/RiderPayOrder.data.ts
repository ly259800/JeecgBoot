import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '应用ID',
    align:"center",
    dataIndex: 'appid'
   },
   {
    title: '商户号',
    align:"center",
    dataIndex: 'mchid'
   },
   {
    title: '商户订单号',
    align:"center",
    dataIndex: 'outTradeNo'
   },
   {
    title: '商品描述',
    align:"center",
    dataIndex: 'description'
   },
   {
    title: '预支付交易会话标识',
    align:"center",
    dataIndex: 'prePayId'
   },
   {
    title: '微信支付订单号',
    align:"center",
    dataIndex: 'transactionId'
   },
   {
    title: '交易类型',
    align:"center",
    dataIndex: 'tradeType_dictText'
   },
   {
    title: '交易状态',
    align:"center",
    dataIndex: 'tradeState_dictText'
   },
   {
    title: '用户唯一标识',
    align:"center",
    dataIndex: 'openid'
   },
   {
    title: '订单总金额',
    align:"center",
    dataIndex: 'totalAmount'
   },
   {
    title: '货币类型',
    align:"center",
    dataIndex: 'currency'
   },
   {
    title: '用户支付金额',
    align:"center",
    dataIndex: 'payAmount'
   },
   {
    title: '用户支付币种类型',
    align:"center",
    dataIndex: 'payCurrency'
   },
   {
    title: '银行类型',
    align:"center",
    dataIndex: 'bankType'
   },
   {
    title: '支付完成时间',
    align:"center",
    dataIndex: 'successTime'
   },
   {
    title: '关闭状态',
    align:"center",
    dataIndex: 'closeState_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '应用ID',
    field: 'appid',
    component: 'Input',
  },
  {
    label: '商户号',
    field: 'mchid',
    component: 'Input',
  },
  {
    label: '商户订单号',
    field: 'outTradeNo',
    component: 'Input',
  },
  {
    label: '商品描述',
    field: 'description',
    component: 'Input',
  },
  {
    label: '预支付交易会话标识',
    field: 'prePayId',
    component: 'Input',
  },
  {
    label: '微信支付订单号',
    field: 'transactionId',
    component: 'Input',
  },
  {
    label: '交易类型',
    field: 'tradeType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"trade_type"
     },
  },
  {
    label: '交易状态',
    field: 'tradeState',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"trade_state"
     },
  },
  {
    label: '用户唯一标识',
    field: 'openid',
    component: 'Input',
  },
  {
    label: '订单总金额',
    field: 'totalAmount',
    component: 'InputNumber',
  },
  {
    label: '货币类型',
    field: 'currency',
    component: 'Input',
  },
  {
    label: '用户支付金额',
    field: 'payAmount',
    component: 'InputNumber',
  },
  {
    label: '用户支付币种类型',
    field: 'payCurrency',
    component: 'Input',
  },
  {
    label: '银行类型',
    field: 'bankType',
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
    label: '关闭状态',
    field: 'closeState',
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
  appid: {title: '应用ID',order: 0,view: 'text', type: 'string',},
  mchid: {title: '商户号',order: 1,view: 'text', type: 'string',},
  outTradeNo: {title: '商户订单号',order: 2,view: 'text', type: 'string',},
  description: {title: '商品描述',order: 3,view: 'text', type: 'string',},
  prePayId: {title: '预支付交易会话标识',order: 4,view: 'text', type: 'string',},
  transactionId: {title: '微信支付订单号',order: 5,view: 'text', type: 'string',},
  tradeType: {title: '交易类型',order: 6,view: 'number', type: 'number',dictCode: 'trade_type',},
  tradeState: {title: '交易状态',order: 7,view: 'number', type: 'number',dictCode: 'trade_state',},
  openid: {title: '用户唯一标识',order: 8,view: 'text', type: 'string',},
  totalAmount: {title: '订单总金额',order: 9,view: 'number', type: 'number',},
  currency: {title: '货币类型',order: 10,view: 'text', type: 'string',},
  payAmount: {title: '用户支付金额',order: 11,view: 'number', type: 'number',},
  payCurrency: {title: '用户支付币种类型',order: 12,view: 'text', type: 'string',},
  bankType: {title: '银行类型',order: 13,view: 'text', type: 'string',},
  successTime: {title: '支付完成时间',order: 14,view: 'datetime', type: 'string',},
  closeState: {title: '关闭状态',order: 15,view: 'radio', type: 'string',dictCode: 'yn',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}