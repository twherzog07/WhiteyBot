package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.Token;

/**
 * Created by Travis on 2/11/2017.
 */
public interface TokenResponseHandler extends BaseFailureHandler {
    void onSuccess(Token token);
}
