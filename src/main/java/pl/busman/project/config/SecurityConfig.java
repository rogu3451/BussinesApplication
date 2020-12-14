package pl.busman.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    public void configure(HttpSecurity security) throws Exception {

        security.authorizeRequests()
               // .antMatchers("/h2-console/**").permitAll()
               // .antMatchers("/knights").hasAnyRole("USER","ADMIN")  // gdy korzystamy z pamieci
               // .antMatchers("/knight").hasAnyRole("ADMIN"). // gdy korzystamy z pamieci
              //  .antMatchers("/knights").hasAnyAuthority("USER","ADMIN") // gdy korzystamy z bazy danych
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN") // gdy korzystamy z bazy danych
                .anyRequest().authenticated()
                .and()
                .formLogin().defaultSuccessUrl("/admin/allProjects",true);
    }

    @Autowired
    public void securityUsers (AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordUser1 = "user1";
        String passwordUser2 = "user2";
        String encodePassword1 = passwordEncoder.encode(passwordUser1);
        String encodePassword2 = passwordEncoder.encode(passwordUser2);
//        auth.inMemoryAuthentication()
//
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("user1").password(encodePassword1).roles("USER")
//                .and()
//                .withUser("user2").password(encodePassword2).roles("ADMIN");

        auth.jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled FROM system_user WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, role FROM role  WHERE username=?");
    }
}
