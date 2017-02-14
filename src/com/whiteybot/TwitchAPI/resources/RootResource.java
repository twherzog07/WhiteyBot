package com.whiteybot.TwitchAPI.resources;

import com.whiteybot.TwitchAPI.handlers.TokenResponseHandler;
import com.whiteybot.TwitchAPI.models.Root;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Travis on 2/13/2017.
 */
public class RootResource extends AbstractResource {

    public RootResource(String baseUrl, int apiVersion) {
        super(baseUrl, apiVersion);
    }

    public void get(final TokenResponseHandler handler) {
        String url = String.format("%s/", getBaseUrl());

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Root value = objectMapper.readValue(content, Root.class);
                    handler.onSuccess(value.getToken());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }
}
