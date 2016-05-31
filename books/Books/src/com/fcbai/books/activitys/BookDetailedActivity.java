package com.fcbai.books.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.fcbai.books.R;
import com.fcbai.books.adapter.UpdateHistoryAdapter;

public class BookDetailedActivity extends BaseActivity {
	
	private ListView historyUpdate;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_detailed);
		
		historyUpdate = (ListView)findViewById(R.id.update_history);
		historyUpdate.setAdapter(new UpdateHistoryAdapter(this));
	}

	@Override
	public void onClick(View v) {
		
	}
}
