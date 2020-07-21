package ex3.asher.controller;

import ex3.asher.beans.SessionBoolean;
import ex3.asher.repo.UserRepository;
import ex3.asher.repo.UsersSearch;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/** Search Controller */
@Controller
public class SearchController {

    /* we need it so  inject the User repo bean */
    @Autowired
    private UserRepository repository;

    /** * @return repository */
    private UserRepository getRepo() {
        return repository;
    }

    // String status to return beak to the client a status in the json
    String status = "";

    // key for synchronized
    String key = "syncKey";

    /**
     * GetMapping search get the GET request of search
     * @param request request
     * @param response response
     * @return null if the session finished
     * @throws IOException IOException if session throws null pointer exception
     */
    @GetMapping("/search")
    public String getSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionBoolean b = (SessionBoolean) request.getSession().getAttribute("SessionBoolean");
        // check if session boolean is false
        if (!b.getSessionBoolean()) {
            response.sendRedirect("/login");
            return null;
        }
        return "search";
    }

    /**
     * PostMapping search get the POST request of search
     * @param name name from RequestBody to search
     * @param request request
     * @return return github json + status
     * @throws JSONException JSONException
     */
    @PostMapping(path = "/search", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public String postSearch(@RequestBody String name, HttpServletRequest request) throws JSONException {
        SessionBoolean b = (SessionBoolean) request.getSession().getAttribute("SessionBoolean");
        // check if session boolean is false
        if (!b.getSessionBoolean()) {
            status = "[{\"status\": \"noSession\"}]";
            return status;
        }

        // new JSONObject for response
        JSONObject jsonObject = new JSONObject(name);
        String theName = jsonObject.getString("name");

        // if the user send a empty name
        if (theName.equals("")){
            status = "[{\"status\": \"empty\"}]";
            return status;
        }

        // url
        String url = "https://api.github.com/users/"+theName+"/followers";
        status = "{\"status\": \"\"}";
        String data = null;
        try {
            data = readUrl(url);
        } catch (IOException e) {
            // if the user name If the name is not exist in github
            status = "[{\"status\": \"notExist\"}]";
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // for return json
        JSONArray jsonArr = null;
        try {
            jsonArr = new JSONArray(data);
        } catch (JSONException | NullPointerException e) {
            return status;
        }
        try {
            jsonArr.put(new JSONObject(status));
        } catch (JSONException e) { }

        // synchronized the add to the database
        synchronized (key)
        {
            // if the user name exist in the repository, update the count to be count++
            if (!getRepo().findByName(theName).isEmpty()) {
                UsersSearch temp = new UsersSearch (theName,getRepo().findByName(theName).get(0).getUserCount()+1);
                getRepo().delete(getRepo().findByName(theName).get(0));
                getRepo().save(temp);
            } else {
                // if the user name is not exist in the repository, create new
                UsersSearch user = new UsersSearch (theName);
                getRepo().save(user);
            }
        }
        // returns the json including the status
        return jsonArr.toString();
    }

    /**
     * this func read the Url and return by String
     * @param urlString url String
     * @return return the json from github
     * @throws IOException if the url is not exist
     */
    private String readUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        BufferedReader readURL = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while((line = readURL.readLine()) != null){
            builder.append(line);
        }
        readURL.close();
        return builder.toString();
    }
}
