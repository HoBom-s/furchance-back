package com.hobom.furchance.url;

public class Url {

    public static final String ID_PARAM = "/{id}";

    public static class AbandonedAnimal {

        public static final String BASE_URL = "/api/v1/abandoned-animals";

        public static final String PAGINATION = "/pagination";
    }

    public static class User {

        public static final String BASE_URL = "/api/v1/users";
    }

    public static class Auth {

        public static final String BASE_URL = "/api/v1/auth";

        public static final String SIGNUP = "/signup";

        public static final String LOGIN = "/login";
    }

}
