package com.ecnu.six.pethospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PethospitalApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(PethospitalApplication.class);
        springApplication.run(args);
    }

}
