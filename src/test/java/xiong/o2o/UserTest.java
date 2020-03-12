package xiong.o2o;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        User myUser = new User(null, "test@test", "pass", 12, "l@x");

        userMapper.insert(myUser);
        QueryWrapper<User> userQuery = new QueryWrapper<>();
        userQuery.eq("name", "test@test");
        List<User> userList = userMapper.selectList(userQuery);
        Assertions.assertEquals(1, userList.size());

        userMapper.deleteById(myUser.getId());
        // userList.forEach(System.out::println);

         Assertions.assertEquals(userList.get(0).getName(), myUser.getName());
    }
}