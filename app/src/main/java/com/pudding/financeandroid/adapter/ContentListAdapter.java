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
import com.pudding.financeandroid.bean.LoanContentBean;
import com.pudding.financeandroid.util.CommonUtil;

import java.util.List;

/**
 * 通用的详情内容列表适配器
 *
 * Created by xiao.hongliang on 2016/8/20.
 */
public class ContentListAdapter extends BaseAdapter{

    private List<LoanContentBean> datas;
    //图片下载器
    private AbImageLoader mAbImageLoader = null;
    private Context mContext;

    public ContentListAdapter(List<LoanContentBean> beans, Context context){
        this.datas = beans;
        this.mAbImageLoader = AbImageLoader.getInstance(context);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return this.datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.content_list_item, null);
            holder = new ViewHolder();
            holder.contentTv = (TextView) convertView.findViewById(R.id.content_item_text);
            holder.contentIv = (ImageView) convertView.findViewById(R.id.content_item_img);

            int screenWidth = CommonUtil.getScreenWidth(mContext);
            ViewGroup.LayoutParams lp = holder.contentIv.getLayoutParams();
            lp.width = screenWidth;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.contentIv.setLayoutParams(lp);
            holder.contentIv.setMaxWidth(screenWidth);
            holder.contentIv.setMaxHeight(screenWidth * 2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LoanContentBean bean = datas.get(position);
        if("text".equals(bean.getType())) {
            holder.contentTv.setVisibility(View.VISIBLE);
            holder.contentIv.setVisibility(View.GONE);
            holder.contentTv.setText(bean.getContent());
        }else {
            holder.contentIv.setVisibility(View.VISIBLE);
            holder.contentTv.setVisibility(View.GONE);
            mAbImageLoader.display(holder.contentIv, bean.getContent());
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView contentIv;
        TextView contentTv;
    }

}
