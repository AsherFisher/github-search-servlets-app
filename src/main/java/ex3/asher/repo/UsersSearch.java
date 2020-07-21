package ex3.asher.repo;

import javax.persistence.*;

/**
 * UsersSearch Entity bean class
 */
@Entity
public class UsersSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // unique name
    @Column(unique = true)
    private String name;

    // counter
    private int userCount = 1;
    private String userLink;

    // constructors
    public UsersSearch() {}

    public UsersSearch(String name) {
        this.name = name;
        this.userLink = "https://github.com/"+name+"/";
    }

    public UsersSearch(String name, int userCount) {
        this.name = name;
        this.userCount = userCount;
        this.userLink = "https://github.com/"+name+"/";
    }

    // getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int count) {
        this.userCount = count;
    }

    public String getUserLink() {
        return userLink;
    }

    public void setUserLink(String userLink) {
        this.userLink = userLink;
    }

    /**
     * Override func toString
     * @return string of user fields
     */
    @Override
    public String toString() {
        return "UsersSearch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userCount=" + userCount +
                ", userLink='" + userLink + '\'' +
                '}';
    }
}

