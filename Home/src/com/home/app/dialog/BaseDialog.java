package com.home.app.dialog;

import android.app.Dialog;
import android.content.Context;

import com.home.app.entity.UserInfo;

abstract public class BaseDialog extends Dialog implements
		android.view.View.OnClickListener {

	protected UserInfo info;

	public BaseDialog(Context context) {
		super(context);
	}

	public UserInfo getInfo() {
		return info;
	}

	public void setInfo(UserInfo info) {
		this.info = info;
	}

}
