package location.model;

public class Location {
    private Integer id;
    private String name;
    private Integer stationUserId;
    private Integer districtCenterUserId;
    private Integer type; // 0: station, 1: district

    public Location(int id, String name, Integer stationUserId, Integer districtCenterUserId, int type) {
        this.id = id;
        this.name = name;
        this.stationUserId = stationUserId;
        this.districtCenterUserId = districtCenterUserId;
        this.type = type;
    }

    public Location(int id, Integer stationUserId, Integer districtCenterUserId, int type) {
        this.id = id;
        this.stationUserId = stationUserId;
        this.districtCenterUserId = districtCenterUserId;
        this.type = type;
    }

    public Location(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public Location(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getStationUserId() {
        return stationUserId;
    }

    public Integer getDistrictCenterUserId() {
        return districtCenterUserId;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stationUserId=" + stationUserId +
                ", districtCenterUserId=" + districtCenterUserId +
                ", type=" + type +
                '}';
    }
}
