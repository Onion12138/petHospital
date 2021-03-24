package com.ecnu.six.pethospital;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.ecnu.six.pethospital.oauth.mapper"})
public class PethospitalApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(PethospitalApplication.class);
        springApplication.run(args);
    }

}
