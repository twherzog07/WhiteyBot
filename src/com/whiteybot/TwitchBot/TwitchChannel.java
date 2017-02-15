package com.whiteybot.TwitchBot;

import com.whiteybot.TwitchAPI.models.Channel;

import java.util.ArrayList;

/**
 * Created by Travis on 2/10/2017.
 */
public class TwitchChannel {

    private String mName;
    private ArrayList<TwitchUser> mUsers;
    private int mCmdSent;

    private Channel twitchChannel;

    public TwitchChannel(String name) {
        mName = name;
        mUsers = new ArrayList<>();
        mCmdSent = 0;
    }

    public void addUser(TwitchUser user) {
        if (user != null)
            mUsers.add(user);
    }

    public void delUser(TwitchUser user) {
        if (user != null && mUsers.contains(user))
            mUsers.remove(user);
    }

    public int getCmdSent() {
        return mCmdSent;
    }

    public String getName() {
        return mName;
    }

    public TwitchUser getModerator(String name) {
        TwitchUser result = null;

        for (TwitchUser u : getModerators()) {
            if (u.getName().equals(name)) {
                result = u;
                break;
            }
        }

        return result;
    }

    public ArrayList<TwitchUser> getModerators() {
        ArrayList<TwitchUser> result = new ArrayList<>();

        for (TwitchUser u : mUsers) {
            if (u.isModerator())
                result.add(u);
        }

        return result;
    }

    public TwitchUser getOperator(String name) {
        TwitchUser result = null;

        for (TwitchUser u : getOperators()) {
            if (u.getName().equals(name)) {
                result = u;
                break;
            }
        }

        return result;
    }

    public ArrayList<TwitchUser> getOperators() {
        ArrayList<TwitchUser> result = new ArrayList<>();

        for (TwitchUser u : mUsers) {
            if (u.isOperator())
                result.add(u);
        }

        return result;
    }

    public Channel getTwitchChannel() {
        return twitchChannel;
    }

    public TwitchUser getUser(String name) {
        TwitchUser result = null;

        for (TwitchUser u : mUsers) {
            if (u.getName().equals(name)) {
                result = u;
                break;
            }
        }

        return result;
    }

    public ArrayList<TwitchUser> getUsers() {
        return mUsers;
    }

    public void setCmdSent(int cmdSent) {
        mCmdSent = cmdSent;
    }

    public void setTwitchChannel(Channel twitchChannel) {
        this.twitchChannel = twitchChannel;
    }

    @Override
    public String toString() {
        return "TwitchChannel[" + mName + "]";
    }
}
