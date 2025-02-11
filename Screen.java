public class Screen {
    private Calendar calendar;
    private String theme;

    public Screen() {

    }

    // Changed visibility to public
    public void changeTheme(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

}
