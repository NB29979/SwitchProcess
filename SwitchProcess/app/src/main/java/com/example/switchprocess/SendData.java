package com.example.switchprocess;

public class SendData {
    private String type;
    private String message;
    private int direction;
    private int speed;

    SendData(String _type, String _sendMessage){
        this.type = _type;
        this.message = _sendMessage;
        direction = -1;
        speed = 0;
    }
}
