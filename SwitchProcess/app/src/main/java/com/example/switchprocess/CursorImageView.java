package com.example.switchprocess;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class CursorImageView extends android.support.v7.widget.AppCompatImageView{
    private GestureDetector gestureDetector;
    private MainActivity mainActivity;
    private int maxPointerCount;

    public CursorImageView(Context _context){
        this(_context, null);
    }
    public CursorImageView(Context _context, AttributeSet _attrs){
        this(_context, _attrs, 0);
    }
    public CursorImageView(Context _context, AttributeSet _attrs, int _defStyleAttr){
        super(_context, _attrs, _defStyleAttr);
        gestureDetector = new GestureDetector(_context, onGestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent _event){
        if(_event.getAction() == MotionEvent.ACTION_DOWN ||
                _event.getAction() == MotionEvent.ACTION_POINTER_DOWN){
            maxPointerCount = 0;
        }
        gestureDetector.onTouchEvent(_event);
        maxPointerCount = Math.max(_event.getPointerCount(), maxPointerCount);
        return true;
    }

    public void SetActivity(MainActivity _mainActivity){
        mainActivity = _mainActivity;
    }

    private GestureDetector.SimpleOnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(MotionEvent _event){
            mainActivity.Send(new SendData("MouseEvent", "SingleTap"), 2);
            return false;
        }
        @Override
        public boolean onFling(MotionEvent _eventFrom, MotionEvent _eventTo, float _vX, float _vY) {
            class pos_{
                float x,y;
                pos_(float _x, float _y){ x=_x;y=_y; }
            }
            pos_ from_ = new pos_(_eventFrom.getX(), _eventFrom.getY());
            pos_ to_ = new pos_(_eventTo.getX(), _eventTo.getY());
            float diffX_ = to_.x-from_.x;
            float diffY_ = to_.y-from_.y;
            float absX_ = Math.abs(diffX_);
            float absY_ = Math.abs(diffY_);

            if((absX_+absY_) < 50)return false;
            else{
                mainActivity.Send(new SendData("MouseEvent","MoveCursor",
                        (double)absX_, (double)absY_,Math.atan2((double)diffY_, (double)diffX_), maxPointerCount), 2);
            }
            return false;
        }
    };
}
