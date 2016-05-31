package com.fcbai.books.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fcbai.books.R;
import com.fcbai.books.entity.Book;
import com.fcbai.books.utils.DateUtil;

public class MainAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	
	private List<Book> books = new ArrayList<Book>();
	
	
	public MainAdapter(Context context) {
		Book b = new Book();
		b.setStartDate("2013-5-4");
		b.setEndDate("2013-6-7");
		b.setStartNumber(3);
		b.setName("this is test bookName");
		b.setCurrentPageNo(304);
		b.setTotalPageNo(670);
		books.add(b);
		this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return books == null || books.isEmpty() ? 0 : books.size();
	}

	@Override
	public Object getItem(int position) {
		return books.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_main, null);
			viewHolder = new ViewHolder();
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		Book book = books.get(position);
		
		viewHolder.progressBar = (ProgressBar)convertView.findViewById(R.id.pb_progressbar);
		
		//viewHolder.progressBar.setMax((int) (DateUtil.coverStringToDate(book.getEndDate()) - DateUtil.coverStringToDate(book.getStartDate())));
		viewHolder.progressBar.setMax(book.getTotalPageNo());
		long now = System.currentTimeMillis();
		int progress = (int) (now - DateUtil.coverStringToDate(book.getStartDate()));
		viewHolder.progressBar.setProgress(progress < 0 ? 0 : book.getCurrentPageNo());
		
		viewHolder.bookName = (TextView)convertView.findViewById(R.id.book_name);
		viewHolder.bookName.setText(book.getName());
		viewHolder.timeLength = (TextView)convertView.findViewById(R.id.time_end);
		viewHolder.timeLength.setText(book.getStartDate() + "~" + book.getEndDate());
		
		viewHolder.start = (RatingBar)convertView.findViewById(R.id.starts);
		viewHolder.start.setProgress(book.getStartNumber());
		
		/**
		viewHolder.currentNo = (TextView)convertView.findViewById(R.id.current_page_no);
		viewHolder.currentNo.setText(book.getCurrentPageNo() + "");
		
		viewHolder.totalNo = (TextView)convertView.findViewById(R.id.total_page_no);
		viewHolder.totalNo.setText(book.getTotalPageNo());
		**/
		return convertView;
	}
	
	private class ViewHolder {
		TextView bookName;
		TextView timeLength;
		ProgressBar progressBar;
		RatingBar start;
		TextView currentNo;
		TextView totalNo;
	}
 
}