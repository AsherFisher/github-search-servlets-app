package ex3.asher.controller;

import ex3.asher.NameAndPassword;
import ex3.asher.beans.SessionBoolean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** * User Controller */
@Controller
public class UserController {

    @Value("${spring.security.user.name}")
    private String username;    //username = admin

    @Value("${spring.security.user.password}")
    private String password;    //password = 1234

    /**
     * GetMapping GET home request
     * @param request request
     * @param response response
     * @return login.html
     * @throws IOException IOException
     */
    @GetMapping("/")
    public String main(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionBoolean b = (SessionBoolean) request.getSession().getAttribute("SessionBoolean");

        // check if the user already logged in, to redirect to search
        if (b.getSessionBoolean()) {
            response.sendRedirect("/search");
            return null;
        }
        return "login";
    }

    /**
     * GetMapping of /login
     * @param request request
     * @param response response
     * @return login.html
     * @throws IOException IOException
     */
    @GetMapping("/login")
    public String getLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionBoolean b = (SessionBoolean) request.getSession().getAttribute("SessionBoolean");

        // check if the user already logged in, to redirect to search
         if (b.getSessionBoolean()) {
            response.sendRedirect("/search");
            return null;
        }
        return "login";
    }

    /**
     * PostMapping of /login
     * @param namePassword a class of to fields String name and String password
     * @param model model
     * @param request request
     * @param response response
     * @return if logged in Redirect to /search, if no logged in return login.html
     * @throws IOException IOException
     */
    @PostMapping("/login")
    public String postLogin(NameAndPassword namePassword, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionBoolean b = (SessionBoolean) request.getSession().getAttribute("SessionBoolean");

        // check if the user already logged in, to redirect to search
        if (b.getSessionBoolean()) {
            response.sendRedirect("/search");
            return null;
        }

        // check if user name and password is correct to Redirect /search
        if (namePassword.getName().equals(username) && namePassword.getPassword().equals(password)){
            request.getSession().setAttribute("SessionBoolean",new SessionBoolean(true));
            response.sendRedirect("/search");
            return null;
        }
        // if the user name not send correct user name or password return login.html + errMeg
        model.addAttribute("errMsg", "username or password is incorrect");
        return "login";
    }

    /**
     * RequestMapping get the GET and POST of /logout
     * @param request request
     * @return back to login page
     */
    @RequestMapping("/logout")
    public String getLogOut(HttpServletRequest request) {
        // turn of the boolean of session
        request.getSession().setAttribute("SessionBoolean",new SessionBoolean(false));
        return "login";
    }
}


