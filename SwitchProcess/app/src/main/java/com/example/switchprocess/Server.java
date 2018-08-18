package com.example.switchprocess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Server {
    private MainActivity activity;
    private ServerSocket serverSocket;
    private String message = "";
    private static final int socketServerPORT = 10080;

    public Server(MainActivity _activity){
        this.activity = _activity;
        Thread socketServerThread_ = new Thread(new SocketServerThread());
        socketServerThread_.start();
    }

    public void onDestroy(){
        if(serverSocket != null){
            try{
                serverSocket.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private class SocketServerThread extends Thread{
        @Override
        public void run(){
            try{
                serverSocket = new ServerSocket(socketServerPORT);

                while(true){
                    System.out.println("Waiting...");
                    final Socket socket_ = serverSocket.accept();
                    activity.setRemoteIPAddress(socket_.getRemoteSocketAddress().toString());

                    BufferedReader br_ = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
                    message = br_.readLine();

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.setLsvWindowList(Parse(message));
                            System.out.println("WindowList has been changed");
                        }
                    });
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        private ArrayList<String> Parse(String _str){
            return new ArrayList<>(Arrays.asList(_str.split(",")));
        }
    }
}
