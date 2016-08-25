package com.pudding.financeandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.bean.MyLoanBean;

import java.util.List;

public class MyLoanListAdapter extends BaseAdapter {

	private Context context;
	private List<MyLoanBean> loanBeen;
	private int listViewItem;
	//图片下载器
	private AbImageLoader mAbImageLoader = null;

	static class ViewHolder {
		TextView tvMyLoanProductName;
		TextView tvMyLoanMonthRateInfo;
		ImageView ivItemIconLoan;
		TextView tvMyLoanStageStr;
		TextView tvMyLoanAmount;
	}

	public MyLoanListAdapter(Context context, List<MyLoanBean> loanBeen, int listViewItem) {
		this.context = context;
		this.loanBeen = loanBeen;
		this.listViewItem = listViewItem;
		this.mAbImageLoader = AbImageLoader.getInstance(context);
	}
	
	@Override
	public int getCount() {
		return loanBeen.size();
	}

	@Override
	public Object getItem(int position) {
		return loanBeen.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			convertView = LayoutInflater.from(context).inflate(listViewItem, null);
			holder = new ViewHolder();
			holder.tvMyLoanProductName = (TextView) convertView.findViewById(R.id.myLoan_productName);
			holder.tvMyLoanMonthRateInfo = (TextView) convertView.findViewById(R.id.myLoan_monthRateInfo);
			holder.ivItemIconLoan = (ImageView) convertView.findViewById(R.id.item_icon_loan);
			holder.tvMyLoanStageStr = (TextView) convertView.findViewById(R.id.myLoan_stageStr);
			holder.tvMyLoanAmount = (TextView) convertView.findViewById(R.id.myLoan_amount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MyLoanBean bean = loanBeen.get(position);
		holder.tvMyLoanProductName.setText(bean.getProductName());
		holder.tvMyLoanMonthRateInfo.setText(bean.getMonthRateInfo());
		holder.tvMyLoanStageStr.setText(bean.getStageStr());
		holder.tvMyLoanAmount.setText(bean.getAmount());

		mAbImageLoader.display(holder.ivItemIconLoan, bean.getLogo());

		return convertView;
	}
	
	public void addNews(List<MyLoanBean> addLoanBeen) {
		for(MyLoanBean bean : addLoanBeen) {
			loanBeen.add(bean);
		}
	}
}
