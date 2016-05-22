package com.example.sample;

import com.example.utils.Constants;
import com.xinbo.upgrade.UpgradeUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AboutYuYouActivity extends Activity implements OnClickListener
{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_yu_you);
		initUI();
	}
		private void initUI() {
			ImageView mImgBack = (ImageView) findViewById(R.id.img_weishequ_back);
			mImgBack.setOnClickListener(this);
			ImageView mImgShare = (ImageView) findViewById(R.id.img_about_yuyou_share);
			mImgShare.setOnClickListener(this);
			LinearLayout layout_DesCa = (LinearLayout) findViewById(R.id.ll_gytc);
			layout_DesCa.setOnClickListener(this);
			LinearLayout layout_Upgrade = (LinearLayout) findViewById(R.id.ll_more_update);
			layout_Upgrade.setOnClickListener(this);
		}
		
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.img_weishequ_back:
				finish();
				break;
			case R.id.img_about_yuyou_share:
				// �򵥷���(�ο�SupportV4�ķ�������) ����Ϊmob������ʹ���л��mob������֤���ͻ
				ShareCompat.IntentBuilder b = ShareCompat.IntentBuilder.from(this);
				b.setType("text/plain").setText("I'm sharing!").startChooser();
				break;
			// ���ڹ�˾����
			case R.id.ll_gytc:
				Intent intent= new Intent(this,AboutCompanyActivity.class);
				startActivity(intent);
				// ��һ�������ǵ�ǰactivity�˳�ʱ�Ķ������ڶ�����������Ҫ��ת��activity����ʱ����
				overridePendingTransition(R.anim.activity_open_enter,R.anim.activity_close_exit);
		
				break;
				
				//������
			case R.id.ll_more_update:
				UpgradeUtils.checkUpgrade(this, Constants.Url.APK_UPGRADEAPK); 
				break;
				
			default:
				break;
			}
		}
	
	
}
