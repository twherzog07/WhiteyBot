package com.whiteybot.TwitchAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Travis on 2/12/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelSubscription {

    @JsonProperty("_id")
    private String id;
    private Date createdAt;
    private User user;

    @Override
    public String toString() {
        return "ChannelSubscription{id='" + id + '\'' + ", createdAt=" + createdAt + ", user=" + user + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChannelSubscription channelSubscription = (ChannelSubscription) o;
        return !(id != null ? !id.equals(channelSubscription.id) : channelSubscription.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
