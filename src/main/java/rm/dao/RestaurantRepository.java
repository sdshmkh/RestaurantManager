package rm.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rm.model.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    List<Restaurant> findByNameLike(String name);
}
