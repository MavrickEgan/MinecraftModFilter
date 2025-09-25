package com.github.MavrickEgan.eventbus.event;

public class SelectFilterEvent {
    private String filter;

    public SelectFilterEvent(String f) {
        this.filter = f;
    }
    public String getFilter() {
        return filter;
    }
}
