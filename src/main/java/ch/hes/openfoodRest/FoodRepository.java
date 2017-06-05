package ch.hes.openfoodRest;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface FoodRepository extends MongoRepository<Food, String> {

	List<Food> findByNameContainsIgnoreCase(String name);
	
	List<Food> findByOrderByQuantityAsc();
	
	List<Food> findByNameContainsIgnoreCaseAndQuantityGreaterThanEqual(String name, int quantity);

}
