import java.util.*;
import java.io.*;

// Factory Pattern for Calendar Creation
class CalendarFactory {
    public static Calendar createCalendar(User user, String name) {
        return new Calendar(user, name);
    }
}

// Strategy Pattern for User Interaction
interface UserAction {
    void execute(User user, BufferedReader reader) throws IOException;
}

class AddCalendarAction implements UserAction {
    public void execute(User user, BufferedReader reader) throws IOException {
        System.out.print("Enter new calendar name: ");
        String newCalendarName = reader.readLine().trim();
        user.addCalendar(newCalendarName);
        System.out.println("Calendar '" + newCalendarName + "' added.");
    }
}

class RemoveCalendarAction implements UserAction {
    public void execute(User user, BufferedReader reader) throws IOException {
        List<String> calendarsList = user.getCalendarsList();
        if (calendarsList.isEmpty()) {
            System.out.println("No calendars available.");
            return;
        }
        System.out.print("Enter calendar name to delete: ");
        String removeCalendarName = reader.readLine().trim();
        user.removeCalendar(removeCalendarName);
        System.out.println("Calendar '" + removeCalendarName + "' removed.");
    }
}

class ViewCalendarAction implements UserAction {
    public void execute(User user, BufferedReader reader) throws IOException {
        List<String> calendarsList = user.getCalendarsList();
        if (calendarsList.isEmpty()) {
            System.out.println("No calendars available.");
            return;
        }
        System.out.println("View a calendar by number:");
        for (int i = 0; i < calendarsList.size(); i++) {
            System.out.println((i + 1) + ". " + calendarsList.get(i));
        }
        System.out.print("Enter number: ");
        try {
            int selectedIndex = Integer.parseInt(reader.readLine().trim());
            user.setSelectedCalendar(selectedIndex);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
}

public class User {
    public String username;
    private final Map<String, Calendar> calendars;
    private Calendar selectedCalendar;
    private final Map<String, UserAction> actions;

    public User(String username) {
        this.username = username;
        this.calendars = new HashMap<>();
        this.actions = new HashMap<>();
        initializeActions();
    }

    private void initializeActions() {
        actions.put("a", new AddCalendarAction());
        actions.put("r", new RemoveCalendarAction());
        actions.put("v", new ViewCalendarAction());
    }

    public void addCalendar(String calendarName) {
        calendars.put(calendarName, CalendarFactory.createCalendar(this, calendarName));
    }

    public void removeCalendar(String calendarName) {
        calendars.remove(calendarName);
    }

    public void setSelectedCalendar(int index) throws IOException {
        List<String> calendarList = getCalendarsList();
        if (index > 0 && index <= calendarList.size()) {
            selectedCalendar = calendars.get(calendarList.get(index - 1));
            System.out.println("Current calendar set to: " + selectedCalendar);
            selectedCalendar.handleUserOptions();
        } else {
            System.out.println("Invalid selection.");
        }
    }

    public static void displayOptions() {
        System.out.println("User Options: b = Back | a = Add Calendar | r = Remove Calendar | v = View Calendar");
    }

    public List<String> getCalendarsList() {
        return new ArrayList<>(calendars.keySet());
    }

    public void handleUserOptions() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            displayOptions();
            String input = reader.readLine().trim();
            if (input.equals("b")) {
                System.out.println("Returning to main menu.");
                return;
            }
            UserAction action = actions.get(input);
            if (action != null) {
                action.execute(this, reader);
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
