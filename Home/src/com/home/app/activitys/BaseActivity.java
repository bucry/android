package com.home.app.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;

import com.home.app.LocationApplication;
import com.home.app.entity.LocalValues;
import com.home.app.entity.UserInfo;

/**
 * 
 * @author piaobomengxiang@gmail.com
 * @descript 基类
 * @createDate 2014-9-8
 * @updateDate 2014-9-8
 * @version 1.0
 *
 */
abstract public class BaseActivity extends Activity implements OnClickListener {
	
	protected UserInfo info;
	protected LocalValues localValues;
	public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        LocationApplication application = (LocationApplication) this.getApplication(); 
        application.getActivityManager().pushActivity(this); 
        info = application.getUserInfo();
        localValues = application.getLocalValues();
    } 
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		/*LocationApplication application = (LocationApplication) getApplication(); 
        application.getActivityManager().popActivity(this); */
	}

	@Override
	protected void onPause() {
		super.onPause();
		LocationApplication application = (LocationApplication) getApplication(); 
        application.getActivityManager().popActivity(this); 
	}

	@Override 
    public void onBackPressed() { 
        super.onBackPressed(); 
        LocationApplication application = (LocationApplication) getApplication(); 
        application.getActivityManager().popActivity(this); 
    } 
}
