package pl.busman.project.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.busman.project.model.dto.UserWithRole;
import pl.busman.project.service.SystemUserService;

@Component
public class UserValidation {

    @Autowired
    SystemUserService systemUserService;

    public boolean validateUser(UserWithRole userWithRole, BindingResult bindingResult, Model model){

        int errors = 0;

        if(bindingResult.hasErrors()){
            model.addAttribute("invalidPassword","Invalid password. Password should have minimum 6 characters, one  uppercase letter and one digit.");
            errors++;
        }

        if(!checkLengthOfUsername(userWithRole.getSystemUser().getUsername())){
            model.addAttribute("incorrectLengthOfUsername","Username should be between 5 and 20 characters.");
            errors++;
        }

        if(checkIfUsernameExist(userWithRole.getSystemUser().getUsername())){
            model.addAttribute("usernameAllreadyExist","Username allready exist.");
            errors++;
        }

        if(errors!=0){
            return false; // something went wrong
        }else{
            return true; // validation correct
        }

    }

    public static void validatePassword(String passwordToCheck) throws IllegalArgumentException{ // true - correct | false - incorrect
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
            System.out.println("Valid password");
        }

    }

    private boolean checkLengthOfUsername(String username){
        if(username.length() < 5 || username.length() > 20){
            return false;
        }else {
            return true;
        }
    }

    private boolean checkIfUsernameExist(String username){
        if(!username.isEmpty()) {
            return systemUserService.checkIfUsernameExist(username);
        }
        return false;
    }
}
