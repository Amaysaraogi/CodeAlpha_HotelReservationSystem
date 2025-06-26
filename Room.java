public class Room {
    private int id;
    private String category;
    private boolean isBooked;

    public Room(int id, String category) {
        this.id = id;
        this.category = category;
        this.isBooked = false;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void book() {
        isBooked = true;
    }

    public void cancel() {
        isBooked = false;
    }

    public String toString() {
        return "Room " + id + " [" + category + "] - " + (isBooked ? "Booked" : "Available");
    }
}
