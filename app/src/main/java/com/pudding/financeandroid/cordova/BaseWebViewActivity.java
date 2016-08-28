package com.pudding.financeandroid.cordova;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebViewClient;

import com.ab.activity.AbActivity;
import com.ab.util.AbLogUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.util.SPUtils;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterfaceImpl;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewEngine;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.ICordovaCookieManager;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginEntry;
import org.apache.cordova.PluginManager;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * webview对象
 *
 * @author zuolangguo
 * @date 2015年12月28日 下午2:17:29
 */
@SuppressLint({ "NewApi", "Override" })
public class BaseWebViewActivity extends AbActivity {
	public static String TAG = BaseWebViewActivity.class.getName();

	/** appView对象 */
	protected CordovaWebView appView;

	/** keepRunning */
	protected boolean keepRunning = true;

	/** immersiveMode */
	protected boolean immersiveMode;

	/** Read from config.xml: */
	protected CordovaPreferences preferences;
	protected String launchUrl;
	protected ArrayList<PluginEntry> pluginEntries;
	protected CordovaInterfaceImpl cordovaInterface;

	/** 上下文对象 */
	private Context mContext;

	/** 标题头 */
	private AbTitleBar mAbTitleBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.linzi_webview);
		mContext = BaseWebViewActivity.this;

		// 加载配置文件
		loadConfig();
		cordovaInterface = makeCordovaInterface();
		if (savedInstanceState != null) {
			cordovaInterface.restoreInstanceState(savedInstanceState);
		}
		// setTitleBar
		String url = getIntent().getStringExtra("url");
		String title = getIntent().getStringExtra("title");

		if (url == null || url.equals("")) {
			url = "file:///android_asset/www/index.html";
		}

		if (title == null || title.equals("")) {
			title = "应用加载中...";
		}

		mAbTitleBar = this.getTitleBar();
		setTitleBar(mAbTitleBar, title);
		// 获取默认

		init();

		// 清除缓存
		appView.clearCache();
		appView.clearHistory();

		setJSESSIONID(url);
		// 加载url
		loadUrl(url);
	}


	Handler handler = new Handler();
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// handler自带方法实现定时器
			String d = new Date().toLocaleString();
			LOG.v("TTTTTTTTTTTTTTTTTTTTTTTTTT", "==="+d);
			appView.sendJavascript("javascript:androidCallJS('"+d+"')");

			handler.postDelayed(this, 600);
		}
	};

	/**
	 * 初始化
	 *
	 */
	protected void init() {
		appView = makeWebView();
		// createViews();
		if (!appView.isInitialized()) {
			appView.init(cordovaInterface, pluginEntries, preferences);
		}
		cordovaInterface.onCordovaInit(appView.getPluginManager());

	}

	/**
	 * 设置JSESSIONID
	 *
	 */
	protected void setJSESSIONID(String url) {
		ICordovaCookieManager cm = appView.getCookieManager();
		String JSESSIONID = (String) SPUtils.get(mContext, "JSESSIONID", "");
		AbLogUtil.d(TAG, "setJSESSIONID JSESSIONID=" + JSESSIONID);
		String cookieString = "JSESSIONID=" + JSESSIONID;

		cm.setCookie(url, cookieString);
	}

	/**
	 * 加载配置文件
	 *
	 */
	protected void loadConfig() {
		ConfigXmlParser parser = new ConfigXmlParser();
		parser.parse(this);
		preferences = parser.getPreferences();
		preferences.setPreferencesBundle(getIntent().getExtras());
		launchUrl = parser.getLaunchUrl();
		pluginEntries = parser.getPluginEntries();

	}

	/**
	 * 创建CordovaWebView对象
	 *
	 */
	protected CordovaWebView makeWebView() {
		return new CordovaWebViewImpl(makeWebViewEngine());
	}

	/**
	 * 获取CordovaWebViewEngine对象
	 *
	 */
	@SuppressLint("SetJavaScriptEnabled")
	protected CordovaWebViewEngine makeWebViewEngine() {
		SystemWebView webView = (SystemWebView) findViewById(R.id.cordovaWebView);
		// 配置参数
		webView.getSettings().setJavaScriptEnabled(true);// 设置开启js

		SystemWebViewEngine parentEngine = new SystemWebViewEngine(webView);

		// 设置WebViewClient
		LZWebViewClient client = new LZWebViewClient(parentEngine);

		// 设置chromeClient
		LZWebChromeClient chromeClient = new LZWebChromeClient(parentEngine);
		chromeClient.init(mAbTitleBar, mContext);


		webView.setWebViewClient(client);

		webView.setWebChromeClient(chromeClient);


		return parentEngine;
	}

	/**
	 * 设置自定义标题
	 *
	 */
	public void setTitleBar(AbTitleBar mAbTitleBar, String title) {
		mAbTitleBar.setTitleText(title);
		mAbTitleBar.setTitleBarHeight(96);
		mAbTitleBar.setLogo(R.drawable.delete_n);
		mAbTitleBar.setTitleBarBackground(R.color.title_bg);
		mAbTitleBar.setTitleTextMargin(0, 0, 0, 0);
		// mAbTitleBar.setLogoLine(R.drawable.line);
		mAbTitleBar.setTitleBarGravity(Gravity.CENTER, Gravity.CENTER);// 设置文字对齐方式
		// mAbTitleBar.setVisibility(View.GONE);
		// 设置AbTitleBar在最上
	}

	protected CordovaInterfaceImpl makeCordovaInterface() {
		return new CordovaInterfaceImpl(this) {
			@Override
			public Object onMessage(String id, Object data) {
				// Plumb this to CordovaActivity.onMessage for backwards
				// compatibility
				return BaseWebViewActivity.this.onMessage(id, data);
			}
		};
	}

	/**
	 * 加载url到webview
	 */
	public void loadUrl(String url) {
		if (appView == null) {
			init();
		}

		// If keepRunning
		this.keepRunning = preferences.getBoolean("KeepRunning", true);

		appView.loadUrlIntoView(url, true);
	}

	/**
	 * Called when the system is about to start resuming a previous activity.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		LOG.d(TAG, "Paused the activity.");

		if (this.appView != null) {
			// CB-9382 If there is an activity that started for result and main
			// activity is waiting for callback
			// result, we shoudn't stop WebView Javascript timers, as activity
			// for result might be using them
			boolean keepRunning = this.keepRunning;
			this.appView.handlePause(keepRunning);
		}
	}

	/**
	 * Called when the activity receives a new intent
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		// Forward to plugins
		if (this.appView != null)
			this.appView.onNewIntent(intent);
	}

	/**
	 * Called when the activity will start interacting with the user.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		LOG.d(TAG, "Resumed the activity.");

		if (this.appView == null) {
			return;
		}
		// Force window to have focus, so application always
		// receive user input. Workaround for some devices (Samsung Galaxy Note
		// 3 at least)
		this.getWindow().getDecorView().requestFocus();

		this.appView.handleResume(this.keepRunning);
	}

	/**
	 * Called when the activity is no longer visible to the user.
	 */
	@Override
	protected void onStop() {
		super.onStop();
		LOG.d(TAG, "Stopped the activity.");

		if (this.appView == null) {
			return;
		}
		this.appView.handleStop();
	}

	/**
	 * Called when the activity is becoming visible to the user.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		LOG.d(TAG, "Started the activity.");

		if (this.appView == null) {
			return;
		}
		this.appView.handleStart();
	}

	/**
	 * The final call you receive before your activity is destroyed.
	 */
	@Override
	public void onDestroy() {
		LOG.d(TAG, "CordovaActivity.onDestroy()");
		super.onDestroy();

		if (this.appView != null) {
			appView.handleDestroy();
		}
	}

	/**
	 * Called when view focus is changed
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus && immersiveMode) {
			final int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

			getWindow().getDecorView().setSystemUiVisibility(uiOptions);
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
		// Capture requestCode here so that it is captured in the
		// setActivityResultCallback() case.
		cordovaInterface.setActivityResultRequestCode(requestCode);


		super.startActivityForResult(intent, requestCode, options);
	}

	/**
	 * Called when an activity you launched exits, giving you the requestCode
	 * you started it with, the resultCode it returned, and any additional data
	 * from it.
	 *
	 * @param requestCode
	 *            The request code originally supplied to
	 *            startActivityForResult(), allowing you to identify who this
	 *            result came from.
	 * @param resultCode
	 *            The integer result code returned by the child activity through
	 *            its setResult().
	 * @param intent
	 *            An Intent, which can return result data to the caller (various
	 *            data can be attached to Intent "extras").
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		LOG.d(TAG, "Incoming Result. Request code = " + requestCode);
		super.onActivityResult(requestCode, resultCode, intent);
		cordovaInterface.onActivityResult(requestCode, resultCode, intent);
	}

	/**
	 * Report an error to the host application. These errors are unrecoverable
	 * (i.e. the main resource is unavailable). The errorCode parameter
	 * corresponds to one of the ERROR_* constants.
	 *
	 * @param errorCode
	 *            The error code corresponding to an ERROR_* value.
	 * @param description
	 *            A String describing the error.
	 * @param failingUrl
	 *            The url that failed to load.
	 */
	public void onReceivedError(final int errorCode, final String description, final String failingUrl) {
		final BaseWebViewActivity me = this;

		// If errorUrl specified, then load it
		final String errorUrl = preferences.getString("errorUrl", null);
		if ((errorUrl != null) && (!failingUrl.equals(errorUrl)) && (appView != null)) {
			// Load URL on UI thread
			me.runOnUiThread(new Runnable() {
				public void run() {
					me.appView.showWebPage(errorUrl, false, true, null);
				}
			});
		}
		// If not, then display error dialog
		else {
			final boolean exit = !(errorCode == WebViewClient.ERROR_HOST_LOOKUP);
			me.runOnUiThread(new Runnable() {
				public void run() {
					if (exit) {
						me.appView.getView().setVisibility(View.GONE);
						me.displayError("Application Error", description + " (" + failingUrl + ")", "OK", exit);
					}
				}
			});
		}
	}

	/**
	 * Display an error dialog and optionally exit application.
	 */
	public void displayError(final String title, final String message, final String button, final boolean exit) {
		final BaseWebViewActivity me = this;
		me.runOnUiThread(new Runnable() {
			public void run() {
				try {
					AlertDialog.Builder dlg = new AlertDialog.Builder(me);
					dlg.setMessage(message);
					dlg.setTitle(title);
					dlg.setCancelable(false);
					dlg.setPositiveButton(button, new AlertDialog.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							if (exit) {
								finish();
							}
						}
					});
					dlg.create();
					dlg.show();
				} catch (Exception e) {
					finish();
				}
			}
		});
	}

	/*
	 * Hook in Cordova for menu plugins
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (appView != null) {
			appView.getPluginManager().postMessage("onCreateOptionsMenu", menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (appView != null) {
			appView.getPluginManager().postMessage("onPrepareOptionsMenu", menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (appView != null) {
			appView.getPluginManager().postMessage("onOptionsItemSelected", item);
		}
		return true;
	}

	/**
	 * Called when a message is sent to plugin.
	 *
	 * @param id
	 *            The message id
	 * @param data
	 *            The message data
	 * @return Object or null
	 */
	public Object onMessage(String id, Object data) {
		if ("onReceivedError".equals(id)) {
			JSONObject d = (JSONObject) data;
			try {
				this.onReceivedError(d.getInt("errorCode"), d.getString("description"), d.getString("url"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if ("exit".equals(id)) {
			finish();
		}
		return null;
	}

	protected void onSaveInstanceState(Bundle outState) {
		cordovaInterface.onSaveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}

	/**
	 * Called by the system when the device configuration changes while your
	 * activity is running.
	 *
	 * @param newConfig
	 *            The new device configuration
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.appView == null) {
			return;
		}
		PluginManager pm = this.appView.getPluginManager();
		if (pm != null) {
			pm.onConfigurationChanged(newConfig);
		}
	}

	/**
	 * Called by the system when the user grants permissions
	 *
	 * @param requestCode
	 * @param permissions
	 * @param grantResults
	 */
	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		try {
			cordovaInterface.onRequestPermissionResult(requestCode, permissions, grantResults);
		} catch (JSONException e) {
			LOG.d(TAG, "JSONException: Parameters fed into the method are not valid");
			e.printStackTrace();
		}

	}


}
