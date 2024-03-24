package com.course.practicaljava.entity;

import java.time.LocalDate;
import java.util.List;

public class Car {

	private List<String> additionalFeatures;
	private boolean available;
	private String brand;
	private String color;
	private Engine engine;
	private LocalDate firstReleaseDate;
	private int price;
	private String type;
	private List<Tyre> tyre;
	
	public Car() {
	}

	public Car(String brand, String color, String type) {
		super();
		this.brand = brand;
		this.color = color;
		this.type = type;
	}

	public List<String> getAdditionalFeatures() {
		return additionalFeatures;
	}

	public String getBrand() {
		return brand;
	}

	public String getColor() {
		return color;
	}

	public Engine getEngine() {
		return engine;
	}

	public LocalDate getFirstReleaseDate() {
		return firstReleaseDate;
	}

	public int getPrice() {
		return price;
	}

	public String getType() {
		return type;
	}

	public List<Tyre> getTyre() {
		return tyre;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAdditionalFeatures(List<String> additionalFeatures) {
		this.additionalFeatures = additionalFeatures;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public void setFirstReleaseDate(LocalDate firstReleaseDate) {
		this.firstReleaseDate = firstReleaseDate;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTyre(List<Tyre> tyre) {
		this.tyre = tyre;
	}

	@Override
	public String toString() {
		return "Car [additionalFeatures=" + additionalFeatures + ", available=" + available + ", brand=" + brand
				+ ", color=" + color + ", firstReleaseDate=" + firstReleaseDate + ", price=" + price + ", type=" + type
				+ ", engine=" + engine + ", tyre=" + tyre + "]";
	}

}
