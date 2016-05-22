package com.example.sample.fragment;

import java.util.List;

import com.android.volley.VolleyError;
import com.example.database.utils.UtilsSharePerference;
import com.example.dingwei.CitiesActivity;
import com.example.model.header1.CellList;
import com.example.model.header1.HomeHeader1;
import com.example.model.header1.ItemList;
import com.example.model.header1.Layout;
import com.example.model.header2.HomeHeader2Kuailechangjia;
import com.example.model.header2.Label;
import com.example.model.header2.RecommendList;
import com.example.model.header2.SubMenuList;
import com.example.sample.DestinationHotCtiyActivity;
import com.example.sample.GlobalSearchesActivity;
import com.example.sample.HomeDetailActivity;
import com.example.sample.HomeMoviceTicketActivity;
import com.example.sample.HomeNearByActivity;
import com.example.sample.HomePanicBuyingActivity;
import com.example.sample.HomeSuperPlayerActivity;
import com.example.sample.HomeTenYuanTravelActivity;
import com.example.sample.HomeVisaActivity;
import com.example.sample.R;
import com.example.sample.library.HomeViewPagerIndicator;
import com.example.sample.library.HomeViewPagerIndicator2;
import com.example.utils.Constants;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.iwgang.countdownview.CountdownView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HomeFragment extends Fragment implements OnClickListener
{

	private View layout;
	private LayoutInflater mInflater;
	private ListView mHomeListView;

	private boolean isDragging; // �û��Ƿ񻬶�
	private ViewPager mHomeTopPager;
	private ViewPager mHomeSecondPager;
	private static final int DILAY_MILLIS = 2500; // �ֲ�˯��
	private static final int MAX_LENGTH = 400000; // �ֲ�����
	private PullToRefreshListView mPtrListView; // �Զ��� ����ˢ�����
	public boolean isPullDown; // �����Ƿ�������״̬
	private View mHeaderView1;
	private GridView mOverlayCard2;
	private LinearLayout mOverlayCard1;
	private HomeViewPagerIndicator mIndicator1;
	private HomeViewPagerIndicator2 mIndicator2;
	private List<ItemList> mSecondBannerItemList;
	private List<ItemList> mFirstBannerItemList;
	private FragmentManager fm;
	private List<ItemList> mActivityThemeData; //�����������ݽ���
	private TextView mTvActivityTheme1_title;
	private TextView mTvActivityTheme1_subTitle;
	private ImageView mImgActivityTheme1;
	private TextView mTvActivityTheme2_title;
	private TextView mTvActivityTheme2_subTitle;
	private ImageView mImgActivityTheme2;
	private TextView mTvActivityTheme3_title;
	private TextView mTvActivityTheme3_subTitle;
	private ImageView mImgActivityTheme3;
	private TextView mTvActivityTheme4_title;
	private TextView mTvActivityTheme4_subTitle;
	private ImageView mImgActivityTheme4;
	
	private List<SubMenuList> subMenuListLongHoliday; //���ֳ���  �Ӳ˵��б�����
	private List<RecommendList> recommendListLongHoliday; // ��ҳListview����   ���ֳ���     �Ƽ��б�
	private TextView tvSubMenuTitle;
	private SubMenuList subMenuData;
	private GridView mGvLongHolidaySubtitle;
	private ListView menuList;
	private PhoneAdapter mPhoneAdapter;
	private String[] menu_title = { "���߿ͷ�", "�����û�", "�����û�" };
	private String[] menu_subtitle = { "��ʱ��Ч�����ٽ����������", "����4007-997-922������ȡ��;��", "+86-512-82209000" };
	int[] menu_image= {R.drawable.icon_service_online,R.drawable.icon_service_common_message_rest,R.drawable.icon_service_greenphone};
	private LinearLayout mlayout_kl_longHoliday;
	private LinearLayout mlayout_kl_weeks;
	private LinearLayout mlayout_kl_shortTrip;
	private LinearLayout mOverlay1_kl_weeks;
	private LinearLayout mOverlay1_kl_shortTrip;
	private LinearLayout mOverlay1_kl_longHoliday;
	private MyReceiver receiver; // �㲥����
	private TextView mTvCtiy_LBS;
	private TextView mTvTop_Search;
	
	public HomeFragment()
	{
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		if (layout == null)
		{
			initUI(inflater, container);
			initDataHeader1();
			initDataHeader2();
		}
		return layout;
	}


	private void initDataHeader1()
	{
		HTTPUtils.get(getContext(), Constants.Url.HOME_HEADER1, new VolleyListener()
		{

			@Override
			public void onResponse(String arg0)
			{
				HomeHeader1 homeHeader1 = GsonUtils.parseJSON(arg0, HomeHeader1.class);
				List<Layout> layout2 = homeHeader1.getResponse().getBody().getLayout();
				List<CellList> cellList = layout2.get(0).getCellList();
				mFirstBannerItemList = cellList.get(0).getItemList();
				
				CellList cellType = cellList.get(1); //�ڶ���banner����
				mSecondBannerItemList = cellType.getItemList();
				
				mHomeTopPager.setAdapter(new BannerAdapter(fm));
				mHomeSecondPager.setAdapter(new SecondPagerAdapter(fm));
				
				CellList cellActivityTheme = cellList.get(6);
				mActivityThemeData = cellActivityTheme.getItemList();//�����������ݽ���
				ItemList mListDataTheme1 = mActivityThemeData.get(0); 
				mTvActivityTheme1_title.setText(mListDataTheme1.getTitle()); // �������ñ���
//				mTvActivityTheme1_title.setTextColor(Color.parseColor(mListDataTheme1.getTitleColor()));// ���ñ�����ɫ
				mTvActivityTheme1_subTitle.setText(mListDataTheme1.getSubTitle()); // �������ø�����
				UILUtils.displayImage(mListDataTheme1.getIconUrl(), mImgActivityTheme1); // ������ʾͼƬ
				
				ItemList mListDataTheme2 = mActivityThemeData.get(1); 
				mTvActivityTheme2_title.setText(mListDataTheme2.getTitle()); // �������ñ���
//				mTvActivityTheme2_title.setTextColor(Color.parseColor(mListDataTheme2.getTitleColor()));// ���ñ�����ɫ
				mTvActivityTheme2_subTitle.setText(mListDataTheme2.getSubTitle()); // �������ø�����
				UILUtils.displayImage(mListDataTheme2.getIconUrl(), mImgActivityTheme2); // ������ʾͼƬ
				
				ItemList mListDataTheme3 = mActivityThemeData.get(2); 
				mTvActivityTheme3_title.setText(mListDataTheme3.getTitle()); // �������ñ���
//				mTvActivityTheme3_title.setTextColor(Color.parseColor(mListDataTheme3.getTitleColor()));// ���ñ�����ɫ
				mTvActivityTheme3_subTitle.setText(mListDataTheme3.getSubTitle()); // �������ø�����
				UILUtils.displayImage(mListDataTheme3.getIconUrl(), mImgActivityTheme3); // ������ʾͼƬ
				
				ItemList mListDataTheme4 = mActivityThemeData.get(3); 
				mTvActivityTheme4_title.setText(mListDataTheme4.getTitle()); // �������ñ���
//				mTvActivityTheme4_title.setTextColor(Color.parseColor(mListDataTheme4.getTitleColor()));// ���ñ�����ɫ
				mTvActivityTheme4_subTitle.setText(mListDataTheme4.getSubTitle()); // �������ø�����
				UILUtils.displayImage(mListDataTheme4.getIconUrl(), mImgActivityTheme4); // ������ʾͼƬ
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0)
			{
				Log.e("onErrorResponse", "error = " + arg0.getMessage());
			}
		});
		
	}

	private void initDataHeader2()
	{
		HTTPUtils.get(getContext(), Constants.Url.HOME_HEADER2_LONGHOLIDAYS, new VolleyListener()
		{
			

			@Override
			public void onResponse(String arg0)
			{
				HomeHeader2Kuailechangjia homeHeader2Kuailechangjia = GsonUtils.parseJSON(arg0, HomeHeader2Kuailechangjia.class);
				subMenuListLongHoliday = homeHeader2Kuailechangjia.getResponse().getBody().getSubMenuList();
				mOverlayCard2.setAdapter(new OverlayLongHolidaySubtitleGridAdapter());
				mGvLongHolidaySubtitle.setAdapter(new LongHolidaySubtitleGridAdapter());
				// ��ҳListview����     ���ֳ���  �Ƽ��б�
				recommendListLongHoliday = homeHeader2Kuailechangjia.getResponse().getBody().getRecommendList();
				mHomeListView.setAdapter(new HomeAdapter()); // Ϊ��ҳListView����������
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0)
			{
				Log.e("onErrorResponse", "error = " + arg0.getMessage());
			}
		});
	}
	
	
	private void initUI(LayoutInflater inflater, ViewGroup container)
	{
		mInflater = inflater;
		layout = inflater.inflate(R.layout.fragment_home, container, false);
		ImageView mImgPhoneCall = (ImageView) layout.findViewById(R.id.img_home_top_phone);
		mImgPhoneCall.setOnClickListener(this); //��ҳ���� �绰�ͷ�
		mTvCtiy_LBS = (TextView) layout.findViewById(R.id.tv_home_top_city);
		mTvCtiy_LBS.setOnClickListener(this); //��ҳ���� ����ѡ��-��λ
		mTvTop_Search = (TextView) layout.findViewById(R.id.tv_home_top_search);
		mTvTop_Search.setOnClickListener(this); //��ҳ���� ȫ������
		
	        
		View headview = initListView(mInflater);
		initBannerUI(headview);

	}

	private View initListView(LayoutInflater mInflater)
	{
		// ����ˢ�����
		mPtrListView = (PullToRefreshListView) layout.findViewById(R.id.pull_refresh_list);
		mHomeListView = mPtrListView.getRefreshableView();
		mOverlayCard1 = (LinearLayout) layout
				.findViewById(R.id.home_overlay_card1);//��ҳ�������1
		mOverlay1_kl_weeks = (LinearLayout) mOverlayCard1.findViewById(R.id.ll_tab1_overlay1); // ����tab1 ������ĩ
		mOverlay1_kl_shortTrip = (LinearLayout) mOverlayCard1.findViewById(R.id.ll_tab2_overlay1);// ����tab2 ���ֶ�;
		mOverlay1_kl_longHoliday = (LinearLayout) mOverlayCard1.findViewById(R.id.ll_tab3_overlay1);// ����tab3 ���ֳ���
		mOverlay1_kl_weeks.setOnClickListener(this); // ����tab1 ������ĩ �����¼�
		mOverlay1_kl_shortTrip.setOnClickListener(this);
		mOverlay1_kl_longHoliday.setOnClickListener(this);
		mOverlay1_kl_longHoliday.setSelected(true);
		
		mOverlayCard2 = (GridView) layout
				.findViewById(R.id.gridview_home_overlay_card2);
		mOverlayCard2.setNumColumns(5); //�����������GridView����
		
		// Ϊ�໬�˵����HeaderView��FooterView Ҫ�ڵ���listview��setAdapter()֮ǰ
		View mHeaderView = initHeaderView(mInflater);
		mPtrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
		{
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				isPullDown = true;
				new GetDataTask().execute();
			}

			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				isPullDown = false;
				new GetDataTask().execute();
			}
		});
		// ��ҳListView����¼�����
		mHomeListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub
				position = position -mHomeListView.getHeaderViewsCount();
				// �����ΪListView���HeaderView Ҫ����һ��position
				if (position < 0)
				{
					return;	
				}
					startActivity(new Intent(getActivity(), HomeDetailActivity.class));
