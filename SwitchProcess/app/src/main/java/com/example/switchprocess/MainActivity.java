package com.example.switchprocess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    public TextView txvProcessMessage;
    private ListView lstProcessList;
    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txvProcessMessage = findViewById(R.id.txv_processName);
        lstProcessList  = findViewById(R.id.lst_processList);

        Toast.makeText(this, "listening", Toast.LENGTH_SHORT).show();
        server = new Server(this);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        server.onDestroy();
    }
}
