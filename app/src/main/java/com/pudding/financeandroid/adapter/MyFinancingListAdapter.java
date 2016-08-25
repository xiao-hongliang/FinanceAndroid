package com.pudding.financeandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pudding.financeandroid.R;
import com.pudding.financeandroid.bean.MyFinancingBean;

import java.util.List;

public class MyFinancingListAdapter extends BaseAdapter {

	private Context context;
	private List<MyFinancingBean> financingBeen;
	private int listViewItem;

	static class ViewHolder {
		TextView tvMyFinancingName;
		TextView tvMyFinancingYearRate;
		TextView tvMyFinancingMinAmount;
		TextView tvMyFinancingInvestTime;
		TextView tvMyFinancingCount;
		TextView tvPrice;
		String infoId;
	}

	public MyFinancingListAdapter(Context context, List<MyFinancingBean> financingBeen, int listViewItem) {
		this.context = context;
		this.financingBeen = financingBeen;
		this.listViewItem = listViewItem;
	}
	
	@Override
	public int getCount() {
		return financingBeen.size();
	}

	@Override
	public Object getItem(int position) {
		return financingBeen.get(position);
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
			holder.tvMyFinancingName = (TextView) convertView.findViewById(R.id.myFinancing_name);
			holder.tvMyFinancingYearRate = (TextView) convertView.findViewById(R.id.myFinancing_yearRate);
			holder.tvMyFinancingMinAmount = (TextView) convertView.findViewById(R.id.myFinancing_minAmount);
			holder.tvMyFinancingInvestTime = (TextView) convertView.findViewById(R.id.myFinancing_investTimeName);
			holder.tvMyFinancingCount = (TextView) convertView.findViewById(R.id.myFinancing_count);
			holder.tvPrice = (TextView) convertView.findViewById(R.id.price_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MyFinancingBean bean = financingBeen.get(position);
		holder.tvMyFinancingName.setText(bean.getProductName());
		holder.tvMyFinancingYearRate.setText(bean.getYearRateStr());
		holder.tvMyFinancingMinAmount.setText(bean.getMinAmount());
		holder.tvMyFinancingInvestTime.setText(bean.getInvestTimeName());
		holder.tvMyFinancingCount.setText("X" + bean.getCount());
		holder.tvPrice.setText(bean.getAmount());
		holder.infoId = bean.getId();

		return convertView;
	}
	
	public void addNews(List<MyFinancingBean> addFinancingBean) {
		for(MyFinancingBean bean : addFinancingBean) {
			financingBeen.add(bean);
		}
	}
}
