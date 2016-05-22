package com.example.sample.usermanage;

import java.text.SimpleDateFormat;

import com.callhh.android.clearedittext.widget.ClearEditText;
import com.callhh.android.clearedittext.widget.PasswordIsHideEditText;
import com.example.sample.R;
import com.example.sample.fragment.MyTongChengFragment;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.demo.AccessTokenKeeper;
import com.sina.weibo.sdk.demo.WeiBoConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.xinbo.utils.RegexValidateUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.SMSSDK;

public class LoginActivity extends FragmentActivity implements OnClickListener
{
	/**
	 * ΢��SDK��ʼ�������������ҳ
	 */
	public static String APPID = "a9bdb76ca703caa2943f1f3d06f47baa";
	

	private TextView mTVRegist;
	private ImageView mImgBackButton;
	private TextView mTVLoginStatic;
	private TextView mTVLoginDynamic;
	private RelativeLayout mLoginModelStatic;
	private RelativeLayout mLoginModelDynamic;
	private ClearEditText mEdUser; // ��̬��½ �û�(�ֻ��Ż�����)
	private PasswordIsHideEditText mEdPasswordInput; // ��̬��½ ����
	private ClearEditText mLoginDynamicPhone,mLoginDynamicVerifyCode; // ��̬��½ �ֻ��ź���֤��
	private String phString;
//	private ImageView mImgSwicthButton;
//	private boolean isHidden;  // �������������״̬�Ƿ� �ɼ�������
	private Button mBtnLoginStatic;
	private Button mBtnLoginDynamic;
	private TextView mTVDynamicSendCode;
	
	


