
import java.util.Calendar;
import java.util.HashMap;

public class User {
    public String username;
    private final HashMap<String, Calendar> calendars;

    /*  Not implemented

    private HashMap<String, Calendar> deletedCalendars;


     */

    public User(String username) {
        this.username = username;
        calendars = new HashMap<>();
    }

    public void addCalendar(String calendarName, Calendar calendar) {
        calendars.put(calendarName, calendar);
    }

    public void removeCalendar(String calendarName) {
        calendars.remove(calendarName);
    }

    public void updateCalendar(String calendarName, Calendar newCalendar) {
        calendars.replace(calendarName, newCalendar);
    }

    /* Not Implemented

    public void notateCalendar(String calendarName,  ArrayList<String> notes) {

    }

    public void showKNotifications(int k) {

    }

     */

}
