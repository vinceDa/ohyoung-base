package com.ohyoung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author vince
 */
@SpringBootApplication
@EnableSwagger2
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.ohyoung.SystemApplication.class, args);
    }
}
