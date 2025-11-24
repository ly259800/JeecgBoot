import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '标题',
    align:"center",
    dataIndex: 'title'
   },
   {
    title: '封面',
    align:"center",
    dataIndex: 'cover',
    customRender:render.renderImage,
   },
   {
    title: '时间地点',
    align:"center",
    dataIndex: 'address'
   },
   {
    title: '标签',
    align:"center",
    dataIndex: 'tag'
   },
   {
    title: '普通价格',
    align:"center",
    dataIndex: 'unRegistPrice'
   },
   {
    title: '会员价格',
    align:"center",
    dataIndex: 'price'
   },
   {
    title: '佣金',
    align:"center",
    dataIndex: 'commission'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '标题',
    field: 'title',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入标题!'},
          ];
     },
  },
  {
    label: '封面',
    field: 'cover',
     component: 'JImageUpload',
     componentProps:{
        fileMax: 0
      },
  },
  {
    label: '时间地点',
    field: 'address',
    component: 'Input',
  },
  {
    label: '标签',
    field: 'tag',
    component: 'Input',
  },
  {
    label: '普通价格',
    field: 'unRegistPrice',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: false},
                 { pattern: /^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
          ];
     },
  },
  {
    label: '会员价格',
    field: 'price',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: false},
                 { pattern: /^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
          ];
     },
  },
  {
    label: '佣金',
    field: 'commission',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: false},
                 { pattern: /^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
          ];
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
  title: {title: '标题',order: 0,view: 'text', type: 'string',},
  cover: {title: '封面',order: 1,view: 'image', type: 'string',},
  address: {title: '时间地点',order: 2,view: 'text', type: 'string',},
  tag: {title: '标签',order: 3,view: 'text', type: 'string',},
  unRegistPrice: {title: '普通价格',order: 4,view: 'number', type: 'number',},
  price: {title: '会员价格',order: 5,view: 'number', type: 'number',},
  commission: {title: '佣金',order: 6,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}