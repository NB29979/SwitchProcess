package com.example.switchprocess;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CursorImageView extends android.support.v7.widget.AppCompatImageView{
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent _event){
        pos currentPos = new pos(_event.getX(), _event.getY());

        if(_event.getAction() == MotionEvent.ACTION_DOWN ||
                _event.getAction() == MotionEvent.ACTION_POINTER_DOWN){
            maxPointerCount = 0;
            oldPos.setPos(_event.getX(), _event.getY());
        }
        maxPointerCount = Math.max(_event.getPointerCount(), maxPointerCount);

        pos variation = new pos(currentPos.x - oldPos.x, currentPos.y - oldPos.y);

        switch (maxPointerCount){
            case 1:
                mainActivity.Send(new SendingData("MouseEvent", "MoveCursor",
                        (double) variation.x, (double) variation.y, maxPointerCount));
                break;
            case 2:
                mainActivity.Send(new SendingData("MouseEvent", "ScrollWindow",
                        (double) variation.x, (double) variation.y, maxPointerCount));
                break;
            case 3:
                mainActivity.Send(new SendingData("MouseEvent", "HScrollWindow",
                        (double) variation.x, (double) variation.y, maxPointerCount));
                break;
        }

        oldPos.setPos(currentPos.x, currentPos.y);

        return true;
    }

    public void SetActivity(MainActivity _mainActivity){
        mainActivity = _mainActivity;
    }
}
