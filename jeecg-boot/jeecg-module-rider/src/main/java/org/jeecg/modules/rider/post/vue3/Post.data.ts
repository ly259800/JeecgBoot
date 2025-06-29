import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '岗位类型编码',
    align:"center",
    dataIndex: 'categoryCode',
    customRender:({text}) => {
       return  render.renderCategoryTree(text,'')
   },
   },
   {
    title: '岗位类型名称',
    align:"center",
    dataIndex: 'categoryName'
   },
   {
    title: '标签',
    align:"center",
    dataIndex: 'tag'
   },
   {
    title: '薪资范围',
    align:"center",
    dataIndex: 'salaryRange'
   },
   {
    title: '岗位名称',
    align:"center",
    dataIndex: 'postName'
   },
   {
    title: '时薪',
    align:"center",
    dataIndex: 'hourlyWage'
   },
   {
    title: '详细地址',
    align:"center",
    dataIndex: 'address'
   },
   {
    title: '经度',
    align:"center",
    dataIndex: 'longitude'
   },
   {
    title: '纬度',
    align:"center",
    dataIndex: 'latitude'
   },
   {
    title: '佣金',
    align:"center",
    dataIndex: 'commission'
   },
   {
    title: '城市',
    align:"center",
    dataIndex: 'city'
   },
   {
    title: '福利',
    align:"center",
    dataIndex: 'benefit'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '岗位类型编码',
    field: 'categoryCode',
    component: 'JCategorySelect',
    componentProps:{
       pcode:"", //TODO back和事件未添加，暂时有问题
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入岗位类型编码!'},
          ];
     },
  },
  {
    label: '岗位类型名称',
    field: 'categoryName',
    component: 'Input',
  },
  {
    label: '标签',
    field: 'tag',
    component: 'Input',
  },
  {
    label: '薪资范围',
    field: 'salaryRange',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入薪资范围!'},
          ];
     },
  },
  {
    label: '岗位名称',
    field: 'postName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入岗位名称!'},
          ];
     },
  },
  {
    label: '时薪',
    field: 'hourlyWage',
    component: 'Input',
  },
  {
    label: '详细地址',
    field: 'address',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入详细地址!'},
          ];
     },
  },
  {
    label: '经度',
    field: 'longitude',
    component: 'Input',
  },
  {
    label: '纬度',
    field: 'latitude',
    component: 'Input',
  },
  {
    label: '佣金',
    field: 'commission',
    component: 'InputNumber',
  },
  {
    label: '城市',
    field: 'city',
    component: 'Input',
  },
  {
    label: '福利',
    field: 'benefit',
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
  categoryCode: {title: '岗位类型编码',order: 0,view: 'cat_tree', type: 'string',pcode: '',},
  categoryName: {title: '岗位类型名称',order: 1,view: 'text', type: 'string',},
  tag: {title: '标签',order: 2,view: 'text', type: 'string',},
  salaryRange: {title: '薪资范围',order: 3,view: 'text', type: 'string',},
  postName: {title: '岗位名称',order: 4,view: 'text', type: 'string',},
  hourlyWage: {title: '时薪',order: 5,view: 'text', type: 'string',},
  address: {title: '详细地址',order: 6,view: 'text', type: 'string',},
  longitude: {title: '经度',order: 7,view: 'text', type: 'string',},
  latitude: {title: '纬度',order: 8,view: 'text', type: 'string',},
  commission: {title: '佣金',order: 9,view: 'number', type: 'number',},
  city: {title: '城市',order: 10,view: 'text', type: 'string',},
  benefit: {title: '福利',order: 11,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}