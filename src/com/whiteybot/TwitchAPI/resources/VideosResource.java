package com.whiteybot.TwitchAPI.resources;

import com.mb3364.http.RequestParams;
import com.whiteybot.TwitchAPI.handlers.VideoResponseHandler;
import com.whiteybot.TwitchAPI.handlers.VideosResponseHandler;
import com.whiteybot.TwitchAPI.models.Video;
import com.whiteybot.TwitchAPI.models.Videos;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Travis on 2/13/2017.
 */
public class VideosResource extends AbstractResource {

    public VideosResource(String baseUrl, int apiVersion) {
        super(baseUrl, apiVersion);
    }

    public void get(final String id, final VideoResponseHandler handler) {
        String url = String.format("%s/videos/%s", getBaseUrl(), id);

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Video value = objectMapper.readValue(content, Video.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getTop(final RequestParams params, final VideosResponseHandler handler) {
        String url = String.format("%s/videos/top", getBaseUrl());

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Videos value = objectMapper.readValue(content, Videos.class);
                    handler.onSuccess(value.getVideos().size(), value.getVideos());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getTop(final VideosResponseHandler handler) {
        getTop(new RequestParams(), handler);
    }

    public void getFollowed(final RequestParams params, final VideosResponseHandler handler) {
        String url = String.format("%s/videos/followed", getBaseUrl());

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Videos value = objectMapper.readValue(content, Videos.class);
                    handler.onSuccess(value.getVideos().size(), value.getVideos());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getFollowed(final VideosResponseHandler handler) {
        getFollowed(new RequestParams(), handler);
    }
}
