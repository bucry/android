package com.fcbai.books.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fcbai.books.R;
import com.fcbai.books.utils.CalendarCard;
import com.fcbai.books.utils.CalendarCard.OnCellClickListener;
import com.fcbai.books.utils.CalendarViewAdapter;
import com.fcbai.books.utils.CustomDate;

public class CustomerDataActivity extends Activity implements OnClickListener, OnCellClickListener{  
    private ViewPager mViewPager;  
    private int mCurrentIndex = 498;  
    private CalendarCard[] mShowViews;  
    private CalendarViewAdapter<CalendarCard> adapter;  
    private SildeDirection mDirection = SildeDirection.NO_SILDE;  
    public final static int RESULT_CODE=1;  
    enum SildeDirection {  
        RIGHT, LEFT, NO_SILDE;  
    }  
      
    private ImageButton preImgBtn, nextImgBtn;  
    private TextView monthText;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.activity_choice_date);  
        mViewPager = (ViewPager) this.findViewById(R.id.vp_calendar);  
        preImgBtn = (ImageButton) this.findViewById(R.id.btnPreMonth);  
        nextImgBtn = (ImageButton) this.findViewById(R.id.btnNextMonth);  
        monthText = (TextView) this.findViewById(R.id.tvCurrentMonth);  
        preImgBtn.setOnClickListener(this);  
        nextImgBtn.setOnClickListener(this);  
          
        CalendarCard[] views = new CalendarCard[3];  
        for (int i = 0; i < 3; i++) {  
            views[i] = new CalendarCard(this, this);  
        }  
        adapter = new CalendarViewAdapter<CalendarCard>(views);  
        setViewPager();  
  
    }  
  
    private void setViewPager() {  
        mViewPager.setAdapter(adapter);  
        mViewPager.setCurrentItem(498);  
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {  
              
            @Override  
            public void onPageSelected(int position) {  
                measureDirection(position);  
                updateCalendarView(position);                 
            }  
              
            @Override  
            public void onPageScrolled(int arg0, float arg1, int arg2) {  
                  
            }  
              
            @Override  
            public void onPageScrollStateChanged(int arg0) {  
                  
            }  
        });  
    }  
  
    @Override  
    public void onClick(View v) {  
        switch (v.getId()) {  
        case R.id.btnPreMonth:  
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);  
            break;  
        case R.id.btnNextMonth:  
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);  
            break;  
        default:  
            break;  
        }  
    }  
  
    @Override  
    public void clickDate(CustomDate date) {  
    	Intent intent=new Intent(); 
        Bundle mBundle = new Bundle(); 
        mBundle.putSerializable("date",date);
        intent.putExtras(mBundle);
        setResult(RESULT_CODE, intent);  
        finish();
    }  
  
    @Override  
    public void changeDate(CustomDate date) {  
    	monthText.setText(date.month + "月");
    }  
  
    /** 
     * 计算方向 
     *  
     * @param arg0 
     */  
    private void measureDirection(int arg0) {  
  
        if (arg0 > mCurrentIndex) {  
            mDirection = SildeDirection.RIGHT;  
  
        } else if (arg0 < mCurrentIndex) {  
            mDirection = SildeDirection.LEFT;  
        }  
        mCurrentIndex = arg0;  
    }  
  
    // 更新日历视图  
    private void updateCalendarView(int arg0) {  
        mShowViews = adapter.getAllItems();  
        if (mDirection == SildeDirection.RIGHT) {  
            mShowViews[arg0 % mShowViews.length].rightSlide();  
        } else if (mDirection == SildeDirection.LEFT) {  
            mShowViews[arg0 % mShowViews.length].leftSlide();  
        }  
        mDirection = SildeDirection.NO_SILDE;  
    }  
  
  
      
}  
