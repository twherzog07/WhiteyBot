package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.Video;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
public interface VideosResponseHandler extends BaseFailureHandler {
    void onSuccess(int total, List<Video> videos);
}
