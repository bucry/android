package com.home.app.activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.home.app.LocationApplication;
import com.home.app.R;
import com.home.app.dialog.RegisterDialog;
import com.home.app.entity.UserInfo;
import com.home.app.server.UserServerInterface;
import com.home.app.utils.MatcherUtils;
import com.home.app.utils.NetUtil;
import com.home.app.utils.StatusCode;
import com.home.app.utils.StringUtils;
import com.home.app.utils.Values;
import com.home.entity.request.RegisterUserRequest;

public class LoginActivity extends BaseActivity {
	private Button loginSubmit;
	private Button register;
	private EditText phoneNumber;
	private EditText passwd;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initViews();
    }
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onStart() {
		if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.icon);
            builder.setTitle("重要提示");
            builder.setMessage("网络出现问题，请检查");
            builder.setPositiveButton("检查", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	ConnectivityManager conMan = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);  
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
		} else {
			//T.showLong(this, "网络可以使用");
		}
		super.onStart();
	}




	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}




	private void initViews() {
		loginSubmit = (Button)findViewById(R.id.login_submit);
		loginSubmit.setOnClickListener(this);
		register = (Button)findViewById(R.id.register);
		register.setOnClickListener(this);
		
        phoneNumber = (EditText)findViewById(R.id.phone_number_edit);
        passwd = (EditText)findViewById(R.id.password_edit);
        initValues();
	}
	
	private void initValues() {
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		//String deviceid = tm.getDeviceId();
		String telPhone = tm.getLine1Number();
		//String imei = tm.getSimSerialNumber();
		//String imsi = tm.getSubscriberId();
		
		if (localValues != null) {
			phoneNumber.setText(localValues.getPhoneNumber());
			passwd.setText(localValues.getPassword());
		}
		 
		if (StringUtils.hasText(telPhone)) {
			phoneNumber.setText(telPhone);
			phoneNumber.setEnabled(false);
		}
		
	}
	
	
	private class AsyncUserLogin extends AsyncTask<String, Integer, Integer> {
		
		@Override
		protected Integer doInBackground(String... params) {
			int uid = -1;
			try {
				RegisterUserRequest request = new RegisterUserRequest();
				request.setPhoneNumber(params[0]);
				request.setPassWord(params[1]);
				uid = UserServerInterface.getInstance().getUserServer().login(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return uid;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			switch (result) {
			case StatusCode.FAILED:
				Toast.makeText(LoginActivity.this, "用户名或密码错误，登录失败", Toast.LENGTH_LONG).show();
				break;

			default:
				UserInfo info = new UserInfo();
				((LocationApplication) getApplication()).setUserInfo(info);
				((LocationApplication) getApplication()).getUserInfo().setUid(result);
				((LocationApplication) getApplication()).getUserInfo().setPhoneNumber(phoneNumber.getText().toString());
				((LocationApplication) getApplication()).getUserInfo().setPassword(passwd.getText().toString());
				
				if (localValues != null) {
					localValues.setPhoneNumber(phoneNumber.getText().toString());
					localValues.setPassword(passwd.getText().toString());
				}
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, LoadingActivity.class);
				startActivity(intent);
				break;
			}
		}
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_submit:
			
			if (!StringUtils.hasText(phoneNumber.getText().toString()) || !StringUtils.hasText(passwd.getText().toString())) {
				Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
				break;
			} 
			if (!MatcherUtils.isMobileNO(phoneNumber.getText().toString())) {
				Toast.makeText(LoginActivity.this, "电话号码格式错误", Toast.LENGTH_LONG).show();
				break;
			}
			
			if (passwd.getText().toString().length() < Values.PASSWORD_LENGTH) {
				Toast.makeText(LoginActivity.this, "密码少于6位", Toast.LENGTH_LONG).show();
				break;
			}
			
			new AsyncUserLogin().execute(phoneNumber.getText().toString(), passwd.getText().toString());
			break;
		case R.id.register:
			RegisterDialog registerDialog = new RegisterDialog(LoginActivity.this);
			registerDialog.setActivity(LoginActivity.this);
			registerDialog.show();
			break;
		default:
			break;
		}
	}

}
