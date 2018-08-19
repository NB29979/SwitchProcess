package com.example.switchprocess;

public class SendData {
    private String type;
    private String message;
    private double rad;
    private int pointerCount;

    SendData(String _type, String _sendMessage, double _rad, int _pointerCount){
        this.type = _type;
        this.message = _sendMessage;
        this.rad = _rad;
        this.pointerCount = _pointerCount;
    }
}
