package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.Team;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
public interface TeamsResponseHandler extends BaseFailureHandler {
    void onSuccess(List<Team> teams);
}
