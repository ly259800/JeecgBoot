import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '名称',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '手机号',
    align:"center",
    dataIndex: 'phone'
   },
   {
    title: '头像',
    align:"center",
    dataIndex: 'avatar'
   },
   {
    title: '身份(1-游客，2-骑手，3-合伙人)',
    align:"center",
    dataIndex: 'identity_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "名称",
      field: 'name',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "手机号",
      field: 'phone',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "身份(1-游客，2-骑手，3-合伙人)",
      field: 'identity',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '名称',
    field: 'name',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入名称!'},
          ];
     },
  },
  {
    label: '手机号',
    field: 'phone',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入手机号!'},
          ];
     },
  },
  {
    label: '头像',
    field: 'avatar',
    component: 'Input',
  },
  {
    label: '身份(1-游客，2-骑手，3-合伙人)',
    field: 'identity',
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
  name: {title: '名称',order: 0,view: 'text', type: 'string',},
  phone: {title: '手机号',order: 1,view: 'text', type: 'string',},
  avatar: {title: '头像',order: 2,view: 'text', type: 'string',},
  identity: {title: '身份(1-游客，2-骑手，3-合伙人)',order: 3,view: 'number', type: 'number',dictCode: '',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}