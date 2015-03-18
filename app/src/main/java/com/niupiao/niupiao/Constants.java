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

            public static String[] getPermissions() {
                return new String[]{EMAIL, PUBLIC_PROFILE};
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
            public static final String STATUS = "status";
            public static final String MESSAGE = "message";
            public static final String USER = "user";
            public static final String API_KEY = "api_key";
        }

        public final static class ApiKey {
            public static final String EXPIRES_AT = "expires_at";
            public static final String ACCESS_TOKEN = "access_token";
        }

        /**
         * API constants for user registration.
         */
        public final static class User {
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
        }

        /**
         * API constants for accessing {@link com.niupiao.niupiao.models.Event} resources.
         */
        public final static class Event {

            public static final String NAME = "name";
            public static final String ORGANIZER = "organizer";
            public static final String DATE = "date";
            public static final String LOCATION = "location";
            public static final String DESCRIPTION = "description";
            public static final String LINK = "link";
            public static final String IMAGE_PATH = "image_path";
            public static final String TOTAL_TICKETS = "total_tickets";
            public static final String TICKETS_SOLD = "tickets_sold";
            public static final String TICKETS = "tickets";
            public static final String NUMBER_OF_TICKET_STATUSES = "number_of_ticket_statuses";
            public static final String TICKET_STATUSES = "ticket_statuses";

            // for requesting
            public static final String HEADER_KEY_REQUEST_TYPE = "request_type";
            public static final String HEADER_VALUE_EVENT_ON_SALE = "on_sale";
            public static final String HEADER_VALUE_EVENT_COMING_SOON = "coming_soon";
            public static final String HEADER_VALUE_EVENT_RECOMMENDED = "recommended";
        }

        /**
         * API constants for accessing {@link com.niupiao.niupiao.models.Ticket} resources.
         */
        public final static class Ticket {

            public static final String EVENT = "event";
            public static final String EVENT_ID = "event_id";
            public static final String USER_ID = "user_id";
            public static final String STATUS = "status";
            public static final String TICKET_STATUS = "ticket_status";


            // for requesting
            public static final String HEADER_KEY_REQUEST_TYPE = "request_type";
            public static final String HEADER_VALUE_TICKET_PAST = "past";
            public static final String HEADER_VALUE_TICKET_UPCOMING = "upcoming";
        }

        public final static class TicketStatus {
            public static final String NAME = "name";
            public static final String PRICE = "price";
            public static final String MAX_PURCHASABLE = "max_purchasable";
        }

        /**
         * API keys that we use to pass data obtained from the Facebook API, e.g., its {@link com.facebook.model.GraphUser}.
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

            /**
             * Status codes that server passes back when we try to login/register with Facebook
             */
            public final static class ResponseStatus {
                public static final String USER_EXISTS = "user_exists";
                public static final String USER_CREATED = "user_created";
            }

        }

    }

}
