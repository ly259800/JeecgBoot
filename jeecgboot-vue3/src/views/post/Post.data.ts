import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
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
    title: '联系人',
    align:"center",
    dataIndex: 'contacts'
  },
  {
    title: '联系电话',
    align:"center",
    dataIndex: 'contactPhone'
  },
   {
    title: '详细地址',
    align:"center",
    dataIndex: 'address'
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
  {
    title: '付费类型',
    align:"center",
    dataIndex: 'payType',
    customRender: ({ text }) => {
      if (text === 0) {
        return '免费';
      } else if (text === 1) {
        return '付费';
      } else {
        return '';
      }
    },
  },
  {
    title: '付费价格',
    align:"center",
    dataIndex: 'price'
  },{
    title: '是否培训',
    align:"center",
    dataIndex: 'trainingStatus',
    customRender: ({ text }) => {
      return render.renderDict(text, 'yn');
    },
  },
   {
    title: '发布状态',
    align:"center",
    dataIndex: 'publishStatus',
     customRender: ({ text }) => {
       return render.renderDict(text, 'yn');
     },
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '岗位类型ID',
    field: 'categoryId',
    component: 'JCategorySelect',
    componentProps:{
       pcode:"", //TODO back和事件未添加，暂时有问题
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入岗位类型ID!'},
          ];
     },
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
    label: '联系人',
    field: 'contacts',
    component: 'Input',
  },
  {
    label: '联系电话',
    field: 'contactPhone',
    component: 'InputNumber',
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
  {
    label: '付费类型',
    field: 'payType',
    component: 'RadioGroup',
    defaultValue: 1,
    componentProps: ({ formModel }) => {
      return {
        options: [
          { label: '免费', value: 0, key: '0' },
          { label: '付费', value: 1, key: '1' },
        ],
      };
    },
  },
  {
    label: '付费价格',
    field: 'price',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
        { required: false},
        { pattern: /^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
      ];
    },
  },
  {
    field: 'trainingStatus',
    label: '是否培训',
    component: 'RadioButtonGroup',
    defaultValue: '0',
    componentProps: {
      options: [
        { label: '否', value: '0' },
        { label: '是', value: '1' },
      ],
    },
    required: true,
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
  categoryId: {title: '岗位类型ID',order: 0,view: 'cat_tree', type: 'string',pcode: '',},
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
  publishStatus: {title: '发布状态',order: 12,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
