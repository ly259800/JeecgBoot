<template>
    <view>
        <!--标题和返回-->
		<cu-custom :bgColor="NavBarColor" isBack :backRouterName="backRouteName">
			<block slot="backText">返回</block>
			<block slot="content">用户个人订单</block>
		</cu-custom>
		 <!--表单区域-->
		<view>
			<form>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">客户ID：</text></view>
                  <input  placeholder="请输入客户ID" v-model="model.customerId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">商户订单号：</text></view>
                  <input  placeholder="请输入商户订单号" v-model="model.outTradeNo"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">订单状态：</text></view>
                  <input type="number" placeholder="请输入订单状态" v-model="model.orderState"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">订单类型：</text></view>
                  <input type="number" placeholder="请输入订单类型" v-model="model.orderType"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">订单总金额：</text></view>
                  <input  placeholder="请输入订单总金额" v-model="model.totalAmount"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">实际支付金额：</text></view>
                  <input  placeholder="请输入实际支付金额" v-model="model.actualAmount"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">商品描述：</text></view>
                  <input  placeholder="请输入商品描述" v-model="model.description"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">支付人ID：</text></view>
                  <input  placeholder="请输入支付人ID" v-model="model.payId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">支付人名称：</text></view>
                  <input  placeholder="请输入支付人名称" v-model="model.payer"/>
                </view>
              </view>
              <my-date label="支付完成时间：" v-model="model.successTime" placeholder="请输入支付完成时间"></my-date>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">取消人ID：</text></view>
                  <input  placeholder="请输入取消人ID" v-model="model.cancelId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">取消人：</text></view>
                  <input  placeholder="请输入取消人" v-model="model.canceler"/>
                </view>
              </view>
              <my-date label="取消时间：" v-model="model.cancelTime" placeholder="请输入取消时间"></my-date>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">支付方式：</text></view>
                  <input type="number" placeholder="请输入支付方式" v-model="model.paymentMethod"/>
                </view>
              </view>
				<view class="padding">
					<button class="cu-btn block bg-blue margin-tb-sm lg" @click="onSubmit">
						<text v-if="loading" class="cuIcon-loading2 cuIconfont-spin"></text>提交
					</button>
				</view>
			</form>
		</view>
    </view>
</template>

<script>
    import myDate from '@/components/my-componets/my-date.vue'

    export default {
        name: "RiderUserOrderForm",
        components:{ myDate },
        props:{
          formData:{
              type:Object,
              default:()=>{},
              required:false
          }
        },
        data(){
            return {
				CustomBar: this.CustomBar,
				NavBarColor: this.NavBarColor,
				loading:false,
                model: {},
                backRouteName:'index',
                url: {
                  queryById: "/order/riderUserOrder/queryById",
                  add: "/order/riderUserOrder/add",
                  edit: "/order/riderUserOrder/edit",
                },
            }
        },
        created(){
             this.initFormData();
        },
        methods:{
           initFormData(){
               if(this.formData){
                    let dataId = this.formData.dataId;
                    this.$http.get(this.url.queryById,{params:{id:dataId}}).then((res)=>{
                        if(res.data.success){
                            console.log("表单数据",res);
                            this.model = res.data.result;
                        }
                    })
                }
            },
            onSubmit() {
                let myForm = {...this.model};
                this.loading = true;
                let url = myForm.id?this.url.edit:this.url.add;
				this.$http.post(url,myForm).then(res=>{
				   console.log("res",res)
				   this.loading = false
				   this.$Router.push({name:this.backRouteName})
				}).catch(()=>{
					this.loading = false
				});
            }
        }
    }
</script>
