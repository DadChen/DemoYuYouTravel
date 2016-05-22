package com.example.sample.calendar;

import com.alipay.sdk.app.PayTask;
import com.example.sample.R;
import com.example.sample.calendar.AddAndSubView.OnNumChangeListener;
import com.example.utils.PayResult;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DateSelectionActivity extends AppCompatActivity implements android.view.View.OnClickListener
{

	private static final int SDK_PAY_FLAG = 1;
	private TextView textView2;
	private CalendarDay DataTime;
	private int priceChild = 0; // ��ͯƱ Ĭ��ֵ
	private int priceAdult = 1; // ����Ʊ Ĭ��ֵ
	private int SubNum;
	private Button mBtnPayNow;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_selection);
		iniUI();

	}

	private void iniUI()
	{
		textView2 = (TextView) findViewById(R.id.textView2);
		LinearLayout LinearView = (LinearLayout) findViewById(R.id.LinearView);
		LinearLayout LinearLayout01 = (LinearLayout) findViewById(R.id.LinearLayout01);
		ImageView day_exit = (ImageView) findViewById(R.id.day_exit);
		day_exit.setOnClickListener(this);
		final AddAndSubView addAndSubView1 = new AddAndSubView(DateSelectionActivity.this, 0);
		final AddAndSubView addAndSubView2 = new AddAndSubView(DateSelectionActivity.this, 1);
		MaterialCalendarView calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
		calendarView.setOnDateChangedListener(new OnDateSelectedListener()
		{
			@Override
			public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected)
			{
				DataTime = date;
				textView2.setText("��ǰ���ڣ�" + date);
			}
		});
		addAndSubView1.setOnNumChangeListener(new OnNumChangeListener()
		{
			@Override
			public void onNumChange(View view, int num)
			{
				// ��ͯƱ��
				priceChild = num;
			}
		});
		addAndSubView2.setOnNumChangeListener(new OnNumChangeListener()
		{

			@Override
			public void onNumChange(View view, int num)
			{
				// ����Ʊ��
				priceAdult = num;
			}
		});
		LinearView.addView(addAndSubView1);
		LinearLayout01.addView(addAndSubView2);
		mBtnPayNow = (Button) findViewById(R.id.btn_paynow);
		mBtnPayNow.setOnClickListener(this);
	}

	private void payNow()
	{
		if (DataTime == null)
		{
			Toast.makeText(DateSelectionActivity.this, "����ѡ������!", Toast.LENGTH_SHORT).show();
		} else
		{
			if (priceChild == 0 && priceAdult == 1)
			{
				new AlertDialog.Builder(DateSelectionActivity.this).setTitle("����ȷ��")
						.setMessage("1.�����µ�ʱ�䣺" + DataTime + "\n" + "2.�ܽ��Ϊ:" + 2999 + "Ԫ")
						.setPositiveButton("ȡ��", null).setNegativeButton("֧��", new OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								// TODO ���������������������Ʒ���ơ�����ͼ۸�
								// ����˷��������ķ���֧���������淶�Ķ�����Ϣ
								final String payInfo = "partner=\"2088101568358171\"&seller_id=\"xxx@alipay.com\"&out_trade_no=\"0819145412-6177\"&subject=\"����\"&body=\"���Բ���\"&total_fee=\"0.01\"&notify_url=\"http://notify.msp.hk/notify.htm\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"30m\"&sign=\"lBBK%2F0w5LOajrMrji7DUgEqNjIhQbidR13GovA5r3TgIbNqv231yC1NksLdw%2Ba3JnfHXoXuet6XNNHtn7VE%2BeCoRO1O%2BR1KugLrQEZMtG5jmJIe2pbjm%2F3kb%2FuGkpG%2BwYQYI51%2BhA3YBbvZHVQBYveBqK%2Bh8mUyb7GM1HxWs9k4%3D\"&sign_type=\"RSA\"";
								pay(payInfo);
							}
						}).show();
				return;
			}
			window();// �Ի���
		}

	}

	private void window()
	{
		Toast.makeText(DateSelectionActivity.this, "tanchu Dialog", Toast.LENGTH_SHORT).show();
		new AlertDialog.Builder(DateSelectionActivity.this)
		.setTitle("����ȷ��")
				.setMessage(
						"1.�����µ�ʱ�䣺" + DataTime + "\n" + "2.�ܽ��Ϊ:" + ((priceChild * 2799) + (priceAdult * 2999)) + "Ԫ")
				.setPositiveButton("ȡ��", null).setNegativeButton("֧��", new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO ���������������������Ʒ���ơ�����ͼ۸�
						// ����˷��������ķ���֧���������淶�Ķ�����Ϣ
						final String payInfo = "partner=\"2088101568358171\"&seller_id=\"xxx@alipay.com\"&out_trade_no=\"0819145412-6177\"&subject=\"����\"&body=\"���Բ���\"&total_fee=\"0.01\"&notify_url=\"http://notify.msp.hk/notify.htm\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"30m\"&sign=\"lBBK%2F0w5LOajrMrji7DUgEqNjIhQbidR13GovA5r3TgIbNqv231yC1NksLdw%2Ba3JnfHXoXuet6XNNHtn7VE%2BeCoRO1O%2BR1KugLrQEZMtG5jmJIe2pbjm%2F3kb%2FuGkpG%2BwYQYI51%2BhA3YBbvZHVQBYveBqK%2Bh8mUyb7GM1HxWs9k4%3D\"&sign_type=\"RSA\"";
						pay(payInfo);
					}
				}).show();
	}

	private void pay(final String payInfo)
	{
		new Thread()
		{
			public void run()
			{
				// ����PayTask ����
				PayTask alipay = new PayTask(DateSelectionActivity.this);
				// ����֧���ӿڣ���ȡ֧�����
				String result = alipay.pay(payInfo, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		}.start();
	}

	private Handler mHandler = new Handler()
	{
		@SuppressWarnings("unused")
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case SDK_PAY_FLAG:
			{
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * ͬ�����صĽ��������õ�����˽�����֤����֤�Ĺ����뿴https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) �����̻������첽֪ͨ
				 */
				String resultInfo = payResult.getResult();// ͬ��������Ҫ��֤����Ϣ

				String resultStatus = payResult.getResultStatus();
				// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
				if (TextUtils.equals(resultStatus, "9000"))
				{
					Toast.makeText(DateSelectionActivity.this, "֧���ɹ�", Toast.LENGTH_SHORT).show();
				} else
				{
					// �ж�resultStatus Ϊ��"9000"��������֧��ʧ��
					// "8000"����֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
					if (TextUtils.equals(resultStatus, "8000"))
					{
						Toast.makeText(DateSelectionActivity.this, "֧�����ȷ����", Toast.LENGTH_SHORT).show();

					} else
					{
						// ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
						Toast.makeText(DateSelectionActivity.this, "֧��ʧ��", Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.day_exit:
			finish();
			break;
		case R.id.btn_paynow:
			payNow();
			break;

		default:
			break;
		}
	}
}
