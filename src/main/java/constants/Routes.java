package constants;

public class Routes {
    public static final String ROUTE_BASE_URL = "http://localhost/ERMS";

    public static final String ENDPOINT_PUBLIC = "/";
    public static final String ENDPOINT_LOGIN = "/login";
    public static final String ENDPOINT_VOTE_MANAGEMENT = "/vote-management";
    public static final String ENDPOINT_VOTES = "/votes";
    public static final String ENDPOINT_VOTES_REVIEW = "/votes-review";
    public static final String ENDPOINT_USERS = "/users";
    public static final String ENDPOINT_LOCATIONS = "/locations";
    public static final String ENDPOINT_PARTIES = "/parties";

    public static final String ROUTE_PUBLIC = ROUTE_BASE_URL;
    public static final String ROUTE_LOGIN = ROUTE_BASE_URL+ENDPOINT_LOGIN;
    public static final String ROUTE_VOTE_MANAGEMENT = ROUTE_BASE_URL+ENDPOINT_VOTE_MANAGEMENT;
    public static final String ROUTE_VOTES = ROUTE_BASE_URL+ENDPOINT_VOTES;
    public static final String ROUTE_VOTES_REVIEW = ROUTE_BASE_URL+ENDPOINT_VOTES_REVIEW;
    public static final String ROUTE_USERS = ROUTE_BASE_URL+ENDPOINT_USERS;
    public static final String ROUTE_LOCATIONS = ROUTE_BASE_URL+ENDPOINT_LOCATIONS;
    public static final String ROUTE_PARTIES = ROUTE_BASE_URL+ENDPOINT_PARTIES;
}
