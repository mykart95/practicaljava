package com.course.practicaljava.service;

import java.util.List;

import com.course.practicaljava.entity.Car;

public interface CarService {

	List<String> BRANDS= List.of("Toyota", "Honda", "Ford");
	List<String> COLORS= List.of("Red", "Black", "White");
	List<String> TYPES= List.of("Sedan", "SUV", "MPV");
	
	Car generateCar();

}
