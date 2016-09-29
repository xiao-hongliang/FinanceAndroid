package com.pudding.financeandroid.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.pudding.financeandroid.bean.ImgSizeBean;
import com.pudding.financeandroid.bean.LoanStageBean;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 公共工具类
 *
 * Created by xiao.hongliang on 2016/8/24.
 */
public class CommonUtil {
    /**
     * 检查当前的网络状态(包括WiFi、手机GPRS)
     * @param context  上下文对象
     * @return  网络状态是否连接
     */
    public static boolean isWifiConnected(Context context) {
        //单独获取WIFI连接状态
        //WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //return manager.isWifiEnabled();
        // 获取手机所以连接管理对象（包括wi-fi，net等连接的管理）
        ConnectivityManager conn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conn != null) {
            // 网络管理连接对象
            NetworkInfo info = conn.getActiveNetworkInfo();
            if(info != null && info.isConnected()) {
                // 判断当前网络是否连接
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String[] parseNameList(List<LoanStageBean> stageBeen) {
        String[] beans = new String[stageBeen.size()];
        for(int i=0; i<stageBeen.size(); i++) {
            beans[i] = stageBeen.get(i).getName();
        }
        return beans;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static ImgSizeBean loadImageFromNetwork(String url) {
        try {
            URL m_url = new URL(url);
            HttpURLConnection con = (HttpURLConnection)m_url.openConnection();
            InputStream in = con.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            int height = options.outHeight;
            int width = options.outWidth;
            return new ImgSizeBean(width, height);
        } catch (Exception e) {
            return null;
        }
    }
}
