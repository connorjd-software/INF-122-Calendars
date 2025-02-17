import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Application {
    private final HashMap<String, User> users;
    private User currentUser;

    public Application() {
        users = new HashMap<>();
    }

    public void displayOptions() {
        CLI.printCenteredOptions("Menu Options", "q = Quit | a = Add User | s = Select User");
    }

    public void addUser(String userName) {
        if (!users.containsKey(userName)) {
            users.put(userName, new User(userName));
            System.out.println("User '" + userName + "' added successfully.");
        } else {
            System.out.println("User '" + userName + "' already exists.");
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // Added these for more depth/functionality
    private List<String> getUsersList() {
        return new ArrayList<>(users.keySet());
    }

    public void setCurrentUser(int index) throws IOException {
        List<String> userList = getUsersList();
        if (index > 0 && index <= userList.size()) {
            currentUser = users.get(userList.get(index - 1));
            System.out.println("Current user set to: " + getCurrentUser().username);
            currentUser.handleUserOptions();
        } else {
            System.out.println("Invalid selection.");
        }
    }

    public static void main(String[] args) throws IOException {
        Application application = new Application();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            application.displayOptions();
            String input = reader.readLine().trim();

            switch (input) {
                case "q":
                    System.out.println("Exiting application.");
                    return;
                case "a":
                    System.out.print("Please enter new username: ");
                    String username = reader.readLine().trim();
                    application.addUser(username);
                    break;
                case "s":
                    List<String> usersList = application.getUsersList();
                    if (usersList.isEmpty()) {
                        System.out.println("No users available.");
                        break;
                    }
                    System.out.println("Select a user by number:");
                    for (int i = 0; i < usersList.size(); i++) {
                        System.out.println((i + 1) + ". " + usersList.get(i));
                    }
                    System.out.print("Enter number: ");
                    try {
                        int selectedIndex = Integer.parseInt(reader.readLine().trim());
                        application.setCurrentUser(selectedIndex);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
