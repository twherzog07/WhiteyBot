package com.whiteybot.TwitchAPI.resources;

import com.mb3364.http.RequestParams;
import com.whiteybot.TwitchAPI.handlers.TopGamesResponseHandler;
import com.whiteybot.TwitchAPI.models.Games;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Travis on 2/13/2017.
 */
public class GamesResource extends AbstractResource {

    public GamesResource(String baseUrl, int apiVersion) {
        super(baseUrl, apiVersion);
    }

    public void getTop(final RequestParams params, final TopGamesResponseHandler handler) {
        String url = String.format("%s/games/top", getBaseUrl());

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Games value = objectMapper.readValue(content, Games.class);
                    handler.onSuccess(value.getTotal(), value.getTop());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getTop(TopGamesResponseHandler handler) {
        getTop(new RequestParams(), handler);
    }
}
