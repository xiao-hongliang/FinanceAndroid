package com.pudding.financeandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pudding.financeandroid.R;
import com.pudding.financeandroid.bean.FinancingBean;

import java.util.List;

/**
 * 理财列表页面的适配器
 *
 * Created by xiao.hongliang on 2016/8/20.
 */
public class FinancingListAdapter extends BaseAdapter{

    private List<FinancingBean> datas;
    private Context mContext;

    public FinancingListAdapter(List<FinancingBean> beans, Context context){
        this.datas = beans;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tab_main_licai_item, null);
            holder = new ViewHolder();
            holder.yearRateTv = (TextView) convertView.findViewById(R.id.yearRate);
            holder.minAmountTv = (TextView) convertView.findViewById(R.id.minAmount);
            holder.investTimeTv = (TextView) convertView.findViewById(R.id.investTime);
            holder.nameTv = (TextView) convertView.findViewById(R.id.financing_name);
            holder.labelNameTv = (TextView) convertView.findViewById(R.id.labelName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FinancingBean bean = datas.get(position);
        holder.yearRateTv.setText(bean.getYearRateStr());
        holder.minAmountTv.setText(bean.getMinAmount() + "");
        holder.investTimeTv.setText(bean.getInvestTimeName());
        holder.nameTv.setText(bean.getName());
        if(bean.getLabelData() != null) {
            holder.labelNameTv.setText(bean.getLabelData().getLabelName());
            holder.labelNameTv.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private class ViewHolder {
        TextView yearRateTv;
        TextView minAmountTv;
        TextView investTimeTv;
        TextView nameTv;
        TextView labelNameTv;
    }

    public void addNews(List<FinancingBean> addFinancingBeen) {
        for(FinancingBean bean : addFinancingBeen) {
            datas.add(bean);
        }
    }
}
