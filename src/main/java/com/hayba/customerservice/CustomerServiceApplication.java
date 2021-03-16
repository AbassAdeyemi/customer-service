package com.hayba.customerservice;

import com.hayba.customerservice.entity.Customer;
import com.hayba.customerservice.repository.CustomerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	ApplicationListener<ApplicationReadyEvent> ready(DatabaseClient dbClient, CustomerRepository repository) {
		return event -> {
			var ddl = dbClient.sql("create table if not exists customer(id serial primary key, name varchar(255) not null)")
					.fetch()
					.rowsUpdated();
			var saved = Flux.just("Ade", "Ola", "Yemi")
					.map(name -> new Customer(null, name))
					.flatMap(repository::save);

			ddl.thenMany(saved).subscribe(System.out::println);
		};
	}
}
