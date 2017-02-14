package com.whiteybot.TwitchAPI.resources;

import com.mb3364.http.RequestParams;
import com.whiteybot.TwitchAPI.handlers.TeamResponseHandler;
import com.whiteybot.TwitchAPI.handlers.TeamsResponseHandler;
import com.whiteybot.TwitchAPI.models.Team;
import com.whiteybot.TwitchAPI.models.Teams;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Travis on 2/13/2017.
 */
public class TeamsResource extends AbstractResource {

    public TeamsResource(String baseUrl, int apiVersion) {
        super(baseUrl, apiVersion);
    }

    public void get(final RequestParams params, final TeamsResponseHandler handler) {
        String url = String.format("%s/teams", getBaseUrl());

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Teams value = objectMapper.readValue(content, Teams.class);
                    handler.onSuccess(value.getTeams());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void get(final TeamsResponseHandler handler) {
        get(new RequestParams(), handler);
    }

    public void get(final String team, final TeamResponseHandler handler) {
        String url = String.format("%s/teams/%s", getBaseUrl());

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Team value = objectMapper.readValue(content, Team.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }
}
