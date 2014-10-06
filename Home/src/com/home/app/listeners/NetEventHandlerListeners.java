package com.home.app.listeners;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.provider.Settings;

import com.home.app.R;
import com.home.app.service.NetBroadcastReceiver;
import com.home.app.service.NetBroadcastReceiver.NetEventHandler;
import com.home.app.utils.NetUtil;

public class NetEventHandlerListeners implements NetEventHandler {
    private Activity activity;
	public NetEventHandlerListeners(Activity activity) {
		this.activity = activity;
		NetBroadcastReceiver.mListeners.add(this);
	}

    @Override
    public void onNetChange() {
        if (NetUtil.getNetworkState(activity) == NetUtil.NETWORN_NONE) {
        	AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setIcon(R.drawable.icon);
            builder.setTitle("重要提示");
            builder.setMessage("网络出现问题，请检查");
            builder.setPositiveButton("检查", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	ConnectivityManager conMan = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);  
                    //mobile 3G Data Network  
                    State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();  
                    //wifi  
                    State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();  
                    //如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接  
                    if(mobile==State.CONNECTED||mobile==State.CONNECTING)  
                        return;  
                    if(wifi==State.CONNECTED||wifi==State.CONNECTING)  
                        return;  
                    activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));//进入无线网络配置界面  
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
            //T.showLong(this, "网络可以使用");
        }
    }

}
