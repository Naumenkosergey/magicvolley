package ru.magicvolley.config;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@Profile("development")
public class DataSourceDevelopmentConfig {

    @Bean(name = "masterDataSource")
    public DataSource dataSource(@Value("${server.port}") int port) throws IOException {

        return EmbeddedPostgres.builder()
                .setPort(port + 1000)
                .start()
                .getPostgresDatabase();
    }
}