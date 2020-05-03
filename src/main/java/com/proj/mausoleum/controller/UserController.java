package com.proj.mausoleum.controller;

import com.proj.mausoleum.pojo.User;
import com.proj.mausoleum.pojo.User;
import com.proj.mausoleum.result.CodeMsg;
import com.proj.mausoleum.result.Result;
import com.proj.mausoleum.service.UserService;
import com.proj.mausoleum.utils.Captcha;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/changeCode")
    public void getIdentifyingCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 验证码存储在session的identifyingCode，属性中
        Captcha.outputCaptcha(request, response);
    }

    @RequestMapping(value = "/register", method = POST)
    public Result register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("email") String email,
                           @RequestParam("identifyCode") String identifyCode,
                           @RequestParam(value = "role") Integer role,
                           HttpSession session) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(email) || StringUtils.isEmpty(identifyCode) || role == null) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        String code = (String) session.getAttribute("code");
        if (!identifyCode.equals(code)) {
            return Result.error(CodeMsg.CODE_ERROR);
        }
        User user = new User();
        user.setUsername(username);
        if (userService.getUser(user) != null) {
            return Result.error(CodeMsg.USERNAME_EXISTS);
        }
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);
        if(userService.saveUser(user)) {
            return Result.success(user);
        } else {
            return Result.error(CodeMsg.REGISTER_FAIL);
        }
    }

    @RequestMapping(value = "/login", method = POST)
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("identifyCode") String identifyCode,
                        HttpServletRequest request,
                        HttpSession session) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(identifyCode)) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        String code = (String) session.getAttribute("code");
        if (!identifyCode.equals(code)) {
            return Result.error(CodeMsg.CODE_ERROR);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User user1 = userService.getUser(user);
        if (user1 == null) {
            return Result.error(CodeMsg.UNPW_ERROR);
        }
        session = request.getSession();
        session.setAttribute("loginUser", user1);
        return Result.success(null);
    }

    @RequestMapping("/getSession")
    public Result getSession(HttpSession session) {
        User loginUser = userService.getSession(session);
        if (loginUser == null) {
            return Result.error(CodeMsg.SESSION_NOTEXISTS);
        }
        return Result.success(loginUser);
    }

    @RequestMapping("/logout")
    public void logout(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser != null) {
            session.removeAttribute("loginUser");
        }
    }

    @RequestMapping(value = "/modify", method = POST)
    public Result modifyPassword(@RequestParam(value = "username", required = false) String username,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("identifyCode") String identifyCode,
                                 HttpSession session) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password) || StringUtils.isEmpty(identifyCode)) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        String code = (String) session.getAttribute("code");
        if (!identifyCode.equals(code)) {
            return Result.error(CodeMsg.CODE_ERROR);
        }
        User user = new User();
        if (username == null) {
            User user1 = userService.getSession(session);
            if (user1 == null) {
                return Result.error(CodeMsg.SESSION_NOTEXISTS);
            } else {
                user.setUsername(user1.getUsername());
            }
        } else {
            user.setUsername(username);
        }
        user.setEmail(email);
        if (userService.getUser(user) == null) {
            return Result.error(CodeMsg.UNEM_ERROR);
        }
        user.setPassword(password);
        if (userService.modifyUser(user)) {
            return Result.success(user);
        } else {
            return Result.error(CodeMsg.REGISTER_FAIL);
        }
    }

    @RequestMapping(value = "/getList", method = GET)
    public Result getUserList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<User> userList = userService.getUserList(page, limit);
        Integer count = userService.getUserListCount();
        return Result.page(userList, count);
    }

    @RequestMapping(value = "/destroy", method = POST)
    public Result destroyUser(@RequestParam("userId") Integer userId,
                               HttpSession session) {
        if(userId == null) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        User user = userService.getSession(session);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_NOTEXISTS);
        }
        if(userService.destroyUser(userId)) {
            return Result.success(null);
        } else {
            return Result.error(CodeMsg.VIDEODESTROY_FAIL);
        }
    }


}
