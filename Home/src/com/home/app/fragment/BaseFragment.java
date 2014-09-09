package com.home.app.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View.OnClickListener;

import com.home.app.LocationApplication;
import com.home.app.entity.LocalValues;
import com.home.app.entity.UserInfo;

abstract public class BaseFragment extends Fragment implements OnClickListener {
	protected UserInfo info = null;
	protected LocalValues localValues;
	@Override
	public void onAttach(Activity activity) {
		info = ((LocationApplication) activity.getApplication()).getUserInfo();
		localValues = ((LocationApplication) activity.getApplication()).getLocalValues();
		super.onAttach(activity);
	}
}
