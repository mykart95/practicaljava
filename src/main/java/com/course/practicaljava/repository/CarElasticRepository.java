package com.course.practicaljava.repository;

import com.course.practicaljava.entity.Car;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarElasticRepository extends ElasticsearchRepository<Car, String> {

	List<Car> findByBrandAndColor(String brand, String color);
	
	List<Car> findByFirstReleaseDateAfter(LocalDate date);
	
	@Query(value = """
			{
        "bool": {
            "must": [
                {
                    "range": {
                        "firstReleaseDate": {
                            "lt": "?0"
                        }
                    }
                }
            ]
        }
    }
			""")
	List<Car> customQuery(LocalDate dateParam);
}
