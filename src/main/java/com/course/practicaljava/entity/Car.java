package com.course.practicaljava.entity;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Document(indexName = "practicaljava")
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Car {

  @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
  private List<String> additionalFeatures;
  
  private boolean available;

  private String brand;

  private String color;

  @JsonUnwrapped
  private Engine engine;
  
  @Field(type = FieldType.Date, format = DateFormat.date)
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
  private LocalDate firstReleaseDate;
  
  @Id
  private String id;
  
  private int price;

  @JsonInclude(value = JsonInclude.Include.NON_EMPTY)  
  private String secretFeature;
  
  @Transient
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Asia/Jakarta")
  private ZonedDateTime timestamp = ZonedDateTime.now();
  
  private List<Tire> tires;
  
  private String type;
  
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

  public String getId() {
    return id;
  }

  public int getPrice() {
    return price;
  }

  public String getSecretFeature() {
    return secretFeature;
  }

  public ZonedDateTime getTimestamp() {
    return timestamp;
  }

  public List<Tire> getTires() {
    return tires;
  }

  public String getType() {
    return type;
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

  public void setId(String id) {
    this.id = id;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public void setSecretFeature(String secretFeature) {
    this.secretFeature = secretFeature;
  }

  public void setTimestamp(ZonedDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public void setTires(List<Tire> tires) {
    this.tires = tires;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Car [additionalFeatures=" + additionalFeatures + ", available=" + available + ", brand=" + brand
        + ", color=" + color + ", engine=" + engine + ", firstReleaseDate=" + firstReleaseDate + ", price=" + price
        + ", tires=" + tires + ", type=" + type + ", timestamp=" + timestamp + ", secretFeature=" + secretFeature + "]";
  }

}
