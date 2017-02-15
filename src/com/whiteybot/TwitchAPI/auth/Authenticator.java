package com.whiteybot.TwitchAPI.auth;

import com.whiteybot.TwitchAPI.auth.grants.implicit.AuthenticationCallbackServer;
import com.whiteybot.TwitchAPI.auth.grants.implicit.AuthenticationError;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Created by Travis on 2/11/2017.
 */
public class Authenticator {

    private String twitchBaseURL;
    private int listenPort;
    private String clientID;
    private URI redirectURI;

    private String accessToken;
    private AuthenticationError authenticationError;

    public Authenticator(String twitchBaseURL) {
        this.twitchBaseURL = twitchBaseURL;
    }

    public String getAuthenticationURL(String clientID, URI redirectURI, Scopes... scopes) {
        this.clientID = clientID;
        this.redirectURI = redirectURI;

        this.listenPort = redirectURI.getPort();
        if (this.listenPort == -1)
            this.listenPort = 80;

        return String.format("%s/oauth2/authorize?response_type=token&client_id=%s&redirect_uri=%s&scope=%s", twitchBaseURL, clientID, redirectURI, Scopes.join(scopes));
    }

    public boolean awaitAccessToken() {
        return awaitAccessToken(null, null, null);
    }

    public boolean awaitAccessToken(URL authURL, URL successURL, URL failURL) {
        if (clientID == null || redirectURI == null) return false;

        AuthenticationCallbackServer server = new AuthenticationCallbackServer(listenPort, authURL, successURL, failURL);
        try {
            server.start();
        } catch (IOException e) {
            authenticationError = new AuthenticationError("JavaException", e.toString());
            return false;
        }

        if (server.hasAuthenticationError() || server.getAccessToken() == null) {
            authenticationError = server.getAuthenticationError();
            return false;
        }

        accessToken = server.getAccessToken();
        return true;
    }

    public String getClientID() {
        return clientID;
    }

    public URI getRedirectURI() {
        return redirectURI;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean hasAccessToken() {
        return accessToken != null;
    }

    public AuthenticationError getAuthenticationError() {
        return authenticationError;
    }

    public boolean hasAuthenticationError() {
        return authenticationError != null;
    }
}
