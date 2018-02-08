package rm.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rm.dao.MenuItemsRepository;
import rm.dao.MenuRepository;
import rm.model.Menu;
import rm.model.MenuItem;
import rm.model.MenuType;
import rm.model.Restaurant;
import rm.service.RestaurantService;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuItemsRepository menuItemsRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Restaurant getRestaurantById(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.findById(id);
        return restaurant;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Restaurant> findRestaurantByName(@RequestParam("name") String name) {
        List<Restaurant> restaurants = restaurantService.findByName(name);
        return restaurants;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantService.createRestaaurant(restaurant);
        return savedRestaurant;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Restaurant deleteRestaurant(@RequestParam("id") Long id) {
        Restaurant restaurant = restaurantService.deleteById(id);
        return restaurant;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Restaurant updateRestaurant(@RequestParam("id") Long id,
                                       @RequestBody Restaurant updatedRestaurant) {
        Restaurant restaurant = restaurantService.updateRestaurantById(id, updatedRestaurant);
        return restaurant;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}/menus")
    public List<Menu> findMenusByRestaurantId(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.findById(id);
        return restaurant.getMenus();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}/menus/{menu_id}")
    public Menu findMenuById(@PathVariable("id") Long id,
                             @PathVariable("menu_id") long menu_id) {
        Menu menu = menuRepository.findOne(menu_id);
        Restaurant restaurant = restaurantService.findById(id);
        restaurant.getMenus().remove(menu);
        restaurantService.saveRestaurant(restaurant);
        return menu;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}/menus")
    public Menu addMenu(@RequestBody Menu menu,
                        @PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.findById(id);
        restaurant.getMenus().add(menu);
        restaurantService.saveRestaurant(restaurant);
        return menu;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/menus/{menu_id}")
    public Menu editMenu(@RequestBody Menu updatedMenu,
                         @PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.findById(id);
        Menu menu = menuRepository.findOne(updatedMenu.getId());
        restaurant.getMenus().remove(menu);
        restaurant.getMenus().add(updatedMenu);
        restaurantService.saveRestaurant(restaurant);
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
