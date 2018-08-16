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
                    for(NetworkInterface n: Collections.list(NetworkInterface.getNetworkInterfaces())){ for(InetAddress addr : Collections.list(n.getInetAddresses())){ if(addr instanceof Inet4Address && !addr.isLoopbackAddress()) { System.out.println( addr.getHostAddress() ); } } }

                    System.out.println("Waiting...");
                    Socket socket_ = serverSocket.accept();

                    BufferedReader br_ = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
                    message = br_.readLine();

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.setLsvWindowList(Parse(message));
                        }
                    });
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        private ArrayList<String> Parse(String _str){
            ArrayList<String> windowRowList = new ArrayList<>();
            for(String title : _str.split(",")){
                windowRowList.add(title);
            }
            return windowRowList;
        }
    }
}
