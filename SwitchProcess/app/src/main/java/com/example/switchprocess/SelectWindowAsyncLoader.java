package com.example.switchprocess;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SelectWindowAsyncLoader extends AsyncTaskLoader<String>{
    private String result;
    private boolean isStarted = false;
    private String ipAddress;
    private String sendMessage;

    SelectWindowAsyncLoader(Context _context, String _ipAddress, String _sendMessage){
        super(_context);
        this.ipAddress = _ipAddress;
        this.sendMessage = _sendMessage;
    }
    @Override
    public String loadInBackground(){
        if(!isLoadInBackgroundCanceled()){
            try{
                InetSocketAddress endPoint_ = new InetSocketAddress(ipAddress, 10090);
                Socket sender_ = new Socket();

                sender_.connect(endPoint_, 1000);
                if(sender_.isConnected()){
                    PrintWriter pw_ = new PrintWriter(sender_.getOutputStream(), true);
                    pw_.println(sendMessage);
                    pw_.close();
                }
                sender_.close();

                return "Selected windowTitle was sent to " + ipAddress;
            }
            catch (UnknownHostException uhe){
                return uhe.getMessage();
            }
            catch (IOException ioe){
                return ioe.getMessage();
            }
        }
        return null;
    }
    @Override
    protected void onStartLoading(){
        if(result != null){
            deliverResult(result);
            return;
        }
        if(!isStarted || takeContentChanged()){
            forceLoad();
        }
    }
    @Override
    protected void onForceLoad(){
        super.onForceLoad();
        isStarted = true;
    }
    @Override
    public void deliverResult(String _result){
        this.result = _result;
        super.deliverResult(_result);
    }
}
