package com.izhbg.typz.base.util;
public final class Constants {
	
	public final static String APP_DEFAULT="5z96kevmj0lm0nf7vmj1";
	/*****************restful 状态****************************/
	public final static int RESULT_CODE_SUCCESS = 0;//操作成功
	public final static int RESULT_CODE_ERROR = 1;//服务端异常
	public final static int RESULT_CODE_FAILED = 2;//请求参数为空
	public final static int RESULT_CODE_UPDATE = 2;
	
	//标志状态位
	public final static Integer STATUS_VALID = 1; //有效
	public final static Integer STATUS_NO_VALID  = 2; //无效
	
	public final static Integer DELETE_STATE = 1; //  删除
	public final static Integer UN_DELETE_STATE = 2; //  没删除
	
	//审核状态
	public final static Integer VERIFY_WAIT = 0;//待审核
	public final static Integer VERIFY_ONRPASS = 1;//审核通过
	public final static Integer VERIFY_ERRORPASS = 2;//审核未通过
	
	//附件类型
	public final static Integer ATTACHE_INDEX = 0;//缩略图
	public final static Integer ATTACHE_NORMAL = 1;//展示图
	
	//价格状态
	public final static Integer PRICE_GUIDE = 0;//指导价格
	public final static Integer PRICE_ORIGINAL = 1;//原价
	public final static Integer PRICE_DSCUT =2;//优惠价格
	public final static Integer PRICE_PLAT =3;//平台预留 利润
	public final static Integer PRICE_COST =4;//推广价格
	//收货地址默认
	public final static Integer ADDRESS_DEFAULT =1;//默认收货地址
	public final static Integer ADDRESS_NOT_DEFAULT =0;//非默认收货地址
	
	//Redis cache 
	public final static String SMS_CODE_CODEID ="sms_code_codeId";
	public final static int SMS_TIMEOUT=120;//短信验证码失效时间
	
	public final static String LOGIN_TOKEN="login_token";
	
	//messages
	public final static String SYSTEMMSG_EMPTYFILED="参数为空";
	public final static String SYSTEMMSG_SUCCESS="操作成功";
	public final static String SYSTEMMSG_FAILED="操作失败";
	public final static String SYSTEMMSG_SMSCODE_FAILED="验证码错误";
	
	//店铺类型
	public final static Integer STORE_DIANPU=1;
	public final static Integer STORE_JINXIAOSHANG=2;
	
	
	
	
}