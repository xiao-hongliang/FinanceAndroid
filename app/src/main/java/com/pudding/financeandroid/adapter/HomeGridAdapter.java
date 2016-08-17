package com.pudding.financeandroid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.bean.ApplicationBean;
import com.pudding.financeandroid.view.BaseViewHolder;

import java.util.List;

/**
 * 首页九宫格适配器
 * @ClassName HomeGridAdapter
 */
public class HomeGridAdapter extends BaseAdapter {
	private Context mContext;
	private List<ApplicationBean> list;
	private String TAG = HomeGridAdapter.class.getName();

	//图片下载器
    private AbImageLoader mAbImageLoader = null;

	public HomeGridAdapter(Context mContext, List<ApplicationBean> list) {
		super();
		this.mContext = mContext;
		this.list = list;
		//图片下载器
        mAbImageLoader = AbImageLoader.getInstance(mContext);
	}
	
	public List<ApplicationBean> getList()
	{
		return list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        //设置加载中的View
        //View loadingView = convertView.findViewById(R.id.progressBar);
        //图片的下载
       // mAbImageLoader.display(itemsIcon,loadingView,imageUrl,150,150);
		//mAbImageLoader.getBitmapResponse(url, 300, 300);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.home_grid_item, parent, false);
		}
		ApplicationBean bean = list.get(position);
		TextView textView = BaseViewHolder.get(convertView, R.id.tv_item);
		ImageView imageView = BaseViewHolder.get(convertView, R.id.iv_item);
//		View tableItemLayout = BaseViewHolder.get(convertView, R.id.table_item_layout);
		imageView.setVisibility(View.VISIBLE);
		imageView.setImageResource(bean.getIcon());
		convertView.setBackgroundResource(bean.getColor());
		
//		String url = bean.getIcon();
		//url = "http://192.168.100.10/appserver/test-img/"+URLEncoder.encode("九宫格-17.png");
//		mAbImageLoader.display(imageView, url);
		
		//AbBitmapResponse abr = mAbImageLoader.getBitmapResponse(bean.getImgUrl(), 300, 300);
		//设置加载中的View
//        View loadingView = convertView.findViewById(R.id.progressBar);
        //图片的下载
//        mAbImageLoader.display(iv,loadingView,bean.getImgUrl(),100,100);
//		if (abr.getBitmap() != null) {
//			iv.setImageBitmap(abr.getBitmap());
//		}else {
//			//iv.setBackground(R.drawable.app_f);
//		}
		textView.setText(bean.getName());
		return convertView;
	}
}
