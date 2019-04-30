package cn.hsy.echo.pojo;

public class Dormitory {
    private int zone;
    private int building;
    private int room;

    public Dormitory() {}

    public Dormitory(int zone, int building, int room) {
        this.zone = zone;
        this.building = building;
        this.room = room;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}
