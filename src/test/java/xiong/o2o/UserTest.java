package xiong.o2o;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.jvm.hotspot.utilities.Assert;
import xiong.o2o.entity.User;
import xiong.o2o.mapper.UserMapper;

import java.util.List;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        userMapper.deleteById(1L);
        User myUser = new User(1L, "hello", "pass", 12, "l@x");
        User myUser2 = new User(1L, "hello", "pass", 12, "l@x");

        userMapper.insert(myUser);
        List<User> userList = userMapper.selectList(null);
        Assertions.assertEquals(1, userList.size());
        // userList.forEach(System.out::println);

         Assertions.assertEquals(userList.get(0).getName(), myUser.getName());
    }
}