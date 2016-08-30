package com.pudding.financeandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pudding.financeandroid.R;
import com.pudding.financeandroid.bean.UserMessageBean;

import java.util.List;

public class UserMessageListAdapter extends BaseAdapter {

	private Context context;
	private List<UserMessageBean> messageBeen;
	private int listViewItem;

	static class ViewHolder {
		TextView tvMessageContent;
		TextView tvCreateTime;
		TextView tvIcon;
	}

	public UserMessageListAdapter(Context context, List<UserMessageBean> messageBeen, int listViewItem) {
		this.context = context;
		this.messageBeen = messageBeen;
		this.listViewItem = listViewItem;
	}
	
	@Override
	public int getCount() {
		return messageBeen.size();
	}

	@Override
	public Object getItem(int position) {
		return messageBeen.get(position);
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
			holder.tvMessageContent = (TextView) convertView.findViewById(R.id.user_message_content);
			holder.tvCreateTime = (TextView) convertView.findViewById(R.id.user_message_createTime);
			holder.tvIcon = (TextView) convertView.findViewById(R.id.user_message_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		UserMessageBean bean = messageBeen.get(position);
		holder.tvMessageContent.setText(bean.getContent());
		holder.tvCreateTime.setText(bean.getCreateTimeStr());
		if("10".equals(bean.getStatus())) {
			holder.tvIcon.setBackgroundResource(R.drawable.shape_message_icon);
		}else {
			holder.tvIcon.setBackgroundResource(R.drawable.shape_message_icon_font9);
		}

		return convertView;
	}
	
	public void addNews(List<UserMessageBean> addMessageBeen) {
		for(UserMessageBean bean : addMessageBeen) {
			messageBeen.add(bean);
		}
	}
}
