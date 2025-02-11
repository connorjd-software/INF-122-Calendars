import java.time.LocalDateTime;

public class Event {
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private Event previousVersion;
    private String notes;


    public Event(String name, LocalDateTime start, LocalDateTime end, String notes) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }


    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String editedNotes) {
        this.notes = editedNotes;
    }
}
