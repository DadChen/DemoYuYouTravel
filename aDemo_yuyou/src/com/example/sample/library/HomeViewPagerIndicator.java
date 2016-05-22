package com.example.sample.library;


import com.example.utils.ScreenUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class HomeViewPagerIndicator extends View
{

	private float cx = 210;
	private float cy = 20;
	private float radiusSize = 7; // �뾶
	private int num = 5;
	private Paint mPaintfore;
	private Paint mPaint_bg;
	private Paint mPaintStroke;
	private float mOffset; //λ��
	private ViewPager mPager;

	public HomeViewPagerIndicator(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initPaint(); // ��ʼ������
	}

	private void initPaint()
	{
		mPaintfore = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintfore.setColor(Color.RED);
		mPaint_bg = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint_bg.setColor(Color.WHITE); // ��ɫ����

		mPaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintStroke.setColor(Color.BLUE); // �⻷��ɫ ��
		mPaintStroke.setStyle(Style.STROKE); // �������ÿ���
		mPaintStroke.setStrokeWidth(3); // ���
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// ���� ��ʽ�� screenwidth /2 - (count - 1) radius * 1.5
		int screenwidth = ScreenUtils.getScreenWidth(getContext());
		cx = (float) (screenwidth / 2 - (num - 1) * 1.5 * radiusSize);
		
		for (int i = 0; i < num; i++)
		{
			canvas.drawCircle(cx + i * 3 * radiusSize, cy, radiusSize, mPaintStroke);
			canvas.drawCircle(cx + i * 3 * radiusSize, cy, radiusSize, mPaint_bg); // ��Բ
		}
		canvas.drawCircle(cx + mOffset, cy, radiusSize, mPaintfore); // ��Բ

	}

	public void move(int position, float percent)
	{
		mOffset = percent * 3 * radiusSize + position * 3 * radiusSize;
		if (position == num - 1)
		{
			// �����������һ��Բʱ �������һ���
			mOffset = position * 3 * radiusSize;
		}
		invalidate();// �ػ�
	}
	
	/**
	 * ���pager��������ѭ�������ô˷���
	 * @param mPager
	 */
	public void setViewPager(ViewPager mPager) {
		this.mPager = mPager;
		num = mPager.getAdapter().getCount();
	}

	/**
	 * ���pager������ѭ�������ô˷���
	 * @param num
	 */
	public void setRealNum(int num) {
		this.num  = num;
	}
	
}
