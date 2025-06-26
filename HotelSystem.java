import java.io.*;
import java.util.*;

public class HotelSystem {
    private ArrayList<Room> rooms;
    private ArrayList<Booking> bookings;
    private Scanner scanner;
    private static final String FILE_BOOKINGS = "bookings.txt";

    public HotelSystem() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
        scanner = new Scanner(System.in);
        initializeRooms();
        loadBookingsFromFile();
    }

    private void initializeRooms() {
        // 3 Standard, 3 Deluxe, 3 Suite rooms
        for (int i = 1; i <= 3; i++) {
            rooms.add(new Room(i, "Standard"));
            rooms.add(new Room(i + 3, "Deluxe"));
            rooms.add(new Room(i + 6, "Suite"));
        }
    }

    public void run() {
        while (true) {
            System.out.println("\n🏨 Hotel Reservation Menu:");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Search rooms by category");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1": showAvailableRooms(); break;
                case "2": bookRoom(); break;
                case "3": cancelBooking(); break;
                case "4": showBookings(); break;
                case "5": searchRoomsByCategory(); break;
                case "6": saveBookingsToFile(); System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void showAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms) {
            if (!room.isBooked()) {
                System.out.println(room);
            }
        }
    }

private void bookRoom() {
    System.out.print("Enter your name: ");
    String name = scanner.nextLine();
    System.out.print("Enter room category (Standard/Deluxe/Suite): ");
    String category = scanner.nextLine();

    for (Room room : rooms) {
        if (!room.isBooked() && room.getCategory().equalsIgnoreCase(category)) {
            System.out.print("Confirm payment of $" + getRoomRate(category) + "? (yes/no): ");
            String confirm = scanner.nextLine();
            if (!confirm.equalsIgnoreCase("yes")) {
                System.out.println("Payment cancelled.");
                return;
            }

            room.book();
            bookings.add(new Booking(name, room.getId(), category));
            System.out.println("Booking successful!");
            System.out.println("Booking Details:");
            System.out.println("Guest: " + name);
            System.out.println("Room ID: " + room.getId());
            System.out.println("Category: " + category);
            System.out.println("Amount Paid: $" + getRoomRate(category));
            return;
        }
    }

    System.out.println("No available rooms in that category.");
}

private int getRoomRate(String category) {
    if (category.equalsIgnoreCase("Standard")) return 100;
    if (category.equalsIgnoreCase("Deluxe")) return 200;
    if (category.equalsIgnoreCase("Suite")) return 300;
    return 0;
}


    private void cancelBooking() {
        System.out.print("Enter your name to cancel booking: ");
        String name = scanner.nextLine();
        Booking toCancel = null;

        for (Booking b : bookings) {
            if (b.getGuestName().equalsIgnoreCase(name)) {
                toCancel = b;
                break;
            }
        }

        if (toCancel != null) {
            bookings.remove(toCancel);
            for (Room r : rooms) {
                if (r.getId() == toCancel.getRoomId()) {
                    r.cancel();
                    break;
                }
            }
            System.out.println("Booking cancelled for " + name);
        } else {
            System.out.println("No booking found for that name.");
        }
    }

    private void showBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No current bookings.");
        } else {
            for (Booking b : bookings) {
                System.out.println(b);
            }
        }
    }

    private void saveBookingsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_BOOKINGS))) {
            for (Booking b : bookings) {
                writer.write(b.getGuestName() + "," + b.getRoomId() + "," + b.getCategory());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }


    private void loadBookingsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_BOOKINGS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int roomId = Integer.parseInt(parts[1]);
                    String category = parts[2];
                    bookings.add(new Booking(name, roomId, category));
                    for (Room r : rooms) {
                        if (r.getId() == roomId && r.getCategory().equalsIgnoreCase(category)) {
                            r.book();
                            break;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("Error loading bookings: " + e.getMessage());
        }
    }

    private void searchRoomsByCategory() {
    System.out.print("Enter category to search (Standard/Deluxe/Suite): ");
    String category = scanner.nextLine();
    boolean found = false;

    for (Room room : rooms) {
        if (room.getCategory().equalsIgnoreCase(category)) {
            System.out.println(room);
            found = true;
        }
    }
    if (!found) {
        System.out.println("No rooms found in category: " + category);
    }
    }


}
