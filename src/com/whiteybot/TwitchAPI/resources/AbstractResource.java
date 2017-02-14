package com.whiteybot.TwitchAPI.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.mb3364.http.AsyncHttpClient;
import com.mb3364.http.StringHttpResponseHandler;
import com.whiteybot.TwitchAPI.handlers.BaseFailureHandler;
import com.whiteybot.TwitchAPI.models.Error;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Travis on 2/12/2017.
 */
public abstract class AbstractResource {

    protected static final ObjectMapper objectMapper = new ObjectMapper();
    protected static final AsyncHttpClient http = new AsyncHttpClient();
    private final String baseUrl;

    protected AbstractResource(String baseUrl, int apiVersion) {
        this.baseUrl = baseUrl;
        http.setHeader("ACCEPT", "application/vnd.twitchtv.v" + Integer.toString(apiVersion) + "+json");
        configureObjectMapper();
    }

    private void configureObjectMapper() {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public void setAuthAccessToken(String accessToken) {
        if (accessToken != null && accessToken.length() > 0)
            http.setHeader("Authorization", String.format("OAuth %s", accessToken));
        else
            http.removeHeader("Authorization");
    }

    public void setClientID(String clientID) {
        if (clientID != null && clientID.length() > 0)
            http.setHeader("Client-ID", clientID);
        else
            http.removeHeader("Client-ID");
    }

    protected String getBaseUrl() {
        return baseUrl;
    }

    protected static abstract class TwitchHttpResponseHandler extends StringHttpResponseHandler {

        private BaseFailureHandler apiHandler;

        public TwitchHttpResponseHandler(BaseFailureHandler apiHandler) {
            this.apiHandler = apiHandler;
        }

        @Override
        public abstract void onSuccess(int statusCode, Map<String, List<String>> headers, String content);

        @Override
        public void onFailure(int statusCode, Map<String, List<String>> headers, String content) {
            try {
                if (content.length() > 0) {
                    Error error = objectMapper.readValue(content, Error.class);
                    apiHandler.onFailure(statusCode, error.getStatusText(), error.getMessage());
                }
                else
                    apiHandler.onFailure(statusCode, "", "");
            } catch (IOException e) {
                apiHandler.onFailure(e);
            }
        }

        @Override
        public void onFailure(Throwable throwable) {
            apiHandler.onFailure(throwable);
        }
    }
}
