package com.example.switchprocess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.app.LoaderManager;
import android.content.Loader;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity{
    private RecyclerView rvwWindowList;
    private CursorImageView civCursorRect;

    private WindowRecycleViewAdapter windowRecycleViewAdapter;

    private Server server;
    private String remoteIPAddress;
    private ArrayList<WindowRowData> windowList;

    private boolean isRClickDOWN;
    private boolean isLClickDOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        windowList = new ArrayList<>();

        rvwWindowList = findViewById(R.id.rvw_windowRecyclerView);
        civCursorRect = findViewById(R.id.civ_cursorRect);

        isRClickDOWN = false;
        isLClickDOWN = false;

        // ウインドウタイトルを選択した場合
        windowRecycleViewAdapter = new WindowRecycleViewAdapter(windowList){
            @Override
            public WindowViewHolder onCreateViewHolder(ViewGroup _parent, int _viewType) {
                final WindowViewHolder holder_ = super.onCreateViewHolder(_parent, _viewType);
                holder_.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int pos_ = holder_.getAdapterPosition();
                        Send(new SendData("SelectTitle", windowList.get(pos_).getTitle()), 1);
                    }
                });
                return holder_;
            }
        };

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvwWindowList.setLayoutManager(llm);
        rvwWindowList.setAdapter(windowRecycleViewAdapter);
        civCursorRect.SetActivity(this);


        Toast.makeText(this, "listening", Toast.LENGTH_SHORT).show();
        server = new Server(this);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        server.onDestroy();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent _keyevent) {
        switch (_keyevent.getKeyCode()) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (_keyevent.getAction() == KeyEvent.ACTION_DOWN) {
                    if(!isRClickDOWN) {
                        Send(new SendData("MouseEvent", "LClickDOWN"), 2);
                        isRClickDOWN = true;
                    }
                }
                else{
                    isRClickDOWN = false;
                    Send(new SendData("MouseEvent", "LClickUP"), 2);
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (_keyevent.getAction() == KeyEvent.ACTION_DOWN) {
                    if(!isLClickDOWN) {
                        Send(new SendData("MouseEvent", "RClickDOWN"), 2);
                        isLClickDOWN = true;
                    }
                }
                else{
                    isLClickDOWN = false;
                    Send(new SendData("MouseEvent", "RClickUP"), 2);
                }
                break;
        }
        return true;
    }

    private final LoaderManager.LoaderCallbacks<String> callbacks = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String>onCreateLoader(int _i, Bundle _bundle){
            return new SelectWindowAsyncLoader(getApplicationContext(),
                    _bundle.getString("ipAddress"), _bundle.getString("sendMessage"));
        }
        @Override
        public void onLoadFinished(Loader<String> _loader, String _status){
            getSupportLoaderManager().destroyLoader(_loader.getId());
            System.out.println(_status);
        }
        @Override
        public void onLoaderReset(Loader<String> _loader){}
    };

    public void setRemoteIPAddress(String _ipAddress){
        remoteIPAddress = _ipAddress.split(":")[0].substring(1);
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
    public void Send(SendData _sendData, int _loaderID){
        String data = new Gson().toJson(_sendData);

        Bundle bundle_ = new Bundle();
        bundle_.putString("ipAddress", remoteIPAddress);
        bundle_.putString("sendMessage", data);

        getLoaderManager().restartLoader(_loaderID, bundle_, callbacks);
    }
}
