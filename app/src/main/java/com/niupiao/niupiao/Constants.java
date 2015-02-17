package com.niupiao.niupiao;

/**
 * Created by kevinchen on 2/17/15.
 */
public class Constants {

    public final static class Url {

        public static final String BASE_URL = "http://localhost:3000";
        public static final String EVENTS_URL = BASE_URL + "/events.json";

        /**
         * @param path must start with '/'
         */
        public static String fullUrl(String path) {
            return BASE_URL + path;
        }

    }
}
