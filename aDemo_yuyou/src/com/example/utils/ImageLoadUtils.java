package com.example.utils;

import com.example.database.utils.PreferenceUtils;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinbo.utils.ConnectionUtils;
import com.xinbo.utils.UILUtils;

import android.content.Context;
import android.widget.ImageView;

public class ImageLoadUtils {
	/**
	 * �Ż�ǰ����ͼƬ������
	 * 
	 * ���wifionly��ѡ�У�����ͼƬ������Ϊ�� 1) ��ǰ��������ΪWIFI ���� 2) �л���
	 * ������wifionlyδѡ�У�����ͼƬ������Ϊ�� 1) ��ǰ���� ���� 2) �л���
	 */
	/**
	 * �Ż�������ͼƬ������
	 * 
	 * 1) �л��� ���� 2) wifionlyselected && isWIFI ���� 3) wifionlyunselected &&
	 * isConnected
	 */
	public static void displayImage(Context context, String imgUrl, ImageView mImageView) {
		// ���UIL��Ļ������
		DiskCache diskCache = ImageLoader.getInstance()
			.getDiskCache();
		// diskCache.get(imgUrl)���ط�null���л���
		boolean hasCache = diskCache.get(imgUrl) != null;
		// ����û����ý�wifi����ͼƬѡ���ֵ
		boolean wifionlyselected = PreferenceUtils.readWIFIOnly(context);
		// �жϵ�ǰ���������Ƿ���WIFI
		boolean isWIFI = ConnectionUtils.isWIFI(context);
		// genymotionģ��������״̬Ĭ����wifi��Ϊ�˲��ԣ���������Ϊfalse��ģ���ֻ�����
		// boolean isWIFI = true;
		// �жϵ�ǰ�Ƿ�����
		boolean isConnected = ConnectionUtils.isConnected(context);
		boolean condition1 = hasCache;
		boolean condition2 = wifionlyselected && isWIFI;
		boolean condition3 = !wifionlyselected && isConnected;
		if (condition1 || condition2 || condition3) {
			UILUtils.displayImage(imgUrl, mImageView);
		}
	}

}
