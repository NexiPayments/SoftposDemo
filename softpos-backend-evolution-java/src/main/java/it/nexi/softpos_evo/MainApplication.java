package it.nexi.softpos_evo;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 *
 * @author Nexi Payments 
 */
@SpringBootApplication(exclude = {
    SecurityAutoConfiguration.class
})
public class MainApplication {

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        SpringApplication.run(MainApplication.class, args);
    }

}