	private ImageView mImgLoginWeiBo;
	/** ��װ�� "access_token"��"expires_in"��"refresh_token"�����ṩ�����ǵĹ����� */
	private Oauth2AccessToken mAccessToken;
	/** ע�⣺SsoHandler ���� SDK ֧�� SSO ʱ��Ч */
	private SsoHandler mSsoHandler;
	private AuthInfo mAuthInfo;


	
	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Bmob.initialize(getApplicationContext(), APPID);
		initUI();
		initWeiboSDK();
	}

	
	
	
	private void initUI()
	{
		mTVLoginStatic = (TextView) findViewById(R.id.tv_login_tab_static); //��̬��½ģʽ
		mTVLoginStatic.setSelected(true);
		mTVLoginDynamic = (TextView) findViewById(R.id.tv_login_tab_dynamic); //��̬��½ģʽ
		mTVRegist = (TextView) findViewById(R.id.tv_login_register);
		mImgBackButton = (ImageView) findViewById(R.id.img_login_back_button);
		mLoginModelStatic = (RelativeLayout) findViewById(R.id.layout_login_model_static); //��̬ģʽ��½����
		mLoginModelDynamic = (RelativeLayout) findViewById(R.id.layout_login_model_fast); //��̬ģʽ��½����
		
		mEdUser = (ClearEditText) findViewById(R.id.et_user_name); //��̬�ֻ��� �����
		mEdPasswordInput = (PasswordIsHideEditText) findViewById(R.id.et_login_static_password_input); //��̬���� �����
		mBtnLoginStatic = (Button) findViewById(R.id.btn_login_static_commit); // ��̬��½ �ύ
		
		/*-----------��̬���������ݵ�½ start----------------*/
		mTVDynamicSendCode = (TextView) findViewById(R.id.tv_login_dynamic_code_send); //��̬��½-������֤��
		mBtnLoginDynamic = (Button) findViewById(R.id.btn_login_dynamic_commit); // ��̬��½ �ύ��ť
		mLoginDynamicPhone = (ClearEditText) findViewById(R.id.et_login_dynamic_account_input); // ��̬�ֻ���2
		mLoginDynamicVerifyCode = (ClearEditText) findViewById(R.id.et_login_dynamic_verificationCode); //��̬��֤��
		
		/*-----------��̬���������ݵ�½ stop----------------*/
		
		mTVLoginStatic.setOnClickListener(this);
		mTVLoginDynamic.setOnClickListener(this);
		mTVRegist.setOnClickListener(this); //ע�ᰴť ����
		mImgBackButton.setOnClickListener(this); // ��½���淵�ذ�ť�����¼�
		mEdPasswordInput.setOnClickListener(this); //��̬������������
		mBtnLoginStatic.setOnClickListener(this); // ��̬��½�����¼�
		mTVDynamicSendCode.setOnClickListener(this); // ��̬��½-������֤�� �����¼�
		mBtnLoginDynamic.setOnClickListener(this); // ��̬��½�����¼�
		
		mImgLoginWeiBo = (ImageView) findViewById(R.id.iv_login_sina); //���˵�½
		mImgLoginWeiBo.setOnClickListener(this); // weibo login
	}

	private void initWeiboSDK() {
		// ����΢��ʵ��
		// mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY,
		// Constants.REDIRECT_URL, Constants.SCOPE);
		// ������Ȩʱ���벻Ҫ���� SCOPE��������ܻ���Ȩ���ɹ�
		mAuthInfo = new AuthInfo(this, WeiBoConstants.APP_KEY, WeiBoConstants.REDIRECT_URL, WeiBoConstants.SCOPE);
		mSsoHandler = new SsoHandler(this, mAuthInfo);
		

		// �� SharedPreferences �ж�ȡ�ϴ��ѱ���� AccessToken ����Ϣ��
		// ��һ��������Ӧ�ã�AccessToken ������
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		if (mAccessToken.isSessionValid()) {
			updateTokenView(true);
		}
	}

	/**
	 * ΢����֤��Ȩ�ص��ࡣ 1. SSO ��Ȩʱ����Ҫ�� {@link #onActivityResult} �е���
	 * {@link SsoHandler#authorizeCallBack} �� �ûص��Żᱻִ�С� 2. �� SSO
	 * ��Ȩʱ������Ȩ�����󣬸ûص��ͻᱻִ�С� ����Ȩ�ɹ����뱣��� access_token��expires_in��uid ����Ϣ��
	 * SharedPreferences �С�
	 */
	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			// �� Bundle �н��� Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			// �������ȡ�û������ �绰������Ϣ
			String phoneNum = mAccessToken.getPhoneNum();
			if (mAccessToken.isSessionValid()) {
				// ��ʾ Token
				updateTokenView(false);

				// ���� Token �� SharedPreferences
				AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
				Toast.makeText(LoginActivity.this, R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
			} else {
				// ���¼�������������յ� Code��
				// 1. ����δ��ƽ̨��ע���Ӧ�ó���İ�����ǩ��ʱ��
				// 2. ����ע���Ӧ�ó��������ǩ������ȷʱ��
				// 3. ������ƽ̨��ע��İ�����ǩ��������ǰ���Ե�Ӧ�õİ�����ǩ����ƥ��ʱ��
				String code = values.getString("code");
				String message = getString(R.string.weibosdk_demo_toast_auth_failed);
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onCancel() {
			Toast.makeText(LoginActivity.this, R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(LoginActivity.this, "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * ��ʾ��ǰ Token ��Ϣ��
	 * 
	 * @param hasExisted
	 *            �����ļ����Ƿ��Ѵ��� token ��Ϣ���ҺϷ�
	 */
	private void updateTokenView(boolean hasExisted) {
		String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				.format(new java.util.Date(mAccessToken.getExpiresTime()));
		String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
//		mTokenText.setText(String.format(format, mAccessToken.getToken(), date));

		String message = String.format(format, mAccessToken.getToken(), date);
		if (hasExisted)  // ����Ѿ�����
		{
			message = getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
		}
//		mTokenText.setText(message);
	}


	/**
	 * �� SSO ��Ȩ Activity �˳�ʱ���ú��������á�
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// SSO ��Ȩ�ص�
		// ��Ҫ������ SSO ��½�� Activity ������д onActivityResults
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}

	}
	
	// ��̬��½����֤�û������˺����������Ƿ�Ϸ�
	private void loginStatic() {
		String account = mEdUser.getText().toString();
		if ("".equals(account)){
			mEdUser.setError("�������ֻ��Ż�����");
			return;
		}
		//������ʽ ��װ
		boolean isMobile = RegexValidateUtil.checkMobileNumber(account);
		boolean isEmail = RegexValidateUtil.checkEmail(account);
		if (!isMobile && !isEmail){
			mEdUser.setError("��������ȷ���ֻ��Ż�����");
			return;
		}
		String password = mEdPasswordInput.getText().toString();
		if ("".equals(account)){
			mEdPasswordInput.setError("����������");
			return;
		}
		boolean isValid = RegexValidateUtil.checkCharacter(password);
		if (!isValid){
			mEdPasswordInput.setError("������Ϸ�����");
			return;
		}
		
		// ����û�������˺ź����붼�ǺϷ��ģ��ύ��������
		signIn(account, password);
		
	}
	
	// ��̬��½����֤�û������˺����������Ƿ�Ϸ�
		private void loginDynamic() {
			String phone2 = mLoginDynamicPhone.getText().toString();
			if ("".equals(phone2)){
				mLoginDynamicPhone.setError("�������ֻ���");
				return;
			}
			//������ʽ ��װ
			boolean isMobile = RegexValidateUtil.checkMobileNumber(phone2);
			if (!isMobile){
				mLoginDynamicPhone.setError("��������ȷ���ֻ���");
				return;
			}
			String verificationCode = mLoginDynamicVerifyCode.getText().toString();
			if ("".equals(verificationCode)){
				mLoginDynamicVerifyCode.setError("��������֤��");
				return;
			}
			boolean isValid = RegexValidateUtil.checkCharacter(verificationCode);
			if (!isValid){  //�ж��Ƿ�Ϸ�
				mLoginDynamicVerifyCode.setError("������Ϸ���֤��");
				return;
			}
			
			// ����û�������˺ź����붼�ǺϷ��ģ��ύ��������
			signIn(phone2, verificationCode);
			
		}

	public void signIn(String account, String password) {
		final User user = new User(account, password);
		user.login(this, new SaveListener() {
			public void onSuccess() {
				toast("�û� :" + user.getUsername() + "�ѵ�½�ɹ�!");
				finish();
			}

			@Override
			public void onFailure(int code, String msg) {
				toast("��½ʧ��,����������!" + msg);
			}
		});
	}
	
	@Override
	public void onClick(View v)
	{
		int id = v.getId();
		Intent intent = new Intent();
		switch (id)
		{
		case R.id.img_login_back_button: // ��½���� ���ذ�ť
			finish();
			break;
		case R.id.tv_login_tab_static: // ��½���� ��̬��½��Ĭ��Ҳ�Ǿ�̬��½��
			mLoginModelStatic.setVisibility(View.VISIBLE);
			mLoginModelDynamic.setVisibility(View.GONE);
			mTVLoginStatic.setSelected(true);
			mTVLoginDynamic.setSelected(false);
			break;
		case R.id.tv_login_tab_dynamic: // ��½���� ��̬��½���������ݵ�½��
			mLoginModelStatic.setVisibility(View.GONE);
			mLoginModelDynamic.setVisibility(View.VISIBLE);
			mTVLoginStatic.setSelected(false);
			mTVLoginDynamic.setSelected(true);
			
			break;
			// ע��
		case R.id.tv_login_register:
//			Log.e("onClick", "R.id.tv_login_register");
			intent.setClass(this, RegistActivity.class);
			startActivity(intent);
			break;
			
		case R.id.btn_login_static_commit: // ��̬��½ �ύ
			loginStatic();
			break;
		case R.id.tv_login_dynamic_code_send:
			if (!TextUtils.isEmpty(mLoginDynamicPhone.getText().toString())) {
				Toast.makeText(this, "�����ѷ������ֻ��� :" + mLoginDynamicPhone.getText().toString().trim(), 1).show();
				SMSSDK.getVerificationCode("86", mLoginDynamicPhone.getText().toString());
				phString = mLoginDynamicPhone.getText().toString();
			} else {
				Toast.makeText(this, "�绰����Ϊ��", 1).show();
			}
			break;

		case R.id.btn_login_dynamic_commit:// ��̬��½ �ύ
			loginDynamic();
//			SMSSDK.submitVerificationCode("86", phString, mLoginDynamicVerifyCode.getText().toString());

			break;
			//΢����½��֤  �����¼�
		case R.id.iv_login_sina: 
			mSsoHandler.authorize(new AuthListener());
			
			break;
//		case R.id.img_register_step_three_hide_psd_switchbutton:
//			Log.e("onClick", "������ʾ������");
//			// �������������״̬�Ƿ� �ɼ�������
//			setPasswordisHide();
//			break;

		default:
			break;
		}
		
	}

	
//	private void setPasswordisHide()
//	{
//		 if (isHidden) {
//             //����EditText�ı�Ϊ�ɼ���
//			 mEdPasswordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//			 mImgSwicthButton.setImageResource(R.drawable.icon_password_show);
//         } else {
//             //����EditText�ı�Ϊ���ص�
//        	 mEdPasswordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
//        	 mImgSwicthButton.setImageResource(R.drawable.icon_password_hide);
//         }
//         isHidden = !isHidden;
//         mEdPasswordInput.postInvalidate();
//         //�л���EditText�������ĩβ
//         CharSequence charSequence = mEdPasswordInput.getText();
//         if (charSequence instanceof Spannable) {
//             Spannable spanText = (Spannable) charSequence;
//             Selection.setSelection(spanText, charSequence.length());
//         }
//
//	}
	
	
}
