package attendancetracker.ase.com.iot_roomtracker.dao;

/**
 * Created by Sangeeta on 01-01-2018.
 */

public class RoomDeatils {
    public RoomDeatils(double latitude, double longitude, boolean isOccupied, String roomName, int floor) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.isOccupied = isOccupied;
        this.roomName = roomName;
        this.floor = floor;
    }

    public double getLongitude() {
        return longitude;

    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    private double longitude;
    private double latitude;
    private boolean isOccupied;
    private String roomName;
    private int floor;
    private String id;
    private String OccupiedBy;
    private String OccupiedByEmail;
    private Boolean isSubscribed;
}
