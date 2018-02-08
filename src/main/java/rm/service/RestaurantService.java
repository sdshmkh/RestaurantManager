package rm.service;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant findById(Long id) {
        Restaurant restaurant = restaurantRepository.findOne(id);
        return restaurant;
    }

    public List<Restaurant> findByName(String name){
        List<Restaurant> restaurantList = restaurantRepository.findByNameLike("%" + name + "%");
        return restaurantList;
    }

    public Restaurant createRestaaurant(Restaurant restaurant){
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return savedRestaurant;
    }

    public Restaurant deleteById(Long id){
        Restaurant restaurant = restaurantRepository.findOne(id);
        restaurantRepository.delete(id);
        return restaurant;
    }

    public Restaurant updateRestaurantById(Long id,Restaurant updatedRestaurant){
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

    public Restaurant saveRestaurant(Restaurant restaurant){
        Restaurant restaurant1 = restaurantRepository.save(restaurant);
        return restaurant1;
    }
}
