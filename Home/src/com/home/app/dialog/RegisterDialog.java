package com.home.app.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.home.app.LocationApplication;
import com.home.app.R;
import com.home.app.activitys.LoadingActivity;
import com.home.app.entity.UserInfo;
import com.home.app.server.UserServerInterface;
import com.home.app.utils.MatcherUtils;
import com.home.app.utils.StatusCode;
import com.home.app.utils.StringUtils;
import com.home.app.utils.Values;
import com.home.entity.request.RegisterUserRequest;

public class RegisterDialog extends BaseDialog {
	
	private EditText phoneNumber;
	private EditText password;
	private EditText surePassword;
	private Button register;
	private Activity activity;
	protected static final String ACTION_SMS_SEND = "lab.sodino.sms.send";    
	protected static final String ACTION_SMS_DELIVERY = "lab.sodino.sms.delivery";    
	protected static final String ACTION_SMS_RECEIVER = "android.provider.Telephony.SMS_RECEIVED";
    
	public RegisterDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setContentView(R.layout.register_layout);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		initViews();
	}

	private void initViews() {
		register = (Button)findViewById(R.id.register);
		register.setOnClickListener(this);
		phoneNumber = (EditText)findViewById(R.id.phone_number_edit);
		password = (EditText)findViewById(R.id.password_edit);
		surePassword = (EditText)findViewById(R.id.sure_password_edit);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register:
			/*// 注册接收下行receiver
			SMSReceiver smsReceiver = new SMSReceiver();
			IntentFilter receiverFilter = new IntentFilter(ACTION_SMS_RECEIVER);
			registerReceiver(smsReceiver, receiverFilter);
			// 发送短信
			SMSCore smscore = new SMSCore();
			smscore.SendSMS2("10001", "501", v.getContext());*/
			
			if (!StringUtils.hasText(phoneNumber.getText().toString()) || !StringUtils.hasText(password.getText().toString())) {
				Toast.makeText(RegisterDialog.this.getContext(), "用户名或密码不能为空", Toast.LENGTH_LONG).show();
				break;
			} 
			if (!MatcherUtils.isMobileNO(phoneNumber.getText().toString())) {
				Toast.makeText(RegisterDialog.this.getContext(), "电话号码格式错误", Toast.LENGTH_LONG).show();
				break;
			}
			
			if (password.getText().toString().length() < Values.PASSWORD_LENGTH) {
				Toast.makeText(RegisterDialog.this.getContext(), "密码少于6位", Toast.LENGTH_LONG).show();
				break;
			}
			
			if (!password.getText().toString().equals(surePassword.getText().toString())) {
				Toast.makeText(RegisterDialog.this.getContext(), "2次密码不相同", Toast.LENGTH_LONG).show();
				break;
			}
			
			new AsyncUserRegister().execute(phoneNumber.getText().toString(), password.getText().toString());
			break;

		default:
			break;
		}
	}
	
	private class AsyncUserRegister extends AsyncTask<String, Integer, Integer> {
		
		@Override
		protected Integer doInBackground(String... params) {
			int uid = -1;
			try {
				RegisterUserRequest request = new RegisterUserRequest();
				request.setPhoneNumber(params[0]);
				request.setPassWord(params[1]);
				uid = UserServerInterface.getInstance().getUserServer().registerUser(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return uid;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			switch (result) {
			case StatusCode.FAILED:
				Toast.makeText(RegisterDialog.this.getContext(), "电话号码已经被注册", Toast.LENGTH_LONG).show();
				break;

			default:
				UserInfo info = new UserInfo();
				((LocationApplication) activity.getApplication()).setUserInfo(info);
				((LocationApplication) activity.getApplication()).getUserInfo().setUid(result);
				((LocationApplication) activity.getApplication()).getUserInfo().setPhoneNumber(phoneNumber.getText().toString());
				((LocationApplication) activity.getApplication()).getUserInfo().setPassword(password.getText().toString());
				Intent intent = new Intent();
				intent.setClass(RegisterDialog.this.getContext(), LoadingActivity.class);
				RegisterDialog.this.getContext().startActivity(intent);
				break;
			}
		}
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
