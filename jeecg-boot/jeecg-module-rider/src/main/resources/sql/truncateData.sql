truncate rider_commission;
truncate rider_customer;
truncate rider_interview;
truncate rider_pay_order;
truncate rider_qrcode;
truncate rider_user_order;

delete from  sys_user where username not in ('ceshi','jeecg','admin')