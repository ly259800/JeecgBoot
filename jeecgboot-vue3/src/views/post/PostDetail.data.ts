import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '岗位ID',
    align:"center",
    dataIndex: 'postId'
   },
   {
    title: '岗位详情',
    align:"center",
    dataIndex: 'postDetail',
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '岗位详情',
    field: 'postDetail',
    component: 'JEditor',
  },
  {
    label: '岗位ID',
    field: 'postId',
    component: 'Input',
    show: false
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
  postId: {title: '岗位ID',order: 0,view: 'text', type: 'string',},
  postDetail: {title: '岗位详情',order: 1,view: 'umeditor', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
