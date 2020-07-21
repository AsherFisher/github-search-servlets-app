package ex3.asher;

import ex3.asher.beans.SessionBoolean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
public class AsherApplication {

    /**
     * Bean Scope session
     * @return new SessionBoolean
     */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)

    public SessionBoolean session () {
        return new SessionBoolean();
    }

    /**
     * main
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(AsherApplication.class, args);
    }

}
