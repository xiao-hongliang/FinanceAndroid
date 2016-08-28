package com.pudding.financeandroid.api;

/**
 * 所有请求url管理
 */
public class BaseApi {

	public static final String BASE_URL = "http://yaolian.budingnet.com/";

	private static BaseApi instance;
	
	public static BaseApi getInstance() {
		if (instance == null) {
			instance = new BaseApi();
		}
		return instance;
	}

	/** 贷款列表 */
	public  String loan_list = "finance/loan/list";
	/** 贷款详情 */
	public  String loan_detail = "finance/loan/detail";
	/** 申请贷款期限列表 */
	public  String loan_stage_list = "finance/loan/stageList";
	/** 申请贷款的提交 */
	public  String loan_apply_submit = "finance/loan/apply/submit";
	/** 用户登录 */
	public  String user_login = "finance/user/login";
	/** 发送手机号短信验证码 */
	public  String send_randCode = "finance/user/sendMessage";
	/** 用户注册 */
	public  String user_register = "finance/user/register";
	/** 发送手机号短信验证码-找回密码 */
	public  String send_randCode_forget_pwd = "finance/user/findMessage";
	/** 用户重置密码 */
	public  String user_forget_pwd = "finance/user/find-password";
	/** 用户修改密码 */
	public  String user_update_pwd = "finance/user/modify-pass";
	/** 理财列表 */
	public  String financing_list = "finance/finance/list";
	/** 理财详情 */
	public  String financing_detail = "finance/finance/detail";
	/** 投资申请提交 */
	public  String financing_apply_send = "finance/finance/apply/submit";
	/** 公司模块的资讯列表 */
	public  String company_list = "finance/info/list";
	/** 公司模块的资讯详情 */
	public  String company_info_detail = "finance/info/detail";
	/** 获取我的理财订单列表信息 */
	public  String my_financing_list = "finance/order/finance-order-list";
	/** 获取我的贷款订单列表信息 */
	public  String my_loan_list = "finance/order/loan-order-list";
	/** 获取我的个人信息 */
	public  String my_user_info = "finance/user/info";
	/** 获取我的理财申请列表信息 */
	public  String my_financing_apply_list = "finance/user/financeapply-list";
	/** 获取我的贷款申请列表信息 */
	public  String my_loan_apply_list = "finance/user/loanapply-list";
	/** 用户退出登录 */
	public  String userLogout = "finance/user/logout";
	/** 获取我的收益概况 */
	public  String myIncome_summary = "finance/user/income-summary";
	/** 获取我的收益明细 */
	public  String myIncome_detail = "finance/user/income-detail";
	/** 获取首页信息 */
	public  String get_index = "finance/index";
	/** APP版本升级检测接口 */
	public static String app_upgrade = "finance/index/upgrade";
}