//				switch (position)
//				{
////				case 0:
////					startActivity(new Intent(getActivity(), DestinationHotCtiyActivity.class));
////					break;
////				case 1:
////					startActivity(new Intent(getActivity(), HomeTenYuanTravelActivity.class));
////					break;
//
//				default:
//					break;
//				}
			}
		});
		
		return mHeaderView;
	}

	private View initHeaderView(LayoutInflater mInflater)
	{
		
		initHeader1(mInflater); // ��ʼ��HeaderView1  �ؼ�
		initHeader2(mInflater); // ��ʼ��HeaderView2  �ؼ�
		initHeader3(mInflater); // ��ʼ��HeaderView3  �ؼ�
		
		View mHeaderView4 = mInflater.inflate(R.layout.headerview4_home, null);
		mHomeListView.addHeaderView(mHeaderView4);
		mGvLongHolidaySubtitle = (GridView) mHeaderView4.findViewById(R.id.gridview_home_theme_trip_kl_subtitle);
		mGvLongHolidaySubtitle.setNumColumns(5);
		
		
		// Ϊ��ҳListView���û��������¼�
		mHomeListView.setOnScrollListener(new OnScrollListener()
		{
			int lastVisiblePosition = 0;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
			/*	if (firstVisibleItem >= 3)
				{
					mOverlayCard1.setVisibility(View.VISIBLE);
					if (firstVisibleItem >= 3)
					{
						if (firstVisibleItem >= 5)
						{
							mOverlayCard2.setVisibility(View.GONE);
						}else
						{
							mOverlayCard2.setVisibility(View.VISIBLE);
						}
//						mOverlayCard2.setVisibility(View.VISIBLE);
					} else
					{
						mOverlayCard2.setVisibility(View.GONE);
					}
				} else
				{
					mOverlayCard1.setVisibility(View.GONE);
				}*/
				
				if (firstVisibleItem > 2)
				{
					mOverlayCard1.setVisibility(View.VISIBLE);
					mOverlayCard2.setVisibility(View.VISIBLE);
					if (firstVisibleItem > 4)
					{
						mOverlayCard2.setVisibility(View.GONE);
						if (firstVisibleItem < lastVisiblePosition)
						{
							mOverlayCard2.setVisibility(View.VISIBLE);
							return;
						}
					}
				} else
				{
					mOverlayCard1.setVisibility(View.GONE);
					mOverlayCard2.setVisibility(View.GONE);
				}
				lastVisiblePosition = firstVisibleItem;
			}
		});
