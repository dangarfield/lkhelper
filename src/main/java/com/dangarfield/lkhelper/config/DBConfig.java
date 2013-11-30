package com.dangarfield.lkhelper.config;

import org.jongo.Jongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.Mongo;

@Configuration
public class DBConfig {

	@Autowired
	private Mongo mongo;
	@Autowired
	private String mongoDBName;
	
	@Bean
    Jongo jongo() {
		return new Jongo(mongo.getDB(mongoDBName));
    }
}
