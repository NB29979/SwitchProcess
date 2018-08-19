package com.example.switchprocess;

public class SendData {
    private String type;
    private String message;
    private double rad;
    private double absX;
    private double absY;
    private int pointerCount;

    SendData(String _type, String _sendMessage){
        this(_type, _sendMessage, 0, 0,0 ,0);
    }
    SendData(String _type, String _sendMessage, double _absX, double _absY, double _rad, int _pointerCount){
        this.type = _type;
        this.message = _sendMessage;
        this.absX = _absX;
        this.absY = _absY;
        this.rad = _rad;
        this.pointerCount = _pointerCount;
    }
}
