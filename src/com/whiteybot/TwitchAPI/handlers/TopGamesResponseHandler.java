package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.TopGame;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
public interface TopGamesResponseHandler extends BaseFailureHandler {
    void onSuccess(int total, List<TopGame> games);
}
