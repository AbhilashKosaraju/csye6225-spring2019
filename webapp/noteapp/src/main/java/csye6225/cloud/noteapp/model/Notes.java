package csye6225.cloud.noteapp.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "notes")
public class Notes {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private UUID note_id;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="user_id")
    private String user_id;

    @Column(name="created_ts")
    private String created_ts;

    @Column(name="updated_ts")
    private String updates_ts;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getNote_id() {
        return note_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreated_ts() {
        return created_ts;
    }

    public void setCreated_ts(String created_ts) {
        this.created_ts = created_ts;
    }

    public String getUpdates_ts() {
        return updates_ts;
    }

    public void setUpdates_ts(String updates_ts) {
        this.updates_ts = updates_ts;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "noteId=" + note_id +
                ", notes='" + content + '\'' +
                '}';
    }
}