package constants;

public class TableData {
    public static String userTable = "users";
    public static String locationTable = "locations";
    public static String partyTable = "parties";

    public static class Users {
        public static String id = "user_id";
        public static String name = "user_name";
        public static String email = "email";
        public static String password = "password";
        public static String contactNumber = "contact_number";
        public static String level = "level";
        public static String locationId = "location_id";
    }
    public static class Locations {
        public static String id = "location_id";
        public static String name = "location_name";
        public static String stationUserId = "station_user_id";
        public static String districtUserId = "district_center_user_id";
        public static String type = "type";
    }
    public static class Parties {
        public static String id = "party_id";
        public static String name = "party_name";
        public static String votes = "votes";
    }
}
