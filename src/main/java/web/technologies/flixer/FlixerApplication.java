package web.technologies.flixer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class FlixerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlixerApplication.class, args);
    }
}