package com.whiteybot.TwitchAPI.resources;

import com.whiteybot.TwitchAPI.handlers.BadgesResponseHandler;
import com.whiteybot.TwitchAPI.handlers.EmoticonsResponseHandler;
import com.whiteybot.TwitchAPI.models.ChannelBadges;
import com.whiteybot.TwitchAPI.models.Emoticons;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Travis on 2/13/2017.
 */
public class ChatResource extends AbstractResource {

    public ChatResource(String baseUrl, int apiVersion) {
        super(baseUrl, apiVersion);
    }

    public void getEmoticons(final EmoticonsResponseHandler handler) {
        String url = String.format("%s/chat/emoticons", getBaseUrl());

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Emoticons value = objectMapper.readValue(content, Emoticons.class);
                    handler.onSuccess(value.getEmoticons());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getBadges(final String channel, final BadgesResponseHandler handler) {
        String url = String.format("%s/chat/%s/badges", getBaseUrl(), channel);

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    ChannelBadges value = objectMapper.readValue(content, ChannelBadges.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }
}
