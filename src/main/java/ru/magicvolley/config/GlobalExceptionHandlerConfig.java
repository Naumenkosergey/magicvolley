package ru.magicvolley.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.magicvolley.exceptionhendler.GlobalExceptionHandler;

@Configuration
public class GlobalExceptionHandlerConfig {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
