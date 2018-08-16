package com.example.switchprocess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity{
    private RecyclerView rvwWindowList;

    private WindowRecycleViewAdapter windowRecycleViewAdapter;

    private Server server;
    private ArrayList<WindowRowData> windowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        windowList = new ArrayList<>();

        rvwWindowList = findViewById(R.id.rvw_windowRecyclerView);

        windowRecycleViewAdapter = new WindowRecycleViewAdapter(windowList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvwWindowList.setLayoutManager(llm);
        rvwWindowList.setAdapter(windowRecycleViewAdapter);

        Toast.makeText(this, "listening", Toast.LENGTH_SHORT).show();
        server = new Server(this);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        server.onDestroy();
    }

    public void setLsvWindowList(ArrayList<String> _newWindowList){
        ArrayList<String> titles = new ArrayList<>();
        for(WindowRowData w : windowList) titles.add(w.getTitle());

        // 閉じられたウインドウをリストから削除
        for (String w : titles) {
            if (!_newWindowList.contains(w)) {
                removeWindow(w);
            }
        }
        // 過去のウインドウリストにない新規ウインドウを追加
        for(String w : _newWindowList){
            if(!titles.contains(w)){
                addWindow(w);
            }
        }
    }
    public void addWindow(String _windowTitle){
        windowList.add(new WindowRowData(_windowTitle));
        windowRecycleViewAdapter.notifyItemChanged(windowList.size()-1);
    }
    public void removeWindow(String _windowTitle){
        int index_ = windowList.indexOf(new WindowRowData(_windowTitle));
        Iterator<WindowRowData> it = windowList.iterator();

        while(it.hasNext()){
            WindowRowData rowData_ = it.next();
            if(rowData_.getTitle().equals(_windowTitle)){
                it.remove();
                windowRecycleViewAdapter.notifyItemRemoved(index_);
                windowRecycleViewAdapter.notifyItemRangeChanged(index_, windowList.size());
                break;
            }
        }
    }
}
