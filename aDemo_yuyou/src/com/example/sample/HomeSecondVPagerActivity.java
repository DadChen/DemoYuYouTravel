package com.example.sample;

import com.example.utils.Constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeSecondVPagerActivity extends Activity implements OnClickListener
{
	private WebView mWebView;
	private ProgressBar mBar;
	private int max = Constants.PROGRESS_MAX;
	private RelativeLayout mWebProgressLoading;
	private RelativeLayout mActionbarBack;
	private TextView mActionbarTitle;
	private String girdPagerUrl;
	private String gridPagerTitle;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_second_vpager);
		initUI();
		initWebView();
	}


	private void initUI()
	{

		Intent intent = getIntent();
		girdPagerUrl = intent.getStringExtra(Constants.IntentKey.HOMESECOND_PAGER_POSITION_URL);
		gridPagerTitle = intent.getStringExtra(Constants.IntentKey.HOMESECOND_PAGER_TITLE);
		
		mWebProgressLoading = (RelativeLayout) findViewById(R.id.layout_web_progress);
		mBar = (ProgressBar) findViewById(com.example.sample.R.id.progressbar_home_secondpager);
		mActionbarTitle = (TextView) findViewById(R.id.tv_actionbar_title);
		mActionbarTitle.setText(gridPagerTitle);
		mActionbarBack = (RelativeLayout) findViewById(R.id.layout_actionbar_back);
		mActionbarBack.setOnClickListener(this);
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void initWebView()
	{
		
		mWebView = (WebView) findViewById(R.id.webview_home_secondpager); 
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
//		settings.setJavaScriptCanOpenWindowsAutomatically(true); // ֧��JavaScript
		// settings.setTextSize(TextSize.LARGER); // ö�ٷ�ʽ ����webView�����С

		// 2) ��ֹ���������������
		mWebView.setWebChromeClient(new WebChromeClient()
		{

			@Override
			public void onProgressChanged(WebView view, int newProgress)
			{
				// ��� ���������htm����
				mBar.setProgress(newProgress);
				if (newProgress == max)
				{ // ���ؽ���100%ʱ�����������غ͵ȴ�����Ӱ�أ�web������ʾ����
					mBar.setVisibility(View.GONE);
					mWebProgressLoading.setVisibility(View.GONE);
					mWebView.setVisibility(View.VISIBLE);
				}
				super.onProgressChanged(view, newProgress);
			}
		});
		mWebView.setWebViewClient(new WebViewClient()
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				// �����ַ�仯
				// Log.e("WebViewClient", "UrlLoading" + url);
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		mWebView.loadUrl(girdPagerUrl); //ָ����·��
		mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);// ����ͼƬ����Ӧ�������С 4.4�汾֮ǰ��Ч
		// ������·��
//		mWebView.loadDataWithBaseURL(null, hotDetail.getArticle().getContent(), "text/html", "utf-8", null);
	}

	@Override
	public void onClick(View v)
	{
		// TODO 
		int id = v.getId();
		switch (id)
		{
		case R.id.layout_actionbar_back:
			finish();
			break;

		default:
			break;
		}
		
	}
}
