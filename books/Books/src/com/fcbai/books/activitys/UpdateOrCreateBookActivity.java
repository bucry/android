package com.fcbai.books.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.fcbai.books.R;
import com.fcbai.books.entity.Book;
import com.fcbai.books.utils.CustomDate;

public class UpdateOrCreateBookActivity extends BaseActivity {
	
	public static final int TO_SELECT_PHOTO = 3;
	private final int START_DATE = 0;
	private final int END_DATE = 1;
	
	private Button selectButton;
	private Button startDate;
	private Button endDate;
	private Button save, close;
	private String picPath = null;
	private ImageView imageView;
	private CustomDate startDateObject;
	private EditText currentNo, totalNo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_book);
		selectButton = (Button) this.findViewById(R.id.select_image);
        selectButton.setOnClickListener(this);
        startDate = (Button) this.findViewById(R.id.start_date);
        startDate.setOnClickListener(this);
        endDate = (Button) this.findViewById(R.id.end_date);
        endDate.setOnClickListener(this);
        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(this);
        close = (Button)findViewById(R.id.close);
        close.setOnClickListener(this);
        currentNo = (EditText)findViewById(R.id.current_page_no);
        totalNo = (EditText)findViewById(R.id.total_page_no);
        
        imageView = (ImageView)findViewById(R.id.image_view);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapFactory.decodeFile(picPath);
			imageView.setImageBitmap(bm);
		}
		
		if (data != null) {
			Bundle bundle = data.getExtras();
			CustomDate date = (CustomDate) bundle.getSerializable("date");
			switch (requestCode) {
			case START_DATE:
				if (date != null)
					startDate.setText(createDate(date, DateType.START));
				break;
			case END_DATE:
				if (date != null)
					endDate.setText(createDate(date, DateType.START));
				break;
			default:
				break;
			}
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.select_image:
			intent = new Intent(this,SelectPicActivity.class);
			startActivityForResult(intent, TO_SELECT_PHOTO);
			break;
		case R.id.start_date:
			intent.setClass(v.getContext(), CustomerDataActivity.class);
			intent.putExtra("result", START_DATE);
			startActivityForResult(intent, START_DATE);
			break;
		case R.id.end_date:
			intent.setClass(v.getContext(), CustomerDataActivity.class);
			intent.putExtra("result", END_DATE);
			startActivityForResult(intent, END_DATE);
			break;
		case R.id.close:
			finish();
			break;
		case R.id.save:
			Book book = new Book();
			break;
		default:
			break;
		}
	}
	
	private interface DateType {
		int START = 0;
	}
	
	private String createDate(CustomDate date, int type) {
		StringBuffer sb = new StringBuffer();
		sb.append(date.year).append("年");
		sb.append(date.month).append("月");
		sb.append(date.day).append("日");
		switch (type) {
		case DateType.START:
			startDateObject = date;
			break;
		default:
			break;
		}
		return sb.toString();
	}
	
}
