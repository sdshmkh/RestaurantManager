package rm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import rm.dao.RestaurantRepository;
import rm.model.Menu;
import rm.model.Restaurant;

import java.util.List;

/**
 * Created by Shahaji on 08/02/2018.
 */
@Service
public class RestaurantService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Cacheable("restaurantsById")
    public Restaurant findById(Long id) {
        logger.info("Accessd repository for (id:{})", id);
        Restaurant restaurant = restaurantRepository.findOne(id);
        return restaurant;
    }

    public List<Restaurant> findByName(String name){
        List<Restaurant> restaurantList = restaurantRepository.findByNameLike("%" + name + "%");
        return restaurantList;
    }

    public Restaurant saveRestaaurant(Restaurant restaurant){
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return savedRestaurant;
    }
    @CacheEvict(value = "restaurantsById", key = "#a0")
    public Restaurant deleteById(Long id){
        Restaurant restaurant = restaurantRepository.findOne(id);
        restaurantRepository.delete(id);
        return restaurant;
    }
    @CacheEvict(value = "restaurantsById", key = "#a0")
    public Restaurant updateRestaurantById(Long id, Restaurant updatedRestaurant){
        Restaurant restaurant = restaurantRepository.findOne(id);
        if (restaurant != null) {
            restaurant.setName(updatedRestaurant.getName());
            restaurant.setAddress(updatedRestaurant.getAddress());
            restaurant.setTimings(updatedRestaurant.getTimings());
            restaurant = restaurantRepository.save(restaurant);
        } else {
            throw new RuntimeException("Restaurant with " + id + " does not exist");
        }
        return restaurant;
    }
}
