package com.pudding.financeandroid.api;

import android.content.Context;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.pudding.financeandroid.form.FinancingApplyForm;
import com.pudding.financeandroid.form.LoanApplyForm;
import com.pudding.financeandroid.form.UserLoginForm;
import com.pudding.financeandroid.form.UserRegisterForm;

/**
 * 请求结果封装
 */
public class RequestImpl {

	/** 请求对象封装 */
	private AbHttpUtil mAbHttpUtil = null;

	/** 请求超时时间 */
	private static final int timeOut = 10000;

	private BaseApi api;

	public RequestImpl(Context mContext) {
		// 获取Http工具类
		mAbHttpUtil = AbHttpUtil.getInstance(mContext);
		mAbHttpUtil.setTimeout(timeOut);
		api = BaseApi.getInstance();
	}

	//贷款列表
	public void loanList(int pageNo, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("pageNo", pageNo);
		mAbHttpUtil.post(BaseApi.BASE_URL + api.loan_list, params, responseListener);
	}

	//贷款详情
	public void loanDetail(String loanId, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("id", loanId);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.loan_detail, params, responseListener);
	}

	//申请贷款期限列表
	public void loanStageList(String productId, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("productId", productId);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.loan_stage_list, params, responseListener);
	}

	//提交申请贷款
	public void loanApplySend(LoanApplyForm form, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("productId", form.getProductId());
		params.put("name", form.getName());
		params.put("mobile", form.getMobile());
		params.put("idNo", form.getIdNo());
		params.put("amount", form.getAmount());
		params.put("stageDicItemId", form.getStageDicItemId());
		mAbHttpUtil.get(BaseApi.BASE_URL + api.loan_apply_submit, params, responseListener);
	}

	//提交申请贷款
	public void userLogin(UserLoginForm form, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("pushId", form.getPushId());
		params.put("userName", form.getUserName());
		params.put("password", form.getPassword());
		mAbHttpUtil.get(BaseApi.BASE_URL + api.user_login, params, responseListener);
	}

	//发送手机验证码
	public void sendRandCode(String mobile, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("mobile", mobile);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.send_randCode, params, responseListener);
	}

	//提交用户注册
	public void userRegister(UserRegisterForm form, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("pushId", form.getPushId());
		params.put("code", form.getCode());
		params.put("mobile", form.getMobile());
		params.put("password", form.getPassword());
		params.put("referrerMobile", form.getReferrerMobile());
		params.put("sex", form.getSex());
		mAbHttpUtil.get(BaseApi.BASE_URL + api.user_register, params, responseListener);
	}

	//发送手机验证码
	public void sendRandCodeForForgetPwd(String mobile, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("mobile", mobile);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.send_randCode_forget_pwd, params, responseListener);
	}

	//提交申请贷款
	public void userForgetPwd(UserRegisterForm form, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("pushId", form.getPushId());
		params.put("code", form.getCode());
		params.put("mobile", form.getMobile());
		params.put("password", form.getPassword());
		mAbHttpUtil.get(BaseApi.BASE_URL + api.user_forget_pwd, params, responseListener);
	}

	//提交修改密码
	public void updateUserPwd(String oldPwd, String newPwd, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("oldPass", oldPwd);
		params.put("newPass", newPwd);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.user_update_pwd, params, responseListener);
	}

	//理财列表
	public void financingList(int pageNo, Boolean isNewExperience, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		//是否是加载新手体验的列表数据
		if(isNewExperience) {
			params.put("moduleId", "24090440408895901");
		}
		params.put("pageNo", pageNo);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.financing_list, params, responseListener);
	}

	//理财详情
	public void financingDetail(String id, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("id", id);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.financing_detail, params, responseListener);
	}

	//提交申请贷款
	public void financingApplySend(FinancingApplyForm form, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("productId", form.getProductId());
		params.put("name", form.getName());
		params.put("mobile", form.getMobile());
		params.put("idNo", form.getIdNo());
		params.put("count", form.getCount());
		mAbHttpUtil.get(BaseApi.BASE_URL + api.financing_apply_send, params, responseListener);
	}

	//获取公司栏目的新闻资讯
	public void companyList(String type, int pageNo, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("type", type);
		params.put("pageNo", pageNo);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.company_list, params, responseListener);
	}

	//获取公司栏目的新闻资讯详情
	public void companyInfoDetail(String infoId, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("id", infoId);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.company_info_detail, params, responseListener);
	}

	//获取我的理财订单列表信息
	public void myFinancingList(int pageNo, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("pageNo", pageNo);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.my_financing_list, params, responseListener);
	}

	//获取我的贷款订单列表信息
	public void myLoanList(int pageNo, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("pageNo", pageNo);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.my_loan_list, params, responseListener);
	}

	//获取个人中心的信息
	public void getUserInfo(AbStringHttpResponseListener responseListener) {
		mAbHttpUtil.get(BaseApi.BASE_URL + api.my_user_info, responseListener);
	}

	//获取我的理财申请列表信息
	public void myFinancingApplyList(int pageNo, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("pageNo", pageNo);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.my_financing_apply_list, params, responseListener);
	}

	//获取我的贷款申请列表信息
	public void myLoanApplyList(int pageNo, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("pageNo", pageNo);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.my_loan_apply_list, params, responseListener);
	}

	//退出登录
	public void userLogout(AbStringHttpResponseListener responseListener) {
		mAbHttpUtil.get(BaseApi.BASE_URL + api.userLogout, responseListener);
	}

	//获取我的收益概况信息
	public void myIncomeSummary(AbStringHttpResponseListener responseListener) {
		mAbHttpUtil.get(BaseApi.BASE_URL + api.myIncome_summary, responseListener);
	}

	//获取我的收益明细列表信息
	public void myIncomeDetailList(int pageNo, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("pageNo", pageNo);
//		params.put("pageSize", 1);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.myIncome_detail, params, responseListener);
	}

	//获取个人中心的信息
	public void getIndex(AbStringHttpResponseListener responseListener) {
		mAbHttpUtil.get(BaseApi.BASE_URL + api.get_index, responseListener);
	}

	//预约贷款申请页面，贷款产品的列表接口
	public void getProductListForLoanApply(AbStringHttpResponseListener responseListener) {
		mAbHttpUtil.get(BaseApi.BASE_URL + api.getProductList_loan, responseListener);
	}

	//预约理财申请页面，理财产品的列表接口
	public void getProductListForFinancingApply(AbStringHttpResponseListener responseListener) {
		mAbHttpUtil.get(BaseApi.BASE_URL + api.getProductList_finance, responseListener);
	}

	//获取我的用户消息列表信息
	public void userMessageList(int pageNo, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("pageNo", pageNo);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.user_message_list, params, responseListener);
	}

	//获取我的用户消息列表信息
	public void getAdvertisementInfo(String id, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("id", id);
		mAbHttpUtil.get(BaseApi.BASE_URL + api.get_advertisement_info, params, responseListener);
	}
}
