package com.course.practicaljava.service.impl;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.course.practicaljava.entity.Car;
import com.course.practicaljava.entity.Engine;
import com.course.practicaljava.entity.Tire;
import com.course.practicaljava.service.CarService;
import com.course.practicaljava.util.RandomDateUtil;

@Service
public class RandomCarService implements CarService {

	@Override
	public Car generateCar() {
		
		var brand=BRANDS.get(ThreadLocalRandom.current().nextInt(BRANDS.size()));
		var color=COLORS.get(ThreadLocalRandom.current().nextInt(COLORS.size()));
		var type=TYPES.get(ThreadLocalRandom.current().nextInt(TYPES.size()));
		
		var available = ThreadLocalRandom.current().nextBoolean();
		var price = ThreadLocalRandom.current().nextInt(5000, 12001);
		var firstReleaseDate=RandomDateUtil.generateRandomLocalDate();
		
		var randomCount=ThreadLocalRandom.current().nextInt(ADDITIONAL_FEATURES.size());
		var additionalFeatures=new ArrayList<String>();
		
		for(int i=0; i<randomCount; i++) {
			additionalFeatures.add(ADDITIONAL_FEATURES.get(i));
		}
		
		var feul=FEULTYPE.get(ThreadLocalRandom.current().nextInt(FEULTYPE.size()));
		var hp=ThreadLocalRandom.current().nextInt(100, 221);
		
		var engine=new Engine();
		engine.setFuelType(feul);
		engine.setHorsePower(hp);
		
		var tires=new ArrayList<Tire>();
		for(int i=0; i<3; i++) {
			var tire=new Tire();
			var prize=ThreadLocalRandom.current().nextInt(200, 401);
			var size=ThreadLocalRandom.current().nextInt(15, 18);
			var tyreManufacturer=TYRE_MANUFACTURERS.get(ThreadLocalRandom.current().nextInt(TYRE_MANUFACTURERS.size()));
			tire.setManufacturer(tyreManufacturer);
			tire.setPrice(prize);
			tire.setSize(size);
			tires.add(tire);
		}
		
		Car car= new Car(brand,color, type);
		car.setAvailable(available);
		car.setFirstReleaseDate(firstReleaseDate);
		car.setPrice(price);
		car.setAdditionalFeatures(additionalFeatures);
		car.setEngine(engine);
		car.setTires(tires);
		
		return car;
	}

}
