package com.whiteybot.TwitchAPI.resources;

import com.mb3364.http.RequestParams;
import com.whiteybot.TwitchAPI.handlers.*;
import com.whiteybot.TwitchAPI.models.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Travis on 2/13/2017.
 */
public class UsersResource extends AbstractResource {

    public UsersResource(String baseUrl, int apiVersion) {
        super(baseUrl, apiVersion);
    }

    public void get(final String user, UserResponseHandler handler) {
        String url = String.format("%s/users/%s", getBaseUrl(), user);

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    User value = objectMapper.readValue(content, User.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void get(final UserResponseHandler handler) {
        String url = String.format("%s/user", getBaseUrl());

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    User value = objectMapper.readValue(content, User.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getSubscription(final String user, final String channel, final UserSubscriptionResponseHandler handler) {
        String url = String.format("%s/users/%s/subscriptions/%s", getBaseUrl(), user, channel);

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    UserSubscription value = objectMapper.readValue(content, UserSubscription.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getFollows(final String user, final RequestParams params, final UserFollowsResponseHandler handler) {
        String url = String.format("%s/users/%s/follows/channels", getBaseUrl(), user);

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    UserFollows value = objectMapper.readValue(content, UserFollows.class);
                    handler.onSuccess(value.getTotal(), value.getFollows());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getFollows(final String user, final UserFollowsResponseHandler handler) {
        getFollows(user, new RequestParams(), handler);
    }

    public void getFollow(final String user, final String channel, final UserFollowResponseHandler handler) {
        String url = String.format("%s/users/%s/follows/channels/%s", getBaseUrl(), user, channel);

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    UserFollow value = objectMapper.readValue(content, UserFollow.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void follow(final String user, final String channel, final boolean enableNotifications, final UserFollowResponseHandler handler) {
        String url = String.format("%s/users/%s/follows/channels/%s", getBaseUrl(), user, channel);

        RequestParams params = new RequestParams();
        params.put("notifications", Boolean.toString(enableNotifications));

        http.put(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    UserFollow value = objectMapper.readValue(content, UserFollow.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void unfollow(final String user, final String channel, final UserUnfollowResponseHandler handler) {
        String url = String.format("%s/users/%s/follows/channels/%s", getBaseUrl(), user, channel);

        http.delete(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                handler.onSuccess();
            }
        });
    }

    public void getBlocks(final String user, final RequestParams params, final BlocksResponseHandler handler) {
        String url = String.format("%s/users/%s/blocks", getBaseUrl(), user);

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Blocks value = objectMapper.readValue(content, Blocks.class);
                    handler.onSuccess(value.getBlocks());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void getBlocks(final String user, final BlocksResponseHandler handler) {
        getBlocks(user, new RequestParams(), handler);
    }

    public void putBlock(final String user, final String target, final BlockResponseHandler handler) {
        String url = String.format("%s/users/%s/blocks/%s", getBaseUrl(), user, target);

        http.put(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Block value = objectMapper.readValue(content, Block.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    public void deleteBlock(final String user, final String target, final UnblockResponseHandler handler) {
        String url = String.format("%s/users/%s/blocks/%s", getBaseUrl(), user, target);

        http.delete(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                handler.onSuccess();
            }
        });
    }
}
