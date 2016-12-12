package com.tumei.web;

import com.tumei.web.model.SecUserBean;
import com.tumei.web.model.SecUserBeanRepository;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leon on 2016/12/12.
 */
public class MongoUserDetailService implements UserDetailsService {
    @Autowired
    private SecUserBeanRepository secUserBeanRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SecUserBean userBean = secUserBeanRepository.findByAccount(s);
        if (userBean == null) {
            throw new UsernameNotFoundException("not found");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userBean.getRole().name()));
        return new User(userBean.getAccount(), userBean.getPasswd(), authorities);
    }
}
