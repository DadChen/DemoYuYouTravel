package com.example.sample;

import com.example.database.utils.TabDB;
import com.umeng.analytics.MobclickAgent;
import com.igexin.sdk.PushManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity
{

	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
		initData();
	}

	@SuppressLint("InflateParams")
	private void initUI()
	{
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		// ��Ƭ�� Fragment ������Activity��
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		LayoutInflater inflater = getLayoutInflater(); // ��ò��ֹ�����
		for (int i = 0; i < TabDB.getTabsImges().length; i++)
		{
			View indicatorView = inflater.inflate(R.layout.indicator_item, null);
			ImageView imageView = (ImageView) indicatorView.findViewById(R.id.ima_indicator);
			imageView.setImageResource(TabDB.getTabsImges()[i]);
			// newTabSpec("simple") ���ñ�ǩ // setIndicator("Simple") ����ָʾ�� //
			// ����ָʾ����ͼƬ
			// ���ܻᱨ�Ƿ�״̬�쳣����ǩд�����û�г�ʼ��
			mTabHost.addTab(mTabHost.newTabSpec("" + TabDB.getTabsImges()[i]).setIndicator(indicatorView),
					TabDB.getFragments()[i], null);
		}
	}

	private void initData()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Umeng ��������ͳ�Ƶ���
	public void onResume()
	{
		super.onResume();
		MobclickAgent.onResume(this);
	}
	// Umeng ��������ͳ�Ƶ���
	public void onPause()
	{
		super.onPause();
		MobclickAgent.onPause(this);
	}

	
	long preTime;
	// ˫���˳�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && 
				event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if (System.currentTimeMillis() - preTime < 2000) // �������ڣ��˳�
			{
				finish();
			}else
			Toast.makeText(this, "�ٰ�һ���˳�Ӧ��", Toast.LENGTH_SHORT).show();
			// System.exit(0);
			preTime = System.currentTimeMillis(); // ����һ�ε����ʱ�䱣�� 
		}
		return true;// ����ϵͳ����
	}

	
}
