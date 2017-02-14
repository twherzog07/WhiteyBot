package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.Block;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
public interface BlocksResponseHandler extends BaseFailureHandler {
    void onSuccess(List<Block> blocks);
}
