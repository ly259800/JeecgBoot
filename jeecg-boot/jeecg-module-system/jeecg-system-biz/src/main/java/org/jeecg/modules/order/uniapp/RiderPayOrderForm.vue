<template>
    <view>
        <!--标题和返回-->
		<cu-custom :bgColor="NavBarColor" isBack :backRouterName="backRouteName">
			<block slot="backText">返回</block>
			<block slot="content">支付订单</block>
		</cu-custom>
		 <!--表单区域-->
		<view>
			<form>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">应用ID：</text></view>
                  <input  placeholder="请输入应用ID" v-model="model.appid"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">商户号：</text></view>
                  <input  placeholder="请输入商户号" v-model="model.mchid"/>
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
                  <view class="title"><text space="ensp">商品描述：</text></view>
                  <input  placeholder="请输入商品描述" v-model="model.description"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">预支付交易会话标识：</text></view>
                  <input  placeholder="请输入预支付交易会话标识" v-model="model.prePayId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">微信支付订单号：</text></view>
                  <input  placeholder="请输入微信支付订单号" v-model="model.transactionId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">交易类型：</text></view>
                  <input type="number" placeholder="请输入交易类型" v-model="model.tradeType"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">交易状态：</text></view>
                  <input type="number" placeholder="请输入交易状态" v-model="model.tradeState"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">用户唯一标识：</text></view>
                  <input  placeholder="请输入用户唯一标识" v-model="model.openid"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">订单总金额：</text></view>
                  <input type="number" placeholder="请输入订单总金额" v-model="model.totalAmount"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">货币类型：</text></view>
                  <input  placeholder="请输入货币类型" v-model="model.currency"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">用户支付金额：</text></view>
                  <input type="number" placeholder="请输入用户支付金额" v-model="model.payAmount"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">用户支付币种类型：</text></view>
                  <input  placeholder="请输入用户支付币种类型" v-model="model.payCurrency"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">银行类型：</text></view>
                  <input  placeholder="请输入银行类型" v-model="model.bankType"/>
                </view>
              </view>
              <my-date label="支付完成时间：" v-model="model.successTime" placeholder="请输入支付完成时间"></my-date>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">关闭状态：</text></view>
                  <input  placeholder="请输入关闭状态" v-model="model.closeState"/>
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
        name: "RiderPayOrderForm",
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
                  queryById: "/order/riderPayOrder/queryById",
                  add: "/order/riderPayOrder/add",
                  edit: "/order/riderPayOrder/edit",
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
