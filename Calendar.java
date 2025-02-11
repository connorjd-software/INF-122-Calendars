import java.util.Set;
import java.util.TimeZone;
import java.util.ArrayList;

public class Calendar {
    public TimeZone timezone;

    private Screen screen;
    private User owner;
    private ArrayList<Event> events;
    private boolean isPrivate;
    private Set<String> sharedWith;
    private Calendar previousVersion;
    private String notes;

    /* Not implemented

    private HashMap<String, Event> deletedEvents;

    */

    public Calendar(User owner, Screen screen, boolean isPrivate) {
        this.screen = screen;
        this.owner = owner;
        this.isPrivate = isPrivate;
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
}
