package com.pudding.financeandroid.api;

import android.content.Context;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
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

	public void Login(String phone, String pwd, AbStringHttpResponseListener responseListener) {
//		AbRequestParams params = new AbRequestParams();
//		params.put("username", phone);
//		params.put("password", pwd);

//		long starTime = System.currentTimeMillis();
//		mAbHttpUtil.post(api.BASE_URL + api.LOGIN, params, responseListener);
//		long endTime = System.currentTimeMillis();
//		long Time = endTime - starTime;
//		MyLog.v("Login", "执行时间:" + Time);
	}

	//贷款列表
	public void loanList(AbStringHttpResponseListener responseListener) {
		mAbHttpUtil.post(BaseApi.BASE_URL + api.loan_list, responseListener);
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

}
