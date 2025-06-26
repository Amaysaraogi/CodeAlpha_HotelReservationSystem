public class Booking {
    private String guestName;
    private int roomId;
    private String category;

    public Booking(String guestName, int roomId, String category) {
        this.guestName = guestName;
        this.roomId = roomId;
        this.category = category;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getCategory() {
        return category;
    }

    public String toString() {
        return "Booking: " + guestName + " -> Room " + roomId + " [" + category + "]";
    }
}
