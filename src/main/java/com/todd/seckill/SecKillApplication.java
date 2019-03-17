package com.todd.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动项
 * @author Todd
 * @date 2019-03-16 23:25
 */
@SpringBootApplication
@MapperScan("com.todd.seckill.dao")
public class SecKillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecKillApplication.class, args);
    }
}
