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
    public void handleUserOptions() {

    }

    public void displayCalendar() {

    }
}
