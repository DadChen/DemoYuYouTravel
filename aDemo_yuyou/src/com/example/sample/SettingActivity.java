package com.example.sample;

import java.io.File;

import com.example.sample.usermanage.LoginActivity;
import com.example.sample.usermanage.User;
import com.xinbo.utils.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;

public class SettingActivity extends Activity implements OnClickListener
{

	private TextView mTV_cache_data;
	private File cacheDir;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initUI();
	}

	private void initUI()
	{
		RelativeLayout mSettingBack = (RelativeLayout) findViewById(R.id.layout_setting_back);
		RelativeLayout mClearCache = (RelativeLayout) findViewById(R.id.layout3_clear_img_cache);
		RelativeLayout mExit = (RelativeLayout) findViewById(R.id.shezhi_exit); // �˳���½
		
		mSettingBack.setOnClickListener(this); // ���� ����
		mClearCache.setOnClickListener(this); // �������
		mExit.setOnClickListener(this); // �˳���½

		mTV_cache_data = (TextView) findViewById(R.id.tv_cache_data);
		cacheDir = getCacheDir();
		mTV_cache_data.setText("(" + FileUtils.size(cacheDir) + ")");

	}

	// @Override //����Ҫʱȥ�� ���߿ͻ��˻�������ֿؼ�
	// public boolean onCreateOptionsMenu(Menu menu)
	// {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.settins, menu);
	// return true;
	// }
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id)
		{
		case R.id.action_settings:
			return true;
		case android.R.id.home: // ���ؼ�����
			// Intent intent = new Intent(this, MainActivity.class);
			// startActivity(intent );
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v)
	{
		int id = v.getId();
		switch (id)
		{
		case R.id.layout_setting_back: // ���ذ�ť �ر�ҳ��
			finish();
			break;
		case R.id.shezhi_exit: // �˳���½���˺�ע��ǰ�ж��û��Ƿ��ѵ�¼
			User myUser = BmobUser.getCurrentUser(this, User.class);
			if (myUser == null)
			{
				Toast.makeText(this, "����ǰδ��¼", Toast.LENGTH_SHORT).show();
				// ���û��¼,����ת��¼ע�����
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else
			{
				// ���裺����˳�-����AlertDialog�Ի���ȡ����ȷ�ϣ�- ���ȷ�ϣ��ص��ҵ�ҳ�棬��ʾע���ɹ�����½ͷ���tv��ʾҲҪ��Ӧ����
				BmobUser.logOut(this);
			}
			finish();
			break;

		case R.id.layout3_clear_img_cache: // ���ͼƬ����
			FileUtils.delFilesFromPath(cacheDir);
			Toast.makeText(this, "��������ɹ�", Toast.LENGTH_SHORT).show();
			mTV_cache_data.setText("(0KB)");
			break;

		// case R.id.layout2_share_setting: // ��������
		// startActivity(new Intent(this, SetShareActivity.class));
		// // ��һ�������ǵ�ǰactivity�˳�ʱ�Ķ������ڶ�����������Ҫ��ת��activity����ʱ����
		// overridePendingTransition(R.anim.activity_open_enter,R.anim.activity_close_exit);
		// break;

		default:
			break;
		}
	}

}
