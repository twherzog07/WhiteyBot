package com.whiteybot.TwitchBot;

import java.util.Date;

/**
 * Created by Travis on 2/10/2017.
 */
public class TwitchUser {

    private final String mName;
    private String mPrefix;
    private int mCmdTimer;
    private boolean mAllowURL;
    private long mPoints;

    private String mTwitchID;
    private String mBio;
    private Date mCreated;
    private String mDisplayName;
    private String mLogo;
    private String mTwitchName;
    private String mType;
    private Date mUpdated;

    public TwitchUser(String name, String prefix) {
        mName = name;
        mPrefix = prefix;
        mCmdTimer = 0;
        mAllowURL = false;

//        if (mName != null)
//            populateUserFromTwitch(mName);
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

    public String getBio() {
        return mBio;
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

    public String getTwitchID() {
        return mTwitchID;
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

    @Override
    public String toString() {
        return "TwitchUser[" + mPrefix + mName + ", " + mCmdTimer + "]";
    }
}
