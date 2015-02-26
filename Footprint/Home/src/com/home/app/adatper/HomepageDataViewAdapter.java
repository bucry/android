package com.home.app.adatper;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.app.R;
import com.home.app.dialog.HomeCreateDialog;
import com.home.app.dialog.HomeDatiledDialog;
import com.home.app.entity.UserInfo;
import com.home.app.server.UserServerInterface;
import com.home.entity.response.HomeResponse;

public class HomepageDataViewAdapter extends BaseAdapter {
	
	private LayoutInflater mLayoutInflater;
	private List<HomeResponse> items;
	private HomepageDataViewAdapter homepageDataViewAdapter;
	private UserInfo info;
	private static final int PROPS_NUMBER_SIZE = 3;
	@Override
	public int getCount() {
		return items == null || items.isEmpty() ? 0 : ((items.size() % PROPS_NUMBER_SIZE == 0) ? items.size() / PROPS_NUMBER_SIZE : (items.size() / PROPS_NUMBER_SIZE + 1));
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
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = mLayoutInflater.inflate( R.layout.home_menu_item_layout, null);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		final View staticView = convertView;
		
		viewHolder.homeOne = (ImageView) convertView.findViewById(R.id.home_1);
		viewHolder.homeTwo = (ImageView) convertView.findViewById(R.id.home_2);
		viewHolder.homeThree = (ImageView) convertView.findViewById(R.id.home_3);
		
		viewHolder.honeNameOne = (TextView) convertView.findViewById(R.id.home_name_1);
		viewHolder.honeNameTwo = (TextView) convertView.findViewById(R.id.home_name_2);
		viewHolder.honeNameThree = (TextView) convertView.findViewById(R.id.home_name_3);
		
		//第一列
		if (position * 3 + 0 < items.size())	{
			HomeResponse response = items.get(position * 3 + 0);
			final HomeResponse home = response;
			if (response.getHomeId() == -1) {
				viewHolder.homeOne.setVisibility(View.VISIBLE);
				viewHolder.homeOne.setImageResource(R.drawable.default_home_add);
				viewHolder.honeNameOne.setText(response.getHomeName());
				viewHolder.honeNameOne.setVisibility(View.VISIBLE);
				viewHolder.homeOne.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						HomeCreateDialog homeDatiledDialog = new HomeCreateDialog(staticView.getContext());
						homeDatiledDialog.setInfo(info);
						homeDatiledDialog.setHomepageDataViewAdapter(homepageDataViewAdapter);
						homeDatiledDialog.show();
					}
				});
			
			} else {
				viewHolder.honeNameOne.setText(response.getHomeName());
				viewHolder.honeNameOne.setVisibility(View.VISIBLE);
				viewHolder.homeOne.setVisibility(View.VISIBLE);
				viewHolder.homeOne.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						HomeDatiledDialog homeDatiledDialog = new HomeDatiledDialog(staticView.getContext());
						homeDatiledDialog.setInfo(info);
						homeDatiledDialog.setHome(home);
						homeDatiledDialog.setHomepageDataViewAdapter(homepageDataViewAdapter);
						homeDatiledDialog.setmLayoutInflater(mLayoutInflater);
						homeDatiledDialog.show();
					}
				});
			}
		} else {
			viewHolder.homeOne.setVisibility(View.GONE);
			viewHolder.honeNameOne.setVisibility(View.GONE);
		}
		//第二列
		if (position * 3 + 1 < items.size()) {
			HomeResponse response = items.get(position * 3 + 1);
			final HomeResponse home = response;
			if (response.getHomeId() == -1) {
				viewHolder.homeTwo.setVisibility(View.VISIBLE);
				viewHolder.honeNameTwo.setText(response.getHomeName());
				viewHolder.honeNameTwo.setVisibility(View.VISIBLE);
				viewHolder.homeTwo.setImageResource(R.drawable.default_home_add);
				viewHolder.homeTwo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						HomeCreateDialog homeDatiledDialog = new HomeCreateDialog(staticView.getContext());
						homeDatiledDialog.setInfo(info);
						homeDatiledDialog.setHomepageDataViewAdapter(homepageDataViewAdapter);
						homeDatiledDialog.show();
					}
				});
			} else {
				viewHolder.honeNameTwo.setText(response.getHomeName());
				viewHolder.honeNameTwo.setVisibility(View.VISIBLE);
				viewHolder.homeTwo.setVisibility(View.VISIBLE);
				viewHolder.homeTwo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						HomeDatiledDialog homeDatiledDialog = new HomeDatiledDialog(staticView.getContext());
						homeDatiledDialog.setInfo(info);
						homeDatiledDialog.setHome(home);
						homeDatiledDialog.setHomepageDataViewAdapter(homepageDataViewAdapter);
						homeDatiledDialog.setmLayoutInflater(mLayoutInflater);
						homeDatiledDialog.show();
					}
				});
			}
		} else {
			viewHolder.homeTwo.setVisibility(View.GONE);
			viewHolder.honeNameTwo.setVisibility(View.GONE);
		}
		//第三列
		if (position * 3 + 2 < items.size())	{
			HomeResponse response = items.get(position * 3 + 2);
			final HomeResponse home = response;
			if (response.getHomeId() == -1) {
				viewHolder.homeThree.setVisibility(View.VISIBLE);
				viewHolder.honeNameThree.setText(response.getHomeName());
				viewHolder.honeNameThree.setVisibility(View.VISIBLE);
				viewHolder.homeThree.setImageResource(R.drawable.default_home_add);
				viewHolder.homeThree.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						HomeCreateDialog homeDatiledDialog = new HomeCreateDialog(staticView.getContext());
						homeDatiledDialog.setInfo(info);
						homeDatiledDialog.setHomepageDataViewAdapter(homepageDataViewAdapter);
						homeDatiledDialog.show();
					}
				});
			} else {
				viewHolder.honeNameThree.setText(response.getHomeName());
				viewHolder.honeNameThree.setVisibility(View.VISIBLE);
				viewHolder.homeThree.setVisibility(View.VISIBLE);
				viewHolder.homeThree.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						HomeDatiledDialog homeDatiledDialog = new HomeDatiledDialog(staticView.getContext());
						homeDatiledDialog.setInfo(info);
						homeDatiledDialog.setHome(home);
						homeDatiledDialog.setHomepageDataViewAdapter(homepageDataViewAdapter);
						homeDatiledDialog.setmLayoutInflater(mLayoutInflater);
						homeDatiledDialog.show();
					}
				});
			}
		} else {
			viewHolder.homeThree.setVisibility(View.GONE);
			viewHolder.honeNameThree.setVisibility(View.GONE);
		}
		convertView.setTag( viewHolder);
		return convertView;
	}
	
	
	private class ViewHolder {
		ImageView homeOne;
		ImageView homeTwo;
		ImageView homeThree;
		TextView honeNameOne;
		TextView honeNameTwo;
		TextView honeNameThree;
	}

	private class AsyncAndGetUserHomes extends AsyncTask<Integer, Integer, List<HomeResponse>> {
		@Override
		protected List<HomeResponse> doInBackground(Integer... params) {
			try {
				List<HomeResponse> items = UserServerInterface.getInstance().getUserServer().findHomes(info.getUid());
				return items;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(List<HomeResponse> result) {
			
			if (result == null || result.isEmpty()) {
				result = new ArrayList<HomeResponse>();
			}
			
			HomeResponse response = new HomeResponse();
			response.setHomeId(-1);
			response.setHomeName("创建家庭");
			items = result;
			items.add(response);
			info.setHomes(result);
			//HomepageDataViewAdapter.this.notifyDataSetChanged();
			homepageDataViewAdapter.notifyDataSetChanged();
		}
	}
	
	public void setmLayoutInflater(LayoutInflater mLayoutInflater) {
		this.mLayoutInflater = mLayoutInflater;
	}

	public void setInfo(UserInfo info) {
		this.info = info;
		new AsyncAndGetUserHomes().execute();
	}

	public void setHomepageDataViewAdapter(
			HomepageDataViewAdapter homepageDataViewAdapter) {
		this.homepageDataViewAdapter = homepageDataViewAdapter;
	}

	public List<HomeResponse> getItems() {
		return items;
	}
}
