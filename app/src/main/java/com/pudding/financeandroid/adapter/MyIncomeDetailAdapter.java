package com.pudding.financeandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pudding.financeandroid.R;
import com.pudding.financeandroid.bean.MyIncomeDetailBean;

import java.util.List;

public class MyIncomeDetailAdapter extends BaseAdapter {

	private Context context;
	private List<MyIncomeDetailBean> myIncomeDetailBeen;
	private int listViewItem;

	static class ViewHolder {
		TextView tvAmount;
		TextView tvDataTitle;
		TextView tvTradeTimeStr;
	}

	public MyIncomeDetailAdapter(Context context, List<MyIncomeDetailBean> myIncomeDetailBeen, int listViewItem) {
		this.context = context;
		this.myIncomeDetailBeen = myIncomeDetailBeen;
		this.listViewItem = listViewItem;
	}
	
	@Override
	public int getCount() {
		return myIncomeDetailBeen.size();
	}

	@Override
	public Object getItem(int position) {
		return myIncomeDetailBeen.get(position);
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
			holder.tvAmount = (TextView) convertView.findViewById(R.id.income_amount);
			holder.tvDataTitle = (TextView) convertView.findViewById(R.id.income_dataTitle);
			holder.tvTradeTimeStr = (TextView) convertView.findViewById(R.id.income_tradeTimeStr);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MyIncomeDetailBean bean = myIncomeDetailBeen.get(position);
		holder.tvAmount.setText("+￥" + bean.getAmount());
		holder.tvDataTitle.setText(bean.getDataTitle());
		holder.tvTradeTimeStr.setText(bean.getTradeTimeStr());

		return convertView;
	}
	
	public void addNews(List<MyIncomeDetailBean> addIncomeBeen) {
		for(MyIncomeDetailBean bean : addIncomeBeen) {
			myIncomeDetailBeen.add(bean);
		}
	}
}
