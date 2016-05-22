package com.example.sample.fragment;

import com.example.sample.AboutYuYouActivity;
import com.example.sample.HelpAndFeedbackActivity;
import com.example.sample.R;
import com.example.sample.SettingActivity;
import com.example.sample.WeiSheQuActivity;
import com.example.sample.usermanage.LoginActivity;
import com.example.sample.usermanage.User;
import com.umeng.analytics.MobclickAgent;
import com.zxing.activity.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MyTongChengFragment extends Fragment implements OnClickListener
{

	private View layout;
	private LayoutInflater inflater;
	private RelativeLayout mLayout_MyTC_Login,mLayoutMyWallet,mLayoutAllOrders;
	private ImageView mImgQR_Code;
	private ImageView mImgLoginTX;
	private TextView mTvLoginToast;
	private RelativeLayout mLayoutSheQu, mLayoutGuwen, mLayoutWeiDian;
	private RelativeLayout mLayouComment,mLayouMyFavor,mLayouBrowsingHistory,mLayouCommonInfo;
	private RelativeLayout mLayouSetting,mLayouFeedback,mLayouTiYanDian,mLayouAboutYuyou;

	public MyTongChengFragment()
	{
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		if (layout == null)
		{
			initUI(inflater, container);

		}
		return layout;
	}

	@Override
	public void onStart()
	{
		super.onStart();
		User myUser = BmobUser.getCurrentUser(getActivity(), User.class);
		if (myUser == null)
		{
			mTvLoginToast.setText("����û�е�¼,�����¼");
			mImgLoginTX.setImageResource(R.drawable.travel_xb_head);
		} else
		{
			mImgLoginTX.setImageResource(R.drawable.icon_abouttongcheng_common);
			mTvLoginToast.setText(myUser.getUsername());
		}
	}

	private void initUI(LayoutInflater inflater, ViewGroup container)
	{
		this.inflater = inflater;
		layout = inflater.inflate(R.layout.fragment_my_tong_cheng, container, false);
		mLayout_MyTC_Login = (RelativeLayout) layout.findViewById(R.id.layout_my_login); // ��½
		mTvLoginToast = (TextView) layout.findViewById(R.id.tv_my_login_toast); // Textview��½��ʾ
		mImgLoginTX = (ImageView) layout.findViewById(R.id.img_my_head_portrait); // ��½ͷ��
		mLayoutAllOrders = (RelativeLayout) layout.findViewById(R.id.layout_my_all_orders); // ȫ������
		mLayoutMyWallet = (RelativeLayout) layout.findViewById(R.id.layout_my_wallet_button); // �ҵ�Ǯ��

		mLayoutSheQu = (RelativeLayout) layout.findViewById(R.id.layout_yuyou_bbs); // ���� ΢����
		mLayoutGuwen = (RelativeLayout) layout.findViewById(R.id.layout_yuyou_guwen); // ���� ����
		mLayoutWeiDian = (RelativeLayout) layout.findViewById(R.id.layout_yuyou_vshop); // ���� ΢��
		mLayouComment = (RelativeLayout) layout.findViewById(R.id.layout_my_comment); // �ҵĵ���
		mLayouCommonInfo = (RelativeLayout) layout.findViewById(R.id.layout_common_info); // ������Ϣ
		mLayouMyFavor = (RelativeLayout) layout.findViewById(R.id.layout_my_favor); // �ҵ��ղ�
		mLayouBrowsingHistory = (RelativeLayout) layout.findViewById(R.id.layout_browsing_history); // �����ʷ
		
		mLayouSetting = (RelativeLayout) layout.findViewById(R.id.layout_setting); // ����
		mLayouFeedback = (RelativeLayout) layout.findViewById(R.id.layout_help_or_feedback); // �����뷴��
		mLayouTiYanDian = (RelativeLayout) layout.findViewById(R.id.layout_yuyou_tiyan_shop); // ���� tiyan��
		mLayouAboutYuyou = (RelativeLayout) layout.findViewById(R.id.layout_about_yuyou); // ��������

		mImgQR_Code = (ImageView) layout.findViewById(R.id.img_my_qr_code); // ��ά��
		mLayout_MyTC_Login.setOnClickListener(this);
		mImgQR_Code.setOnClickListener(this); // ��ά�����
		
		mLayoutAllOrders.setOnClickListener(this); // �ҵ�Ǯ��
		mLayoutMyWallet.setOnClickListener(this); // �ҵ�Ǯ��

		mLayoutSheQu.setOnClickListener(this);
		mLayoutGuwen.setOnClickListener(this);
		mLayoutWeiDian.setOnClickListener(this);
		mLayouTiYanDian.setOnClickListener(this);
		
		mLayouComment.setOnClickListener(this);
		mLayouCommonInfo.setOnClickListener(this);
		mLayouMyFavor.setOnClickListener(this);
		mLayouBrowsingHistory.setOnClickListener(this);
		
		mLayouSetting.setOnClickListener(this);
		mLayouFeedback.setOnClickListener(this);
		mLayouAboutYuyou.setOnClickListener(this);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		// ����ɨ�������ڽ�������ʾ��
		if (resultCode == Activity.RESULT_OK)
		{
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			// mTextView.setText(scanResult);
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
		MobclickAgent.onResume(getContext());
	}

	@Override
	public void onPause()
	{
		super.onPause();
		MobclickAgent.onPause(getContext());
	}

	@Override
	public void onClick(View v)
	{
		int id = v.getId();
		Intent intent = new Intent();
		switch (id)
		{
		case R.id.layout_my_login: // ��½
			User myUser = BmobUser.getCurrentUser(getActivity(), User.class);
			if (myUser == null)
			{
				// ���û��¼,����ת��¼ע�����
				intent.setClass(getContext(), LoginActivity.class);
				startActivity(intent);
			} else
			{
				mTvLoginToast.setText(myUser.getUsername());
				// TODO �����¼�˾���ת���û�������Ϣ����
				// Intent intentUserInfo = new Intent(getActivity(),
				// UserInfoActivity.class);
				// startActivity(intentUserInfo);
			}

			break;
		case R.id.img_my_qr_code: // ɨ���ά��
			// ��ɨ�����ɨ����������ά��
			Intent openCameraIntent = new Intent(getContext(), CaptureActivity.class);
			startActivityForResult(openCameraIntent, 0);
			break;
		case R.id.layout_my_all_orders: // ȫ������
			intent.setClass(getContext(), LoginActivity.class);
			startActivity(intent);
			
			break;
		case R.id.layout_my_wallet_button: // �ҵ�Ǯ��
			intent.setClass(getContext(), LoginActivity.class);
			startActivity(intent);
			
			break;
		case R.id.layout_yuyou_bbs: // ����΢����
			intent.setClass(getContext(), WeiSheQuActivity.class);
			startActivity(intent);
			// ��һ�������ǵ�ǰactivity�˳�ʱ�Ķ������ڶ�����������Ҫ��ת��activity����ʱ����
//			overridePendingTransition(R.anim.activity_open_enter,R.anim.activity_close_exit);
			
			break;
		case R.id.layout_yuyou_guwen: // ���ι���
			intent.setClass(getContext(), WeiSheQuActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_yuyou_vshop: // ����΢��
			intent.setClass(getContext(), WeiSheQuActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_yuyou_tiyan_shop: // ���������
			intent.setClass(getContext(), WeiSheQuActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_my_comment: // �ҵĵ���
			intent.setClass(getContext(), LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_common_info: // ������Ϣ
			intent.setClass(getContext(), LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_my_favor: // TODO �ҵ��ղ�
			Toast.makeText(getContext(), "��ʱû���ղ�", Toast.LENGTH_SHORT).show();
			break;
		case R.id.layout_browsing_history: // �����ʷ
			intent.setClass(getContext(), LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_setting: // ����
			intent.setClass(getContext(), SettingActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_help_or_feedback: // �����뷴��
			intent.setClass(getContext(), HelpAndFeedbackActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_about_yuyou: // ����TC����
			intent.setClass(getContext(), AboutYuYouActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
