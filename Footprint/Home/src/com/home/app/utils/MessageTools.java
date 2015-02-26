package com.home.app.utils;
import android.app.AlertDialog;  
import android.app.AlertDialog.Builder;  
import android.content.Context;  
import android.content.DialogInterface;  
import android.content.DialogInterface.OnClickListener;  
  
public class MessageTools {  
	
    public static void ShowDialog(Context context, String text){  
        AlertDialog.Builder builder = new Builder(context);  
          builder.setMessage(text);  
  
          builder.setTitle("提示");  
  
          builder.setPositiveButton("确认", new OnClickListener() {  
  
           @Override  
           public void onClick(DialogInterface dialog, int which) {  
            dialog.dismiss();  
  
            dialog.dismiss();  
           }  
          });  
  
          builder.setNegativeButton("取消", new OnClickListener() {  
           @Override  
           public void onClick(DialogInterface dialog, int which) {  
            dialog.dismiss();  
           }  
          });  
          builder.create().show();  
    }  
}  