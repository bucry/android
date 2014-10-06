package com.home.app.dialog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.home.app.R;
import com.home.app.hessian.HessianManager;
import com.home.app.user.IUserServer;
import com.home.entity.request.FindHistoryRequest;
import com.home.entity.response.LocalPositionResponse;

public class HistoryDatiledDialog extends BaseDialog  {
	private ListView listView;
	private List<LocalPositionResponse> items;
	private DataViewAdapter dataViewAdapter = new DataViewAdapter();
	private LayoutInflater mLayoutInflater;
	
	public HistoryDatiledDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setContentView(R.layout.history_layout);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		listView = (ListView)findViewById(R.id.listItems);
		new AsyncAndGetUser().execute();

	}

	private class AsyncAndGetUser extends AsyncTask<Integer, Integer, List<LocalPositionResponse>> {
		@Override
		protected List<LocalPositionResponse> doInBackground(Integer... params) {
			try {
				IUserServer loginService=(IUserServer)HessianManager.createConnect(IUserServer.class, "IUserServer");
				FindHistoryRequest request = new FindHistoryRequest();
				request.setCurrentUid(1);
				request.setTargetUid(1);
				request.setPageNo(1);
				request.setPageSize(20);
				List<LocalPositionResponse> items = loginService.findLocalPositionByHome(request);
				return items;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(List<LocalPositionResponse> result) {
			Set<LocalPositionResponse> set = new HashSet<LocalPositionResponse>(result);
			items = new ArrayList<LocalPositionResponse>(set);
			dataViewAdapter.notifyDataSetChanged();
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
			viewHolder.textViewOne.setText(items.get(position).getAddress() + "("+ items.get(position).getLatitude() + "," + items.get(position).getLongitude() +")");
			
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

	public LayoutInflater getmLayoutInflater() {
		return mLayoutInflater;
	}

	public void setmLayoutInflater(LayoutInflater mLayoutInflater) {
		this.mLayoutInflater = mLayoutInflater;
	}
}
