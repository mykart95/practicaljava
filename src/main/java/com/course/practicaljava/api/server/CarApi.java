package com.course.practicaljava.api.server;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.practicaljava.api.response.ErrorResponse;
import com.course.practicaljava.entity.Car;
import com.course.practicaljava.repository.CarElasticRepository;
import com.course.practicaljava.service.CarService;

@RestController
@RequestMapping("/api/car/v1")
public class CarApi {

	@Autowired
	private CarService carService;
	
	@Autowired
	private CarElasticRepository carElasticRepository;
	
	private static final Logger LOG=LoggerFactory.getLogger(CarApi.class); 
	
	@GetMapping(value = "/random", produces = MediaType.APPLICATION_JSON_VALUE)
	public Car random() {
		return carService.generateCar();
	}
	
	@PostMapping(value="/echo", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String echo(@RequestBody Car car) {
		LOG.info("Car is : "+car);
		return car.toString();
		
	}
	
	@GetMapping(value = "/random-collection", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Car> randomCarArray(){
		var result=new ArrayList<Car>();
		
		for(int i=0; i<ThreadLocalRandom.current().nextInt(1, 6); i++) {
			result.add(carService.generateCar());
		}
		
		return result;
	}
	
	@GetMapping(value = "/count", produces = MediaType.TEXT_PLAIN_VALUE)
	public String countCar() {
		return "There are "+ carElasticRepository.count() +"cars";
	}
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String createNewCar(@RequestBody Car car) {
		 var newCar = carElasticRepository.save(car);
		 return "Saved with ID: " + newCar.getId();
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Car findCarByID(@PathVariable("id") String carId) {
		return carElasticRepository.findById(carId).orElse(null);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String UpdateCar(@PathVariable("id") String carId, @RequestBody Car updatedCar) {
		updatedCar.setId(carId);
		var updatedCarOnElasticsearch = carElasticRepository.save(updatedCar);
		return "Updated car with ID " +updatedCarOnElasticsearch.getId();
		
	}
	
	@GetMapping(value = "/find-json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Car> findCarByBrandAndColor(@RequestBody Car car, 
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10")int size){
		var pageable=PageRequest.of(page, size, Sort.by(Direction.DESC, "price"));
		return carElasticRepository.findByBrandAndColor(car.getBrand(), car.getColor(), pageable).getContent();
	}
	
	@GetMapping(value = "/cars/{brand}/{color}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findCarsByPath(@PathVariable("brand") String brand, 
			@PathVariable("color") String color,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10")int size) {
		
		var headers=new HttpHeaders();
		headers.add(HttpHeaders.SERVER, "Spring");
		headers.add("X-Custom", "Custom response header");
		
			if(StringUtils.isNumeric(color)) {
				var errorResponse=new ErrorResponse("Invalid color", ZonedDateTime.now());
				return new ResponseEntity(errorResponse, null, HttpStatus.BAD_REQUEST);
			}
					var pageable=PageRequest.of(page, size, Sort.by(Direction.DESC, "price"));
				 List<Car> cars = carElasticRepository.findByBrandAndColor(brand, color, pageable).getContent();
				 return  ResponseEntity.ok().headers(headers).body(cars);
			}
	
	@GetMapping(value="/cars", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Car> findCarByReqParam(@RequestParam(name = "brand", required = true) String brand, 
			@RequestParam(name = "color", required = true) String color,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10")int size){
		if(StringUtils.isNumeric(color)) {
			throw new IllegalArgumentException("Invalid color:" +color);
		}
		var pageable=PageRequest.of(page, size, Sort.by(Direction.DESC, "price"));
		return carElasticRepository.findByBrandAndColor(brand, color, pageable).getContent();
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleInvalidColorException(IllegalArgumentException e){
		var exceptionMessage="Exception : "+e.getMessage();
		LOG.warn("{}", exceptionMessage);
		var errorResponse=new ErrorResponse(exceptionMessage, ZonedDateTime.now());
		return new ResponseEntity<>(errorResponse, null, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(value = "/cars/date", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Car> findByReleaseAfter(@DateTimeFormat(pattern = "yyyy-MM-dd") 
	@RequestParam(value = "first_release_date") LocalDate firstReleaseDate){
		return carElasticRepository.findByFirstReleaseDateAfter(firstReleaseDate);
		
	}
	

	@GetMapping(value = "/cars/date_before", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Car> findCarsReleaseBefore(@DateTimeFormat(pattern = "yyyy-MM-dd") 
	@RequestParam(value = "first_release_date") LocalDate firstReleaseDate){
		return carElasticRepository.customQuery(firstReleaseDate);
		
	}
}
