import java.io.IOException;
import java.util.HashMap;


public class Application {
    // CLI variables & methods (added)
    public static final int CLI_WIDTH = 74;
    public static void printCentered(String text, int width) {
        int padding = (width - text.length()) / 2;
        String formattedText = String.format("%" + padding + "s%s%" + padding + "s", "", text, "");

        formattedText = formattedText.length() > width ? formattedText.substring(0, width)
                : String.format("%-" + width + "s", formattedText);

        System.out.println("|" + formattedText + "|");
    }

    private HashMap<String, User> users;
    private User currentUser;
    private Calendar currentCalendar;

    public Application() {
        users = new HashMap<>();
    }

    public void displayOptions() {
        System.out.println("\n=============================== Menu Options ===============================");
        printCentered("q = Quit | u = Add User | s = Select User | d = Display Users", CLI_WIDTH);
        System.out.println("============================================================================");
        System.out.print("Enter choice then press enter: ");
    }


    public void addUser(String userName) {
        users.put(userName, new User(userName));
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setCurrentUser(String username) {
        this.currentUser = users.get(username);
    }

    public static void main(String[] args) throws IOException {
        Application application = new Application();

        while (true) {
            application.displayOptions();
            int key = System.in.read();
            if (key == 'q') break;
            if (key == 'u') {
                System.out.print("Please enter new username: ");
                String username = System.console().readLine();
                application.addUser(username);
            }
            if (key == 'd') {
                System.out.println("Users: " + application.getUsers());
            }


            long skipped = System.in.skip(2);
            System.out.println("Key Pressed: " + (char) key);
        }
    }

}
