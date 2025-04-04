import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '参数编码',
    align:"center",
    dataIndex: 'paramCode'
   },
   {
    title: '参数值',
    align:"center",
    dataIndex: 'paramValue'
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
    label: '参数编码',
    field: 'paramCode',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入参数编码!'},
          ];
     },
  },
  {
    label: '参数值',
    field: 'paramValue',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入参数值!'},
          ];
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
  paramCode: {title: '参数编码',order: 0,view: 'text', type: 'string',},
  paramValue: {title: '参数值',order: 1,view: 'text', type: 'string',},
  memo: {title: '备注',order: 2,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}