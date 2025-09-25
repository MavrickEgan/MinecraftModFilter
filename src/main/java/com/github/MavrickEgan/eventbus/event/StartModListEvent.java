package com.github.MavrickEgan.eventbus.event;

public class StartModListEvent {
    String platform;
    String modloader;
    String version;
    public String fileName;

    public StartModListEvent(String p, String m, String v) {
        platform=p;
        modloader=m;
        version=v;
        fileName= p+"-"+m+"-"+v+".csv";
    }

}
