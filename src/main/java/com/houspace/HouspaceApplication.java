package com.houspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // 잠시 로그인 기능 비활성화
@SpringBootApplication
@EnableJpaAuditing
public class HouspaceApplication {

    public static void main(String[] args) {

        SpringApplication.run(HouspaceApplication.class, args);
    }

}
