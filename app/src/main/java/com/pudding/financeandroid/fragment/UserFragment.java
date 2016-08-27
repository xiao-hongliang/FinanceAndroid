package com.pudding.financeandroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbToastUtil;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.activity.MyFinancingApplyActivity;
import com.pudding.financeandroid.activity.MyLoanApplyActivity;
import com.pudding.financeandroid.activity.MyOrderActivity;
import com.pudding.financeandroid.activity.UpdateUserPwdActivity;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.bean.UserInfoBean;
import com.pudding.financeandroid.response.UserInfoResponse;
import com.pudding.financeandroid.util.SPUtils;
import com.shizhefei.fragment.LazyFragment;

/**
 * 我的的fragment
 *
 * Created by xiao.hongliang on 2016/8/16.
 */
public class UserFragment extends LazyFragment implements View.OnClickListener{
    private static final String TAG = UserFragment.class.getName();
    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";
    private Context mContext;
    /** 连接对象 */
    private RequestImpl ri = null;
    private String phone = "";

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.tab_main_user);
        mContext = getApplicationContext();
        ri = new RequestImpl(mContext);

        this.findViewById(R.id.user_logout_btn).setOnClickListener(this);
        this.findViewById(R.id.user_center_layout_1).setOnClickListener(this);
        this.findViewById(R.id.user_center_layout_2).setOnClickListener(this);
        this.findViewById(R.id.user_center_layout_3).setOnClickListener(this);
        this.findViewById(R.id.user_center_layout_4).setOnClickListener(this);
        this.findViewById(R.id.user_center_layout_5).setOnClickListener(this);
        this.findViewById(R.id.user_center_layout_6).setOnClickListener(this);

        httpPost();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch(v.getId()) {
            case R.id.user_logout_btn:
//                AbToastUtil.showToast(mContext, "退出登陆哦");
                SPUtils.put(mContext, "phone", "");
                break;
            case R.id.user_center_layout_1:
                intent.setClass(mContext, MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.user_center_layout_2:
                AbToastUtil.showToast(mContext, R.string.user_center_2);
                break;
            case R.id.user_center_layout_3:
                intent.setClass(mContext, MyFinancingApplyActivity.class);
                startActivity(intent);
                break;
            case R.id.user_center_layout_4:
                intent.setClass(mContext, MyLoanApplyActivity.class);
                startActivity(intent);
                break;
            case R.id.user_center_layout_5:
                intent.setClass(mContext, UpdateUserPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.user_center_layout_6:
                //跳转到拨号界面
                Intent call_intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                call_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(call_intent);
                break;
        }
    }

    private void initView(UserInfoBean userInfoBean) {
        this.phone = userInfoBean.getPhone();

        TextView userName = (TextView) this.findViewById(R.id.user_info_name);
        userName.setText(userInfoBean.getUserName());
    }

    private void httpPost() {
        ri.getUserInfo(new AbStringHttpResponseListener() {
            // 开始执行前
            @Override
            public void onStart() {
                // 显示进度框
//                 AbDialogUtil.showProgressDialog(mContext, 0, "正在获取数据...");
            }
            // 完成后调用，失败，成功
            @Override
            public void onFinish() {
//                AbDialogUtil.removeDialog(mContext);
                Log.d(TAG, "onFinish");
            }
            // 失败，调用
            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                AbToastUtil.showToast(mContext, error.getMessage());
                Log.v(TAG, "onFailure");
            }
            // 获取数据成功会调用这里
            public void onSuccess(int statusCode, String content) {
                try{
                    UserInfoResponse bean = (UserInfoResponse) AbJsonUtil.fromJson(content, UserInfoResponse.class);
                    // 验证成功
                    if (bean.getSuccess()) {
                        initView(bean.getData());
                    } else {
                        AbToastUtil.showToast(mContext, bean.getMsg());
                    }
                }catch(Exception e) {
                    Log.v(TAG, "Home加载数据异常！" + e.getMessage());
                }
            }
        });
    }
}
