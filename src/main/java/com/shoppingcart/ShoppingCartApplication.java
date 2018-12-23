package com.shoppingcart;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootApplication(scanBasePackages = "com.shoppingcart")
public class ShoppingCartApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}	

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource h2DataSource) {
		
		return new NamedParameterJdbcTemplate(h2DataSource);
	}
	
	@Bean (name="h2DataSource")	
	public DataSource datasource() {
		return new EmbeddedDatabaseBuilder()				
				.setType(EmbeddedDatabaseType.H2)
				.addScript("/sql/import-h2.sql")
				.build();
		
	}
}
