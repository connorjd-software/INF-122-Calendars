import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Calendar {
    public TimeZone timezone;
    private final User owner;
    private ArrayList<Event> events;
    private boolean isPrivate;
    private Set<String> sharedWith;
    private Calendar previousVersion;
    private String notes;

    // Added these
    private String name;


    /* Not implemented

    private HashMap<String, Event> deletedEvents;

    */


    // Removed screen to reflect new UML
    public Calendar(User owner, String name) {
        this.owner = owner;
        this.name = name;
        this.isPrivate = true;
        events = new ArrayList<>();
        timezone = TimeZone.getDefault();
        sharedWith = new HashSet<>();
        notes = "";
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void deleteEvent(Event event) {
        events.remove(event);
    }

    public Event updateEvent(Event event) {
        return event;
    }


    // TODO: Not sure about this method
    public void changeEventTimes() {

    }

    public void toggleIsPrivate() {
        this.isPrivate = !this.isPrivate;
    }

    public void displayDays(int startDay, int endDay) {

    }

    //  Cannot actually share with current implementation
    public void shareWith(String username) {
        System.out.println("Shared with " + username);
    }

    public void notateEvent(Event event, String notes) {
        event.setNotes(notes);
    }

    @Override
    public String toString() {
        return this.name;
    }

    // Added methods for CLI options
    public void displayOptions() {
        CLI.printCenteredOptions(name, "b = Back | a = Add event | e = Edit Event | t = Toggle Privacy | s = Share With");
    }

    public void handleUserOptions() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            displayCalendar();
            displayOptions();
            String input = reader.readLine().trim();
            switch (input) {
                case "b":
                    System.out.println("Returning to main menu.");
                    return;
                case "a":
                    try {
                        System.out.print("Enter event name: ");
                        String eventName = reader.readLine().trim();
                        System.out.print("Enter event start time (YYYY-MM-DDTHH:MM): ");
                        LocalDateTime startTime = LocalDateTime.parse(reader.readLine().trim());
                        System.out.print("Enter event end time (YYYY-MM-DDTHH:MM): ");
                        LocalDateTime endTime = LocalDateTime.parse(reader.readLine().trim());
                        System.out.print("Enter event description: ");
                        String description = reader.readLine().trim();
                        addEvent(new Event(eventName, startTime, endTime, description));
                        System.out.println("\nEvent added.");
                    } catch (DateTimeParseException e) {
                        System.out.println("Error parsing date-time input (Make sure you use the format YYYY-MM-DDTHH:MM with the T in-between DD and HH.");
                    }
                    break;
                case "e":
                    break;
                case "t":
                    String visibility = this.isPrivate ? "private" : "public";
                    toggleIsPrivate();
                    System.out.println("Changed calendar visibility to: " + visibility);
                    break;
                case "s":
                    System.out.print("Enter username to share with: ");
                    String username = reader.readLine().trim();
                    shareWith(username);

            }
        }
    }

    public void displayCalendar() {
        // Get current date
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        // Get first day of the month
        LocalDate firstDayOfMonth = LocalDate.of(currentYear, currentMonth, 1);
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); // 1 = Monday, ... 7 = Sunday

        // Adjust for Sunday start
        firstDayOfWeek = (firstDayOfWeek % 7);

        // Get number of days in the month
        int daysInMonth = firstDayOfMonth.lengthOfMonth();

        // Collect events by day
        HashMap<Integer, String> eventDays = new HashMap<>();
        for (Event event : events) {
            int eventDay = event.getStart().getDayOfMonth();
            eventDays.put(eventDay, event.getName());
        }

        // Display calendar header
        System.out.println("\n     " + today.getMonth() + " " + currentYear);
        System.out.println(" Su   Mo   Tu   We   Th   Fr   Sa");

        // Print padding for the first week
        for (int i = 0; i < firstDayOfWeek; i++) {
            System.out.print("    ");
        }

        // Print the days of the month
        for (int day = 1; day <= daysInMonth; day++) {
            // Check if there is an event on this day
            if (eventDays.containsKey(day)) {
                System.out.printf("[%2d] ", day); // Event days in brackets
            } else {
                System.out.printf(" %2d  ", day); // Regular days
            }

            // New line at the end of the week
            if ((day + firstDayOfWeek) % 7 == 0) {
                System.out.println();
            }
        }
        System.out.println("A date surrounded by brackets [] indicates event(s) on that day.");
    }
}
