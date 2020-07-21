package ex3.asher.filters;
import ex3.asher.beans.SessionBoolean;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * this class is a filter, intercepts all requests to access denied if no logged in
 */
public class LoggingInterceptor implements HandlerInterceptor {

    @Resource(name = "session")
    private SessionBoolean sessionBoolean;

    /**
     * preHandle intercepts all requests
     * @param request request
     * @param response response
     * @param handler handler
     * @return true to continue and false to denied
     * @throws Exception Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        sessionBoolean = (SessionBoolean) request.getSession().getAttribute("SessionBoolean");
        String loginURL = "/login";
        String searchURL = "/search";
        String URI = request.getRequestURI();

        // if the user is new create a new sessionBoolean bean class
        if(sessionBoolean == null){
            sessionBoolean = new SessionBoolean();
            request.getSession().setAttribute("SessionBoolean", sessionBoolean);
        }

        // if the user session Boolean is false, and the Request URI == '/search'
        if(!sessionBoolean.getSessionBoolean() && searchURL.equals(URI)){
            // allow the user to get to search page, because i choice to use Ajax/JSON by search
            return true;
        }

        // if the user session Boolean is false, and the Request URI != '/login'
        if(!sessionBoolean.getSessionBoolean() && !loginURL.equals(URI)){
            // Redirect to login page
            response.sendRedirect("/login");
            return false;
        }
        // if the user session Boolean is true, allow and return true
        return true;
    }
}
