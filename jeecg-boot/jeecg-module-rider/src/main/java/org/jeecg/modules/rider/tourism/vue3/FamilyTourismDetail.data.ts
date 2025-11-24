import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '头部图片',
    align:"center",
    dataIndex: 'image'
   },
   {
    title: '视频',
    align:"center",
    dataIndex: 'video'
   },
   {
    title: '路线图片',
    align:"center",
    dataIndex: 'routePic'
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
    label: '头部图片',
    field: 'image',
    component: 'Input',
  },
  {
    label: '视频',
    field: 'video',
    component: 'Input',
  },
  {
    label: '路线图片',
    field: 'routePic',
    component: 'Input',
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
  image: {title: '头部图片',order: 0,view: 'text', type: 'string',},
  video: {title: '视频',order: 1,view: 'text', type: 'string',},
  routePic: {title: '路线图片',order: 2,view: 'text', type: 'string',},
  memo: {title: '备注',order: 3,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}