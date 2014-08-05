package com.locuslabs.notiphisample;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NotificationCenterWebView extends Activity{

	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notiphi_webview_layout);
		WebView webView = (WebView)findViewById(R.id.notiphi_web_view);
		WebSettings webSettings = webView.getSettings();
		webSettings.setBuiltInZoomControls(true);
		webView.setWebViewClient(new NotiphiCallBack());
		webView.loadUrl("http://notiphi.com");
	}
	
	@Override
	public void onStart(){
		super.onStart();
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	@Override
	public void onPause(){
		super.onPause();
	}
	
	private class NotiphiCallBack extends WebViewClient{
		@Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }
	}
}
