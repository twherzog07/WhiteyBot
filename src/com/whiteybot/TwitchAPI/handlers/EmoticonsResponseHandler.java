package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.Emoticon;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
public interface EmoticonsResponseHandler extends BaseFailureHandler {
    void onSuccess(List<Emoticon> emoticons);
}
