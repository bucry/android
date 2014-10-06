package com.home.app.dialog;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.app.R;
import com.home.app.activitys.HistoryActivity;
import com.home.app.adatper.HomepageDataViewAdapter;
import com.home.app.server.UserServerInterface;
import com.home.app.utils.StatusCode;
import com.home.entity.request.FindHomeRequest;
import com.home.entity.response.HomeResponse;
import com.home.entity.response.UserResponse;

public class HomeDatiledDialog extends BaseDialog implements OnItemClickListener {
	private ListView listView;
	private List<UserResponse> items;
	private DataViewAdapter dataViewAdapter = new DataViewAdapter();
	private LayoutInflater mLayoutInflater;
	private HomepageDataViewAdapter homepageDataViewAdapter;
	private Button exitHome;
	private Button addUserToHome;
	private HomeResponse home;
	
	public HomeDatiledDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setContentView(R.layout.home_detailed_layout);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		listView = (ListView)findViewById(R.id.listItems);
		exitHome = (Button)findViewById(R.id.exitHome);
		exitHome.setOnClickListener(this);
		addUserToHome = (Button)findViewById(R.id.invitUser);
		addUserToHome.setOnClickListener(this);
		new AsyncAndFindHomeInfo().execute();

	}

	private class AsyncAndFindHomeInfo extends AsyncTask<Integer, Integer, List<UserResponse>> {
		@Override
		protected List<UserResponse> doInBackground(Integer... params) {
			try {
				FindHomeRequest request = new FindHomeRequest();
				request.setUid(info.getUid());
				request.setHomeId(home.getHomeId());
				List<UserResponse> items = UserServerInterface.getInstance().getUserServer().findHomeMember(request);
				return items;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(List<UserResponse> result) {
			items = result;
			dataViewAdapter.notifyDataSetChanged();
			exitHome.setText(home.getOnwerUid() == info.getUid() ? "解散家庭" : "退出家庭");
			exitHome.setVisibility(View.VISIBLE);
			listView.setOnItemClickListener(HomeDatiledDialog.this);
			listView.setAdapter(dataViewAdapter);
		}
	}
	
	
	
	private class DataViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return items == null || items.isEmpty() ? 0 : items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = new ViewHolder();
			if (convertView == null) {
				convertView = mLayoutInflater.inflate( R.layout.item, null);
			} else {
				viewHolder = (ViewHolder)convertView.getTag();
			}
			
			viewHolder.textViewOne = (TextView) convertView.findViewById(R.id.item);
			
			if (items.get(position).getUid() == home.getOnwerUid()) {
				viewHolder.textViewOne.setText(items.get(position).getPhoneNumber() + "(户主)");
			} else {
				viewHolder.textViewOne.setText(items.get(position).getPhoneNumber()  + "(成员)");
			}
			convertView.setTag( viewHolder);
			return convertView;
		}
		
	}
	
	private static class ViewHolder {
		TextView textViewOne;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exitHome:
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setIcon(R.drawable.icon);
            builder.setTitle(home.getOnwerUid() == info.getUid() ? "解散家庭" : "退出家庭");
            builder.setMessage("确定" + (home.getOnwerUid() == info.getUid() ? "解散家庭" : "退出家庭") +"吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	new AsyncAndExitHome().execute();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	HomeDatiledDialog.this.dismiss();
                }
            });
            builder.show();
			break;
		case R.id.invitUser:
			UserAddDatiledDialog userAddDatiledDialog = new UserAddDatiledDialog(v.getContext());
			userAddDatiledDialog.setHome(home);
			userAddDatiledDialog.setInfo(info);
			userAddDatiledDialog.show();
			break;
		default:
			break;
		}
	}

	
	private class AsyncAndExitHome extends AsyncTask<Integer, Integer, Integer> {
		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				return UserServerInterface.getInstance().getUserServer().exitHome(info.getUid(), home.getHomeId());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			if (result == StatusCode.SUCCESS) {
				if (home.getOnwerUid() == info.getUid()) {
					Toast.makeText(HomeDatiledDialog.this.getContext(), "解散成功", Toast.LENGTH_LONG).show();
				} else if (result == StatusCode.HOME_NOT_FOUND){
					Toast.makeText(HomeDatiledDialog.this.getContext(), "失败，家庭未找到", Toast.LENGTH_LONG).show();
				} else if (result == StatusCode.HAVE_MEMBERS){
					Toast.makeText(HomeDatiledDialog.this.getContext(), "解散失败，家庭还有其他会员", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(HomeDatiledDialog.this.getContext(), "退出成功", Toast.LENGTH_LONG).show();
				}
				
				int removeHid = -1;
				for (int index = 0; index < homepageDataViewAdapter.getItems().size(); index++) {
					if (home.getHomeId() == homepageDataViewAdapter.getItems().get(index).getHomeId()) {
						removeHid = index;
						break;
					}
				}
				if (removeHid != -1) {
					homepageDataViewAdapter.getItems().remove(removeHid);
				}
				homepageDataViewAdapter.notifyDataSetChanged();
				HomeDatiledDialog.this.dismiss();
			} else {
				Toast.makeText(HomeDatiledDialog.this.getContext(), "出现错误，请稍后再试", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
	public LayoutInflater getmLayoutInflater() {
		return mLayoutInflater;
	}

	public void setmLayoutInflater(LayoutInflater mLayoutInflater) {
		this.mLayoutInflater = mLayoutInflater;
	}

	public HomeResponse getHome() {
		return home;
	}

	public void setHome(HomeResponse home) {
		this.home = home;
	}

	public void setHomepageDataViewAdapter(HomepageDataViewAdapter homepageDataViewAdapter) {
		this.homepageDataViewAdapter = homepageDataViewAdapter;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		UserResponse otherUserInfo = items.get(arg2);
		Intent intent = new Intent();
		Bundle mBundle = new Bundle(); 
		mBundle.putSerializable(HistoryActivity.USER_FLAG, otherUserInfo);
		intent.putExtras(mBundle);
		intent.setClass(arg1.getContext(), HistoryActivity.class);
		arg0.getContext().startActivity(intent);
	}
}
