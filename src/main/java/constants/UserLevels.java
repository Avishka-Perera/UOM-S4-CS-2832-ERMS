package constants;

import java.util.Arrays;
import java.util.List;

public class UserLevels {
    public static int STATION_OFFICER_USER_LEVEL = 0;
    public static int DISTRICT_OFFICER_USER_LEVEL = 1;
    public static int SECRETARIAT_OFFICER_USER_LEVEL = 2;
    public static int ADMIN_USER_LEVEL = 3;
    public static List<Integer> VOTE_USER_LEVELS = Arrays.asList(DISTRICT_OFFICER_USER_LEVEL, STATION_OFFICER_USER_LEVEL);
}
