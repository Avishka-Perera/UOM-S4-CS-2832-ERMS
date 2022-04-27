package location.model;

public class Location {
    private final String name;
    private final int stationUID;
    private final int districtCenterUID;

    public Location(String name, int stationUID, int districtCenterUID) {
        this.name = name;
        this.stationUID = stationUID;
        this.districtCenterUID = districtCenterUID;
    }

    public String getName() {
        return name;
    }

    public int getStationUID() {
        return stationUID;
    }

    public int getDistrictCenterUID() {
        return districtCenterUID;
    }
}
