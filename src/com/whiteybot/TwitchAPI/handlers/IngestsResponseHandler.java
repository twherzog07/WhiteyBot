package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.Ingest;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
public interface IngestsResponseHandler extends BaseFailureHandler {
    void onSuccess(List<Ingest> ingests);
}
