package com.example.switchprocess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class WindowRecycleViewAdapter extends RecyclerView.Adapter<WindowViewHolder>{
    private List<WindowRowData> windowList;
    private Context context;

    public WindowRecycleViewAdapter(Context _context, List<WindowRowData> _list){
        this.context = _context;
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
        WindowRowData data_ = windowList.get(_pos);
        _holder.titleView.setText(data_.getTitle());

        if(!data_.getIcon().equals("null")){
            byte[] decodedByteArray_ = Base64.decode(data_.getIcon(), 0);
            Bitmap receivedBmp_ = BitmapFactory.decodeByteArray(decodedByteArray_, 0, decodedByteArray_.length);
            Bitmap resourceBmp_ = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

            receivedBmp_ = Bitmap.createScaledBitmap(receivedBmp_, resourceBmp_.getWidth(), resourceBmp_.getHeight(), true);
            _holder.iconView.setImageBitmap(receivedBmp_);
        }
    }
    @Override
    public int getItemCount(){
        return windowList.size();
    }
}
