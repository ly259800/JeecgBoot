import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '描述',
    align:"center",
    dataIndex: 'description'
   },
   {
    title: '类型',
    align:"center",
    dataIndex: 'type'
   },
   {
    title: '顺序',
    align:"center",
    dataIndex: 'sort'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '描述',
    field: 'description',
    component: 'Input',
  },
  {
    label: '类型',
    field: 'type',
    component: 'InputNumber',
  },
  {
    label: '顺序',
    field: 'sort',
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
  description: {title: '描述',order: 0,view: 'text', type: 'string',},
  type: {title: '类型',order: 1,view: 'number', type: 'number',},
  sort: {title: '顺序',order: 2,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}