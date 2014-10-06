package com.home.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption.LocationMode;
import com.home.app.LocationApplication;
import com.home.app.R;

public class SetFragment extends BaseFragment {
	
	private LocationClient mLocationClient;
	private Button startLocation;
	private RadioGroup selectMode, selectCoordinates;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor = "gcj02";
	
	@Override
	public void onAttach(Activity activity) {
		mLocationClient = ((LocationApplication) activity.getApplication()).mLocationClient;
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.set_layout, null);
		setValue(tempMode, tempcoor);
		startLocation = (Button) view.findViewById(R.id.saveSet);
		startLocation.setOnClickListener(this);
		selectMode = (RadioGroup) view.findViewById(R.id.selectMode);
		selectCoordinates = (RadioGroup) view.findViewById(R.id.selectCoordinates);
		selectMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_hight:
					tempMode = LocationMode.Hight_Accuracy;
					break;
				case R.id.radio_low:
					tempMode = LocationMode.Battery_Saving;
					break;
				case R.id.radio_device:
					tempMode = LocationMode.Device_Sensors;
					break;
				default:
					break;
				}
			}
		});
		selectCoordinates
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.radio_gcj02:
							tempcoor = "gcj02";
							break;
						case R.id.radio_bd09ll:
							tempcoor = "bd09ll";
							break;
						case R.id.radio_bd09:
							tempcoor = "bd09";
							break;
						default:
							break;
						}
					}
				});
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saveSet:
			Toast.makeText(v.getContext(), "保存成功", Toast.LENGTH_LONG).show();
			break;

		default:
			break;
		}
	}
	
	private void setValue(LocationMode tempMode, String tempcoor) {
		this.tempMode = tempMode;
		this.tempcoor = tempcoor;
	}
	
	@Override
	public void onStop() {
		mLocationClient.stop();
		super.onStop();
	}
}
