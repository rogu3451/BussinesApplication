package pl.busman.project.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Size(min=5, max=20, message = "Username should be between 5 and 20 characters")
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
        validatePassword(password);
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
        validatePassword(password);
        String encodedPassword = encodePassword(password);
        this.password = encodedPassword;
        this.enabled=true;
    }

    public static boolean validatePassword(String passwordToCheck){
        int upperCase = 0;
        int isDigit = 0;

        for (int k = 0; k < passwordToCheck.length(); k++) {
            if (Character.isDigit(passwordToCheck.charAt(k))) isDigit++;
            if (Character.isUpperCase(passwordToCheck.charAt(k))) upperCase++;
        }

        if ((passwordToCheck.length() < 6) || (upperCase < 1) || (isDigit < 1)){
            throw new IllegalArgumentException("Invalid password. Password should have minimum 6 characters, one  uppercase letter and one digit");
        }
        else{
            System.out.println("Valid password.");
            return true;
        }

    }

}
