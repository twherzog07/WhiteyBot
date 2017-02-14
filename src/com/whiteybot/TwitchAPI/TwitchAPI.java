package com.whiteybot.TwitchAPI;

import com.whiteybot.TwitchAPI.auth.Authenticator;
import com.whiteybot.TwitchAPI.resources.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Travis on 2/11/2017.
 */
public class TwitchAPI {

    public static final String DEFAULT_BASE_URL = "https://api.twitch.tv/kraken";
    public static final int DEFAULT_API_VERSION = 3;
    private String clientID;
    private Authenticator authenticator;
    private Map<String, AbstractResource> resources;

    public TwitchAPI(String baseUrl, int apiVersion) {
        authenticator = new Authenticator(baseUrl);

        resources = new HashMap<>();
        resources.put("channels", new ChannelsResource(baseUrl, apiVersion));
        resources.put("chat", new ChatResource(baseUrl, apiVersion));
        resources.put("games", new GamesResource(baseUrl, apiVersion));
        resources.put("ingests", new IngestsResource(baseUrl, apiVersion));
        resources.put("root", new RootResource(baseUrl, apiVersion));
        resources.put("search", new SearchResource(baseUrl, apiVersion));
        resources.put("streams", new StreamsResource(baseUrl, apiVersion));
        resources.put("teams", new TeamsResource(baseUrl, apiVersion));
        resources.put("users", new UsersResource(baseUrl, apiVersion));
        resources.put("videos", new VideosResource(baseUrl, apiVersion));
    }

    public TwitchAPI() {
        this(DEFAULT_BASE_URL, DEFAULT_API_VERSION);
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
        for (AbstractResource r : resources.values())
            r.setClientID(clientID);
    }

    private AbstractResource getResource(String key) {
        AbstractResource r = resources.get(key);
        r.setAuthAccessToken(authenticator.getAccessToken());
        return r;
    }

    public Authenticator auth() {
        return authenticator;
    }

    public ChannelsResource channels() {
        return (ChannelsResource) getResource("channels");
    }

    public ChatResource chat() {
        return (ChatResource) getResource("chat");
    }

    public GamesResource games() {
        return (GamesResource) getResource("games");
    }

    public IngestsResource ingests() {
        return (IngestsResource) getResource("ingests");
    }

    public RootResource root() {
        return (RootResource) getResource("root");
    }

    public SearchResource search() {
        return (SearchResource) getResource("search");
    }

    public StreamsResource streams() {
        return (StreamsResource) getResource("streams");
    }

    public UsersResource users() {
        return (UsersResource) getResource("users");
    }

    public VideosResource videos() {
        return (VideosResource) getResource("videos");
    }
}
