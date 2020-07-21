package ex3.asher.beans;

import org.springframework.stereotype.Component;
import java.io.Serializable;


/**
 * SessionBoolean is a session bean local flag what turn on when the user logged in
 */
@Component
public class SessionBoolean implements Serializable {

    // boolean flag
    private boolean sessionBoolean;

    // getters and setters
    public SessionBoolean() {
        this.sessionBoolean = false;
    }

    public SessionBoolean(boolean b) {
        this.sessionBoolean = b;
    }

    public boolean getSessionBoolean() {
        return this.sessionBoolean;
    }

    public void setSessionBoolean(boolean b) {
        this.sessionBoolean = b;
    }

}
