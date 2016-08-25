package com.pudding.financeandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.activity.InfoDetailActivity;
import com.pudding.financeandroid.bean.InfoBean;

import java.util.List;

public class InfoListAdapter extends BaseAdapter {

	//图片下载器
	private AbImageLoader mAbImageLoader = null;
	private Context context;
	private List<InfoBean> infoBeen;
	private int listViewItem;
	
	static class ViewHolder {
		ImageView ivLogo;
		TextView tvTitle;
		TextView createTime;
		String infoId;
	}

	public InfoListAdapter(Context context, List<InfoBean> infoBeen, int listViewItem) {
		this.context = context;
		this.infoBeen = infoBeen;
		this.listViewItem = listViewItem;
		this.mAbImageLoader = AbImageLoader.getInstance(context);
	}
	
	@Override
	public int getCount() {
		return infoBeen.size();
	}

	@Override
	public Object getItem(int position) {
		return infoBeen.get(position);
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
			holder.ivLogo = (ImageView) convertView.findViewById(R.id.info_item_logo);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.info_item_title);
			holder.createTime = (TextView) convertView.findViewById(R.id.info_item_createTime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		InfoBean info = infoBeen.get(position);
		holder.tvTitle.setText(info.getTitle());
		holder.createTime.setText(info.getCreateTimeStr());
		holder.infoId = info.getId();

		mAbImageLoader.display(holder.ivLogo, info.getLogo());

		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ViewHolder holder = (ViewHolder) v.getTag();
				String id = holder.infoId;
				Intent it = new Intent(v.getContext(), InfoDetailActivity.class);
				it.putExtra("infoId", id);
				v.getContext().startActivity(it);
			}
		});
		return convertView;
	}
	
	public void addNews(List<InfoBean> addInfoBeen) {
		for(InfoBean info : addInfoBeen) {
			infoBeen.add(info);
		}
	}
}
