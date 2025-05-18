import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import {JVxeTypes,JVxeColumn} from '/@/components/jeecg/JVxeTable/types'
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
    dataIndex: 'quesType_dictText'
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
    field: 'quesType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"ques_type"
     },
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
//子表单数据
//子表表格配置
export const aiOptionColumns: JVxeColumn[] = [
    {
      title: '问题ID',
      key: 'quesId',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '问题选项',
      key: 'quesOption',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '答复',
      key: 'answer',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
  ]


// 高级查询数据
export const superQuerySchema = {
  description: {title: '描述',order: 0,view: 'text', type: 'string',},
  quesType: {title: '类型',order: 1,view: 'number', type: 'number',dictCode: 'ques_type',},
  sort: {title: '顺序',order: 2,view: 'number', type: 'number',},
  //子表高级查询
  aiOption: {
    title: '选项',
    view: 'table',
    fields: {
        quesId: {title: '问题ID',order: 0,view: 'text', type: 'string',},
        quesOption: {title: '问题选项',order: 1,view: 'text', type: 'string',},
        answer: {title: '答复',order: 2,view: 'text', type: 'string',},
    }
  },
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
// 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}