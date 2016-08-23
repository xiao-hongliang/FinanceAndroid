package com.pudding.financeandroid.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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

import com.llb.util.PullToRefreshListView;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.adapter.InfoListAdapter;
import com.pudding.financeandroid.bean.InfoBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoListFragment extends Fragment {
	private final String TAG = "InfoListFragment";
	private Context context;
	private int flag;
	private PullToRefreshListView ptrlvMainContent = null;
	private InfoListAdapter newAdapter = null;
	/**  0表示常规的资讯, 1表示广告区的信息, 2表示下边全部栏信息  */
	private final String INDEXDOWN = "0";
	private final String INDEXUPALL = "2";
	private final int pageSize = 10;
	private int pageNum = 1;
	private ProgressDialog progressDialog = null;
	private Handler prohandler = new IndexHandler();
	List<InfoBean> dataAll,dataNews,dataDongTai,dataMaoYi;
	
	private final class IndexHandler extends Handler{
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			switch(msg.what){
				case 182:
					initView((List<InfoBean>)msg.obj);
					break;
				case 183:
					newAdapter.cleanDatas();
					newAdapter.addNews((List<InfoBean>)msg.obj);
					newAdapter.notifyDataSetChanged();
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
        
        ptrlvMainContent = (PullToRefreshListView) view.findViewById(R.id.ptrlvMainCont);
        switch(flag){
        	case 0:
        		initData(0);   //全部
        		break;
        	case 1:
        		initData(10);   //行业新闻
        		break;
        	case 2:
        		initData(20);   //公司动态
        		break;
        	case 3:
        		initData(30);   //贸易公司
        		break;
        }
        return view;
    }
	
	private void initData(int type){
		if(data.isEmpty()){
			sendRequestByWeb(flag);
		}else{
			initView(data);
		}
	}

	private void initView(List<InfoBean> datas){
		newAdapter = new InfoListAdapter(context, datas,R.layout.list_item);
//        ptrlvMainContent.setMode(Mode.PULL_FROM_END);
        ptrlvMainContent.setOnRefreshListener(new MyOnRefreshListener2(ptrlvMainContent));
        ptrlvMainContent.setAdapter(newAdapter);
	}
	
	private void sendRequestByWeb(final int typeflag){
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("数据加载中,请稍候...");
		progressDialog.show();
    	new Thread(new Runnable() {
			public void run() {
				try {
					switch(typeflag){
						case 0:
							 List<InfoBean> sqlitelist = infoService.getInfosForMain(AppManager.PUBNEWSLIST, INDEXUPALL, "1",
									AppManager.globalOrgCode, null, "GET",null);
							infoService.saveInfosBySQLite(sqlitelist,INDEXUPALL);
							prohandler.sendMessage(prohandler.obtainMessage(182, sqlitelist));
							break;
						case 1:
							getlistdata(AppManager.noticeId);
							break;
						case 2:
							getlistdata(AppManager.ycnewsId);
							break;
						case 3:
							getlistdata(AppManager.faguiId);
							break;
					}
					progressDialog.cancel();
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					progressDialog.cancel();
					prohandler.sendEmptyMessage(-1);
				}
			}
		}).start();
	}
	
	private void getlistdata(String classifyId) throws Exception{
		List<PubInfo> fromdata = infoService.getInfosForMain(AppManager.PUBINFOS, null, "1",
				AppManager.globalOrgCode, classifyId, "GET",null);
		infoService.saveInfosBySQLite(fromdata,INDEXDOWN);
		prohandler.sendMessage(prohandler.obtainMessage(182, fromdata));
	}
	
	class MyOnRefreshListener2 implements OnRefreshListener2<ListView> {
		private PullToRefreshListView mPtflv;
		public MyOnRefreshListener2(PullToRefreshListView ptflv) {
			this.mPtflv = ptflv;
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
			new GetNewsTask(mPtflv).execute();
		}
	}
	
	/**
	 * 请求网络获得新闻信息
	 */
	class GetNewsTask extends AsyncTask<String, Integer, Map<String,Object>> {
		private PullToRefreshListView mPtrlv;
		public GetNewsTask(PullToRefreshListView ptrlv) {
			this.mPtrlv = ptrlv;
		}
		protected Map<String,Object> doInBackground(String... params) {
			Map<String,Object> map = new HashMap<String,Object>();
			List<PubInfo> addData = new ArrayList<PubInfo>();
			try {
				if(CommonUtil.isWifiConnected(context)){
					pageNum++;
					switch(flag){
						case 0:
							addData = infoService.getInfosForMain(AppManager.PUBNEWSLIST, INDEXUPALL, pageNum+"",
									AppManager.globalOrgCode, null, "GET",null);
							break;
						case 1:
							addData = infoService.getInfosForMain(AppManager.PUBINFOS, null, pageNum+"",
									AppManager.globalOrgCode, AppManager.noticeId, "GET",null);
							break;
						case 2:
							addData = infoService.getInfosForMain(AppManager.PUBINFOS, null, pageNum+"",
									AppManager.globalOrgCode, AppManager.ycnewsId, "GET",null);
							break;
						case 3:
							addData = infoService.getInfosForMain(AppManager.PUBINFOS, null, pageNum+"",
									AppManager.globalOrgCode, AppManager.faguiId, "GET",null);
							break;
					}
				}else{
					map.put("status", false);
				}
				map.put("status", true);
			} catch (Exception e) {
				Log.e(TAG, e.toString());
				map.put("status", false);
				prohandler.sendEmptyMessage(-1);
			}
			map.put("data", addData);
			return map;
		}
		@SuppressWarnings("unchecked")
		protected void onPostExecute(Map<String,Object> result) {
			boolean connect = (Boolean) result.get("status");
			List<PubInfo> addData = (List<PubInfo>) result.get("data");
			if(connect){
				if(addData.size() > 0){
					newAdapter.addNews(addData);
					newAdapter.notifyDataSetChanged();
				}else{
					Toast.makeText(context, R.string.noData, Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(context, R.string.netOff, Toast.LENGTH_SHORT).show();
			}
			mPtrlv.onRefreshComplete();
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
		if(flag == 0){
			Log.i("IndexFragmentActivity", flag + "个InfoListFragment : dataall "+dataall.size());
		}
		super.onResume();
	}	

}
