package it.nexi.softpos_cloud_auth;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 *
 * @author Nexi Payments 
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     *
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainApplication.class);
    }

}
