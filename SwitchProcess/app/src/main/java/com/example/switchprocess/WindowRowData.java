package com.example.switchprocess;

public class WindowRowData {
    public String title;
    public String icon;

    WindowRowData(String _title){
        this.title = _title;
    }
    WindowRowData(String _title, String _icon){ this.title = _title; this.icon = _icon; }
    public String getTitle(){
        return title;
    }
    public String getIcon(){ return icon; }
    public WindowRowData getWindowData() { return this; }
}
