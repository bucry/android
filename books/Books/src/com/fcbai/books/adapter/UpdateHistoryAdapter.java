package com.fcbai.books.adapter;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fcbai.books.R;
import com.fcbai.books.entity.HistoryUpdate;
import com.fcbai.books.views.FoldingLayout;
import com.fcbai.books.views.OnFoldListener;

public class UpdateHistoryAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<HistoryUpdate> hsitoryUpdate = new ArrayList<HistoryUpdate>();
	private String TAG_ARROW = "service_arrow";
    private String TAG_ITEM = "service_item";
    
    private Context context;
    private final int FOLD_ANIMATION_DURATION = 1000;
    
    private int mNumberOfFolds = 3;
	
	public UpdateHistoryAdapter(Context context) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		//return hsitoryUpdate == null || hsitoryUpdate.isEmpty() ? 0 : hsitoryUpdate.size();
		return 1;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	@Override
	public Object getItem(int position) {
		return hsitoryUpdate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_update_history, null);
			viewHolder = new ViewHolder();
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		
		
		
		
		viewHolder.mTrafficLayout = (LinearLayout) convertView.findViewById(R.id.traffic_layout);
		viewHolder.mLifeLayout = (LinearLayout) convertView.findViewById(R.id.life_layout);
		viewHolder.mMedicalLayout = (LinearLayout) convertView.findViewById(R.id.medical_layout);
		viewHolder.mLiveLayout = (LinearLayout) convertView.findViewById(R.id.live_layout);
		viewHolder.mPublicLayout = (LinearLayout) convertView.findViewById(R.id.public_layout);
                
		viewHolder.mTrafficBarLayout = (RelativeLayout) convertView.findViewById(R.id.traffic_bar_layout);
		viewHolder.mLifeBarLayout = (RelativeLayout) convertView.findViewById(R.id.life_bar_layout);
        viewHolder.mMedicalBarLayout = (RelativeLayout) convertView.findViewById(R.id.medical_bar_layout);
        viewHolder.mLiveBarLayout = (RelativeLayout) convertView.findViewById(R.id.live_bar_layout);
        viewHolder.mPublicBarLayout = (RelativeLayout) convertView.findViewById(R.id.public_bar_layout);
        
        viewHolder.mTrafficFoldingLayout = ((FoldingLayout) viewHolder.mTrafficLayout.findViewWithTag(TAG_ITEM));
        viewHolder.mLifeFoldingLayout = ((FoldingLayout) viewHolder.mLifeLayout.findViewWithTag(TAG_ITEM));
        viewHolder.mMedicalFoldingLayout = ((FoldingLayout) viewHolder.mMedicalLayout.findViewWithTag(TAG_ITEM));
        viewHolder.mLiveFoldingLayout = ((FoldingLayout) viewHolder.mLiveLayout.findViewWithTag(TAG_ITEM));
        viewHolder.mPublicFoldingLayout = ((FoldingLayout) viewHolder.mPublicLayout.findViewWithTag(TAG_ITEM));
        
        viewHolder.mBottomView = convertView.findViewById(R.id.bottom_view);
		
        
        
        
        
        
        
        viewHolder.mTrafficBarLayout.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                handleAnimation(v, viewHolder.mTrafficFoldingLayout, viewHolder.mTrafficLayout, viewHolder.mLifeLayout);
            }
        });
        
        viewHolder.mLifeBarLayout.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                handleAnimation(v, viewHolder.mLifeFoldingLayout, viewHolder.mLifeLayout, viewHolder.mMedicalLayout);
            }
        });
        
        viewHolder.mMedicalBarLayout.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                handleAnimation(v, viewHolder.mMedicalFoldingLayout, viewHolder.mMedicalLayout, viewHolder.mLiveLayout);
            }
        });

        viewHolder.mLiveBarLayout.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                handleAnimation(v, viewHolder.mLiveFoldingLayout, viewHolder.mLiveLayout, viewHolder.mPublicLayout);
            }
        });

        viewHolder.mPublicBarLayout.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                handleAnimation(v, viewHolder.mPublicFoldingLayout, viewHolder.mPublicLayout, viewHolder.mBottomView);
            }
        });
        
        viewHolder.mTrafficFoldingLayout.setNumberOfFolds(mNumberOfFolds);
        viewHolder.mLifeFoldingLayout.setNumberOfFolds(mNumberOfFolds);
        viewHolder.mMedicalFoldingLayout.setNumberOfFolds(mNumberOfFolds);
        viewHolder.mLiveFoldingLayout.setNumberOfFolds(mNumberOfFolds);
        viewHolder.mPublicFoldingLayout.setNumberOfFolds(mNumberOfFolds);
        
        animateFold(viewHolder.mTrafficFoldingLayout, 350);
        setMarginToTop(1, viewHolder.mLifeLayout);
        
        animateFold(viewHolder.mLifeFoldingLayout, 350);
        setMarginToTop(1, viewHolder.mMedicalLayout);
        
        animateFold(viewHolder.mMedicalFoldingLayout, 350);
        setMarginToTop(1, viewHolder.mLiveLayout);
        
        animateFold(viewHolder.mLiveFoldingLayout, 350);
        setMarginToTop(1, viewHolder.mPublicLayout);
        
        animateFold(viewHolder.mPublicFoldingLayout, 350);
        setMarginToTop(1, viewHolder.mBottomView);
        
        
		
		return convertView;
	}
	
	 private void handleAnimation(final View bar, final FoldingLayout foldingLayout, View parent, final View nextParent) {
	        
	        final ImageView arrow = (ImageView) parent.findViewWithTag(TAG_ARROW);
	        
	        foldingLayout.setFoldListener(new OnFoldListener() {
	            
	            @Override
	            public void onStartFold(float foldFactor) {
	                
	                bar.setClickable(true);
	                arrow.setBackgroundResource(R.drawable.service_arrow_up);
	                resetMarginToTop(foldingLayout, foldFactor, nextParent);
	            }
	            
	            @Override
	            public void onFoldingState(float foldFactor, float foldDrawHeight) {
	                bar.setClickable(false);
	                resetMarginToTop(foldingLayout, foldFactor, nextParent);
	            }
	            
	            @Override
	            public void onEndFold(float foldFactor) {
	                
	                bar.setClickable(true);
	                arrow.setBackgroundResource(R.drawable.service_arrow_down);
	                resetMarginToTop(foldingLayout, foldFactor, nextParent);
	            }
	        });
	        
//	      foldingLayout.setNumberOfFolds(mNumberOfFolds);
	        animateFold(foldingLayout, FOLD_ANIMATION_DURATION);
	        
	    }
	    
	    private void resetMarginToTop(View view, float foldFactor, View nextParent) {
	        
	        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) nextParent.getLayoutParams();
	        lp.topMargin =(int)( - view.getMeasuredHeight() * foldFactor) + dp2px(context, 10);
	        nextParent.setLayoutParams(lp);
	    }
	    
	    private void setMarginToTop(float foldFactor, View nextParent) {
	        
	        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) nextParent.getLayoutParams();
	        lp.topMargin =(int)( - dp2px(context, 135) * foldFactor) + dp2px(context, 10);
	        nextParent.setLayoutParams(lp);
	    }
	    
	    @SuppressLint("NewApi") 
	    public void animateFold(FoldingLayout foldLayout, int duration) {
	        float foldFactor = foldLayout.getFoldFactor();

	        ObjectAnimator animator = ObjectAnimator.ofFloat(foldLayout,
	                "foldFactor", foldFactor, foldFactor > 0 ? 0 : 1);
	        animator.setRepeatMode(ValueAnimator.REVERSE);
	        animator.setRepeatCount(0);
	        animator.setDuration(duration);
	        animator.setInterpolator(new AccelerateInterpolator());
	        animator.start();
	    }
	    
	    public final static int dp2px(Context context, float dpValue) {
	        float density = context.getResources().getDisplayMetrics().density;
	        return (int) (dpValue * density + 0.5f);
	    }
	
	
	private class ViewHolder {
		TextView bookName;
		TextView timeLength;
		ProgressBar progressBar;
		RatingBar start;
		TextView currentNo;
		TextView totalNo;
		
		
		View mBottomView;
		LinearLayout mTrafficLayout, mLifeLayout, mMedicalLayout, mLiveLayout, mPublicLayout;
	    RelativeLayout mTrafficBarLayout, mLifeBarLayout, mMedicalBarLayout, mLiveBarLayout, mPublicBarLayout;
	    FoldingLayout mTrafficFoldingLayout, mLifeFoldingLayout, mMedicalFoldingLayout, mLiveFoldingLayout, mPublicFoldingLayout;
	}
 
}
