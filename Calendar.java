import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.TimeZone;
import java.util.ArrayList;

public class Calendar {
    public TimeZone timezone;
    private User owner;
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
    }

    public void addEvent(Event event) {

    }

    public void deleteEvent(Event event) {

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

    public void shareWith(String username) {

    }

    public void notateEvent(Event event, String notes) {

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
                    System.out.println("Changed calendar visibility to: " + !this.isPrivate);
                    this.isPrivate = !this.isPrivate;
                    break;
            }
        }
    }

    public void displayCalendar() {

    }
}
