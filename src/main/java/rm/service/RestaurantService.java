package rm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rm.dao.RestaurantRepository;
import rm.model.Menu;
import rm.model.Restaurant;

/**
 * Created by Shahaji on 08/02/2018.
 */
@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant findByName(Long id) {
        Restaurant restaurant = restaurantRepository.findOne(id);
        return restaurant;
    }
}
