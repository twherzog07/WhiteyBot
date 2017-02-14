package com.whiteybot.TwitchAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Travis on 2/12/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StreamsSummary {

    private int channels;
    private int viewers;

    @Override
    public String toString() {
        return "StreamsSummary{channels=" + channels + ", viewers=" + viewers + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StreamsSummary streamsSummary = (StreamsSummary) o;
        if (channels != streamsSummary.channels) return false;
        return viewers == streamsSummary.viewers;
    }

    @Override
    public int hashCode() {
        int result = channels;
        result = 31 * result + viewers;
        return result;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }
}
