package com.github.MavrickEgan.eventbus.event;

public class AddListEvent {
    int id;
    public AddListEvent(int id) {
        this.id= id;
    }

    public int getId() {
        return id;
    }
}
