package com.tumei.web;
import com.tumei.web.model.SecUserBeanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.PostConstruct;

/**
 * Created by leon on 2016/11/5.
 */
@Configuration
@EnableWebSecurity // 开启安全校验
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    SecUserBeanRepository userBeanRepository;

    @PostConstruct
    public void dataInit() {
//        SecUserBean admin = new SecUserBean();
//        admin.setPasswd("leon");
//        admin.setAccount("leon");
//        admin.setRole(ROLE.ADIMN);
//        admin.setCreatetime(DateTime.now());
//        userBeanRepository.save(admin);
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new MongoUserDetailService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // 只对
//        http.antMatcher("/yxwd");
        http.authorizeRequests() // 定义哪些url需要被保护，哪些不需要
            .antMatchers("/login","/register","/image/*","/newuser","/css/*","/js/*","/css/admin/*").permitAll() // / 和 /home是可以直接访问的
//            .antMatchers("/yxwd").hasAnyRole("YXWD", "ADMIN", "OWNER")
//            .antMatchers("/xxkg").hasAnyAuthority("XXKG", "ADMIN", "OWNER")
//            .antMatchers("/management").access("hasAnyRole('ADMIN', 'OWNER')")
            .anyRequest().authenticated() // 其他都需要验证
            .and()
            .formLogin().loginPage("/login") // 需要用户登录的时候,路由到/login下
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
//        auth.inMemoryAuthentication().withUser("leon").password("fuckyou").roles("USER"); // 在内存中的定义一个角色的帐号密码
    }
}
