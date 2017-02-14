package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.StreamsSummary;

/**
 * Created by Travis on 2/11/2017.
 */
public interface StreamsSummaryResponseHandler extends BaseFailureHandler {
    void onSuccess(StreamsSummary summary);
}
