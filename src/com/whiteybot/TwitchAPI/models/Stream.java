package com.whiteybot.TwitchAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Travis on 2/12/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stream {

    @JsonProperty("_id")
    private long id;
    private String game;
    private int viewers;
    private Date createdAt;
    private int videoHeight;
    private double averageFPS;
    private StreamPreview preview;
    private Channel channel;

    public boolean isOnline() {
        return id != 0;
    }

    @Override
    public String toString() {
        return "Stream{id=" + id + ", game='" + game + '\'' + ", viewers=" + viewers + ", createdAt=" + createdAt +
                ", videoHeight=" + videoHeight + ", averageFPS=" + averageFPS + ", preview=" + preview +
                ", channel=" + channel + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stream stream = (Stream) o;
        return id == stream.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public StreamPreview getPreview() {
        return preview;
    }

    public void setPreview(StreamPreview preview) {
        this.preview = preview;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void setAverageFPS(double averageFPS) {
        this.averageFPS = averageFPS;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
