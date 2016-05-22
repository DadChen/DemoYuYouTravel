package com.example.sample;

import com.example.utils.Constants;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class WeiSheQuActivity extends Activity implements OnClickListener
{

	private WebView mWebView;
	private ProgressBar mBar;
	private int max = Constants.PROGRESS_MAX;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wei_she_qu);
		initUI();
		initWebView();
	}

	private void initUI()
	{
		ImageView mImgBack = (ImageView) findViewById(R.id.img_weishequ_back);
		ImageView mImgShare = (ImageView) findViewById(R.id.img_weishequ_share);

		mImgBack.setOnClickListener(this); // ����
		mImgShare.setOnClickListener(this); // ����

	}

	@SuppressLint("SetJavaScriptEnabled")
	public void initWebView()
	{
		final RelativeLayout mWebProgress = (RelativeLayout) findViewById(R.id.layout_web_progress);
		mBar = (ProgressBar) findViewById(com.example.sample.R.id.progressbar_weishequ);
		mBar.setMax(max );
		mWebView = (WebView) findViewById(R.id.webview_weishequ);
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		// settings.setJavaScriptCanOpenWindowsAutomatically(true); //
		// ֧��JavaScript
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
					mWebProgress.setVisibility(View.GONE);
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

		mWebView.loadUrl(Constants.Url.MINE_SHEQU); // ָ����·��

		// ������·��
		// mWebView.loadDataWithBaseURL(null,
		// hotDetail.getArticle().getContent(), "text/html", "utf-8", null);
		// ����ͼƬ����Ӧ�������С 4.4�汾֮ǰ��Ч
		mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

	}

	@Override
	public void onClick(View v)
	{
		int id = v.getId();
		switch (id)
		{
		case R.id.img_weishequ_back:
			finish();
			break;
		case R.id.img_weishequ_share:
			// �򵥷���(�ο�SupportV4�ķ�������) ����Ϊmob������ʹ���л��mob������֤���ͻ
			ShareCompat.IntentBuilder b = ShareCompat.IntentBuilder.from(this);
			b.setType("text/plain").setText("I'm sharing!").startChooser();
			break;

		default:
			break;
		}

	}

}
