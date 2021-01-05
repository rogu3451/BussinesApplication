package pl.busman.project.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.busman.project.model.Project;
import pl.busman.project.model.SystemUser;
import pl.busman.project.service.ProjectService;
import pl.busman.project.service.SystemUserService;

@Component
public class ProjectValidation {

    @Autowired
    SystemUserService systemUserService;

    @Autowired
    ProjectService projectService;

    public boolean validateProject(BindingResult bindingResult, Project project, Model model) { // In this case validation was created with hibernate's annotations.
        if (bindingResult.hasErrors() | !checkIfCustomerExists(project.getCustomerId(),model) | !checkIfChangesOccured(project,model)) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return false;
        } else {
            return true;
        }
    }

    private boolean checkIfChangesOccured(Project projectAfterModication, Model model) {
        Project projectBeforeModification = projectService.getProject(projectAfterModication.getId());
        String modifiedName = projectAfterModication.getName();
        String modifiedDescription = projectAfterModication.getDescription();
        Long modifiedCustomerId = projectAfterModication.getCustomerId();


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
