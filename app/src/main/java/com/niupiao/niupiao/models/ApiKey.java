package com.niupiao.niupiao.models;

import com.google.gson.annotations.SerializedName;
import com.niupiao.niupiao.Constants;

/**
 * Created by kevinchen on 2/18/15.
 */
public class ApiKey {

    @SerializedName(Constants.JsonApi.ApiKey.EXPIRES_AT)
    private long expiresAt;

    @SerializedName(Constants.JsonApi.ApiKey.ACCESS_TOKEN)
    private String accessToken;

    public long getExpiresAt() {
        return expiresAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

}
