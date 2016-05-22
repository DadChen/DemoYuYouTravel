package com.example.sample.usermanage;

import org.json.JSONObject;

import com.example.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

public class RegistStepTwoActivity extends Activity implements OnClickListener
{

	private String phString;
	private TextView mTextPhone;
	private EditText mEditVerifyPhone;
	private Button mButtonCommit;
	private ImageView mImgBack;
	// ��д�Ӷ���SDKӦ�ú�̨ע��õ���APPKEY
	private static String APPKEY = "117100c1aa6da";
	// ��д�Ӷ���SDKӦ�ú�̨ע��õ���APPSECRET
	private static String APPSECRET = "8d9dc8631a62a65c17b99c91e5728dab";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist_step_two);
		initUI();
	}

	private void initUI()
	{
		Intent intent = getIntent();
		// ���ע����洫�������ֻ��� ����
		phString = intent.getStringExtra("phoneNum");

		mTextPhone = (TextView) findViewById(R.id.text_phone);
		mTextPhone.setText(phString);

		mImgBack = (ImageView) findViewById(R.id.img_regist_step_two_back_button);
		mEditVerifyPhone = (EditText) findViewById(R.id.edit_verify_phone);
		mButtonCommit = (Button) findViewById(R.id.btn_regist_step_two_commit);

		mImgBack.setOnClickListener(this);
		mButtonCommit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.img_regist_step_two_back_button: // ���ذ�ť
			finish();
			break;
		case R.id.btn_regist_step_two_commit: // ע��ڶ��� �ύ
			SMSSDK.submitVerificationCode("86", phString, mEditVerifyPhone.getText().toString());
			initSMSSDK();
			break;
		default:
			break;
		}
	}

	private void initSMSSDK()
	{
		SMSSDK.initSDK(this, APPKEY, APPSECRET, true);
		EventHandler eh = new EventHandler()
		{
			@Override
			public void afterEvent(int event, int result, Object data)
			{
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				mHandler.sendMessage(msg);
			}
		};
		SMSSDK.registerEventHandler(eh);
	}

	private Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				Log.e("event", "event=" + event);
				if (result == SMSSDK.RESULT_COMPLETE) {
					System.out.println("--------result" + event);
					// ����ע��ɹ�����ת��ע������� ���������û�����
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// �ύ��֤��ɹ�
						Toast.makeText(getApplicationContext(), "�ύ��֤��ɹ�", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getApplicationContext(),RegistStepThreeActivity.class);
						intent.putExtra("phone", phString);
						startActivity(intent);
						finish();
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						// �Ѿ���֤
						Toast.makeText(getApplicationContext(), "��֤���Ѿ�����", Toast.LENGTH_SHORT).show();
					}
				} else {
					int status = 0;
					try {
						((Throwable) data).printStackTrace();
						Throwable throwable = (Throwable) data;

						JSONObject object = new JSONObject(throwable.getMessage());
						String des = object.optString("detail");
						status = object.optInt("status");
						if (!TextUtils.isEmpty(des)) {
							Toast.makeText(RegistStepTwoActivity.this, des, Toast.LENGTH_SHORT).show();
							return;
						}
					} catch (Exception e) {
						SMSLog.getInstance().w(e);
					}
				}

			};
		};

	

	protected void onDestroy()
	{
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	}
}
