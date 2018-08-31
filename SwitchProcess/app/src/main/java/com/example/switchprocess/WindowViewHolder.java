package com.example.switchprocess;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WindowViewHolder extends RecyclerView.ViewHolder {
    public TextView titleView;
    public ImageView iconView;

    WindowViewHolder(View _view){
        super(_view);
        titleView = _view.findViewById(R.id.txv_title);
        iconView = _view.findViewById(R.id.imv_icon);
    }
}
