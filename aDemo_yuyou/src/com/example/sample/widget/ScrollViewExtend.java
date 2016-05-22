package com.example.sample.widget;
//widget�����
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

// �����  ����һ����̳�ScrollView  
// ��д2�����ϵĹ��췽��	; 
// ����������������  1������һ���ӿ�	2�������ӿ����͵ĳ�Ա����	3������ע������¼��ķ���
public class ScrollViewExtend extends ScrollView
{

	private OnScrollChangeListener mListener; // 2�������ӿ����͵ĳ�Ա����

	public ScrollViewExtend(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	//1������һ���ӿ�
	public interface OnScrollChangeListener{
		// �ı����������
		public void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY);

	}
	
	// 3������ע������¼��ķ���
	public void setOnScrollChangeListener(OnScrollChangeListener l)
	{
		mListener = l;
	}
	
		
	// 3) ��ĳ��ʱ���£����ýӿڶ���ķ����������¼�֪ͨ
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt)
	{
		// ���¼�����ʱ  ֪ͨ�ӿ�ʵ�ֵ�����
		super.onScrollChanged(l, t, oldl, oldt);
		if (mListener != null)
		{
			mListener.onScrollChange(l, t, oldl, oldt);
		}
	}
	
}
