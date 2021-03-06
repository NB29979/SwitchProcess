package com.example.switchprocess;

public class SendingData {
    private String type;
    private String message;
    private double variationX;
    private double variationY;
    private int pointerCount;

    SendingData(String _type, String _sendMessage){
        this(_type, _sendMessage, 0, 0,0);
    }
    SendingData(String _type, String _sendMessage, double _variationX, double _variationY, int _pointerCount){
        this.type = _type;
        this.message = _sendMessage;
        this.variationX = _variationX;
        this.variationY = _variationY;
        this.pointerCount = _pointerCount;
    }
}
