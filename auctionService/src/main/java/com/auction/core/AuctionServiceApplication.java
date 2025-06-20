package com.auction.core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan("org.auction")
@ComponentScan(basePackages = {"com.auction.core","org.auction.common"})
@EnableJpaRepositories(basePackages = "org.auction.*")
public class AuctionServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(AuctionServiceApplication.class, args);
	}

}
