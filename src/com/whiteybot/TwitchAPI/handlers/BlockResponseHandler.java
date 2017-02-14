package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.Block;

/**
 * Created by Travis on 2/11/2017.
 */
public interface BlockResponseHandler extends BaseFailureHandler {
    void onSuccess(Block block);
}
