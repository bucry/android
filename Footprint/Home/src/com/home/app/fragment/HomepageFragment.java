package com.home.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.home.app.R;
import com.home.app.adatper.HomepageDataViewAdapter;
import com.home.app.dialog.HistoryDatiledDialog;

public class HomepageFragment extends BaseFragment implements OnItemClickListener {
	
	private GridView menuView;
	private LayoutInflater mLayoutInflater;
	private View staticView;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.homepage_layout, null);
		staticView = view;
		mLayoutInflater = inflater;
		menuView = (GridView)view.findViewById(R.id.home_menu);
		HomepageDataViewAdapter homepageDataViewAdapter = new HomepageDataViewAdapter();
		homepageDataViewAdapter.setmLayoutInflater(inflater);
		homepageDataViewAdapter.setInfo(info);
		homepageDataViewAdapter.setHomepageDataViewAdapter(homepageDataViewAdapter);
		menuView.setAdapter(homepageDataViewAdapter);
		return view;
	}

	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		HistoryDatiledDialog historyDatiledDialog = new HistoryDatiledDialog(staticView.getContext());
		historyDatiledDialog.setmLayoutInflater(mLayoutInflater);
		historyDatiledDialog.setInfo(info);
		historyDatiledDialog.show();
	}
}
