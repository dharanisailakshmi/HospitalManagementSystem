package pro;

public class Room {
    private int roomNumber;
    private boolean isOccupied;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isOccupied = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void assignRoom() {
        isOccupied = true;
    }

    public void vacateRoom() {
        isOccupied = false;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + ", " + (isOccupied ? "Occupied" : "Vacant");
    }
}
