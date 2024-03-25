package com.course.practicaljava.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
	public class MyElasticsearchConfig extends ElasticsearchConfiguration {

	  @Override
	  public ClientConfiguration clientConfiguration() {
	    return ClientConfiguration.builder().connectedTo("localhost:9200")
//	        .usingSsl("52278befda0aa19f96daf638075e3edb011c1eb522522d22f8c6b11f5fa8d0b1")
	        .withBasicAuth("elastic", "mgd9H_*OB6i3Y9Hi9tW-").build();
	  }

	}

