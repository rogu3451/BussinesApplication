package pl.busman.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.busman.project.model.Role;
import pl.busman.project.model.SystemUser;
import pl.busman.project.repository.RoleRepository;
import pl.busman.project.repository.SystemUserRepository;

import java.util.Properties;


@Component
@Scope("singleton")
@Transactional
public class Starter implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SystemUserRepository systemUserRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {  // przyjmuje dowolna ilosc stringow


       // loadData();

    }



    private void loadData (){
        try{
            // Users
            SystemUser admin = new SystemUser("admin","Root12","Karol","Rogoziński");
            systemUserRepository.save(admin);

            Role adminRole = new Role("admin","ADMIN");
            roleRepository.save(adminRole);

            SystemUser employee = new SystemUser("employee","Root12", "Emil","Bułka");
            systemUserRepository.save(employee);

            Role employeeRole = new Role("employee","EMPLOYEE");
            roleRepository.save(employeeRole);

            SystemUser customer = new SystemUser("customer","Root12", "Martyna","Karwacka");
            systemUserRepository.save(customer);

            Role customerRole = new Role("customer","CUSTOMER");
            roleRepository.save(customerRole);

        }catch (Exception e){
            System.out.println("Loading data error");
        }


    }
}
