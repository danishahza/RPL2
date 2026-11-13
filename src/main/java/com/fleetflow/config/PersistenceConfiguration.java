package com.fleetflow.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {

	@Bean
	DataSource dataSource(@Value("${db.url}") String url, @Value("${db.username}") String username,
			@Value("${db.password}") String password) {
		return DataSourceBuilder.create()
				.url(url)
				.username(username)
				.password(password)
				.driverClassName("org.postgresql.Driver")
				.build();
	}
}
