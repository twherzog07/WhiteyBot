package com.whiteybot.TwitchBot;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
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

    private void populateUserFromTwitch(String userName) {
        String url = "https://api.twitch.tv/kraken";
        String charset = StandardCharsets.UTF_8.name();

        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Accept", "application/vnd.twitchtv.v5+json");
            connection.setRequestProperty("Authorization", "OAuth gdq83yl8688gjzlt3im6nmg42v0ann");
            InputStream response = connection.getInputStream();

            JSONObject obj = new JSONObject(response).getJSONObject("users");
            mTwitchID = obj.getString("_id");
            mBio = obj.getString("bio");
            try {
                mCreated = DateFormat.getDateInstance().parse(obj.getString("created_at"));
            } catch (ParseException e) {

            }
            mDisplayName = obj.getString("display_name");
            mLogo = obj.getString("logo");
            mTwitchName = obj.getString("name");
            mType = obj.getString("type");
            try {
                mUpdated = DateFormat.getDateInstance().parse(obj.getString("updated_at"));
            } catch (ParseException e) {

            }

        } catch (IOException e) {

        }
    }

    public void save() {

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
