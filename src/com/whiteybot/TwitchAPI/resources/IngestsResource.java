package com.whiteybot.TwitchAPI.resources;

import com.whiteybot.TwitchAPI.handlers.IngestsResponseHandler;
import com.whiteybot.TwitchAPI.models.Ingests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Travis on 2/13/2017.
 */
public class IngestsResource extends AbstractResource {

    public IngestsResource(String baseUrl, int apiVersion) {
        super(baseUrl, apiVersion);
    }

    public void get(final IngestsResponseHandler handler) {
        String url = String.format("%s/ingests", getBaseUrl());

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Ingests value = objectMapper.readValue(content, Ingests.class);
                    handler.onSuccess(value.getIngests());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }
}
