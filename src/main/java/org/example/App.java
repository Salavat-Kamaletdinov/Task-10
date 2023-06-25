package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        Communication communication = context.getBean("communication", Communication.class);
        System.out.println("Answer: " + communication.allMethods());
    }
}
