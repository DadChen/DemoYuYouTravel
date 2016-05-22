package com.example.BmobApplication;

import android.app.Application;
import android.content.Context;
import cn.bmob.v3.Bmob;

import com.baidu.mapapi.SDKInitializer;
import com.bmob.BmobConfiguration;
import com.bmob.BmobPro;
import com.igexin.sdk.PushManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * @ClassName: BmobApplication
 * @Description:
 * @author smile
 * @date 2014-12-9 ����10:29:50
 */
public class BmobApplication extends Application
{

	/**
	 * ΢��SDK��ʼ�������������ҳ
	 */
	public static String APPID = "a9bdb76ca703caa2943f1f3d06f47baa";

	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		initImageLoader(getApplicationContext());
		initConfig(getApplicationContext());
		// Bmob�ƶ˷�����
		Bmob.initialize(getApplicationContext(), APPID);
		// ���� ��Ϣ���� ��ʼ��SDK
		PushManager.getInstance().initialize(this.getApplicationContext());
		// ��ʹ�ðٶȵ�ͼ SDK �����֮ǰ��ʼ�� context ��Ϣ������ ApplicationContext
		SDKInitializer.initialize(this);
	}

	/**
	 * ��ʼ���ļ�����
	 * 
	 * @param context
	 */
	public static void initConfig(Context context)
	{
		BmobConfiguration config = new BmobConfiguration.Builder(context).customExternalCacheDir("Smile").build();
		BmobPro.getInstance(context).initConfig(config);
	}

	public static void initImageLoader(Context context)
	{
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator()).diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// .writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
