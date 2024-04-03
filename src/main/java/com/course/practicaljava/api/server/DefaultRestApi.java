package com.course.practicaljava.api.server;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api")
public class DefaultRestApi {
	
	private static final Logger LOG=LoggerFactory.getLogger(DefaultRestApi.class);
	
	@GetMapping("/welcome")
	public String welcome() {
		var text=StringUtils.join("Hello,", "this is", "Spring Boot", "Rest API");
		LOG.info(text);
		return "Welcome to Spring Boot";
	}
	
	@GetMapping("/time")
	public String time() {
		return LocalDate.now().toString();
	}
	
	@GetMapping(value = "/header-one", produces = MediaType.TEXT_PLAIN_VALUE)
	public String headersByAnnotation(@RequestHeader(name=HttpHeaders.USER_AGENT) String headerUserAgent, 
			@RequestHeader(name="Practical-Java", required = false) String headerPracticalJava) {
		return headerUserAgent+" "+headerPracticalJava;
	}
	
	@GetMapping(value = "/header-two", produces = MediaType.TEXT_PLAIN_VALUE)
	public String headerByRequset(HttpServletRequest request) {
		StringBuilder sb=new StringBuilder();
		var headerUserAgent=request.getHeaders(HttpHeaders.USER_AGENT);
		var headerPracticalJava=request.getHeaders("Practical-Java");
		sb.append(HttpHeaders.USER_AGENT + "\n");
		
		while (headerUserAgent.hasMoreElements()) {
			sb.append(headerUserAgent.nextElement());
			sb.append("\n");
		}
		
		sb.append("Practical-Java" + "\n");
		while(headerPracticalJava.hasMoreElements()) {
			sb.append(headerPracticalJava.nextElement());
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
