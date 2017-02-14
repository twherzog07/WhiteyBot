package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.UserFollow;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
public interface UserFollowsResponseHandler extends BaseFailureHandler {
    void onSuccess(int total, List<UserFollow> follows);
}
