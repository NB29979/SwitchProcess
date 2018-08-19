package com.example.switchprocess;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class CursorImageView extends android.support.v7.widget.AppCompatImageView{
    private GestureDetector gestureDetector;

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
        gestureDetector.onTouchEvent(_event);
        return true;
    }

    private GestureDetector.SimpleOnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener(){
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

            if((Math.abs(diffX_) + Math.abs(diffY_)) < 50)return false;
            else{
                System.out.println(
                Math.atan2((double)diffY_, (double)diffX_));
            }

            System.out.println("fling");
            return false;
        }
    };
}
