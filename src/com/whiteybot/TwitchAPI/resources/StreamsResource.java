package com.whiteybot.TwitchAPI.resources;

import com.mb3364.http.RequestParams;
import com.whiteybot.TwitchAPI.handlers.FeaturedStreamResponseHandler;
import com.whiteybot.TwitchAPI.handlers.StreamResponseHandler;
import com.whiteybot.TwitchAPI.handlers.StreamsResponseHandler;
import com.whiteybot.TwitchAPI.handlers.StreamsSummaryResponseHandler;
import com.whiteybot.TwitchAPI.models.FeaturedStreams;
import com.whiteybot.TwitchAPI.models.StreamWrapper;
import com.whiteybot.TwitchAPI.models.Streams;
import com.whiteybot.TwitchAPI.models.StreamsSummary;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Travis on 2/13/2017.
 */
public class StreamsResource extends AbstractResource {

    public StreamsResource(String baseUrl, int apiVersion) {
        super(baseUrl, apiVersion);
    }

    public void get(final String channelName, final StreamResponseHandler handler) {
        String url = String.format("%s/streams/%s", getBaseUrl(), channelName);

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    StreamWrapper value = objectMapper.readValue(content, StreamWrapper.class);
                    handler.onSuccess(value.getStream());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void get(final RequestParams params, final StreamsResponseHandler handler) {
        String url = String.format("%s/streams", getBaseUrl());

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Streams value = objectMapper.readValue(content, Streams.class);
                    handler.onSuccess(value.getTotal(), value.getStreams());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void get(final StreamsResponseHandler handler) {
        get(new RequestParams(), handler);
    }

    public void getFeatured(final RequestParams params, final FeaturedStreamResponseHandler handler) {
        String url = String.format("%s/streams/featured", getBaseUrl());

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    FeaturedStreams value = objectMapper.readValue(content, FeaturedStreams.class);
                    handler.onSuccess(value.getFeatured());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getFeatured(final FeaturedStreamResponseHandler handler) {
        getFeatured(new RequestParams(), handler);
    }

    public void getSummary(final String game, final StreamsSummaryResponseHandler handler) {
        String url = String.format("%s/streams/summary", getBaseUrl());
        RequestParams params = new RequestParams();
        params.put("game", game);

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    StreamsSummary value = objectMapper.readValue(content, StreamsSummary.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getSummary(final StreamsSummaryResponseHandler handler) {
        String url = String.format("%s/streams/summary", getBaseUrl());

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    StreamsSummary value = objectMapper.readValue(content, StreamsSummary.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getFollowed(final RequestParams params, final StreamsResponseHandler handler) {
        String url = String.format("%s/streams/followed", getBaseUrl());

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Streams value = objectMapper.readValue(content, Streams.class);
                    handler.onSuccess(value.getTotal(), value.getStreams());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getFollowed(final StreamsResponseHandler handler) {
        getFollowed(new RequestParams(), handler);
    }
}
