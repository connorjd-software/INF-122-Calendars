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

    public static void displayOptions() {
        System.out.println("\n=== User Options ===");
        System.out.println("b = Back to Menu");
        System.out.println("n = Add Calendar");
        System.out.println("r = Remove Calendar");
        System.out.println("u = Update Calendar");
        System.out.println("====================");
        System.out.print("Enter choice then press enter: ");
    }

    /* Not Implemented

    public void notateCalendar(String calendarName,  ArrayList<String> notes) {

    }

    public void showKNotifications(int k) {

    }

     */

}
