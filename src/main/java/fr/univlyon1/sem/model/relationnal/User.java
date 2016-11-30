package fr.univlyon1.sem.model.relationnal;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public User() {

    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "password")
    private String password;

    @Column(name = "fb_id")
    private String fb_id;

    @Column(name = "activated")
    private boolean activated;

    @OneToMany(mappedBy = "id_user", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ConfirmationToken> confirmation_token;


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User u = (User) obj;
            return u.getId() == this.getId();
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public List<ConfirmationToken> getConfirmationsToken() {
        return confirmation_token;
    }

    public ConfirmationToken getToken(int type) {
        Optional<ConfirmationToken> tok = getConfirmationsToken().stream().filter(t -> t.getType() == type).findFirst();

        if (tok.isPresent()) {
            return tok.get();
        }

        return null;
    }

    public void addConfirmationToken(int type, String token){
        ConfirmationToken tok = new ConfirmationToken();
        tok.setType(type);
        tok.setId_user(this.id);
        tok.setToken(token);
        if (confirmation_token==null){
            confirmation_token= new ArrayList<>();
        }
        confirmation_token.add(tok);
    }


    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }


    public void setConfirmation_token(List<ConfirmationToken> confirmation_token) {
        this.confirmation_token = confirmation_token;
    }

    public void removeToken(ConfirmationToken token) {
        this.getConfirmationsToken().remove(token);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}