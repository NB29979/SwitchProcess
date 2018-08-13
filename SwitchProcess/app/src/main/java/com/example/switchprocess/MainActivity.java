package com.example.switchprocess;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity{
    private ServerSocket serverSocket;
    private static final int port = 8080;
    private TextView txvProcessMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txvProcessMessage = findViewById(R.id.txv_processName);

        server();
    }

    private void server(){
        final Socket socket_;
        try {
            serverSocket = new ServerSocket(port);
            socket_ = serverSocket.accept();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final BufferedReader bufferedReader_ = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    txvProcessMessage.setText(bufferedReader_.readLine());
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                        socket_.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
