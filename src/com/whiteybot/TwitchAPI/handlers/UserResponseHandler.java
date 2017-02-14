package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.User;

/**
 * Created by Travis on 2/11/2017.
 */
public interface UserResponseHandler extends BaseFailureHandler {
    void onSuccess(User user);
}
