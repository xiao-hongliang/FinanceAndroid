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
import com.pudding.financeandroid.bean.LoanBean;

import java.util.List;

/**
 * 贷款列表页面的适配器
 *
 * Created by xiao.hongliang on 2016/8/20.
 */
public class LoanListAdapter extends BaseAdapter{

    private List<LoanBean> datas;
    //图片下载器
    private AbImageLoader mAbImageLoader = null;
    private Context mContext;

    public LoanListAdapter(List<LoanBean> beans, Context context){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tab_main_daikuan_item, null);
            holder = new ViewHolder();
            holder.logoIv = (ImageView) convertView.findViewById(R.id.loan_item_logo);
            holder.nameTv = (TextView) convertView.findViewById(R.id.loan_item_name);
            holder.loanTypeNameTv = (TextView) convertView.findViewById(R.id.loan_item_type_name);
            holder.monthRateInfoTv = (TextView) convertView.findViewById(R.id.loan_item_month_rate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LoanBean bean = datas.get(position);
        holder.nameTv.setText(bean.getName());
        holder.loanTypeNameTv.setText(bean.getLoanType().getName());
        holder.monthRateInfoTv.setText(bean.getMonthRateInfo());

        mAbImageLoader.display(holder.logoIv, bean.getLogo());

        return convertView;
    }

    private class ViewHolder {
        ImageView logoIv;
        TextView nameTv;
        TextView loanTypeNameTv;
        TextView monthRateInfoTv;
    }
}
