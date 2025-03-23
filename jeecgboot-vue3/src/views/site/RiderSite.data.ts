import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '城市',
    align:"center",
    dataIndex: 'city'
   },
   {
    title: '区域',
    align:"center",
    dataIndex: 'region'
   },
   {
    title: '名称',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '缺口',
    align:"center",
    dataIndex: 'gap'
   },
  {
    title: '佣金',
    align:"center",
    dataIndex: 'commission'
  },
  {
    title: '利润',
    align:"center",
    dataIndex: 'profit'
  },
  {
    title: '备注',
    align:"center",
    dataIndex: 'memo'
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '城市',
    field: 'city',
    component: 'Input',
  },
  {
    label: '区域',
    field: 'region',
    component: 'Input',
  },
  {
    label: '名称',
    field: 'name',
    component: 'Input',
  },
  {
    label: '缺口',
    field: 'gap',
    component: 'InputNumber',
  },
  {
    label: '佣金',
    field: 'commission',
    component: 'InputNumber',
  },
  {
    label: '利润',
    field: 'profit',
    component: 'InputNumber',
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
  city: {title: '城市',order: 0,view: 'text', type: 'string',},
  region: {title: '区域',order: 1,view: 'text', type: 'string',},
  name: {title: '名称',order: 2,view: 'text', type: 'string',},
  gap: {title: '缺口',order: 3,view: 'text', type: 'number',},
  commission: {title: '佣金',order: 4,view: 'number', type: 'number',},
  profit: {title: '利润',order: 5,view: 'number', type: 'number',},
  memo: {title: '备注',order: 6,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
