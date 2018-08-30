package com.example.switchprocess;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class CursorImageView extends android.support.v7.widget.AppCompatImageView{
    private GestureDetector gestureDetector;
    private MainActivity mainActivity;
    private int maxPointerCount;
    private class pos{
        float x,y;
        pos(float _x, float _y){setPos(_x, _y);}
        void setPos(float _x, float _y){ x = _x; y = _y;}
    }
    private pos oldPos = new pos(0,0);

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
        pos currentPos = new pos(_event.getX(), _event.getY());

        if(_event.getAction() == MotionEvent.ACTION_DOWN ||
                _event.getAction() == MotionEvent.ACTION_POINTER_DOWN){
            maxPointerCount = 0;
            oldPos.setPos(_event.getX(), _event.getY());
        }
        gestureDetector.onTouchEvent(_event);
        maxPointerCount = Math.max(_event.getPointerCount(), maxPointerCount);

        pos variation = new pos(currentPos.x-oldPos.x, currentPos.y-oldPos.y);

        mainActivity.Send(new SendData("MouseEvent","MoveCursor",
                (double)variation.x, (double)variation.y,0, maxPointerCount));

        oldPos.setPos(currentPos.x, currentPos.y);

        return true;
    }

    public void SetActivity(MainActivity _mainActivity){
        mainActivity = _mainActivity;
    }

    private GestureDetector.SimpleOnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(MotionEvent _event){
            mainActivity.Send(new SendData("MouseEvent", "SingleTap"));
            return false;
        }
        @Override
        public boolean onFling(MotionEvent _eventFrom, MotionEvent _eventTo, float _vX, float _vY) {
            return false;
        }
    };
}
