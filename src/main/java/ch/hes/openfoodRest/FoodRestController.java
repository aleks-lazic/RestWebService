package ch.hes.openfoodRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/food")
public class FoodRestController {

	@Autowired
	private FoodRepository repo;

	@RequestMapping(method = RequestMethod.GET)
	public List<Food> getAllProducts() {
		return repo.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/sort")
	public List<Food> getAllProductsSortedByQuantity() {
			return repo.findByOrderByQuantityAsc();	
	}

	@RequestMapping(method = RequestMethod.GET, params = { "name" })
	public @ResponseBody List<Food> getProductsByName(@RequestParam(value = "name") String name) {
		return repo.findByNameContainsIgnoreCase(name);
	}

	@RequestMapping(method = RequestMethod.GET, params = { "name", "quantity" })
	public @ResponseBody List<Food> getProductsByNameAndQuantity(@RequestParam(value = "name") String name,
			@RequestParam(value = "quantity") int quantity) {		
		return repo.findByNameContainsIgnoreCaseAndQuantityGreaterThanEqual(name, quantity);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Food createProduct(@RequestBody Food food) {
		return repo.save(food);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public @ResponseBody void delete(@PathVariable String id) {
		repo.delete(id);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public Food update(@PathVariable String id, @RequestBody Food food) {
		Food updated = repo.findOne(id);
		updated.setIngredients_translations(food.getIngredients_translations());
		updated.setUnit(food.getUnit());
		updated.setPortion_unit(food.getPortion_unit());
		updated.setQuantity(food.getQuantity());
		updated.setName(food.getName());
		updated.setPortion_quantity(food.getPortion_quantity());
		updated.setNutrients(food.getNutrients());
		return repo.save(updated);
	}

}
