package com.niupiao.niupiao;

/**
 * Created by kevinchen on 2/17/15.
 */
public class Constants {

    /**
     * Contains keys used for SharedPreferences
     */
    public final static class SharedPrefs {
        public static final String LOGIN_CREDENTIALS = "login";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String REMEMBER_ME = "remember_me";
        public static final String ACCESS_TOKEN = "access_token";
        public static final String ACCESS_TOKEN_EXPIRES_AT = "expires_at";
    }

    public final static class FacebookApi {
        public final static class Permissions {
            public final static String EMAIL = "email";
            public final static String PUBLIC_PROFILE = "public_profile";
            public final static String[] getPermissions() {
                return new String[] { EMAIL, PUBLIC_PROFILE };
            }
        }
    }

    /**
     * Contains parameter keys used for POST requests.
     * The reason we have these all together in one file is to easily ensure they
     * match the parameter keys that the JSON API expects.
     */
    public final static class JsonApi {

        public final static class EndPoints {

            public static final String BASE_URL = "https://niupiao-rails.herokuapp.com"; //"http://localhost:3000";
            public static final String TICKETS_URL = BASE_URL + "/me/tickets.json";
            public static final String EVENTS_URL = BASE_URL + "/events.json";
            public static final String SIGNUP_URL = BASE_URL + "/signup.json";
            public static final String FACEBOOK_LOGIN_URL = BASE_URL + "/facebooklogin.json";
            public static final String LOGIN_URL = BASE_URL + "/login.json";

            /**
             * TODO use exceptions
             *
             * @param path must start with '/'
             */
            public static String fullUrl(String path) {
                return BASE_URL + path;
            }
        }

        /**
         * Constants contained in server responses.
         */
        public final static class Response {
            public static final String SUCCESS = "success";
            public static final String MESSAGE = "message";
            public static final String USER = "user";
            public static final String API_KEY = "api_key";
        }

        /**
         * API constants for user registration.
         */
        public final static class Register {
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
        }

        /**
         * API constants for logging in.
         */
        public final static class Login {
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
        }

        /**
         * API constants for accessing {@link com.niupiao.niupiao.models.Event} resources.
         */
        public final static class Event {
            public static final String HEADER_KEY_REQUEST_TYPE = "request_type";
            public static final String HEADER_VALUE_EVENT_ON_SALE = "on_sale";
            public static final String HEADER_VALUE_EVENT_COMING_SOON = "coming_soon";
            public static final String HEADER_VALUE_EVENT_RECOMMENDED = "recommended";
        }

        /**
         * API constants for accessing {@link com.niupiao.niupiao.models.Ticket} resources.
         */
        public final static class Ticket {
            public static final String HEADER_KEY_REQUEST_TYPE = "request_type";
            public static final String HEADER_VALUE_TICKET_PAST = "past";
            public static final String HEADER_VALUE_TICKET_UPCOMING = "upcoming";
        }

        /**
         * API keys pertaining to the Facebook API, e.g., its {@link com.facebook.model.GraphUser}.
         */
        public final static class Facebook {
            public final static class User {
                public static final String EMAIL = "email";
                public static final String BIRTHDAY = "birthday";
                public static final String FIRST_NAME = "first_name";
                public static final String MIDDLE_NAME = "middle_name";
                public static final String LAST_NAME = "last_name";
                public static final String NAME = "name";
                public static final String USERNAME = "username";
                public static final String LOCATION = "location";
                public static final String LINK = "link";
                public static final String ID = "facebook_id";
            }
        }

    }

}
