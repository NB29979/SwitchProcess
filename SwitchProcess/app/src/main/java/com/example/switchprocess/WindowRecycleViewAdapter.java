package com.example.switchprocess;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class WindowRecycleViewAdapter extends RecyclerView.Adapter<WindowViewHolder>{
    private List<WindowRowData> windowList;

    public WindowRecycleViewAdapter(List<WindowRowData> _list){
        this.windowList = _list;
    }
    @Override
    public WindowViewHolder onCreateViewHolder(ViewGroup _parent, int _viewType){
        View inflate = LayoutInflater.from(_parent.getContext())
                .inflate(R.layout.row, _parent,false);
        WindowViewHolder holder_ = new WindowViewHolder(inflate);
        return holder_;
    }
    @Override
    public void onBindViewHolder(WindowViewHolder _holder, int _pos){
        _holder.titleView.setText(windowList.get(_pos).getTitle());
    }
    @Override
    public int getItemCount(){
        return windowList.size();
    }
}
