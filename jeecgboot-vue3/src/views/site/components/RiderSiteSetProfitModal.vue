<template>
  <BasicModal v-bind="$attrs" @register="register" title="设置利润" @visible-change="handleVisibleChange">
    <div class="pt-3px pr-3px">
      <BasicForm @register="registerForm" :model="model" />
    </div>
  </BasicModal>
</template>
<script lang="ts">
import { defineComponent, ref, nextTick } from 'vue';
import { BasicModal, useModalInner } from '/@/components/Modal';
import { BasicForm, FormSchema, useForm } from '/@/components/Form/index';

const schemas: FormSchema[] = [
  {
    field: 'profit',
    component: 'Input',
    label: '利润',
    colProps: {
      span: 24,
    },
  },
];
export default defineComponent({
  components: { BasicModal, BasicForm },
  props: {
    userData: { type: Object },
  },
  setup(props) {
    const modelRef = ref({});
    const [
      registerForm,
      {
        // setFieldsValue,
        // setProps
      },
    ] = useForm({
      labelWidth: 120,
      schemas,
      showActionButtonGroup: false,
      actionColOptions: {
        span: 24,
      },
    });

    const [register] = useModalInner((data) => {
      data && onDataReceive(data);
    });

    function onDataReceive(data) {
      modelRef.value = { profit: data.data };
    }

    function handleVisibleChange(v) {
      v && props.userData && nextTick(() => onDataReceive(props.userData));
    }

    return { register, schemas, registerForm, model: modelRef, handleVisibleChange };
  },
});
</script>
