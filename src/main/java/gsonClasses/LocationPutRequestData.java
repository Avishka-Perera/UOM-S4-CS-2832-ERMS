package gsonClasses;

public class LocationPutRequestData {
    private int locationId;
    private Integer stationUserId;
    private Integer districtUserId;
    private int type;

    public LocationPutRequestData() {
    }

    public int getLocationId() {
        return locationId;
    }

    public Integer getStationUserId() {
        return stationUserId;
    }

    public Integer getDistrictUserId() {
        return districtUserId;
    }

    public int getType() {
        return type;
    }

    public String toString() {
        return "PutRequestData [ locationId: " + locationId + ", stationUserId: " + stationUserId + ", districtUserId: " + districtUserId + ", type: " + type + " ]";
    }
}
