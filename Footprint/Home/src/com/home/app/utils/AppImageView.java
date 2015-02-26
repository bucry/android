package com.home.app.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ImageView;

public class AppImageView extends ImageView {
	private int mTouchSlop;

	public AppImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public AppImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public AppImageView(Context context) {
		super(context);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	private boolean bZoomOut = false;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch(action){
		case MotionEvent.ACTION_DOWN:
			bZoomOut = true;
			break;
		case MotionEvent.ACTION_CANCEL:
			bZoomOut = false;
			break;
		case MotionEvent.ACTION_MOVE:
			if(!pointInView(event.getX(), event.getY())){
				bZoomOut = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			bZoomOut = false;
			break;
		}
		invalidate();
		return super.onTouchEvent(event);
	}
	
	private boolean pointInView(float localX, float localY) {
		int slop = mTouchSlop;
        return localX >= -slop && localY >= -slop && localX < ((getRight() - getLeft()) + slop) &&
                localY < ((getBottom() - getTop()) + slop);
    }

	private static final float scale = 1.1f;
	@Override
	protected void onDraw(Canvas canvas) {
		int sc = canvas.save();
		if(bZoomOut){
			int width = getWidth();
			int height = getHeight();
			canvas.scale(scale, scale, width/2, height/2);
		}
		super.onDraw(canvas);
		canvas.restoreToCount(sc);
	}

}
