package com.pudding.financeandroid.api;

/**
 * 所有请求url管理
 */
public class BaseApi {

	public static final String BASE_URL = "http://wld.budingnet.com/";

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

}
