package com.tumei.web.controller.platform;

import com.tumei.web.model.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 *
 * web平台帐号相关操作
 *
 */
@RestController
@RequestMapping({"/management"})
public class WebController {
    private Log log = LogFactory.getLog(WebController.class);

    @Autowired
    public SecUserBeanRepository repository;

    class ReturnVal {
        public int code;
        public String msg;

        public ReturnVal(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /***
     * 注册一个web平台的帐号
     * @param account
     * @return
     */
    @ApiOperation(value = "注册用户", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "帐号", required = true, dataType = "String",
                    paramType = "query"),
            @ApiImplicitParam(name = "passwd", value = "密码", required = true, dataType = "String",
                    paramType = "query")
    })
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ReturnVal Register(@RequestParam String account, @RequestParam String passwd ) {
        log.info("注册帐号:" + account + ", " + passwd);

        // 对注册帐号函数进行保护，时间和IP间隔
        SecUserBean bean = repository.findByAccount(account);
        if (bean != null)
        {
            return new ReturnVal(-1, "已经存在相同的帐号名.");
        }
        else
        {
            bean = new SecUserBean();
            bean.setAccount(account);
            bean.setPasswd(passwd);
            bean.setRole(ROLE.USER);
            bean.setCreatetime(DateTime.now());
            repository.save(bean);
        }

        return new ReturnVal(0, "注册成功");
    }

    @ApiOperation(value = "根据指定的页数（从0开始），和每页展示的条目数，获取当前已经注册的帐号信息", notes = "")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Long",
            paramType = "query", defaultValue = "0"),
    @ApiImplicitParam(name = "size", value = "每页显示的条目", required = true, dataType = "int",
            paramType = "query", defaultValue = "10")
    })
    @RequestMapping(value = "/getAccounts", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<SecUserBean> getAccounts(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "15") Integer size , ModelMap map) {
//        SecurityContext sc = SecurityContextHolder.getContext();

        // 对注册帐号函数进行保护，时间和IP间隔
        Sort sort = new Sort(Sort.Direction.DESC, "account");
        Pageable pageable = new PageRequest(page, size, sort);
        return repository.findAll(pageable);
    }

    @ApiOperation(value = "获取指定帐号的详细信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "帐号名", required = true, dataType = "String",
                    paramType = "query")
    })
    @RequestMapping(value = {"/getAccount"}, method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SecUserBean getAccount(@RequestParam(value = "account") String account ) {
        return repository.findByAccount(account);
    }


    @ApiOperation(value = "获取系统当前的所有权限", notes = "")
    @RequestMapping(value = "/getPrivileges", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ROLE[] getPrivileges() {
        return ROLE.values();
    }

    @ApiOperation(value = "设置指定帐号的对应权限", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "帐号名", required = true, dataType = "String",
                    paramType = "query"),
            @ApiImplicitParam(name = "auth", value = "权限", required = true, dataType = "String",
                    paramType = "query", defaultValue = "USER,ADMIN")
    })
    @RequestMapping(value = "/setAuth", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ReturnVal setAuth(@RequestParam String account, @RequestParam String auth) {
        SecUserBean userBean = repository.findByAccount(account);
        if (userBean == null) {
            return new ReturnVal(-1, "指定的帐号不存在");
        }

        userBean.setRole(auth);
        repository.save(userBean);
        return new ReturnVal(0, "设置权限成功.");
    }


}
