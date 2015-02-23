package com.niupiao.niupiao;

/**
 * Created by kevinchen on 2/17/15.
 */
public class Constants {

    public final static class SharedPrefs {
        public static final String LOGIN_CREDENTIALS = "login";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String REMEMBER_ME = "remember_me";
        public static final String ACCESS_TOKEN = "access_token";
        public static final String ACCESS_TOKEN_EXPIRES_AT = "expires_at";
    }

    public final static class Url {

        public static final String BASE_URL = "http://localhost:3000";
        public static final String LOGIN_URL = BASE_URL + "/login.json";
        public static final String EVENTS_URL = BASE_URL + "/events.json";
        public static final String TICKETS_URL = BASE_URL + "/me/tickets.json";

        /**
         * @param path must start with '/'
         */
        public static String fullUrl(String path) {
            return BASE_URL + path;
        }

    }
}
