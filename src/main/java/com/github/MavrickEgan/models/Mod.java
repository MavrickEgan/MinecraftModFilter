package com.github.MavrickEgan.models;


public class Mod {
    private int id;
    private String name;
    private String link;
    private String description;
    private String art;

    public Mod(int id, String name, String link, String art, String description) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.art = art;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }
}
