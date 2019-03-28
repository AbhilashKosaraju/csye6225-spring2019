package csye6225.cloud.noteapp.model;

import javax.persistence.Entity;

@Entity
public class resetUser {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
