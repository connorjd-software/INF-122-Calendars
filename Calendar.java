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
        CLI.printCenteredOptions(name, "b = Back | a = Add event | e = Edit Event | t = Toggle Privacy | s = Share With | v = Change View");
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
                        System.out.println("Event added.");
                    } catch (DateTimeParseException e) {
                        System.out.println("Error parsing date-time input (Make sure you use the format YYYY-MM-DDTHH:MM with the T in-between DD and HH.");
                    }
                    break;
                case "e":
                    System.out.println("Edit Event feature is not yet implemented.");
                    break;
                case "t":
                    String visibility = this.isPrivate ? "public" : "private";
                    toggleIsPrivate();
                    System.out.println("Changed calendar visibility to: " + visibility);
                    break;
                case "s":
                    System.out.print("Enter username to share with: ");
                    String username = reader.readLine().trim();
                    shareWith(username);
                    break;
                case "v":
                    changeView();
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void changeView() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Select view (d = Day | w = Week | m = Month | y = Year): ");
        String input = reader.readLine().trim();
        switch (input) {
            case "d":
                displayDayView(LocalDate.now());
                break;
            case "w":
                displayWeekView();
                break;
            case "m":
                displayMonthView();
                break;
            case "y":
                displayYearView();
                break;
            default:
                System.out.println("Invalid view option.");
        }
    }

    public void displayDayView(LocalDate date) {
        System.out.println("Day View: " + date);
        boolean hasEvents = false;
        for (int hour = 0; hour < 24; hour++) {
            LocalDateTime startHour = date.atTime(hour, 0);
            LocalDateTime endHour = date.atTime(hour, 59);
            System.out.printf("%02d:00 - %02d:59: ", hour, hour);
            boolean eventInHour = false;
            for (Event event : events) {
                if (!event.getStart().isAfter(endHour) && !event.getEnd().isBefore(startHour)) {
                    System.out.print(event.getName() + " ");
                    eventInHour = true;
                    hasEvents = true;
                }
            }
            if (!eventInHour) {
                System.out.print("No events");
            }
            System.out.println();
        }        for (Event event : events) {
            if (event.getStart().toLocalDate().equals(date)) {
                System.out.println("- " + event.getName() + " from " + event.getStart().toLocalTime() + " to " + event.getEnd().toLocalTime());
                hasEvents = true;
            }
        }
        if (!hasEvents) {
            System.out.println("No events scheduled for today.");
        }
    }

    public void displayWeekView() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() % 7);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        System.out.println("Week View: " + startOfWeek + " to " + endOfWeek);

        for (LocalDate date = startOfWeek; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            System.out.println(date + " (" + date.getDayOfWeek() + ")");

            for (int hour = 0; hour < 24; hour++) {
                LocalDateTime startHour = date.atTime(hour, 0);
                LocalDateTime endHour = date.atTime(hour, 59);
                System.out.printf("%02d:00 - %02d:59: ", hour, hour);

                boolean eventInHour = false;
                for (Event event : events) {
                    if (!event.getStart().isAfter(endHour) && !event.getEnd().isBefore(startHour)) {
                        System.out.print(event.getName() + " ");
                        eventInHour = true;
                    }
                }
                if (!eventInHour) {
                    System.out.print("No events");
                }
                System.out.println();
            }
        }
    }

    public void displayMonthView() {
        System.out.println("Month View");
        // Implementation for month view
    }

    public void displayYearView() {
        System.out.println("Year View");
        // Implementation for year view
    }
}
