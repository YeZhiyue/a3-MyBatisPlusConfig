package com.example.mybatispluscodegeneral;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example")
public class MybatisPlusCodeGeneralApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusCodeGeneralApplication.class, args);
    }
}
