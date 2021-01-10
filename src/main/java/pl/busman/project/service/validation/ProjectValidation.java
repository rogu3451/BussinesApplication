package pl.busman.project.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.busman.project.model.Project;
import pl.busman.project.model.SystemUser;
import pl.busman.project.service.ProjectService;
import pl.busman.project.service.SystemUserService;
import pl.busman.project.service.Utils;

@Component
public class ProjectValidation {

    @Autowired
    SystemUserService systemUserService;

    @Autowired
    ProjectService projectService;

    public boolean validateProject(BindingResult bindingResult, Project project, Model model, boolean isUpdate) { // In this case validation was created with hibernate's annotations.
        if (bindingResult.hasErrors() | checkIfSpecialCharactersExistInForm(project,model)
                | !checkIfCustomerExists(project.getCustomerId(),model) | (isUpdate && !checkIfChangesOccured(project,model)) ) {
            return false;
        }{
            return true;
        }
    }

    private boolean checkIfSpecialCharactersExistInForm(Project project, Model model) {
        int errors = 0;

        if(Utils.checkIfSpecialCharactersExists(project.getName())){
            model.addAttribute("specialCharactersExists1","You can't use special characters in this field");
            errors++;
        }

        if(Utils.checkIfSpecialCharactersExists(project.getDescription())){
            model.addAttribute("specialCharactersExists2","You can't use special characters in this field");
            errors++;
        }

        if(errors!=0){
            return true;
        }else {
            return false;
        }

    }


    private boolean checkIfChangesOccured(Project projectAfterModification, Model model) {
        Project projectBeforeModification = projectService.getProject(projectAfterModification.getId());
        String modifiedName = projectAfterModification.getName();
        String modifiedDescription = projectAfterModification.getDescription();
        Long modifiedCustomerId = projectAfterModification.getCustomerId();


        if(projectBeforeModification.getName().equals(modifiedName) &&
           projectBeforeModification.getDescription().equals(modifiedDescription) &&
           projectBeforeModification.getCustomerId().equals(modifiedCustomerId)){

           model.addAttribute("nothingHasChanged","Nothing has changed");
           return false;

        }else {
            return true;
        }
    }

    private boolean checkIfCustomerExists(Long customerId,Model model) {
        SystemUser systemUser = systemUserService.getCustomerById(customerId);
        if(systemUser==null){
            model.addAttribute("customerDoesNotExist","Customer with this id does not exist in database");
            return false; // customer does not exist
        }else {
            return true; // customer exists
        }
    }


}
