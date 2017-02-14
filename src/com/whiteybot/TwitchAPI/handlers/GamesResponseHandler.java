package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.Game;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
public interface GamesResponseHandler extends BaseFailureHandler {
    void onSuccess(int total, List<Game> streams);
}
