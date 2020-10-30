package com.example.jotham_gadsproject;

import java.io.Serializable;

public class Subject implements Serializable {
    private String id;
    private String title;
    private String classname;
    private String description;
    private String ImageUrl;

    public Subject() {
    }

    public Subject(String title, String classname, String description, String imageUrl) {
        this.id = id;
        this.title = title;
        this.classname = classname;
        this.description = description;
        ImageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
