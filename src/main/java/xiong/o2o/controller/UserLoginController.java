package xiong.o2o.controller;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;
import xiong.o2o.entity.User;
import xiong.o2o.exception.InvalidParamException;
import xiong.o2o.exception.UnauthorizatedException;
import xiong.o2o.mapper.UserMapper;
import xiong.o2o.shiro.JWTUtil;
import xiong.o2o.util.OutputResult;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.FileSystemNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

@Data
class SigninReturnData {
    final String token;
}

@RestController
public class UserLoginController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @PostMapping("/sendVerifyCode")
    OutputResult sendVerifyCode(@RequestParam String email) {
        int randomNum = (int)Math.floor(Math.random() * 1000000);
        String numStr = String.format("%06d", new Integer(randomNum));
        System.out.println(String.format("For email %s, get verification code %s", email, numStr));

        redisTemplate.opsForValue().set("code." + email, numStr);
        redisTemplate.expire("code." + email, 5, TimeUnit.MINUTES);
        // request.getSession().setAttribute("email", email);
        // request.getSession().setAttribute("verificationCode", numStr);
        return new OutputResult(null);
    }

    @PostMapping("/signup")
    OutputResult signup(@RequestBody SignupInfoData loginInfo) {
        if (loginInfo.getEmail() == null) {
            throw new InvalidParamException("The email is not valid.");
        }

        String existCode = (String)redisTemplate.opsForValue().get("code." + loginInfo.getEmail());

        if (existCode == null || loginInfo.getVerificationCode() == null || !existCode.equals(loginInfo.getVerificationCode())) {
            throw new InvalidParamException("The verification is not valid.");
        }

        User user = new User();
        user.setName(loginInfo.getUsername());
        // 对密码进行md5哈希
        String password = loginInfo.getPassword();
        try {
            byte[] originPwd = password.getBytes();
            byte[] hashPwd = MessageDigest.getInstance("md5").digest(originPwd);
            String pwdStr = Base64.getEncoder().encodeToString(hashPwd);
            user.setPassword(pwdStr);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e);
        }

        user.setEmail(loginInfo.getEmail());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insert(user);
        assert user.getId() != null;

        String token = JWTUtil.sign(user.getId().toString(), "123456");
        // request.getSession().setAttribute("loginUser", user.getId());
        // request.getSession().setAttribute("username", loginInfo.);
        return new OutputResult(new SigninReturnData(token));
    }

    @PostMapping("/login")
    OutputResult login(@RequestBody LoginInfoData loginInfo) {
        String username = loginInfo.getUsername();

        String encodePwd = "";
        try {
            byte[] pwdBytes = loginInfo.getPassword().getBytes();
            byte[] encodeBytes = MessageDigest.getInstance("md5").digest(pwdBytes);
            encodePwd = Base64.getEncoder().encodeToString(encodeBytes);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e);
        }

        QueryWrapper wrapper = new QueryWrapper<User>();
        wrapper.eq("name", loginInfo.getUsername());
        logger.info("select user sql", wrapper.getSqlSegment());
        User user = userMapper.selectOne(wrapper);

        if (!user.getPassword().equals(encodePwd)) {
            throw new UnauthorizatedException("Username or password is incorrect");
        }

        String token = JWTUtil.sign(user.getId().toString(), "123456");
        return new OutputResult(new SigninReturnData(token));
    }
}
