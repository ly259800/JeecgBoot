import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '问题ID',
    align:"center",
    dataIndex: 'quesId'
   },
   {
    title: '问题选项',
    align:"center",
    dataIndex: 'quesOption'
   },
   {
    title: '答复',
    align:"center",
    dataIndex: 'answer'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '问题ID',
    field: 'quesId',
    component: 'Input',
  },
  {
    label: '问题选项',
    field: 'quesOption',
    component: 'Input',
  },
  {
    label: '答复',
    field: 'answer',
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
  quesId: {title: '问题ID',order: 0,view: 'text', type: 'string',},
  quesOption: {title: '问题选项',order: 1,view: 'text', type: 'string',},
  answer: {title: '答复',order: 2,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}