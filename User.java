import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    // Added for CLI
    public static void displayOptions() {
        cli.printCenteredOptions("User Options", "b = Back to Menu | n = New Calendar | r = Remove Calendar | u = Update Calendar");
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
                case "n":
                    System.out.print("Enter new calendar name: ");
                    String newCalendarName = reader.readLine().trim();
                    // TODO: FINISH THIS
                    System.out.println("Calendar '" + newCalendarName + "' added.");
                    break;
                case "r":
                    System.out.print("Enter calendar name to delete: ");
                    String removeCalendarName = reader.readLine().trim();
                    removeCalendar(removeCalendarName);
                    System.out.println("Calendar '" + removeCalendarName + "' removed.");
                    break;
                case "u":
                    System.out.print("Enter calendar name to update: ");
                    String updateCalendarName = reader.readLine().trim();
                    // TODO: FINISH THIS
                    System.out.println("Calendar '" + updateCalendarName + "' updated.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /* Not Implemented

    public void notateCalendar(String calendarName,  ArrayList<String> notes) {

    }

    public void showKNotifications(int k) {

    }

     */

}
