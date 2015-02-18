package com.niupiao.niupiao.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kevinchen on 2/18/15.
 */
public class ApiKey {

    @SerializedName("expires_at")
    private long expiresAt;

    @SerializedName("access_token")
    private String accessToken;

    public long getExpiresAt() {
        return expiresAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

}
