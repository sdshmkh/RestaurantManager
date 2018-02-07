package rm.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rm.dao.MenuItemsRepository;
import rm.dao.MenuRepository;
import rm.dao.RestaurantRepository;
import rm.model.Menu;
import rm.model.MenuItem;
import rm.model.MenuType;
import rm.model.Restaurant;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuItemsRepository menuItemsRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Restaurant getRestaurantById(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantRepository.findOne(id);
        return restaurant;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Restaurant> findRestaurantByName(@RequestParam("name") String name) {
        List<Restaurant> restaurants = restaurantRepository.findByNameLike("%" + name + "%");
        return restaurants;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return savedRestaurant;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteRestaurant(@RequestParam("id") Long id) {
        restaurantRepository.delete(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Restaurant updateRestaurant(@RequestParam("id") Long id,
                                       @RequestBody Restaurant updatedRestaurant) {
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

    @RequestMapping(method = RequestMethod.GET, path = "/{id}/menus")
    public List<Menu> findMenusByRestaurantId(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantRepository.findOne(id);
        return restaurant.getMenus();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}/menus/{menu_id}")
    public Menu findMenuById(@PathVariable("id") Long id,
                             @PathVariable("menu_id") long menu_id) {
        Menu menu = menuRepository.findOne(menu_id);
        Restaurant restaurant = restaurantRepository.findOne(id);
        restaurant.getMenus().remove(menu);
        restaurantRepository.save(restaurant);
        return menu;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}/menus")
    public Menu addMenu(@RequestBody Menu menu,
                        @PathVariable("id") Long id) {
        Restaurant restaurant = restaurantRepository.findOne(id);
        restaurant.getMenus().add(menu);
        restaurantRepository.save(restaurant);
        return menu;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/menus/{menu_id}")
    public Menu editMenu(@RequestBody Menu updatedMenu,
                         @PathVariable("id") Long id) {
        Restaurant restaurant = restaurantRepository.findOne(id);
        Menu menu = menuRepository.findOne(updatedMenu.getId());
        restaurant.getMenus().remove(menu);
        restaurant.getMenus().add(updatedMenu);
        restaurantRepository.save(restaurant);
        return updatedMenu;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}/menus/{menu_id}/menuitems")
    public List<MenuItem> findMenuItemsByMenuId(@PathVariable("id") Long id,
                                                @PathVariable("menu_id") Long menuId) {
        Menu menu = menuRepository.findOne(menuId);
        List<MenuItem> menuItems = menu.getItems();
        return menuItems;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}/menus/{menu_id}/menuitems")
    public List<MenuItem> createMenuItem(@PathVariable("id") Long id,
                                         @PathVariable("menu_id") Long menuId,
                                         @RequestBody MenuItem menuItem) {
        Menu menu = menuRepository.findOne(menuId);
        List<MenuItem> menuItems = menu.getItems();
        menuItems.add(menuItem);
        Menu save = menuRepository.save(menu);
        return menuItems;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}/menus/{menu_id}/menuitems/{menuitems_id}")
    public MenuItem deleteMenuItem(@PathVariable("menuitems_id") Long menuItemsId,
                                   @PathVariable("menu_id") Long menuId) {
        MenuItem menuItem = menuItemsRepository.findOne(menuItemsId);
        menuItemsRepository.delete(menuItemsId);
        return menuItem;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/menus/{menu_id}/menuitems/{menuitems_id}")
    public MenuItem updateMenuItem(@PathVariable("menuitems_id") Long menuItemsId,
                                   @RequestBody MenuItem menuItem) {
        MenuItem item = menuItemsRepository.findOne(menuItemsId);
        item.setName(menuItem.getName());
        item.setCalorieCount(menuItem.getCalorieCount());
        item.setDescription(menuItem.getDescription());
        item.setPrice(menuItem.getPrice());
        menuItemsRepository.save(item);
        return item;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/menus/menuitems")
    public List<MenuItem> findMenuItemsByName(@PathParam("name") String name) {
        List<MenuItem> menuItemList = menuItemsRepository.findByNameLike("%"+name+"%");
        return menuItemList;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/menus")
    public List<Menu> findMenuByType(@PathParam("type") MenuType type) {
        return menuRepository.findByType(type);
    }

}
