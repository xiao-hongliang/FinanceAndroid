package com.pudding.financeandroid.api;

import android.content.Context;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;

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
	public void loanDetail(int loanId, AbStringHttpResponseListener responseListener) {
		AbRequestParams params = new AbRequestParams();
		params.put("id", loanId);
		mAbHttpUtil.post(BaseApi.BASE_URL + api.loan_detail, responseListener);
	}

}
