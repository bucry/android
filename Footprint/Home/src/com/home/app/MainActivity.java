package com.home.app;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.Toast;

import com.home.app.entity.LocalValues;
import com.home.app.fragment.HistoryFragment;
import com.home.app.fragment.HomepageFragment;
import com.home.app.fragment.SetFragment;
import com.home.app.service.LocalPositionService;
import com.home.app.service.NetBroadcastReceiver;
import com.home.app.service.NetBroadcastReceiver.NetEventHandler;
import com.home.app.service.SpeedListenerService;
import com.home.app.utils.NetUtil;
import com.home.app.utils.ObjectSerializable;

public class MainActivity extends FragmentActivity  implements NetEventHandler {  
  
    private FragmentTabHost mTabHost = null;
    protected TabWidget tabWidget = null;
    private int defaultIndex = 1;
    private LocalValues localValues;
    private LocationManager lm;
    private String ns = null;
    private NotificationManager mNotificationManager = null;
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.activity_main);  
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationApplication application = (LocationApplication) this.getApplication(); 
        localValues = application.getLocalValues();
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);  
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent); 
        tabWidget = mTabHost.getTabWidget();
        
        ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) getSystemService(ns);
        
        initMenu();
    }  
    
   
    
    @Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
	}



	@Override
	protected void onStart() {
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
       	 	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setIcon(R.drawable.icon);
            builder.setTitle("重要提示");
            builder.setMessage("没有打开GPS，定位可能不会准确，是否打开GPS？");
            builder.setPositiveButton("打开", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);   
                    startActivityForResult(intent,0); 
                    return;
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	//finish();
                	//return;
                }
            });
            builder.show();
        }
        startService(new Intent(MainActivity.this, LocalPositionService.class));
		super.onStart();
	}

	 @Override
	 public void onNetChange() {
	     if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE) {
	      AlertDialog.Builder builder = new AlertDialog.Builder(this);
	         builder.setIcon(R.drawable.icon);
	         builder.setTitle("重要提示");
	         builder.setMessage("网络出现问题，请检查");
	         builder.setPositiveButton("检查", new DialogInterface.OnClickListener() {
	            @Override
	             public void onClick(DialogInterface dialog, int which) {
	             	ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);  
	                //mobile 3G Data Network  
                    State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();  
	                //wifi  
	                State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();  
	                //如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接  
	                if(mobile==State.CONNECTED||mobile==State.CONNECTING)  
	                   return;  
	                if(wifi==State.CONNECTED||wifi==State.CONNECTING)  
	                   return;  
	                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));//进入无线网络配置界面  
	                //startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)); //进入手机中的wifi网络设置界面 
	                return;
	            }
		       });
		          builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
		              @Override
		              public void onClick(DialogInterface dialog, int which) {
		                	//finish();
		                	//return;
		              }
		       });
		         builder.show();
		  }else {
			  Toast.makeText(getApplicationContext(), "网络已经恢复", Toast.LENGTH_SHORT).show();
		}
	 }
	private void initMenu() {
    	
		registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));  
		
    	ImageView historyMnenu = new ImageView(this);
    	historyMnenu.setImageResource(R.drawable.history_not_selected);
    	historyMnenu.setTag("history");
        mTabHost.addTab(mTabHost.newTabSpec("历史").setIndicator(historyMnenu), HistoryFragment.class, null);
        
        ImageView homepageMnenu = new ImageView(this);
        homepageMnenu.setImageResource(R.drawable.home_page_selected);
        homepageMnenu.setTag("homepage");
        mTabHost.addTab(mTabHost.newTabSpec("首页").setIndicator(homepageMnenu), HomepageFragment.class, null);
        
        
        ImageView setMnenu = new ImageView(this);
        setMnenu.setImageResource(R.drawable.home_set_not_selected);
        setMnenu.setTag("set");
        mTabHost.addTab(mTabHost.newTabSpec("设置").setIndicator(setMnenu), SetFragment.class, null);
        
        mTabHost.setCurrentTab(defaultIndex);
        onTabChangedListenerTodo();
        NetBroadcastReceiver.mListeners.add(this);
        startService(new Intent(MainActivity.this, SpeedListenerService.class));
    }
    
    private void onTabChangedListenerTodo() {
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {// 点击监听事件
			@Override
			public void onTabChanged(String tabTag) {
				resetMenuBackground();
				int index = mTabHost.getCurrentTab();
				View view = tabWidget.getChildTabViewAt(index);
				switch (index) {
				case 0:
					((ImageView) view).setImageResource(R.drawable.history_selected);
					break;
				case 1:
					((ImageView) view).setImageResource(R.drawable.home_page_selected);				
					break;
				case 2:
					((ImageView) view).setImageResource(R.drawable.home_set_selected);
					break;
				default:
					break;
				}
			}
		});
	}

    private void resetMenuBackground() {
    	for (int index = 0; index < tabWidget.getChildCount(); index++) {
    		View view = tabWidget.getChildTabViewAt(index);
    		switch (index) {
			case 0:
	    		((ImageView) view).setImageResource(R.drawable.history_not_selected);
				break;
			case 1:
	    		((ImageView) view).setImageResource(R.drawable.home_page_not_selected);
				break;
			case 2:
				((ImageView) view).setImageResource(R.drawable.home_set_not_selected);
				break;
			default:
				break;
			}
		}
    }
    
    private long exitTime = 0; 
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
		// 获取手机当前音量值
		switch (keyCode) {
		// 音量减小
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			Toast.makeText(getApplicationContext(), "一键报警触发。。。", Toast.LENGTH_SHORT).show();        
			return true;
			// 音量增大
		case KeyEvent.KEYCODE_VOLUME_UP:
			 Toast.makeText(getApplicationContext(), "一键报警触发。。。", Toast.LENGTH_SHORT).show();        
			return true;
		}
    	
    	if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){    
    	    if((System.currentTimeMillis()-exitTime) > 2000){    
    	        Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                 
    	        exitTime = System.currentTimeMillis();    
    	    }    
    	    else{    
    	    	 //moveTaskToBack(false);  
    	    	 ObjectSerializable.writeObject(localValues);
    	    	 //startService(new Intent(MainActivity.this, LocalPositionService.class));
    	    	 finish();
    	    }    
    	        return true; //返回true表示执行结束不需继续执行父类按键响应  
    	    }    
    	    return super.onKeyDown(keyCode, event); 
     }
    
    
    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {  
        String SYSTEM_REASON = "reason";  
        String SYSTEM_HOME_KEY = "homekey";  
        String SYSTEM_HOME_KEY_LONG = "recentapps";  
           
        @SuppressWarnings("deprecation")
        @Override  
        public void onReceive(Context context, Intent intent) {  
            String action = intent.getAction();  
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {  
                String reason = intent.getStringExtra(SYSTEM_REASON);  
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {  
                     //表示按了home键,程序到了后台  
                    Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();  
                    int icon = R.drawable.icon;
                    CharSequence tickerText = "脚印正在运行";
                    long when = System.currentTimeMillis();
            		Notification notification = new Notification(icon, tickerText, when);
                    CharSequence contentTitle = "脚印正在运行";
                    CharSequence contentText = "脚印正在运行";
                    Intent notificationIntent = new Intent(context, MainActivity.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
                    notification.setLatestEventInfo(context, contentTitle, contentText,  contentIntent);
                    notification.flags |= Notification.FLAG_ONGOING_EVENT;
                    mNotificationManager.notify(1, notification);
                    
                    
                }else if(TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)){  
                    //表示长按home键,显示最近使用的程序列表  
                }  
            }   
        }  
    }; 
    
    
    @Override  
    public void onResume() {  
        super.onResume();  
        mNotificationManager.cancelAll();
        //在当前的activity中注册广播  
       /* IntentFilter filter = new IntentFilter();  
        filter.addAction(GlobalVarable.EXIT_ACTION);  
        this.registerReceiver(this.broadcastReceiver, filter);  */
    }  
    
    @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        mTabHost = null;  
    }  
}  
