package com.home.app.dialog;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.home.app.R;
import com.home.app.adatper.HomepageDataViewAdapter;
import com.home.app.server.UserServerInterface;
import com.home.app.utils.StatusCode;
import com.home.app.utils.StringUtils;
import com.home.entity.request.HomeRequest;
import com.home.entity.response.HomeResponse;

public class HomeCreateDialog extends BaseDialog {
	
	private EditText nameEditText;
	private Button submitButton;
	private HomeResponse response = new HomeResponse();
	private HomepageDataViewAdapter homepageDataViewAdapter;
	public HomeCreateDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setContentView(R.layout.dialog_home_add_layout);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		nameEditText = (EditText)findViewById(R.id.name_edit);
		submitButton = (Button)findViewById(R.id.submit);
		submitButton.setOnClickListener(this);

	}

	
	private class AsyncCreateHome extends AsyncTask<HomeRequest, Integer, Integer> {
		@Override
		protected Integer doInBackground(HomeRequest... params) {
			try {
				response.setHomeName(params[0].getHomeName());
				response.setOnwerUid(info.getUid());
				response.setHomeId(-3);
				return UserServerInterface.getInstance().getUserServer().createHome(params[0]);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			if (result == StatusCode.SUCCESS) {
				/*int lastIndex = -1;
				for (int index = 0; index < homepageDataViewAdapter.getItems().size(); index++) {
					if (homepageDataViewAdapter.getItems().get(index).getHomeId() == -1) {
						lastIndex = index;
						break;
					}
				}
				if (lastIndex != -1) {
					homepageDataViewAdapter.getItems().remove(lastIndex);
				}*/
				
				homepageDataViewAdapter.getItems().add(response);
				homepageDataViewAdapter.notifyDataSetChanged();
				
				Toast.makeText(HomeCreateDialog.this.getContext(), "创建成功", Toast.LENGTH_LONG).show();
				HomeCreateDialog.this.dismiss();
			} else if (result == StatusCode.NAME_EXIT){
				Toast.makeText(HomeCreateDialog.this.getContext(), "名称已经存在", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(HomeCreateDialog.this.getContext(), "创建失败", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit:
			
			if (!StringUtils.hasText(nameEditText.getText().toString())) {
				Toast.makeText(HomeCreateDialog.this.getContext(), "名称不能为空", Toast.LENGTH_LONG).show();
				break;
			}
			
			if (nameEditText.getText().toString().length() > 6) {
				Toast.makeText(HomeCreateDialog.this.getContext(), "名称不能超过6个字", Toast.LENGTH_LONG).show();
				break;
			}
			
			HomeRequest request = new HomeRequest();
			request.setHomeName(nameEditText.getText().toString());
			request.setOnwerUid(info.getUid());
			new AsyncCreateHome().execute(request);
			break;

		default:
			break;
		}
	}

	public void setHomepageDataViewAdapter(
			HomepageDataViewAdapter homepageDataViewAdapter) {
		this.homepageDataViewAdapter = homepageDataViewAdapter;
	}
}
