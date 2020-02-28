package xiong.o2o;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ShiroTests {
    SimpleAccountRealm accountRealm = new SimpleAccountRealm();

    @BeforeEach
    public void addUser() {
        accountRealm.addAccount("hello", "world");
    }

    @Test
    public void testAuthentication() {
        // 构建SecurityManager
        DefaultSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(accountRealm);

        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("hello", "world");
        subject.login(token);

        assertTrue(subject.isAuthenticated());

        subject.logout();
        assertFalse(subject.isAuthenticated());
    }
}
