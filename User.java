import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class User {
    public String username;
    private final HashMap<String, Calendar> calendars;

    // Added for Calendar selection
    private Calendar selectedCalendar;

    /*  Not implemented

    private HashMap<String, Calendar> deletedCalendars;


     */

    public User(String username) {
        this.username = username;
        calendars = new HashMap<>();
    }

    // Changed to only have one argument of String to create a calendar
    public void addCalendar(String calendarName) {
        calendars.put(calendarName, new Calendar(this, calendarName));
    }

    public void removeCalendar(String calendarName) {
        calendars.remove(calendarName);
    }

    public void updateCalendar(String calendarName, Calendar newCalendar) {
        calendars.replace(calendarName, newCalendar);
    }

    public void setSelectedCalendar(int index) throws IOException {
        List<String> calendarList = getCalendarsList();
        if (index > 0 && index <= calendarList.size()) {
            selectedCalendar = calendars.get(calendarList.get(index - 1));
            System.out.println("Current calendar set to: " + selectedCalendar.toString());
            selectedCalendar.handleUserOptions();
        } else {
            System.out.println("Invalid selection.");
        }
    }

    // Added for CLI
    public static void displayOptions() {
        CLI.printCenteredOptions("User Options", "b = Back | a = Add Calendar | r = Remove Calendar | v = View Calendar");
    }

    private List<String> getCalendarsList() {
        return new ArrayList<>(calendars.keySet());
    }

    public void handleUserOptions() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            List<String> calendarsList = this.getCalendarsList();
            displayOptions();
            String input = reader.readLine().trim();
            switch (input) {
                case "b":
                    System.out.println("Returning to main menu.");
                    return;
                case "a":
                    System.out.print("Enter new calendar name: ");
                    String newCalendarName = reader.readLine().trim();
                    addCalendar(newCalendarName);
                    System.out.println("Calendar '" + newCalendarName + "' added.");
                    break;
                case "r":
                    if (calendarsList.isEmpty()) {
                        System.out.println("No calendars available.");
                        break;
                    }
                    System.out.print("Enter calendar name to delete: ");
                    String removeCalendarName = reader.readLine().trim();
                    removeCalendar(removeCalendarName);
                    System.out.println("Calendar '" + removeCalendarName + "' removed.");
                    break;
                case "v":
                    if (calendarsList.isEmpty()) {
                        System.out.println("No calendars available.");
                        break;
                    }
                    System.out.println("View a calendar by number:");
                    for (int i = 0; i < calendarsList.size(); i++) {
                        System.out.println((i + 1) + ". " + calendarsList.get(i));
                    }
                    System.out.print("Enter number: ");
                    try {
                        int selectedIndex = Integer.parseInt(reader.readLine().trim());
                        this.setSelectedCalendar(selectedIndex);
                        selectedCalendar.handleUserOptions();
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
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
