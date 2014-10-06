package com.home.app.dialog;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.home.app.R;
import com.home.app.server.UserServerInterface;
import com.home.app.utils.MatcherUtils;
import com.home.app.utils.StringUtils;
import com.home.entity.request.HomeRequest;
import com.home.entity.response.HomeResponse;

public class UserAddDatiledDialog extends BaseDialog {

	private EditText nameEditText;
	private Button submitButton;
	private HomeResponse home;

	public UserAddDatiledDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setContentView(R.layout.dialog_user_add_layout);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		nameEditText = (EditText) findViewById(R.id.name_edit);
		submitButton = (Button) findViewById(R.id.submit);
		submitButton.setOnClickListener(this);

	}

	private class AsyncAddUserToHome extends AsyncTask<HomeRequest, Integer, Integer> {
			
		@Override
		protected Integer doInBackground(HomeRequest... params) {
			try {
				return UserServerInterface.getInstance().getUserServer().addUserToHome(params[0]);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result == 1) {
				Toast.makeText(UserAddDatiledDialog.this.getContext(), "添加成功", Toast.LENGTH_LONG).show();
				UserAddDatiledDialog.this.dismiss();
			} else {
				Toast.makeText(UserAddDatiledDialog.this.getContext(), "添加失败，对方尚未加入系统", Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit:
			
			if (!StringUtils.hasText(nameEditText.getText().toString())) {
				Toast.makeText(UserAddDatiledDialog.this.getContext(), "电话号码不能为空", Toast.LENGTH_LONG).show();
				break;
			}
			
			if (!MatcherUtils.isMobileNO(nameEditText.getText().toString())) {
				Toast.makeText(UserAddDatiledDialog.this.getContext(), "电话号码格式错误", Toast.LENGTH_LONG).show();
				break;
			}
			
			if (nameEditText.getText().toString().equals(info.getPhoneNumber())) {
				Toast.makeText(UserAddDatiledDialog.this.getContext(), "不能添加自己", Toast.LENGTH_LONG).show();
			}
			
			HomeRequest request = new HomeRequest();
			request.setUserPoneNumber(nameEditText.getText().toString());
			request.setHomeId(home.getHomeId());	
			new AsyncAddUserToHome().execute(request);
			break;

		default:
			break;
		}
	}

	public HomeResponse getHome() {
		return home;
	}

	public void setHome(HomeResponse home) {
		this.home = home;
	}

}
