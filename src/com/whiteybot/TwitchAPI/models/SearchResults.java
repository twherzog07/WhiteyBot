package com.whiteybot.TwitchAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Travis on 2/12/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResults {

    @JsonProperty("_total")
    private int total;
    private List<Channel> channels;
    private List<Stream> streams;
    private List<Game> games;

    @Override
    public String toString() {
        return "SearchResults{total=" + total + ", channels=" + channels + ", streams=" + streams + ", games=" + games + '}';
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
