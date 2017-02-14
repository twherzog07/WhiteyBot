package com.whiteybot.TwitchAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Blocks {

    List<Block> blocks;

    @Override
    public String toString() {
        return "Blocks{" + "blocks=" + blocks + '}';
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }
}
