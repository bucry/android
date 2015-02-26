package com.home.app.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.home.app.entity.LocalValues;
import com.home.app.server.UserServerInterface;
import com.home.app.utils.MessageType;
import com.home.app.utils.ObjectSerializable;
import com.home.app.utils.StatusCode;
import com.home.entity.request.MessageRequest;

public class CloseRecevier extends BroadcastReceiver {
	
	private LocalValues localValues = (LocalValues) ObjectSerializable.readObject();
	
    public void onReceive(Context context, Intent intent) {
       // if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
        	MessageRequest request = new MessageRequest();
			request.setEventType(MessageType.SHUTDOWN);
			request.setMessageName("关机");
			request.setUid(localValues.getUid());
			new AsyncAndAddMessage().execute(request);
       // }
    }
    
    private class AsyncAndAddMessage extends AsyncTask<MessageRequest, Integer, Integer> {
  		@Override
  		protected Integer doInBackground(MessageRequest... params) {
  			try {
  				UserServerInterface.getInstance().getUserServer().addEventMessage(params[0]);
  				return StatusCode.SUCCESS;
  			} catch (Exception e) {
  				e.printStackTrace();
  				return StatusCode.FAILED;
  			}
  		}
  		
  		@Override
  		protected void onPostExecute(Integer result) {
  			
  		}
  	}
}
