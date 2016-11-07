package com.pudding.financeandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.adapter.InfoListAdapter;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.bean.HandlerInfoBean;
import com.pudding.financeandroid.response.InfoResponse;

public class InfoListFragment extends Fragment {
	private final String TAG = "InfoListFragment";
	private Context context;
	private int flag;
	private PullToRefreshListView ptrlvMainContent = null;
	private InfoListAdapter newAdapter = null;
	private int pageNum = 1;
	private Handler proHandler = new IndexHandler();
	/** 连接对象 */
	private RequestImpl ri = null;

	private final class IndexHandler extends Handler{
		public void handleMessage(Message msg) {
			switch(msg.what){
				case 182:
					HandlerInfoBean infoBean = (HandlerInfoBean) msg.obj;
					initView(infoBean);
					break;
				case -1:
					break;
			}
		}
	}
	
	static InfoListFragment newInstance(int taFlag) {
		InfoListFragment newFragment = new InfoListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tabFlag", taFlag);
        newFragment.setArguments(bundle);
        return newFragment;
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        flag = (int) bundle.get("tabFlag");
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.company_info_list, container, false);
        context = view.getContext();
		ri = new RequestImpl(context);
        
        ptrlvMainContent = (PullToRefreshListView) view.findViewById(R.id.ptrlvMainCont);
        switch(flag){
        	case 0:
				httpPost("00", Boolean.FALSE, null);   //全部
        		break;
        	case 1:
				httpPost("10", Boolean.FALSE, null);   //行业新闻
        		break;
        	case 2:
				httpPost("20", Boolean.FALSE, null);   //公司动态
        		break;
        	case 3:
				httpPost("30", Boolean.FALSE, null);   //贸易公司
        		break;
        }
        return view;
    }
	
	private void initView(HandlerInfoBean infoBeen){
		newAdapter = new InfoListAdapter(context, infoBeen.getHandlerData(), R.layout.company_info_list_item);
        ptrlvMainContent.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        ptrlvMainContent.setOnRefreshListener(new MyOnRefreshListener2(ptrlvMainContent, infoBeen.getType()));
        ptrlvMainContent.setAdapter(newAdapter);
	}

	private void httpPost(final String typeValue, final Boolean isMorePage, final PullToRefreshListView perv) {
		if(isMorePage) {
			pageNum++;
		}
		ri.companyList(typeValue, pageNum, new AbStringHttpResponseListener() {
			// 开始执行前
			@Override
			public void onStart() {
				// 显示进度框
				// AbDialogUtil.showProgressDialog(mContext, 0, "正在获取数据...");
			}
			// 完成后调用，失败，成功
			@Override
			public void onFinish() {
				Log.d(TAG, "onFinish");
			}
			// 失败，调用
			@Override
			public void onFailure(int statusCode, String content, Throwable error) {
				AbToastUtil.showToast(context, error.getMessage());
				Log.v(TAG, "onFailure");
			}
			// 获取数据成功会调用这里
			public void onSuccess(int statusCode, String content) {
				try{
					InfoResponse bean = (InfoResponse) AbJsonUtil.fromJson(content, InfoResponse.class);
					// 验证成功
					if (bean.getSuccess()) {
						if(isMorePage) {
							if(bean.getData().size() > 0){
								newAdapter.addNews(bean.getData());
								newAdapter.notifyDataSetChanged();
							}else{
								Toast.makeText(context, R.string.noData, Toast.LENGTH_SHORT).show();
							}
							perv.onRefreshComplete();
						}else {
							proHandler.sendMessage(proHandler.obtainMessage(182,
									new HandlerInfoBean(typeValue, bean.getData())));
						}
					} else {
						AbToastUtil.showToast(context, bean.getMsg());
					}
				}catch(Exception e) {
					Log.v(TAG, "Home加载数据异常！" + e.getMessage());
				}
			}
		});
	}

	class MyOnRefreshListener2 implements PullToRefreshBase.OnRefreshListener2<ListView> {
		private PullToRefreshListView mPtflv;
		private String typeValue;
		public MyOnRefreshListener2(PullToRefreshListView ptflv, String typeValue) {
			this.mPtflv = ptflv;
			this.typeValue = typeValue;
		}
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {}
		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 上拉加载
			String label = DateUtils.formatDateTime(context,
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME| DateUtils.FORMAT_SHOW_DATE
					| DateUtils.FORMAT_ABBREV_ALL);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			refreshView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
			refreshView.getLoadingLayoutProxy().setRefreshingLabel("加载中……");
			refreshView.getLoadingLayoutProxy().setReleaseLabel("释放加载");

			httpPost(typeValue, Boolean.TRUE, mPtflv);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.i("IndexFragmentActivity", flag + "个InfoListFragment : onSaveInstanceState is called");
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onPause() {
		Log.i("IndexFragmentActivity", flag + "个InfoListFragment : onPause is called");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.i("IndexFragmentActivity", flag + "个InfoListFragment : onStop is called");
		super.onStop();
	}

	@Override
	public void onDestroy() {
		Log.i("IndexFragmentActivity", flag + "个InfoListFragment : onDestroy is called");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		Log.i("IndexFragmentActivity", flag + "个InfoListFragment : onDestroyView is called");
		super.onDestroyView();
	}

	@Override
	public void onResume() {
		Log.i("IndexFragmentActivity", flag + "个InfoListFragment : onResume is called");
		super.onResume();
	}	

}
