package pl.busman.project.model;

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
    private boolean enabled;

    @Transient
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public SystemUser() {
    }

    public SystemUser(String username, String password) throws Exception{
        UserValidation.validatePassword(password);
        String encodedPassword = encodePassword(password);
        this.username=username;
        this.password=encodedPassword;
        this.enabled=true;
    }

    public String encodePassword(String passwordToEncode) throws Exception{
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

}
