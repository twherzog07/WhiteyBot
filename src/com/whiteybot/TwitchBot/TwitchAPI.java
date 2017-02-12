package com.whiteybot.TwitchBot;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;

import static com.whiteybot.tools.Globals.*;

/**
 * Created by Travis on 2/11/2017.
 */
public class TwitchAPI {

    public TwitchAPI() {

    }

    public void connect() {
        String url = "https://api.twitch.tv/kraken/users/whitey_h";
        String charset = StandardCharsets.UTF_8.name();

        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Accept", "application/vnd.twitchtv.v5+json");
            connection.setRequestProperty("Authorization", "OAuth " + gBotOAuth);
            InputStream response = connection.getInputStream();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
