package com.whiteybot.TwitchAPI.resources;

import com.mb3364.http.RequestParams;
import com.whiteybot.TwitchAPI.handlers.ChannelsResponseHandler;
import com.whiteybot.TwitchAPI.handlers.GamesResponseHandler;
import com.whiteybot.TwitchAPI.handlers.StreamsResponseHandler;
import com.whiteybot.TwitchAPI.models.SearchResults;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Travis on 2/13/2017.
 */
public class SearchResource extends AbstractResource {

    public SearchResource(String baseUrl, int apiVersion) {
        super(baseUrl, apiVersion);
    }

    public void channels(final String query, final RequestParams params, final ChannelsResponseHandler handler) {
        String url = String.format("%s/search/channels", getBaseUrl());
        params.put("q", query);

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    SearchResults value = objectMapper.readValue(content, SearchResults.class);
                    handler.onSuccess(value.getTotal(), value.getChannels());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void channels(String query, ChannelsResponseHandler handler) {
        channels(query, new RequestParams(), handler);
    }

    public void streams(final String query, final RequestParams params, final StreamsResponseHandler handler) {
        String url = String.format("%s/search/streams", getBaseUrl());
        params.put("q", query);

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    SearchResults value = objectMapper.readValue(content, SearchResults.class);
                    handler.onSuccess(value.getTotal(), value.getStreams());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void streams(final String query, final StreamsResponseHandler handler) {
        streams(query, new RequestParams(), handler);
    }

    public void games(final String query, final RequestParams params, final GamesResponseHandler handler) {
        String url = String.format("%s/search/games", getBaseUrl());
        params.put("q", query);
        params.put("type", "suggest");

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    SearchResults value = objectMapper.readValue(content, SearchResults.class);
                    handler.onSuccess(value.getTotal(), value.getGames());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void games(final String query, final GamesResponseHandler handler) {
        games(query, new RequestParams(), handler);
    }
}
