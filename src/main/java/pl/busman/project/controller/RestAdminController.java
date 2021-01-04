package pl.busman.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.busman.project.model.SystemUser;
import pl.busman.project.service.SystemUserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class RestAdminController {

    @Autowired
    SystemUserService systemUserService;

    @GetMapping("/getAllCustomers")
    public List<SystemUser> getAllCustomers(Model model){
        System.out.println("X: "+systemUserService.getAllCustomers());
        return systemUserService.getAllCustomers();
    }
}
