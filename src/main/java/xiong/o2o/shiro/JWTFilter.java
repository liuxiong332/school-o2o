package xiong.o2o.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class JWTFilter extends BasicHttpAuthenticationFilter {
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String authstr = httpRequest.getHeader("Authorization");
        return authstr != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String authstr = httpRequest.getHeader("Authorization");
        JWTToken token = new JWTToken(authstr);
        Subject currentUser = getSubject(request, response);
        currentUser.login(token);

        String username = JWTUtil.getUserName(authstr);
        request.setAttribute("currentUser", username);
        return true;
    }
}
