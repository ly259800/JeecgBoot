import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '分类名称',
    align:"center",
    dataIndex: 'classificationName'
   },
   {
    title: '名称',
    align:"center",
    dataIndex: 'name'
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
   },
   {
    title: '封面',
    align:"center",
    dataIndex: 'cover',
    customRender:render.renderImage,
   },
   {
    title: '视频链接',
    align:"center",
    dataIndex: 'url',
   },
   {
    title: '排序',
    align:"center",
    dataIndex: 'sort'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "分类",
      field: 'classification',
      component: 'JCategorySelect',
      componentProps:{
          pcode:"",//back和事件未添加，暂时有问题
      },
      //colProps: {span: 6},
 	},
  {
    label: "名称",
    field: "name",
    component: 'JInput',
  },
	{
      label: "付费类型",
      field: 'payType',
      component: 'JDictSelectTag',
      componentProps:{
          dictCode:"pay_type"
      },
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '分类',
    field: 'classification',
    component: 'JCategorySelect',
    componentProps:{
       pcode:"", //TODO back和事件未添加，暂时有问题
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入分类!'},
          ];
     },
  },
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
  /*{
    label: '付费类型',
    field: 'payType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"pay_type"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入付费类型!'},
          ];
     },
  },*/
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
    label: '封面',
    field: 'cover',
     component: 'JImageUpload',
     componentProps:{
        fileMax:1
      },
  },
  {
    label: '视频链接',
    field: 'url',
    component: 'JUpload',
    componentProps:{
       maxCount:1
     },
  },
  {
    label: '排序',
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

// 高级查询数据
export const superQuerySchema = {
  classification: {title: '分类',order: 0,view: 'cat_tree', type: 'string',pcode: '',},
  classificationName: {title: '分类名称',order: 1,view: 'text', type: 'string',},
  name: {title: '名称',order: 2,view: 'text', type: 'string',},
  payType: {title: '付费类型',order: 3,view: 'number', type: 'number',dictCode: 'pay_type',},
  price: {title: '付费价格',order: 4,view: 'number', type: 'number',},
  cover: {title: '封面',order: 5,view: 'image', type: 'string',},
  url: {title: '视频链接',order: 6,view: 'file', type: 'string',},
  sort: {title: '排序',order: 7,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
