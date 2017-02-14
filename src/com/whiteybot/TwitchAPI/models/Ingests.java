package com.whiteybot.TwitchAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Travis on 2/12/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingests {

    private List<Ingest> ingests;

    @Override
    public String toString() {
        return "Ingests{ingests=" + ingests + '}';
    }

    public List<Ingest> getIngests() {
        return ingests;
    }

    public void setIngests(List<Ingest> ingests) {
        this.ingests = ingests;
    }
}
