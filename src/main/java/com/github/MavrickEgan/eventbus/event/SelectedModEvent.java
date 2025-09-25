package com.github.MavrickEgan.eventbus.event;


import com.github.MavrickEgan.models.Mod;

public class SelectedModEvent {
    private Mod mod;
    public SelectedModEvent(Mod mod) {
        this.mod = mod;
    }

    public Mod getMod() {
        return mod;
    }

    public void setMod(Mod mod) {
        this.mod = mod;
    }
}
