import java.util.HashMap;

public class Application {
    private HashMap<String, User> users;
    private User currentUser;
    private Calendar currentCalendar;

    public Application() {
        users = new HashMap<>();
    }

    public void displayOptions() {

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

    public static void main(String[] args) {
        Application application = new Application();

    }

}
