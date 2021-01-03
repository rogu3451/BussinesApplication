package pl.busman.project.model;

import lombok.Builder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.busman.project.service.validation.UserValidation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.*;

@Entity
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private boolean enabled;

    @Transient
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public SystemUser() {
    }

    public SystemUser(Long id, String username, String firstName, String lastName) {
        this.id = id;
        this.username=username;
        this.firstName=firstName;
        this.lastName=lastName;
        this.enabled=true;
    }


    public SystemUser(Long id, String username, String password) throws Exception{
        if(password!=null){
            UserValidation.validatePassword(password);
            String encodedPassword = encodePassword(password);
            this.password=encodedPassword;
        }
        this.id = id;
        this.username=username;
        this.enabled=true;
    }

    public SystemUser(String username, String password, String firstName, String lastName) throws Exception{
        if(password!=null){
            UserValidation.validatePassword(password);
            String encodedPassword = encodePassword(password);
            this.password=encodedPassword;
        }
        this.firstName=firstName;
        this.lastName=lastName;
        this.username=username;
        this.enabled=true;
    }

    public static String encodePassword(String passwordToEncode) throws Exception{
        return passwordEncoder.encode(passwordToEncode);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception{
        UserValidation.validatePassword(password);
        String encodedPassword = encodePassword(password);
        this.password = encodedPassword;
        this.enabled=true;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


}
