package com.project.s3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AwsS3Application {

    public static void main(String[] args) {
        SpringApplication.run(AwsS3Application.class, args);
    }

}
