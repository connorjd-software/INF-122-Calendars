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
                displayWeekView(LocalDate.now());
                break;
            case "m":
                displayMonthView(LocalDate.now());
                break;
            case "y":
                displayYearView(LocalDate.now());
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

    public void displayWeekView(LocalDate startOfWeek) {
        // Calculate the end of the week
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        System.out.println("\nWeek View: " + startOfWeek + " to " + endOfWeek);

        // Header for the days of the week
        System.out.println("      |  Monday   | Tuesday |  Wednesday | Thursday | Friday | Saturday | Sunday");

        // Collect events by day and hour
        HashMap<LocalDate, HashMap<Integer, List<String>>> eventsMap = new HashMap<>();
        for (LocalDate date = startOfWeek; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            eventsMap.put(date, new HashMap<>());
            for (int hour = 0; hour < 24; hour++) {
                eventsMap.get(date).put(hour, new ArrayList<>());
            }
        }

        for (Event event : events) {
            LocalDate eventDate = event.getStart().toLocalDate();
            if (!eventDate.isBefore(startOfWeek) && !eventDate.isAfter(endOfWeek)) {
                int startHour = event.getStart().getHour();
                int endHour = event.getEnd().getHour();
                for (int hour = startHour; hour <= endHour; hour++) {
                    eventsMap.get(eventDate).get(hour).add(event.getName());
                }
            }
        }

        // Display each hour of the day
        for (int hour = 0; hour < 24; hour++) {
            // AM/PM Formatting
            int displayHour = (hour == 0 || hour == 12) ? 12 : hour % 12;
            String amPm = (hour < 12) ? "am" : "pm";

            // Display hour label
            System.out.printf("%2d%s  |", displayHour, amPm);

            // Display events for each day at this hour
            for (LocalDate date = startOfWeek; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
                List<String> eventsAtHour = eventsMap.get(date).get(hour);
                if (eventsAtHour.isEmpty()) {
                    System.out.printf(" %-10s|", " ");
                } else {
                    System.out.printf(" %-10s|", eventsAtHour.get(0)); // Display first event if multiple
                }
            }
            System.out.println();
        }
    }


    public void displayMonthView(LocalDate date) {
        // Get the month and year from the parameter
        int month = date.getMonthValue();
        int year = date.getYear();

        // Get first day of the specified month
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); // 1 = Monday, ... 7 = Sunday

        // Adjust for Sunday start (to align with the calendar grid)
        firstDayOfWeek = (firstDayOfWeek % 7);

        // Get number of days in the month
        int daysInMonth = firstDayOfMonth.lengthOfMonth();

        // Collect events by day for the specified month
        HashMap<Integer, String> eventDays = new HashMap<>();
        for (Event event : events) {
            LocalDate eventDate = event.getStart().toLocalDate();
            if (eventDate.getMonthValue() == month && eventDate.getYear() == year) {
                int eventDay = eventDate.getDayOfMonth();
                eventDays.put(eventDay, event.getName());
            }
        }

        // Display calendar header
        System.out.println("\n     " + date.getMonth() + " " + year);
        System.out.println(" Su   Mo   Tu   We   Th   Fr   Sa");

        // Print padding for the first week
        for (int i = 0; i < firstDayOfWeek; i++) {
            System.out.print("     ");
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
        System.out.println("\nA date surrounded by brackets [] indicates event(s) on that day.");
    }


    public void displayYearView(LocalDate date) {
        int year = date.getYear();
        System.out.println("\nYear View: " + year);
        System.out.println("=========================================");

        // Loop through all 12 months
        for (int month = 1; month <= 12; month++) {
            LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
            String monthName = firstDayOfMonth.getMonth().toString();
            int daysInMonth = firstDayOfMonth.lengthOfMonth();
            int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() % 7;

            // Header for the month
            System.out.println(monthName + " - " + date.getYear());
            System.out.println(" Su   Mo   Tu   We   Th   Fr   Sa");

            // Collect events for this month
            HashSet<Integer> eventDays = new HashSet<>();
            for (Event event : events) {
                LocalDate eventDate = event.getStart().toLocalDate();
                if (eventDate.getMonthValue() == month && eventDate.getYear() == year) {
                    eventDays.add(eventDate.getDayOfMonth());
                }
            }

            // Print padding for the first week
            for (int i = 0; i < firstDayOfWeek; i++) {
                System.out.print("     ");
            }

            // Print all days of the month
            for (int day = 1; day <= daysInMonth; day++) {
                if (eventDays.contains(day)) {
                    System.out.printf("[%2d]", day); // Event days in brackets
                } else {
                    System.out.printf(" %2d  ", day); // Regular days
                }

                // New line at the end of the week
                if ((day + firstDayOfWeek) % 7 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
        }
        System.out.println("Days with events are shown in brackets [ ].");
    }

}