//		gridAdapter = new MyGridAdapter(); // ΪGridView����������
//		mOverlayCard2.setAdapter(gridAdapter);
		return mHeaderView1;
	}

	private void initHeader1(LayoutInflater mInflater)
	{
		mHeaderView1 = mInflater.inflate(R.layout.headerview1_home, null);
		mHomeListView.addHeaderView(mHeaderView1);
		
		CountdownView mCvCountdownViewTest1 = (CountdownView)mHeaderView1.findViewById(R.id.cv_countdownViewTest2);
		mCvCountdownViewTest1.setTag("test1"); // ����ʱ
        long time1 = (long)5 * 60 * 60 * 1000;
        mCvCountdownViewTest1.start(time1);
        for (int time=0; time<1000; time++) {
        	mCvCountdownViewTest1.updateShow(time);
		}
        RelativeLayout mLayoutPanicBuying = (RelativeLayout) mHeaderView1.findViewById(R.id.layout_home_xianshi_qianggou);
        
		RelativeLayout mLayoutHaiWaiWanLe = (RelativeLayout) mHeaderView1.findViewById(R.id.layout_home_haiwaiwanle);
		RelativeLayout mLayoutVisa = (RelativeLayout) mHeaderView1.findViewById(R.id.layout_home_qianzheng);
		RelativeLayout mLayoutWifi = (RelativeLayout) mHeaderView1.findViewById(R.id.layout_home_allworld_wifi);
		RelativeLayout mLayoutMovieTicket = (RelativeLayout) mHeaderView1.findViewById(R.id.layout_home_movie_ticket);
		RelativeLayout mLayoutLiCai = (RelativeLayout) mHeaderView1.findViewById(R.id.layout_home_licai);
		RelativeLayout mLayoutCarTicket = (RelativeLayout) mHeaderView1.findViewById(R.id.layout_home_car_ticket);
		mLayoutPanicBuying.setOnClickListener(this); //����
		mLayoutHaiWaiWanLe.setOnClickListener(this); //��������  �����¼�
		mLayoutVisa.setOnClickListener(this); //ǩ֤  �����¼�
		mLayoutWifi.setOnClickListener(this); //ȫ��wifi  �����¼�
		mLayoutMovieTicket.setOnClickListener(this); //��ӰƱ  �����¼�
		mLayoutLiCai.setOnClickListener(this); //���  �����¼�
		mLayoutCarTicket.setOnClickListener(this); //����Ʊ  �����¼�
		
	}

	private void initHeader2(LayoutInflater mInflater)
	{
		View mHeaderView2 = mInflater.inflate(R.layout.headerview2_home, null);
		mHomeListView.addHeaderView(mHeaderView2);
		RelativeLayout mLayoutWoShenBian = (RelativeLayout) mHeaderView2.findViewById(R.id.layout_home_lbs_my_shenbian);
		mLayoutWoShenBian.setOnClickListener(this); //����߿ؼ������¼�
		
		// header2 ����1
		RelativeLayout mLayoutActivityTheme1 = (RelativeLayout) mHeaderView2.findViewById(R.id.layout_home_activity_theme1);
		mTvActivityTheme1_title = (TextView) mHeaderView2.findViewById(R.id.tv_home_activity_theme1_title);
		mTvActivityTheme1_subTitle = (TextView) mHeaderView2.findViewById(R.id.tv_home_activity_theme1_subtitle);
		mImgActivityTheme1 = (ImageView) mHeaderView2.findViewById(R.id.img_home_activity_theme1);
		mLayoutActivityTheme1.setOnClickListener(this); //����1 �ؼ������¼�
		
		// header2 ����2
		RelativeLayout mLayoutActivityTheme2 = (RelativeLayout) mHeaderView2.findViewById(R.id.layout_home_activity_theme2);
		mTvActivityTheme2_title = (TextView) mHeaderView2.findViewById(R.id.tv_home_activity_theme2_title);
		mTvActivityTheme2_subTitle = (TextView) mHeaderView2.findViewById(R.id.tv_home_activity_theme2_subtitle);
		mImgActivityTheme2 = (ImageView) mHeaderView2.findViewById(R.id.img_home_activity_theme2);
		mLayoutActivityTheme2.setOnClickListener(this); //����2 �ؼ������¼�
		
		// header2 ����3
		RelativeLayout mLayoutActivityTheme3 = (RelativeLayout) mHeaderView2.findViewById(R.id.layout_home_activity_theme3);
		mTvActivityTheme3_title = (TextView) mHeaderView2.findViewById(R.id.tv_home_activity_theme3_title);
		mTvActivityTheme3_subTitle = (TextView) mHeaderView2.findViewById(R.id.tv_home_activity_theme3_subtitle);
		mImgActivityTheme3 = (ImageView) mHeaderView2.findViewById(R.id.img_home_activity_theme3);
		mLayoutActivityTheme3.setOnClickListener(this); //����3 �ؼ������¼�
		
		// header2 ����4
		RelativeLayout mLayoutActivityTheme4 = (RelativeLayout) mHeaderView2.findViewById(R.id.layout_home_activity_theme4);
		mTvActivityTheme4_title = (TextView) mHeaderView2.findViewById(R.id.tv_home_activity_theme4_title);
		mTvActivityTheme4_subTitle = (TextView) mHeaderView2.findViewById(R.id.tv_home_activity_theme4_subtitle);
		mImgActivityTheme4 = (ImageView) mHeaderView2.findViewById(R.id.img_home_activity_theme4);
		mLayoutActivityTheme4.setOnClickListener(this); //����4 �ؼ������¼�
		
		// header2 ����5
		RelativeLayout mLayoutActivityTheme5 = (RelativeLayout) mHeaderView2.findViewById(R.id.layout_home_theme_activity_10yuan);
		mLayoutActivityTheme5.setOnClickListener(this);
		// header2 ����6
		RelativeLayout mLayoutActivityTheme6 = (RelativeLayout) mHeaderView2.findViewById(R.id.layout_home_theme_activity_100yuan);
		mLayoutActivityTheme6.setOnClickListener(this);
		// header2 ����7
		RelativeLayout mLayoutActivityTheme7 = (RelativeLayout) mHeaderView2.findViewById(R.id.layout_home_theme_activity_temai_quanqiu);
		mLayoutActivityTheme7.setOnClickListener(this);
		// header2 ����8
		RelativeLayout mLayoutActivityTheme8 = (RelativeLayout) mHeaderView2.findViewById(R.id.layout_home_theme_activity_temai_youlun);
		mLayoutActivityTheme8.setOnClickListener(this);
	}

	@SuppressLint("InflateParams")
	private void initHeader3(LayoutInflater mInflater)
	{
		View mHeaderView3 = mInflater.inflate(R.layout.headerview3_home, null);
		mHomeListView.addHeaderView(mHeaderView3);
		mlayout_kl_weeks = (LinearLayout) mHeaderView3.findViewById(R.id.kl_tab1);
		mlayout_kl_shortTrip = (LinearLayout) mHeaderView3.findViewById(R.id.kl_tab2);
		mlayout_kl_longHoliday = (LinearLayout) mHeaderView3.findViewById(R.id.kl_tab3);
		mlayout_kl_weeks.setOnClickListener(this); //������ĩ  �����¼�
		mlayout_kl_shortTrip.setOnClickListener(this); //���ֶ�;  �����¼�
		mlayout_kl_longHoliday.setOnClickListener(this); //���ֳ���  �����¼�
		mlayout_kl_longHoliday.setSelected(true); //Ĭ��ѡ��
	}
	
	private void initBannerUI(View mHeaderView1)
	{
		// Ƭ������Ƕ��Ƭ��Ҫ��getChildFragmentManager()����
		fm = getChildFragmentManager();
		
		initPagerUI(mHeaderView1, fm);
		initIndicatorUI(mHeaderView1);
		autoScroll();
	}

	private void initPagerUI(View mHeaderView1, FragmentManager fm)
	{
		mHomeTopPager = (ViewPager) mHeaderView1.findViewById(R.id.pager_home_top);
		mHomeSecondPager = (ViewPager) mHeaderView1.findViewById(R.id.pager_home_second);
		
		mHomeTopPager.setCurrentItem(MAX_LENGTH / 2); // ��ʼ��ʱ����Pager��ʾ�м�ҳ��
		// �ֶ��Զ��ĳ�ͻ����
		mHomeTopPager.addOnPageChangeListener(new FirstBannerPagerListent());
		mHomeSecondPager.addOnPageChangeListener(new SecondBannerPagerListent());
	}

	private void initIndicatorUI(View mHeaderView1)
	{
		mIndicator1 = (HomeViewPagerIndicator) mHeaderView1.findViewById(R.id.viewPagerIndicator1);
		mIndicator1.setRealNum(6);
		mIndicator2 = (HomeViewPagerIndicator2) mHeaderView1.findViewById(R.id.viewPagerIndicator_second);
		// ���ViewPager������ѭ���͵���setRealNum()��������ָʾ��ѭ������
		mIndicator2.setRealNum(3);
	}


	private final class FirstBannerPagerListent implements OnPageChangeListener
	{
		@Override
		public void onPageSelected(int position)
		{
			// ��ҳ��ѡ��
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int arg2)
		{
			// ��ҳ�����
			// �����Զ���ָʾ������ViewPager�������ķ����д���indicator��̬ˢ��
			position %= 6;
			mIndicator1.move(position, positionOffset);
		}

		@Override
		public void onPageScrollStateChanged(int state)
		{
			switch (state)
			{
			case ViewPager.SCROLL_STATE_DRAGGING:
				// Log.e("onPageScrollStateChanged", "�û���ק");
				isDragging = true;
				break;
			case ViewPager.SCROLL_STATE_SETTLING:
				// Log.e("onPageScrollStateChanged", "��λ");
				isDragging = false;
				break;
			case ViewPager.SCROLL_STATE_IDLE:
				// Log.e("onPageScrollStateChanged", "����");
				isDragging = false;
				break;

			default:
				break;
			}
		}
	}

	private void autoScroll()
	{
		mHomeTopPager.postDelayed(new Runnable()
		{
			// ��ʱÿ2.5���Զ�������һҳ
			public void run()
			{
				// ��ʱ����һֱ����
				mHomeTopPager.postDelayed(this, DILAY_MILLIS); // �����Զ��ֲ�
				if (!isDragging) // û����ק���飬�Զ���һҳ
				{
					int currentItem = mHomeTopPager.getCurrentItem();
					int nextItem = currentItem + 1;
					mHomeTopPager.setCurrentItem(nextItem);
				}
			}
		}, DILAY_MILLIS);
	}
	
	private final class SecondBannerPagerListent implements OnPageChangeListener
	{
		@Override
		public void onPageSelected(int position)
		{
			// ��ҳ��ѡ��
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int arg2)
		{
			// ��ҳ�����
			// �����Զ���ָʾ������ViewPager�������ķ����д���indicator��̬ˢ��
			position %= 3;
			mIndicator2.move(position, positionOffset);
		}

		@Override
		public void onPageScrollStateChanged(int state)
		{
			switch (state)
			{
			case ViewPager.SCROLL_STATE_DRAGGING:
				// Log.e("onPageScrollStateChanged", "�û���ק");
				isDragging = true;
				break;
			case ViewPager.SCROLL_STATE_SETTLING:
				// Log.e("onPageScrollStateChanged", "��λ");
				isDragging = false;
				break;
			case ViewPager.SCROLL_STATE_IDLE:
				// Log.e("onPageScrollStateChanged", "����");
				isDragging = false;
				break;

			default:
				break;
			}
		}
	}
	
	

	// ��һ��BannerViewPager �Զ��ֲ�
	private final class BannerAdapter extends FragmentPagerAdapter
	{
		private BannerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public int getCount()
		{
			// ����ѭ�� �޸�Pager��������getCount()����400000
			return MAX_LENGTH;
		}

		@Override
		public Fragment getItem(int position)
		{

			return new HomeFirstPagerFragment(position,mFirstBannerItemList);
		}
	}

	// ��2��BannerViewPager ���Զ��ֲ�
	private final class SecondPagerAdapter extends FragmentPagerAdapter
	{
		private SecondPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public int getCount()
		{
			// û������ѭ�� �޸�Pager��������getCount()����3
			return 3;
		}

		@Override
		public Fragment getItem(int position)
		{
			// ���ص��ڶ���PagerƬ��
			return new HomeSecondPagerFragment(position,mSecondBannerItemList);
		}
	}

	// ��ҳListView ������
	class HomeAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return recommendListLongHoliday.size(); // ���ֳ��� �Ƽ��б���������� 
		}

		@Override
		public Object getItem(int position)
		{
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			return 0;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View layout = mInflater.inflate(R.layout.home_longholiday_listitem, null);
			ImageView mImageView = (ImageView) layout.findViewById(R.id.img_home_longholiday_list_zhutu);
			TextView mTvJingXuan = (TextView) layout.findViewById(R.id.tv_home_longholiday_list_jingxuan);
			TextView mTvPrice = (TextView) layout.findViewById(R.id.tv_home_longholiday_list_price);
			
			TextView mTvTitle = (TextView) layout.findViewById(R.id.tv_home_longholiday_list_title);
			TextView mTvSubTitle = (TextView) layout.findViewById(R.id.tv_home_longholiday_list_subtitle);
			TextView mTvlabel1 = (TextView) layout.findViewById(R.id.tv_home_longholiday_list_label1);
			TextView mTvlabel2 = (TextView) layout.findViewById(R.id.tv_home_longholiday_list_label2);
			TextView mTvlabel3 = (TextView) layout.findViewById(R.id.tv_home_longholiday_list_label3);
			TextView mTvlabel4 = (TextView) layout.findViewById(R.id.tv_home_longholiday_list_label4);
			TextView mTvlabel5 = (TextView) layout.findViewById(R.id.tv_home_longholiday_list_label5);
			
			RecommendList recommendListData = recommendListLongHoliday.get(position);
			UILUtils.displayImage(recommendListData.getImageUrl(), mImageView);
			mTvJingXuan.setText(recommendListData.getLabelName());
			mTvPrice.setText(recommendListData.getPriceNew());
			mTvTitle.setText(recommendListData.getTitle());
			mTvSubTitle.setText(recommendListData.getSubTitle());
			
			List<Label> labelList = recommendListData.getLabel();
			if (labelList.size() == 1)
			{
				mTvlabel1.setText(labelList.get(0).getTitle());
				
			}else if (labelList.size() == 2)
			{
				mTvlabel1.setText(labelList.get(0).getTitle());
				mTvlabel2.setText(labelList.get(1).getTitle());
			}else if (labelList.size() == 3)
			{
				mTvlabel1.setText(labelList.get(0).getTitle());
				mTvlabel2.setText(labelList.get(1).getTitle());
				mTvlabel3.setText(labelList.get(2).getTitle());
			}else if (labelList.size() == 4)
			{
				mTvlabel1.setText(labelList.get(0).getTitle());
				mTvlabel2.setText(labelList.get(1).getTitle());
				mTvlabel3.setText(labelList.get(2).getTitle());
				mTvlabel4.setText(labelList.get(3).getTitle());
			}else if (labelList.size() == 5)
			{
				mTvlabel1.setText(labelList.get(0).getTitle());
				mTvlabel2.setText(labelList.get(1).getTitle());
				mTvlabel3.setText(labelList.get(2).getTitle());
				mTvlabel4.setText(labelList.get(3).getTitle());
				mTvlabel5.setText(labelList.get(4).getTitle());
			}
			
			return layout;
		}

	}

	private class GetDataTask extends AsyncTask<Void, Void, String>
	{
		protected String doInBackground(Void... params)
		{
			try
			{
				Thread.sleep(2000);
			} catch (InterruptedException e)
			{
			}
			return "aaa";
		}

		protected void onPostExecute(String result)
		{
			// if (isPullDown) {
			// mData.add(0, "pullDown");
			// } else {
			// mData.add("pullUp");
			// }
			// mAdapter.notifyDataSetChanged();
			mPtrListView.onRefreshComplete();

		}
	}

	// ��ҳHeaderView4  ���ֳ��ٵ�gridview������
		@SuppressLint("ViewHolder")
		private final class LongHolidaySubtitleGridAdapter extends BaseAdapter
		{

			@Override
			public int getCount() {
				return subMenuListLongHoliday.size();
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
//				ItemList itemData = mSecondBannerItemList.get(realPos);
				View itemlayout = mInflater.inflate(R.layout.home_header4_gridview_item, null);
				
				tvSubMenuTitle = (TextView) itemlayout.findViewById(R.id.tv_home_header4_subMenuTitle);
				subMenuData = subMenuListLongHoliday.get(position);
				tvSubMenuTitle.setText(subMenuData.getTitle());
				return itemlayout;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

		}
		// ��ҳHeaderView4  ���ֳ��ٵ�gridview������
		@SuppressLint("ViewHolder")
		private final class OverlayLongHolidaySubtitleGridAdapter extends BaseAdapter
		{
			
			private TextView tvOverlaySubMenuTitle;
			private SubMenuList overlaySubMenuData;

			@Override
			public int getCount() {
				return subMenuListLongHoliday.size();
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
//				ItemList itemData = mSecondBannerItemList.get(realPos);
				View itemlayout = mInflater.inflate(R.layout.home_overlay_card2_gridview_item, null);
				tvOverlaySubMenuTitle = (TextView) itemlayout.findViewById(R.id.tv_home_overlay_card2_gridview_subMenuTitle);
				overlaySubMenuData = subMenuListLongHoliday.get(position);
//				Log.e("OverlayLongHolidaySubtitleGridAdapter", "getView");
				tvOverlaySubMenuTitle.setText(overlaySubMenuData.getTitle());
				return itemlayout;
			}
			
			@Override
			public long getItemId(int position) {
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				return null;
			}
			
		}
	// ��ҳ����  �绰��ѯ������
		@SuppressLint({ "ViewHolder", "InflateParams" })
		class PhoneAdapter extends BaseAdapter
		{

			private TextView mTvTitle;
			private TextView mTvSubTitle;
			private ImageView mImagePhone;

			@Override
			public int getCount() {
				return 3;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View phonelayout = mInflater.inflate(R.layout.list_phone_trim, null);
				mTvTitle = (TextView) phonelayout.findViewById(R.id.tv_title);
				mTvSubTitle = (TextView) phonelayout.findViewById(R.id.tv_subtitle);
				mImagePhone = (ImageView) phonelayout.findViewById(R.id.image_phone);
				mTvTitle.setText(menu_title[position]+"");
				mTvSubTitle.setText(menu_subtitle[position]+"");
				mImagePhone.setImageResource(menu_image[position]);
				return phonelayout;
			}
			
			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

		}
	private void initData()
	{

	}

    @SuppressLint("InflateParams")
	private void PhoneCallDialogNoTitle() {	
    	View menuView = mInflater.inflate(R.layout.listview_menu, null);
	// ����AlertDialog
    	final AlertDialog menuDialog = new AlertDialog.Builder(getActivity()).create();
    	menuDialog.setView(menuView);
	
    	menuList = (ListView) menuView.findViewById(R.id.listView_phone);
    	mPhoneAdapter = new PhoneAdapter();
    	menuList.setAdapter(mPhoneAdapter);
	
    	menuList.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
			switch (position)
			{
			case Constants.TELPHONE_KEFU: // ��תweb ���߿ͷ�
				Toast.makeText(getContext(), "�벦��400�绰������ѯ", Toast.LENGTH_SHORT).show();
				break;
			case Constants.TELPHONE_DEMESTIC_USER: // �����û� �绰��ѯ
				// ���Ȩ��
				// ����绰    ����ʽ��ͼ��
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4007995833"));
				startActivity(intent );
				break;
			case Constants.TELPHONE_FOREIGN_USER: // �����û� �绰��ѯ
				// ���Ȩ��
				// ����绰    ����ʽ��ͼ��
				Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "8651282209000"));
				startActivity(intent1 );
				break;

			default:
				break;
			}
			}
    	});
    			menuDialog.show();
    	}
    
    @Override
	public void onStart()
	{
    	String city = UtilsSharePerference.getCity(getActivity());
    	mTvCtiy_LBS.setText(city);
    	
		receiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.IntentFilter.UPDATA_OVERLAY_TAB1); // ���ù�������   ������ťtab1��ͬ��ѡ��
		filter.addAction(Constants.IntentFilter.UPDATA_OVERLAY_TAB2); // ��������  ���� ������ťtab2��ͬ��ѡ��
		filter.addAction(Constants.IntentFilter.UPDATA_OVERLAY_TAB3); // ��������  ���� ������ťtab3��ͬ��ѡ��  
		getActivity().registerReceiver(receiver, filter); // ע��㲥
		super.onStart();
	}
	

	@Override
	public  void onStop(){

		getActivity().unregisterReceiver(receiver); // ע���㲥
		super.onStop();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(getContext());
	}
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(getContext());
	}
    
 // ������յ��㲥ʱ����ʼ���¿ؼ�
 	class MyReceiver extends BroadcastReceiver
 	{
 		@Override
 		public void onReceive(Context context, Intent intent)
 		{
 			// ���չ㲥  ���¿ؼ� 
 			String action = intent.getAction();
 			switch (action)
 			{
 			case Constants.IntentFilter.UPDATA_OVERLAY_TAB1:
 				mlayout_kl_weeks.setSelected(true);
 				mlayout_kl_longHoliday.setSelected(false);
 				mlayout_kl_shortTrip.setSelected(false);
 				
 				mOverlay1_kl_weeks.setSelected(true);
 				mOverlay1_kl_longHoliday.setSelected(false);
 				mOverlay1_kl_shortTrip.setSelected(false);
 				break;
 			case Constants.IntentFilter.UPDATA_OVERLAY_TAB2:
 				mlayout_kl_shortTrip.setSelected(true);
 				mlayout_kl_longHoliday.setSelected(false);
 				mlayout_kl_weeks.setSelected(false);
 				
 				mOverlay1_kl_shortTrip.setSelected(true);
 				mOverlay1_kl_longHoliday.setSelected(false);
 				mOverlay1_kl_weeks.setSelected(false);
 				break;
 			case Constants.IntentFilter.UPDATA_OVERLAY_TAB3:
 				mlayout_kl_longHoliday.setSelected(true);
 				mlayout_kl_shortTrip.setSelected(false);
 				mlayout_kl_weeks.setSelected(false);
 				
 				mOverlay1_kl_longHoliday.setSelected(true);
 				mOverlay1_kl_weeks.setSelected(false);
 				mOverlay1_kl_shortTrip.setSelected(false);
 				break;
 			
 				
 			default:
 				break;
 			}
 			
 			
 		}
 	}
    
	@Override
	public void onClick(View v)
	{
		// TODO 
		Intent intent = new Intent();
		int id = v.getId();
		switch (id)
		{
		case R.id.tv_home_top_city: //��ҳ��������ѡ��λ
			intent.setClass(getActivity(), CitiesActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_home_top_search: //��ҳ����  global search ȫ������
			intent.setClass(getActivity(), GlobalSearchesActivity.class);
			startActivity(intent);
			break;
		case R.id.img_home_top_phone: //��ҳ�����绰Ѱ�����
			PhoneCallDialogNoTitle(); 
			break;
		case R.id.layout_home_haiwaiwanle: //��������
			intent.setClass(getActivity(), HomeVisaActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_qianzheng: //ǩ֤
			intent.setClass(getActivity(), HomeVisaActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_allworld_wifi: //ȫ��wifi
			intent.setClass(getActivity(), HomeVisaActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_movie_ticket: //��ӰƱ
			intent.setClass(getActivity(), HomeMoviceTicketActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_licai: //���
			intent.setClass(getActivity(), HomeMoviceTicketActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_car_ticket: //����Ʊ
			intent.setClass(getActivity(), HomeMoviceTicketActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_xianshi_qianggou: //����
			intent.setClass(getActivity(), HomePanicBuyingActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_lbs_my_shenbian: //�����
			intent.setClass(getActivity(), HomeNearByActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_activity_theme1: //����1  �����ػ�
			intent.setClass(getActivity(), HomeSuperPlayerActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_activity_theme2: //����2  TCר��
			intent.setClass(getActivity(), HomeSuperPlayerActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_activity_theme3: //����3  ��Ʊר��
			intent.setClass(getActivity(), HomeSuperPlayerActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_activity_theme4: //����4 �����濧
			intent.setClass(getActivity(), HomeSuperPlayerActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_theme_activity_10yuan: //����5  ʮԪ��ĩ
			intent.setClass(getActivity(), HomeTenYuanTravelActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_theme_activity_100yuan: //����6  ��Ԫ�й�
			intent.setClass(getActivity(), HomeTenYuanTravelActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_theme_activity_temai_quanqiu: //����7  ����ȫ��
			intent.setClass(getActivity(), HomeTenYuanTravelActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_home_theme_activity_temai_youlun: //����8  ��������
			intent.setClass(getActivity(), HomeTenYuanTravelActivity.class);
			startActivity(intent);
			break;
			
		case R.id.kl_tab1: // ������ĩ
			intent.setAction(Constants.IntentFilter.UPDATA_OVERLAY_TAB1);
			getActivity().sendBroadcast(intent);
			Toast.makeText(getContext(), "������ĩ~", Toast.LENGTH_SHORT).show();
			break;
		case R.id.kl_tab2: // ���ֶ�;
			intent.setAction(Constants.IntentFilter.UPDATA_OVERLAY_TAB2);
			getActivity().sendBroadcast(intent);
			Toast.makeText(getContext(), "���ֶ�;~", Toast.LENGTH_SHORT).show();
			break;
		case R.id.kl_tab3: // ���ֳ���
			intent.setAction(Constants.IntentFilter.UPDATA_OVERLAY_TAB3);
			getActivity().sendBroadcast(intent);
			break;
		case R.id.ll_tab1_overlay1: // �������tab1 ������ĩ
			intent.setAction(Constants.IntentFilter.UPDATA_OVERLAY_TAB1);
			getActivity().sendBroadcast(intent);
			Toast.makeText(getContext(), "������ĩ~", Toast.LENGTH_SHORT).show();
			break;
		case R.id.ll_tab2_overlay1: // �������tab2 ���ֶ�;
			intent.setAction(Constants.IntentFilter.UPDATA_OVERLAY_TAB2);
			getActivity().sendBroadcast(intent);
			Toast.makeText(getContext(), "���ֶ�;~", Toast.LENGTH_SHORT).show();
			break;
		case R.id.ll_tab3_overlay1: // �������tab3 ���ֳ���
			intent.setAction(Constants.IntentFilter.UPDATA_OVERLAY_TAB3);
			getActivity().sendBroadcast(intent);
			break;

		default:
			break;
		}
		
	}

}
