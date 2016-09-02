package com.pudding.financeandroid.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * listView的专门工具类
 *
 * Created by xiao.hongliang on 2016/8/27.
 */
public class ListViewUtil {

    /**
     * 当listView被ScrollView嵌套时，手动字段listView的实际高度
     *
     * @param listView     需要计算高度的listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView, Boolean isLimitHeight) {
        // 获取ListView对应的Adapter
        BaseAdapter listAdapter = (BaseAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            int itemHeight = listItem.getMeasuredHeight();
            if(isLimitHeight != null && isLimitHeight) {
                if(itemHeight > 450) {
                    itemHeight = itemHeight / 2;
                }
            }
            totalHeight += itemHeight;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
