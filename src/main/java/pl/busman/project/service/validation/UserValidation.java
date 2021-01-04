package pl.busman.project.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.busman.project.model.Role;
import pl.busman.project.model.SystemUser;
import pl.busman.project.model.dto.UserWithRole;
import pl.busman.project.model.dto.UsersWithRoleQuery;
import pl.busman.project.service.RoleService;
import pl.busman.project.service.SystemUserService;

@Component
public class UserValidation {

    @Autowired
    SystemUserService systemUserService;

    @Autowired
    RoleService roleService;

    public boolean validateUser(UserWithRole userWithRole, BindingResult bindingResult, Model model){ // used while adding

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

        if(isNull(userWithRole.getSystemUser().getFirstName())){
            model.addAttribute("firstNameIsNull","First name can not be null.");
            errors++;
        }

        if(isNull(userWithRole.getSystemUser().getLastName())){
            model.addAttribute("lastNameIsNull","Last name can not be null.");
            errors++;
        }

        if(errors!=0){
            return false; // something went wrong
        }else{
            return true; // validation correct
        }

    }

    public boolean validateUser(UsersWithRoleQuery usersWithRoleQuery, BindingResult bindingResult, Model model) { // used while modification

        int errors = 0;

        try{

            // Data from db
            SystemUser userFromDb = systemUserService.getSystemUser(usersWithRoleQuery.getUserId());
            Role roleFromDb = roleService.getRole(usersWithRoleQuery.getRoleId());

            // Data to modification
            Long userId = usersWithRoleQuery.getUserId();
            String firstName = usersWithRoleQuery.getFirstName();
            String password = usersWithRoleQuery.getPassword();
            String username = usersWithRoleQuery.getUsername();
            String lastName = usersWithRoleQuery.getLastName();
            String role = usersWithRoleQuery.getRole();
            if(userFromDb.getFirstName().equals(firstName) &&
                        userFromDb.getLastName().equals(lastName) &&
                        userFromDb.getUsername().equals(username) &&
                        roleFromDb.getRole().equals(role) &&
                        password.isEmpty()

            ){
                        errors++;
                        model.addAttribute("nothingHasChanged","Nothing has changed");
            }else{
                if(!password.isEmpty()){
                    try{
                        validatePassword(password);
                    }catch (Exception e){
                        errors++;
                        model.addAttribute("invalidPassword","Invalid password. " +
                                "Password should have minimum 6 characters, one  uppercase letter and one digit.");
                    }
                }

                if(!checkLengthOfUsername(username)){
                    model.addAttribute("incorrectLengthOfUsername","Username should be between 5 and 20 characters.");
                    errors++;
                }

                if(checkIfUsernameExist(username)){
                    if(!username.equals(getUsernameById(userId))){
                        model.addAttribute("usernameAllreadyExist","Username allready exist.");
                        errors++;
                    }
                }

                if(isNull(firstName)){
                    model.addAttribute("firstNameIsNull","First name can not be null.");
                    errors++;
                }

                if(isNull(lastName)){
                    model.addAttribute("lastNameIsNull","Last name can not be null.");
                    errors++;
                }

                if(!password.isEmpty()){
                    try{
                        validatePassword(password);
                        SystemUser.encodePassword(password);
                    }catch (Exception e){
                        errors++;
                        model.addAttribute("invalidPassword","Invalid password. " +
                                "Password should have minimum 6 characters, one  uppercase letter and one digit.");
                    }
                }

                if(!checkLengthOfUsername(username)){
                    model.addAttribute("incorrectLengthOfUsername","Username should be between 5 and 20 characters.");
                    errors++;
                }

                if(isNull(firstName)){
                    model.addAttribute("firstNameIsNull","First name can not be null.");
                    errors++;
                }

                if(isNull(lastName)){
                    model.addAttribute("lastNameIsNull","Last name can not be null.");
                    errors++;
                }
            }



        }catch (Exception e){
            System.out.println("Encoding password error occur");
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

    private String getUsernameById(Long id){
        return systemUserService.getUsernameById(id);
    }

    private boolean isNull(String stringToCheck){
        if(stringToCheck.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}
