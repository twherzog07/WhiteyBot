package com.whiteybot.TwitchAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Travis on 2/12/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelSubscriptions {

    @JsonProperty("_total")
    private int total;
    private List<ChannelSubscription> subscriptions;

    @Override
    public String toString() {
        return "ChannelSubscriptions{total=" + total + ", subscriptions=" + subscriptions + '}';
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ChannelSubscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<ChannelSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
