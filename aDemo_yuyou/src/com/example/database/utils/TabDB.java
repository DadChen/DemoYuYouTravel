package com.example.database.utils;

import com.example.sample.R;
import com.example.sample.fragment.DestinationFragment;
import com.example.sample.fragment.FindFragment;
import com.example.sample.fragment.HomeFragment;
import com.example.sample.fragment.MyTongChengFragment;
import com.example.sample.fragment.XingChengFragment;

public final class TabDB
{
	public TabDB()
	{
	};

	// public static String[] getTabsTxt(){
	// String[] tabs={"����","�Ķ�","����","����"," ��"};
	// return tabs;
	// }
	// �����Դ�ļ���ͼƬ
	public static int[] getTabsImges()
	{
		int[] images =
		{ R.drawable.selector_btn_home_main, R.drawable.selector_btn_home_destination,
				R.drawable.selector_btn_home_find, R.drawable.selector_btn_home_xingcheng,
				R.drawable.selector_btn_home_mytc };
		return images;
	}

	// ����л���fragemnt
	@SuppressWarnings("rawtypes")
	public static Class[] getFragments()
	{
		Class[] clas =
		{ HomeFragment.class, 
				DestinationFragment.class,
				FindFragment.class, 
				XingChengFragment.class, 
				MyTongChengFragment.class };
		return clas;
	}

}
