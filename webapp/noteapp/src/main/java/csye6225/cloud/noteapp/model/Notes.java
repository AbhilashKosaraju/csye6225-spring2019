package csye6225.cloud.noteapp.model;

import javax.persistence.*;

@Entity
@Table(name = "notes")
public class Notes {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int noteId;

    @Column(name="notes")
    private String notes;

    @Column(name="userId")
    private int userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userId", referencedColumnName = "email")
    private User user;

    public Notes() {
    }

    public Notes(String notes){
        this.notes = notes;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "noteId=" + noteId +
                ", notes='" + notes + '\'' +
                '}';
    }
}
