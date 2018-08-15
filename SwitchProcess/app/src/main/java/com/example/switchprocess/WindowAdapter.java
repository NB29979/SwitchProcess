package com.example.switchprocess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class WindowAdapter extends BaseAdapter{
    private ArrayList<String> windows;
    private LayoutInflater inflater;
    private int resourceId;
    private String[] items;

    WindowAdapter(Context _context, ArrayList<String> _windows){
        windows = _windows;
        inflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class WindowViewHolder{
        Button windowButton;

        WindowViewHolder(View _view){
            windowButton = _view.findViewById(R.id.btn_windowSelect);
        }
    }
    @Override
    public View getView(final int _pos, View _view, final ViewGroup _parent){
        String windowTitle_ = windows.get(_pos);
        final WindowViewHolder holder_;

        if(_view == null){
            _view = inflater.inflate(R.layout.window_prop, null);
            holder_ = new WindowViewHolder(_view);
            _view.setTag(holder_);
        }
        else{
            holder_ = (WindowViewHolder)_view.getTag();
        }
        holder_.windowButton.setText(windowTitle_);

        return _view;
    }
    @Override
    public int getCount(){
        return windows.size();
    }
    @Override
    public Object getItem(int _pos){
        return windows.get(_pos);
    }
    @Override
    public long getItemId(int _pos){
        return 0;
    }
}
