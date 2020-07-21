package ex3.asher.controller;

import ex3.asher.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * History Controller
 */
@Controller
public class HistoryController {

    /* we need it so inject the User repo bean */
    @Autowired
    private UserRepository repository;

    /** * @return repository */
    private UserRepository getRepo() {
        return repository;
    }

    /**
     * RequestMapping func, get the request of GET and POST of history
     * @param model model to add the users by repository
     * @return history.html
     */
    @RequestMapping("/history")
    public String History(Model model) {
        model.addAttribute("users", getRepo().findFirst10ByOrderByUserCountDesc());
        return "history";
    }

    /**
     * RequestMapping func, get the request of GET and POST of clearHistory
     * @param model model to delete All users of repository
     * @return history.html
     */
    @RequestMapping("/clearHistory")
    public String clearHistory(Model model) {
        getRepo().deleteAll();
        model.addAttribute("users", getRepo().findFirst10ByOrderByUserCountDesc());
        return "history";
    }
}
