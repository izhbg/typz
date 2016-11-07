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
	public final static Integer ATTACHE_INDEX = 1;//缩略图
	public final static Integer ATTACHE_NORMAL = 0;//展示图
	
	//价格状态
	public final static Integer GOODS_PRICE_GUIDE = 0;//指导价格
	public final static Integer GOODS_PRICE_ORIGINAL = 1;//原价
	public final static Integer GOODS_PRICE_DSCUT =2;//优惠价格
	public final static Integer GOODS_PRICE_PLAT =3;//平台预留 利润
	public final static Integer GOODS_PRICE_COST =4;//推广价格
	
	public final static Integer GOODS_COMMENT_0 = 0;//差评
	public final static Integer GOODS_COMMENT_1 = 1;//中评
	public final static Integer GOODS_COMMENT_2 = 2;//好评
	
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
	public final static Integer STORE_CHANGJIA=1;//厂家
	public final static Integer STORE_JIAMENSHANG=2;//加盟商
	
	public static final int STORE_ITEM_WAITE = 0;//店铺待审核
	public static final int STORE_ITEM_PASS=1;//店铺审核通过
	public static final int STORE_ITEM_NO_PASS=2;//店铺审核未通过
	
	public static final int STORE_WIDTHDRAW_WAIT_1 = 1;//待提现
	public static final int STORE_WIDTHDRAW_ED_2 = 2;//已提现
	
	//订单状态
	public static final int STORE_ORDER_0 = 0;//已下单    待付款
	public static final int STORE_ORDER_1 = 1;//已支付/到付  待发货
	public static final int STORE_ORDER_2 = 2;//待收货   已发货
	public static final int STORE_ORDER_3 = 3;//已收货   完成 
	
	public final static Integer GOODS_VERSION_DEFAULT = -1;//默认版本
	
	public final static Integer GOODS_STATUS_SALE = 1;//上架
	public final static Integer GOODS_STATUS_UN_SALE = -1;//下架
	
	public final static Integer GOODS_IS_FACE = 1;//是否首图
	
	
}