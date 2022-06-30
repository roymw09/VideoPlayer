package com.silverorange.videoplayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("hlsURL")
    @Expose
    private String hlsUrl;
    @SerializedName("fullURL")
    @Expose
    private String fullUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("publishedAt")
    @Expose
    private String date;
    @SerializedName("author")
    @Expose
    private Author author;

    public Video(String id, String title, String hlsUrl,
    String fullUrl, String description, String date, Author author) {
        this.id = id;
        this.title = title;
        this.hlsUrl = hlsUrl;
        this.fullUrl = fullUrl;
        this.description = description;
        this.date = date;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle() {
        this.title = title;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }

    public void setHlsUrl(String hlsUrl) {
        this.hlsUrl = hlsUrl;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
