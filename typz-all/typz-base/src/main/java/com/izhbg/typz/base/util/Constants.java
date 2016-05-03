package com.izhbg.typz.base.util;
public final class Constants {
	public  final static String  SESSION_USER = "gUser";
	//系统参数表中    不走adminAction里面的判断的特殊 funcids 如 地区控件的action 等
	public final static String POPEDOM_TYPE="popedomType";
	public final static String TABLE_DEFAULT="g_param";
	public final static String APP_DEFAULT="5z96kevmj0lm0nf7vmj1";
	public final static int RESULT_CODE_SUCCESS = 0;
	public final static int RESULT_CODE_ERROR = 1;
	public final static int RESULT_CODE_FAILED = 2;
	public final static int RESULT_CODE_UPDATE = 2;
	//是否叶子节点标识
	public final static String LEFT_MARK_FATHER = "0"; //父节点
	public final static String LEFT_MARK_CHILD  = "1"; //子节点
	//标志状态位
	public final static Integer STATUS_VALID = 1; //有效
	public final static Integer STATUS_NO_VALID  = 2; //无效
	public final static Integer DELETE_STATE = 1; //  删除
	public final static Integer UN_DELETE_STATE = 2; //  没删除
	//在AdminAction中对Session添加funcCodes值，然后按钮标签生成时，从Action中取
	public final static String SESSION_FUNCIDS = "gUserFuncCodes";//验证码
	public static final String System_SessionID = "SystemSessionID_Donne";
	//===================分割线有用/无用============================
	public final static String DATE_FORMAT="yyyy-MM-dd" ;
	public final static String TIME_FORMAT="yyyy-MM-dd HH24:mi:ss" ;
	public final static String BEGIN_TIME_OF_DAY = " 00:00:01"; //开始时间前面空格不可以去除
	public final static String LAST_TIME_OF_DAY = " 23:59:59"; //截止时间前面空格不可以去除
	/**------------------------------字典----------------------------*/
	public final static String TRUE = "true" ;
	public final static String FALSE = "false" ;
	/** 级别  国家 */
	public final static String USER_ORGAN_STATE="1";
	/** 级别  省级 */
	public final static String USER_ORGAN_PROVINCE="2";
	/** 级别  市级 */
	public final static String USER_ORGAN_MUNICIPAL="3";	/** 级别  县级 */
	public final static String USER_ORGAN_COUNTY="4";/** 级别  乡级 */
	public final static String USER_ORGAN_TOWNSHIP="5";	// 设置缓冲区大小
	public static final int BUFFER_SIZE = 8192 ;
	// 匹配正则表达式 yyyy-MM-dd
	public static final String PATTERN_PLUS_LOW = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-])(10|12|0?[13578])([-])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-])(11|0?[469])([-])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-])(0?2)([-])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-])(0?2)([-])(29)$)|(^([3579][26]00)([-])(0?2)([-])(29)$)|(^([1][89][0][48])([-])(0?2)([-])(29)$)|(^([2-9][0-9][0][48])([-])(0?2)([-])(29)$)|(^([1][89][2468][048])([-])(0?2)([-])(29)$)|(^([2-9][0-9][2468][048])([-])(0?2)([-])(29)$)|(^([1][89][13579][26])([-])(0?2)([-])(29)$)|(^([2-9][0-9][13579][26])([-])(0?2)([-])(29)$))";
	// 匹配正则表达式 yyyyMMdd
	public static final String PATTERN_PLUS_NUL = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))(10|12|0?[13578])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))(11|0?[469])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))(0?2)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)(0?2)(29)$)|(^([3579][26]00)(0?2)(29)$)|(^([1][89][0][48])(0?2)(29)$)|(^([2-9][0-9][0][48])(0?2)(29)$)|(^([1][89][2468][048])(0?2)(29)$)|(^([2-9][0-9][2468][048])(0?2)(29)$)|(^([1][89][13579][26])(0?2)(29)$)|(^([2-9][0-9][13579][26])(0?2)(29)$))";
	// 匹配数字
	public static final String NUMRIC_PATTERN = "^[\\d]*[\\.]{0,1}[\\d]*$" ;
	public static final String CHECK_STATU_SURE = "1" ;//确认
	public static final String CHECK_STATU_OTHER = "2" ;//其他
	public static final String CHECK_STATU_NO = "3" ;//未审核
	/** * 1.不存在  2，已存在 */
	public final static String NOT_EXIST = "1";
	public final static String HAVE_EXIST = "2";
	//======================分割================================
	// 操作类型
	public static final int ADD_TYPE = 1; // 新增
	public static final int UPDATE_TYPE = 2; // 修改
	public static final int DEL_TYPE = 3; // 删除";
	public static final int DIR_TYPE = 4; // 查询
	public static final int UPLOAD_TYPE = 5; // 上传
	public static final int DOWNLOAD_TYPE = 6; // 下载
	public static final int PRINT_TYPE = 7; // 打印
	public static final int EXPORT_TYPE = 8; // 导出
	public static final int LOGIN_TYPE = 9; // 登录	
	public static final int EXIT_TYPE = 10; // 退出
	public static final int VERIFY_TYPE = 11; // 审核
	//系统编号(对应综合管理中的系统管理)
	public static final int OPEN = 1;
	public static final int CLOSE = 2;
	//系统操作对象类型
	public static final String SOURCEOBJ = "1"; // 资源
	public static final String COURCEOBJ = "2"; // 课程
	public static final String DIARYOBJ = "3"; // 日志
	public static final String PHOTOOBJ = "4"; // 相册
	public static final String VOTEOBJ = "5"; // 投票
	//===========================================================================================
	public static final Integer resServerId = 3;
	public static final Integer resPicServerId = 3;
	public static final Integer resBisTypeId = 1;  //资源在系统中的业务代码设为1
	public static final Integer coursePicServerId = 3;
	public static final Integer jypdPicServerId = 3;
	public static final Integer trainOrganPicServerId = 4;
	//==============================  考试认证相关参数   ========================================
	//设置考试认证在本系统中的业务类型编码
	public static final Integer jypdBisTypeId = 3;
	public static final Integer jypdAttachSerId = 3;
	//==============================  考券相关参数   ========================================
	//设置考试认证在本系统中的业务类型编码
	public static final Integer examTicketTypeId = 4;
	}