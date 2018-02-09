package rm.util;

import rm.model.Restaurant;

public class ObjectUtil {

    public static Restaurant createRestaurant(String name, Long id) {
        Restaurant restaurant = new Restaurant(name, "address", "", "description");
        restaurant.setId(id);
        return restaurant;

    }
}