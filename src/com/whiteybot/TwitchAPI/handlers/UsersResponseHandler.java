package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.User;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
public interface UsersResponseHandler extends BaseFailureHandler {
    void onSuccess(List<User> users);
}
