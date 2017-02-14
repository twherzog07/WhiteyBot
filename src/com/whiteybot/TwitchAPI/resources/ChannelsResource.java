package com.whiteybot.TwitchAPI.resources;

import com.mb3364.http.RequestParams;
import com.mb3364.http.StringHttpResponseHandler;
import com.whiteybot.TwitchAPI.auth.Scopes;
import com.whiteybot.TwitchAPI.handlers.*;
import com.whiteybot.TwitchAPI.models.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The {@link ChannelsResource} provides the functionality to access
 * the <code>/channels</code> endpoints of the Twitch API.
 *
 * Created by Travis on 2/13/2017.
 */
public class ChannelsResource extends AbstractResource {

    /**
     * Construct the resource using the Twitch API base URL and specified API version.
     *
     * @param baseUrl       the base URL of the Twitch API
     * @param apiVersion    the requested version of the Twitch API
     */
    public ChannelsResource(String baseUrl, int apiVersion) {
        super(baseUrl, apiVersion);
    }

    /**
     * Returns a channel object of authenticated user. Channel object includes stream key.
     * <p>Authenticated, requred scope: {@link Scopes#CHANNEL_READ}</p>
     *
     * @param handler   the response handler
     */
    public void get(final StringHttpResponseHandler handler) {
        String url = String.format("%s/channel", getBaseUrl());

        http.get(url, new StringHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Map<String, List<String>> map, String s) {
                try {
                    Channel value = objectMapper.readValue(s, Channel.class);
                    handler.onSuccess(i, map, s);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }

            @Override
            public void onFailure(int i, Map<String, List<String>> map, String s) {
                System.out.println("FAILURE");
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("FAILURE");
            }
        });
    }

    /**
     * Returns a {@link Channel} object.
     *
     * @param channelName   the name of the Channel
     * @param handler       the response handler
     */
    public void get(final String channelName, final ChannelResponseHandler handler) {
        String url = String.format("%s/channels/%s", getBaseUrl(), channelName);

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Channel value = objectMapper.readValue(content, Channel.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    /**
     * Returns a list of user objects who are editors of <code>channelName</code>.
     * <p>Authenticated, required scope: {@link Scopes#CHANNEL_READ}</p>
     *
     * @param channelName   the name of the Channel
     * @param handler       the response handler
     */
    public void getEditors(final String channelName, final UsersResponseHandler handler) {
        String url = String.format("%s/channels/%s/editors", getBaseUrl(), channelName);

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Editors value = objectMapper.readValue(content, Editors.class);
                    handler.onSuccess(value.getUsers());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void put(final String channelName, final RequestParams params, final ChannelResponseHandler handler) {
        String url = String.format("%s/channels/%s", getBaseUrl(), channelName);

        if (params.containsKey("status")) {
            params.put("channel[status]", params.getString("status"));
            params.remove("status");
        }

        if (params.containsKey("game")) {
            params.put("channel[game]", params.getString("game"));
            params.remove("game");
        }

        if (params.containsKey("delay")) {
            params.put("channel[delay]", params.getString("delay"));
            params.remove("delay");
        }

        http.put(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Channel value = objectMapper.readValue(content, Channel.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void resetStreamKey(final String channelName, final ChannelResponseHandler handler) {
        String url = String.format("%s/channels/%s/stream_key", getBaseUrl(), channelName);

        http.delete(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Channel value = objectMapper.readValue(content, Channel.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void startCommercial(final String channelName, final int length, final CommercialResponseHandler handler) {
        String url = String.format("%s/channels/%s/commercial", getBaseUrl(), channelName);

        RequestParams params = new RequestParams();
        params.put("length", Integer.toString(length));

        http.post(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                handler.onSuccess();
            }
        });
    }

    public void getTeams(final String channelName, final TeamsResponseHandler handler) {
        String url = String.format("%s/channels/%s/teams", getBaseUrl(), channelName);

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Teams value = objectMapper.readValue(content, Teams.class);
                    handler.onSuccess(value.getTeams());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getFollows(final String channelName, final RequestParams params, final ChannelFollowsResponseHandler handler) {
        String url = String.format("%s/channels/%s/follows", getBaseUrl(), channelName);

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    ChannelFollows value = objectMapper.readValue(content, ChannelFollows.class);
                    handler.onSuccess(value.getTotal(), value.getFollows());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getFollows(final String channelName, final ChannelFollowsResponseHandler handler) {
        getFollows(channelName, new RequestParams(), handler);
    }

    public void getVideos(final String channelName, final RequestParams params, final VideosResponseHandler handler) {
        String url = String.format("%s/channels/%s/videos", getBaseUrl(), channelName);

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Videos value = objectMapper.readValue(content, Videos.class);
                    handler.onSuccess(value.getTotal(), value.getVideos());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getVideos(final String channelName, final VideosResponseHandler handler) {
        getVideos(channelName, new RequestParams(), handler);
    }

    public void getSubscriptions(final String channelName, final RequestParams params, final ChannelSubscriptionsResponseHandler handler) {
        String url = String.format("%s/channels/%s/subscriptions", getBaseUrl(), channelName);

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    ChannelSubscriptions value = objectMapper.readValue(content, ChannelSubscriptions.class);
                    handler.onSuccess(value.getTotal(), value.getSubscriptions());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getSubscriptions(final String channelName, final ChannelSubscriptionsResponseHandler handler) {
        getSubscriptions(channelName, new RequestParams(), handler);
    }

    public void getSubscription(final String channelName, final String user, final ChannelSubscriptionResponseHandler handler) {
        String url = String.format("%s/channels/%s/subscriptions/%s", getBaseUrl(), channelName, user);

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    ChannelSubscription value = objectMapper.readValue(content, ChannelSubscription.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }
}
