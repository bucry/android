package com.home.app.service;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.home.app.LocationApplication;
import com.home.app.utils.NetUtil;

public class NetBroadcastReceiver extends BroadcastReceiver {
    public static ArrayList<NetEventHandler> mListeners = new ArrayList<NetEventHandler>();
    private static String NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(NET_CHANGE_ACTION)) {
        	LocationApplication.mNetWorkState = NetUtil.getNetworkState(context);
            if (mListeners.size() > 0)// 通知接口完成加载
                for (NetEventHandler handler : mListeners) {
                    handler.onNetChange();
                }
        }
    }

    public static abstract interface NetEventHandler {
        public abstract void onNetChange();
    }
}