package xiong.o2o.controller;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;
import xiong.o2o.entity.User;
import xiong.o2o.exception.InvalidParamException;
import xiong.o2o.exception.UnauthorizatedException;
import xiong.o2o.mapper.UserMapper;
import xiong.o2o.util.OutputResult;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.FileSystemNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;

@Data
class LoginInfoData {
    String username;
    String password;
}

@Data
class SignupInfoData {
    String username;
    String password;
    String email;
    String verificationCode;
}

@RestController
public class UserLoginController {

    @Autowired
    UserMapper userMapper;

    Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @PostMapping("/sendVerifyCode")
    OutputResult sendVerifyCode(@RequestParam String email, HttpServletRequest request) {
        int randomNum = (int)Math.floor(Math.random() * 1000000);
        String numStr = String.format("%06d", new Integer(randomNum));
        System.out.println(String.format("For email %s, get verification code %s", email, numStr));
        request.getSession().setAttribute("email", email);
        request.getSession().setAttribute("verificationCode", numStr);
        return new OutputResult(null);
    }

    @PostMapping("/signup")
    OutputResult signup(@RequestBody SignupInfoData loginInfo, HttpServletRequest request) {
        if (!request.getSession().getAttribute("email").equals(loginInfo.getEmail())) {
            throw new InvalidParamException("The verification is not valid, please retry to get it.");
        }
        String code = (String)request.getSession().getAttribute("verificationCode");
        if (!code.equals(loginInfo.getVerificationCode())) {
            throw new InvalidParamException("The verification is not valid.");
        }
        User user = new User();
        user.setName(loginInfo.getUsername());
        // 对密码进行md5哈希
        String password = loginInfo.getPassword();
        try {
            byte[] hashPwd = MessageDigest.getInstance("md5").digest(password.getBytes());
            user.setPassword(hashPwd.toString());
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e);
        }

        user.setEmail(loginInfo.getEmail());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insert(user);
        assert user.getId() != null;

        request.getSession().setAttribute("loginUser", user.getId());
        // request.getSession().setAttribute("username", loginInfo.);
        return new OutputResult(null);
    }

    @PostMapping("/login")
    OutputResult login(@RequestBody LoginInfoData loginInfo, HttpServletRequest request) {
        String username = loginInfo.getUsername();

        String encodePwd = "";
        try {
            encodePwd = MessageDigest.getInstance("md5").digest(loginInfo.getPassword().getBytes()).toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e);
        }

        QueryWrapper wrapper = new QueryWrapper<User>();
        wrapper.eq("username", loginInfo.getUsername());
        logger.info("select user sql", wrapper.getSqlSelect());
        User user = userMapper.selectOne(wrapper);

        if (!user.getPassword().equals(encodePwd)) {
            throw new UnauthorizatedException("Username or password is incorrect");
        }
        request.getSession().setAttribute("loginUser", user.getId());
        return new OutputResult(null);
    }

    @GetMapping("/greeting")
    public String greeting() {
        return "hello world";
    }
}
