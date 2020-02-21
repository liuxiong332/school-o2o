package xiong.o2o.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Data
class LoginInfoData {
    String username;
    String password;
}

@RestController
public class UserLoginController {

    @PostMapping("/login")
    String login(@RequestBody LoginInfoData loginInfo) {
        return loginInfo.getUsername() + loginInfo.getPassword();
    }

    @GetMapping("/greeting")
    public String greeting() {
        return "hello world";
    }
}
