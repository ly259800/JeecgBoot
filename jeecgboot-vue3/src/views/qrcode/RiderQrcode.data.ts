import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
import {getAllRolesListNoByTenant} from "@/views/system/user/user.api";
import {getAllCustomerList} from "@/views/qrcode/RiderQrcode.api";
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '二维码链接',
    align:"center",
    dataIndex: 'url',
     customRender: ({ text }) => {
       if(!text){
         return text;
       }
       return render.renderImage({text});
     },
   },
   {
    title: '二维码传参',
    align:"center",
    dataIndex: 'scene'
   },
  {
    title: '二维码跳转页面',
    align:"center",
    dataIndex: 'page'
  },
   {
    title: '推广人ID',
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
    label: '二维码跳转页面',
    field: 'page',
    component: 'Input',
  },{
    label: '小程序版本',
    field: 'envVersion',
    component: 'Input',
  },
  {
    label: '推广人',
    field: 'customerId',
    component: 'ApiSelect',
    componentProps: {
      //mode: 'multiple',
      api: getAllCustomerList,
      numberToString: true,
      labelField: 'phone',
      valueField: 'id',
      immediate: false,
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
  url: {title: '二维码链接',order: 0,view: 'text', type: 'string',},
  scene: {title: '二维码传参',order: 1,view: 'text', type: 'string',},
  customerId: {title: '推广人ID',order: 2,view: 'text', type: 'string',},
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
