package ru.magicvolley.config;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class DataSourceConfig {

    @Bean(name = "masterDataSource")
    public DataSource dataSource(@Value("${server.port}") int port) throws IOException {

        return EmbeddedPostgres.builder()
                .setPort(port + 1000)
                .start()
                .getPostgresDatabase();
    }
}
