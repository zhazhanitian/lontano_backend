package cn.pledge.envconsole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class EnvConsoleApplication {

  public static void main(String[] args) {
    SpringApplication.run(EnvConsoleApplication.class, args);
  }
}
