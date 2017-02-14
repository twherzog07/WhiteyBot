package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.Team;

/**
 * Created by Travis on 2/11/2017.
 */
public interface TeamResponseHandler extends BaseFailureHandler {
    void onSuccess(Team team);
}
