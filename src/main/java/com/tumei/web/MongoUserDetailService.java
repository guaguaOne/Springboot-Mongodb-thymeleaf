package com.tumei.web;

import com.tumei.web.model.SecUserBean;
import com.tumei.web.model.SecUserBeanRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    private Log log = LogFactory.getLog(MongoUserDetailService.class);

    @Autowired
    private SecUserBeanRepository secUserBeanRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SecUserBean userBean = secUserBeanRepository.findByAccount(s);
        if (userBean == null) {
            throw new UsernameNotFoundException("not found");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        log.info(" 增加权限: " + userBean.getRole());

        authorities.add(new SimpleGrantedAuthority(userBean.getRole()));
        return new User(userBean.getAccount(), userBean.getPasswd(), authorities);
    }
}
