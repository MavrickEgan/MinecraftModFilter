package com.github.MavrickEgan.eventbus.event;

public class HideModEvent {
    private int id;
    public HideModEvent(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
