package com.example.app_dev.config;

import com.example.app_dev.service.impl.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        // Sét đặt dịch vụ để tìm kiếm User trong Database.
        // Và sét đặt PasswordEncoder.
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // Các trang không yêu cầu login
        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();

        // Nếu chưa login, nó sẽ redirect tới trang /login.

        // chỉ DIRECTOR mới có quyền sửa password Employee
//        http.authorizeRequests()
//                .antMatchers("/employee/changePw","/employee/changePw/*")
//                .access("hasRole('ROLE_DIRECTOR')");

        // MANAGER hoặc DIRECTOR mới có được quyền thêm sửa xóa customer, employee, service, contract, contract details
        http.authorizeRequests()
            .antMatchers("/customer/edit","/customer/edit/*","/customer/delete/*",
                                    "/employee/edit","/employee/edit/*","/employee/delete/*",
                                    "/contract/edit","/contract/edit/*","/contract/delete/*","/contract/createDetail",
                                    "/service/edit" ,"/service/edit/*" ,"/service/delete/*")
            .access("hasAnyRole('ROLE_DIRECTOR', 'ROLE_MANAGER')");

        // ADMIN chỉ có quyền thêm mới customer, employee, service, contract, contract details
        http.authorizeRequests().antMatchers("/customer/create",
                                                        "/employee/create",
                                                        "/service/create",
                                                        "/contract/create",
                                                        "/contract/createDetail")
                .access("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_MANAGER')");

        // EMPLOYEE được quyền xem danh sách, search, xem chi tiết đối tượng từ bảng
        // customer, employee, service, contract, contract details
        // EMPLOYEE được thay đổi password của mình
        http.authorizeRequests().antMatchers("/customer/view/*",
                                                        "/employee/view/*",
                                                        "/service/view/*",
                                                        "/contract/viewDetail/*"
                                                        )
                .access("hasRole('ROLE_EMPLOYEE')");

        // Khi người dùng đã login, với vai trò XX.
        // Nhưng truy cập vào trang yêu cầu vai trò YY,
        // Ngoại lệ AccessDeniedException sẽ ném ra.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        // Cấu hình cho Login Form.
        http.authorizeRequests().and().formLogin()//
                // Submit URL của trang login
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/login")//
                .defaultSuccessUrl("/home")//
                .failureUrl("/login/?error=true")//
                .usernameParameter("username")//
                .passwordParameter("password")
                // Cấu hình cho Logout Page.
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");

        // Cấu hình Remember Me.
        http.authorizeRequests().and() //
                .rememberMe().tokenRepository(this.persistentTokenRepository()) //
                .tokenValiditySeconds(24 * 60 * 60); // 24h

    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

}
