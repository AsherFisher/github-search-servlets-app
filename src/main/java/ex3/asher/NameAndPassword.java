package ex3.asher;

/**
 * NameAndPassword class, put in the input name and password from the user
 */
public class NameAndPassword {

    private String name;
    private String password;

    //constructor
    public NameAndPassword(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
