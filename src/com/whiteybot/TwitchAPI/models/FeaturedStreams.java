package com.whiteybot.TwitchAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Travis on 2/12/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeaturedStreams {

    private List<FeaturedStream> featured;

    @Override
    public String toString() {
        return "FeaturedStreams{featured=" + featured + '}';
    }

    public List<FeaturedStream> getFeatured() {
        return featured;
    }

    public void setFeatured(List<FeaturedStream> featured) {
        this.featured = featured;
    }
}
