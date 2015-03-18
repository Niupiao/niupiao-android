package com.niupiao.niupiao.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kevinchen on 3/18/15.
 */
public abstract class Model {

    @SerializedName("id")
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
