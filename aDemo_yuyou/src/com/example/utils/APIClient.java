package com.example.utils;

import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import android.content.Context;

public class APIClient
{
//	private static final String[] mURL = new String[]
//		{ TKConstants.Url.HOT, 
//			TKConstants.Url.TUIJIAN, 
//			TKConstants.Url.KEJI,
//			TKConstants.Url.CHUANGYE,
//			TKConstants.Url.SHUMA, 
//			TKConstants.Url.JISHU, 
//			TKConstants.Url.SHEJI, 
//			TKConstants.Url.YINGXIAO 
//		};

/*	// ����position������ͬ ����ͬ��ַ�ķ���--->����
	public static void getHomeData(Context context, int position, VolleyListener listener)
	{
		HTTPUtils.get(context, mURL[position], listener);
	}*/

	// Ŀ�ĵ�1
	public static void getMDD1(Context context, VolleyListener listener)
	{
		HTTPUtils.get(context, Constants.Url.DESTINATION1, listener);
	}
	// Ŀ�ĵ�2
	public static void getMDD2(Context context, VolleyListener listener)
	{
		HTTPUtils.get(context, Constants.Url.DESTINATION2, listener);
	}
	
	//����
	public static void getFind(Context context, VolleyListener listener)
	{
		HTTPUtils.get(context, Constants.Url.FIND, listener);
	}
	
	//�г�
	public static void getXingCheng(Context context, VolleyListener listener)
	{
		HTTPUtils.get(context, Constants.Url.XINGCHENG, listener);
	}


}
