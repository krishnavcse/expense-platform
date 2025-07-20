package com.demo.expense.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.demo.expense")
@EnableJpaRepositories(basePackages = {
        "com.demo.expense.user.repository",
        "com.demo.expense.expense.repository"
})
@EntityScan(basePackages = {
        "com.demo.expense.user.domain",
        "com.demo.expense.expense.domain"
})
public class ExpensePlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExpensePlatformApplication.class, args);
    }
}