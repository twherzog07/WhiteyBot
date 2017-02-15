package com.whiteybot.TwitchBot;

import com.whiteybot.TwitchAPI.models.User;

/**
 * Created by Travis on 2/10/2017.
 */
public class TwitchUser {

    private final String mName;
    private String mPrefix;
    private int mCmdTimer;
    private boolean mAllowURL;
    private long mPoints;

    private User twitchProfile;

    public TwitchUser(String name, String prefix) {
        mName = name;
        mPrefix = prefix;
        mCmdTimer = 0;
        mAllowURL = false;
    }

    public void addPrefixChar(String prefix) {
        mPrefix = prefix + mPrefix;
    }

    public void delPrefixChar(String prefix) {
        mPrefix = mPrefix.replace(prefix, "");
    }

    public boolean getAllowURL() {
        return mAllowURL;
    }

    public int getCmdTimer() {
        return mCmdTimer;
    }

    public String getName() {
        return mName;
    }

    public String getPrefix() {
        return mPrefix;
    }

    public User getTwitchProfile() {
        return twitchProfile;
    }

    public boolean isAdmin() {
        return mPrefix.contains("&");
    }

    public boolean isModerator() {
        return mPrefix.contains("*") | isAdmin();
    }

    public boolean isOperator() {
        return mPrefix.contains("@");
    }

    public void setAllowURL(boolean allowURL) {
        mAllowURL = allowURL;
    }

    public void setCmdTimer(int time) {
        mCmdTimer = time;
    }

    public void setPrefix(String prefix) {
        mPrefix = prefix;
    }

    public void setTwitchProfile(User twitchProfile) {
        this.twitchProfile = twitchProfile;
    }

    @Override
    public String toString() {
        return "TwitchUser[" + mPrefix + mName + ", " + mCmdTimer + "]";
    }
}
