package com.whiteybot.TwitchAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Travis on 2/12/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Emoticons {

    private List<Emoticon> emoticons = new ArrayList<>();

    @Override
    public String toString() {
        return "Emoticons{emoticons=" + emoticons + '}';
    }

    public List<Emoticon> getEmoticons() {
        return emoticons;
    }

    public void setEmoticons(List<Emoticon> emoticons) {
        this.emoticons = emoticons;
    }
}
