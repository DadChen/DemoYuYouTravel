package com.example.utils;


//��������̬�ڲ��� ---> ������� �����Ż����� 
public class Constants
{

	public static final int TELPHONE_KEFU = 0;//�ͷ�����
	public static final int TELPHONE_DEMESTIC_USER = 1;//�����û�
	public static final int TELPHONE_FOREIGN_USER = 2; //�����û�
	public static final int DETAIL_ACTIONBAR_MENU_MYFAVOR = 0; //�ҵ��ղ�
	public static final int DETAIL_ACTIONBAR_MENU_HISTORY = 1;//�����ʷ
	public static final int DETAIL_ACTIONBAR_MENU_HOME = 2; //TC������ҳ
	public static final int PROGRESS_MAX = 100; //�ܽ���
	
	public static final class Url
	{
//		public static final String HOST = "http://169.254.184.76:8080/tongcheng_server/";
		
		// ��ţ�ƶ˴洢����
		public static final String HOST = "http://7xt68a.com2.z0.glb.clouddn.com/";
		public static final String HOME_HEADER1 = HOST + "home_header1.txt"; // ��ҳ��һ��headerView
		// ��ҳ��2��headerView ���ֳ�������
		public static final String HOME_HEADER2_LONGHOLIDAYS = HOST + "home_header2_kuailechangjia.txt"; 
		// ��ҳ��2��headerView ���ֶ�;����
		public static final String HOME_HEADER2_SHORT_TRIP = HOST + "home_header2_kuaileduantu.txt"; 
		// ��ҳ��2��headerView ������ĩ����
		public static final String HOME_HEADER2_WEEKS_HAPPY = HOST + "home_header2_kuailezhoumo.txt";
		
		public static final String DESTINATION1 = HOST + "mudidi.txt"; // Ŀ�ĵ� ���Listview
		public static final String DESTINATION2 = HOST + "mudidi2.txt"; // Ŀ�ĵ� �ұ�Listview
		public static final String FIND = HOST + "faxian.txt"; // ����
		public static final String XINGCHENG = HOST + "xingcheng.txt"; // �г�
		public static final String MY = HOST + "mine.txt"; // �ҵ� ҳ��
		
		
		public static final String MINE_SHEQU = "http://appnew.ly.com/wsq/"; // ΢����
//		public static final String MINE_SHEQU = "http://m.ly.com/selftrip/"; // ΢����
		public static final String MINE_DESCRIPTIONCOMPANY = "http://m.17u.cn/client/General/CompanyIntroduction"; //����ͬ�� ��˾����
		public static final String HOME_VISA = "http://m.ly.com/visa/"; //�����뷴��
		public static final String HOME_MOVIE_TICKET = "http://m.ly.com/movie/"; //��ҳ��ӰƱ
		public static final String HOME_PANIC_BUYING = "http://m.ly.com/panicbuying"; //����
		public static final String HOME_NEARBY = "http://zby.ly.com/zizhuyou/huodong.html"; //�����
		
		public static final String HOME_SPRING_OUTING = "http://www.yiqiyou.com/pub/TCTripDay/main/index?channelid=89&cityid=tcwvscid&wvc1=1&wvc2=1&tcwvcwl&shareTag=wxfx"; //�����ػ�
		public static final String HOME_TC_ZHUANXIAN = "http://app.ly.com/pub/apptopic/SpeciallyLine/index?channelid=22&cityId=tcwvcid&citysId=tcwvscid&wvc1=1&wvc2=1&tcwvcwl"; //TCר��
		public static final String HOME_TICKET_VIP = "http://m.ly.com/scenery/"; //��Ʊר��
		public static final String HOME_SUPER_PLAYER = "http://www.yiqiyou.com/pub/Talent/Talent/index?sortid=4&channelid=136&wvc1=1&wvc2=1&cityId=tcwvcid&citysId=tcwvscid&tcwvcwl"; //�����濧
		public static final String HOME_TEN_YUAN = "http://qianggou.ly.com/zzyapp/firstpage.aspx?channelid=137&wvc1=1&wvc2=1"; //ʮԪ
		public static final String HOME_ONE_HUNDRED_YUAN = "http://m.ly.com/guoneiyou/zhuanti/temai?channelid=138&wvc1=1&wvc2=1"; //��Ԫ
		public static final String HOME_SALE = "http://m.ly.com/dujia/zhuanti/miaosha.html?channelid=139&memberId=tcwvmid&wvc1=1&wvc2=1"; //����
		public static final String HOME_CRUISE_SALE = "http://m.ly.com/youlun/cruisesale?channelid=154&lid=63&wvc1=1&wvc2=1"; //����
		
		public static final String DESTINATION_XIAMEN = "http://m.ly.com/destination/toprecommended?city_name=%E5%8E%A6%E9%97%A8"; //�����뷴��
		public static final String MINE_HELP = "http://m.ly.com/help/index.html"; //�����뷴��
		public static final String APK_UPGRADEAPK = "http://7xszlf.com2.z0.glb.clouddn.com/upgradeapk.txt";
		
		
	}
	
	
	public static final class IntentFilter {
		public static final String UPDATA_OVERLAY_TAB1 = "overlay_tab1";
		public static final String UPDATA_OVERLAY_TAB2 = "overlay_tab2";
		public static final String UPDATA_OVERLAY_TAB3 = "overlay_tab3";
		
	}
	public static final class IntentKey {
		public static final String INTENT_XC_WEB = "listview_url";
		public static final String BANNER_POSITION_URL = "banner_position_url";
		public static final String HOMESECOND_PAGER_POSITION_URL = "homesecond_pager_position_url";
		public static final String HOMESECOND_PAGER_TITLE = "homesecond_pager_title";
		
	}
	
	
}
