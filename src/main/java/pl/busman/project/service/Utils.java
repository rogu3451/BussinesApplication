package pl.busman.project.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

public class Utils {


    public static String getCurrentUserName(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("name",currentPrincipalName);
        return  currentPrincipalName;
    }

    public static String getCurrentUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return  currentPrincipalName;
    }

    public static boolean checkIfSpecialCharactersExists(String stringToCheck){
        if(stringToCheck!=null){
            char[] specialCharacters = {'*', '&', '\'','\"'};
            for(int i=0; i<stringToCheck.length(); i++){
                for(int j=0; j<specialCharacters.length; j++){
                    if (stringToCheck.charAt(i) == specialCharacters[j]){
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
