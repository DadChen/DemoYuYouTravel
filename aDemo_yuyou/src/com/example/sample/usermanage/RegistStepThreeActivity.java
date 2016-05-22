package com.example.sample.usermanage;

import com.example.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

public class RegistStepThreeActivity extends Activity implements OnClickListener
{

	private EditText mEdPasswordInput;
//	private boolean isHidden;  // �������������״̬�Ƿ� �ɼ�������
	private ImageView mImgBack;
private Button mBtnRegistSubmit;
private String userPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist_step_three);
		initUI();
	}

	private void initUI()
	{
		Intent intent = getIntent(); // �����ͼ �����û��ֻ���
		userPhone = intent.getStringExtra("phone");
		mImgBack = (ImageView) findViewById(R.id.img_regist_step_three_back_button);
		mEdPasswordInput = (EditText) findViewById(R.id.et_register_step_three_password_input); //�û�����������
		mBtnRegistSubmit = (Button) findViewById(R.id.btn_register_step_three_submit); // �ύ��ť
		
		mImgBack.setOnClickListener(this);
		mEdPasswordInput.setOnClickListener(this);
		mBtnRegistSubmit.setOnClickListener(this); // ע���ύ  ����
		
		
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id)
		{
		case R.id.img_regist_step_three_back_button: // ���ذ�ť ���ص���¼����
			startActivity(new Intent(RegistStepThreeActivity.this, LoginActivity.class));
			finish();
			break;
			//ע�� �ύ
		case R.id.btn_register_step_three_submit:

			final User user= new User(userPhone, mEdPasswordInput.getText().toString());
			user.signUp(RegistStepThreeActivity.this,new SaveListener(){
				@Override
				public void onSuccess() {
					Toast.makeText(RegistStepThreeActivity.this, "ע��ɹ�", Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(RegistStepThreeActivity.this,LoginActivity.class);
					// ������ͨ����ʽ��ͼ������½����
					intent.putExtra("password", mEdPasswordInput.getText().toString());
					startActivity(intent);
					try {
						Thread.sleep(2000);
						finish();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					Toast.makeText(RegistStepThreeActivity.this, "ע��ʧ��"+arg0+arg1, Toast.LENGTH_SHORT).show();
				}
			} );
		
			break;
//		case R.id.img_register_step_three_hide_psd_switchbutton:
//			// �������������״̬�Ƿ� �ɼ�������
//			setPasswordisHide();
//			break;

		default:
			break;
		}
		
	}

//	// �����Զ���ؼ�ʵ�ִ˹���
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
