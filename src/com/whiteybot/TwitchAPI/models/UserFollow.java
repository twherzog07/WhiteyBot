package com.whiteybot.TwitchAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created by Travis on 2/12/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFollow {

    private Date createdAt;
    private boolean notifications;
    private Channel channel;

    @Override
    public String toString() {
        return "UserFollow{createdAt=" + createdAt + ", notifications=" + notifications + ", channel=" + channel + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserFollow userFollow = (UserFollow) o;
        if (notifications != userFollow.notifications) return false;
        if (createdAt != null ? !createdAt.equals(userFollow.createdAt) : userFollow.createdAt != null) return false;
        return !(channel != null ? !channel.equals(userFollow.channel) : userFollow.channel != null);
    }

    @Override
    public int hashCode() {
        int result = createdAt != null ? createdAt.hashCode() : 0;
        result = 31 * result + (notifications ? 1 : 0);
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        return result;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean notificationsEnabled() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
