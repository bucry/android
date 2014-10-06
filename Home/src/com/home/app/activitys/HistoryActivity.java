package com.home.app.activitys;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.home.app.R;
import com.home.app.server.UserServerInterface;
import com.home.app.user.IUserServer;
import com.home.entity.request.FindHistoryRequest;
import com.home.entity.response.LocalPositionResponse;
import com.home.entity.response.UserResponse;

public class HistoryActivity extends BaseActivity {

	public static final String USER_FLAG = "HistoryActivity";
	
	private PullToRefreshListView mPullRefreshListView;
	private List<LocalPositionResponse> items;
	private TextView noThing;
	private DataViewAdapter dataViewAdapter = new DataViewAdapter();
	private boolean isRefreshing;
	private LayoutInflater mLayoutInflater;
	private UserResponse otherUserInfo;
	private boolean isLoadMore = false;
	private int totalSize = 0;
	private int pageSize = 10;
	private int pageNo = 0;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutInflater = this.getLayoutInflater();
        otherUserInfo = (UserResponse) getIntent().getSerializableExtra(USER_FLAG);
        setContentView(R.layout.history_layout);
        noThing = (TextView)findViewById(R.id.nothing);
        mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.listItems);
        mPullRefreshListView.setMode(Mode.BOTH);  
        mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("加载成功");  
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");  
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("放开已加载更多");
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {  
            @Override  
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {  
            	if (!isRefreshing) {  
                    isRefreshing = true;  
                    if (mPullRefreshListView.isHeaderShown()) {  
                    	isLoadMore = false;
                    	 String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),  DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL); 
                         // Update the LastUpdatedLabel  
                         refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);  
                         new AsyncAndFindUserPosition().execute(0, 10);  
                    } else if (mPullRefreshListView.isFooterShown()) {  
                    	isLoadMore = true;
                    	pageNo = pageNo + 1;
                    	new AsyncAndFindUserPosition().execute(pageNo, pageSize); 
                    }  
                } else {  
                	mPullRefreshListView.onRefreshComplete();  
                }  
            	isRefreshing = false;
            }  
        });  
        //此句放在监听前边则不会显示列表
        mPullRefreshListView.setAdapter(dataViewAdapter);
        new AsyncAndFindUserPosition().execute(0, 10);
	}
	
	private class AsyncAndFindUserPosition extends AsyncTask<Integer, Integer, List<LocalPositionResponse>> {
		@Override
		protected List<LocalPositionResponse> doInBackground(Integer... params) {
			try {
				IUserServer loginService = UserServerInterface.getInstance().getUserServer();
				FindHistoryRequest request = new FindHistoryRequest();
				request.setCurrentUid(info.getUid());
				request.setTargetUid(otherUserInfo.getUid());
				request.setPageNo(params[0]);
				request.setPageSize(params[1]);
				List<LocalPositionResponse> items = loginService.findLocalPositionByHome(request);
				
				if (items != null & !items.isEmpty()) {
					totalSize = items.size();
				}
				
				return items;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(List<LocalPositionResponse> result) {
			
			if ((result == null || result.isEmpty()) && totalSize == 0) { //第一次加载，没有数据，页面显示当前没有内容
				noThing.setVisibility(View.VISIBLE);
				return;
			}
			
			
			if (result == null || result.isEmpty()) { //下拉加载更多，没有数据，保持之前内容
				return;
			}
			
			noThing.setVisibility(View.GONE);
			if (isLoadMore) {
				items.addAll(result);
			} else {
				items = result;
			}
			dataViewAdapter.notifyDataSetChanged();
			mPullRefreshListView.onRefreshComplete();
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
		public boolean isEnabled(int position) {
			return false;
		}
		@SuppressLint("SimpleDateFormat")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = new ViewHolder();
			if (convertView == null) {
				convertView = mLayoutInflater.inflate( R.layout.item, null);
			} else {
				viewHolder = (ViewHolder)convertView.getTag();
			}
			
			viewHolder.textViewOne = (TextView) convertView.findViewById(R.id.item);
			
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date dt = new Date(items.get(position).getUploadDate());  
			String sDateTime = sdf.format(dt); 
			viewHolder.textViewOne.setText(items.get(position).getAddress() + "("+ sDateTime +")");
			
			convertView.setTag( viewHolder);
			return convertView;
		}
		
	}
	
	private static class ViewHolder {
		TextView textViewOne;
	}
	
	@Override
	public void onClick(View v) {
		
	}

	public void setOtherUserInfo(UserResponse otherUserInfo) {
		this.otherUserInfo = otherUserInfo;
	}
}
