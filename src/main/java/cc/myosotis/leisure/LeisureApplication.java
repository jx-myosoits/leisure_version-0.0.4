package cc.myosotis.leisure;

import cc.myosotis.leisure.util.Register;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeisureApplication {

    public static Register register;

    static {
        register = new Register();
    }

    public static void main(String[] args) {
        SpringApplication.run(LeisureApplication.class, args);
    }
}
