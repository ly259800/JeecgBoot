import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '二维码链接',
    align:"center",
    dataIndex: 'url'
   },
   {
    title: '二维码传参',
    align:"center",
    dataIndex: 'scene'
   },
   {
    title: '绑定推广人',
    align:"center",
    dataIndex: 'customerId'
   },
   {
    title: '手机号',
    align:"center",
    dataIndex: 'phone'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '二维码链接',
    field: 'url',
    component: 'Input',
  },
  {
    label: '二维码传参',
    field: 'scene',
    component: 'Input',
  },
  {
    label: '绑定推广人',
    field: 'customerId',
    component: 'Input',
  },
  {
    label: '手机号',
    field: 'phone',
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
  url: {title: '二维码链接',order: 0,view: 'text', type: 'string',},
  scene: {title: '二维码传参',order: 1,view: 'text', type: 'string',},
  customerId: {title: '绑定推广人',order: 2,view: 'text', type: 'string',},
  phone: {title: '手机号',order: 3,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}