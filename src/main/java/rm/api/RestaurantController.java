package rm.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import rm.model.Menu;
import rm.model.MenuItem;
import rm.model.MenuType;
import rm.model.Restaurant;
import rm.service.MenuItemService;
import rm.service.MenuService;
import rm.service.RestaurantService;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("restaurants")
public class RestaurantController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuItemService menuItemService;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Restaurant getRestaurantById(@PathVariable("id") Long id) {
        logger.info("Finding restaurant by id: {}", id);
        Restaurant restaurant = restaurantService.findById(id);
        logger.info("Found restaurant: {}", restaurant);
        return restaurant;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Restaurant> findRestaurantsByName(@RequestParam("name") String name) {
        logger.info("Finding restaurants by name: {}", name);
        List<Restaurant> restaurants = restaurantService.findByName(name);
        logger.info("Number of restaurants found: {}", restaurants == null ? 0 : restaurants.size());
        return restaurants;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        logger.info("Creating restaurant: {}", restaurant);
        Restaurant savedRestaurant = restaurantService.saveRestaaurant(restaurant);
        logger.info("Created restaurant with id: {}", savedRestaurant == null ? null : savedRestaurant.getId());
        return savedRestaurant;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Restaurant deleteRestaurant(@RequestParam("id") Long id) {
        logger.info("Deleting restaurant: {}", id);
        Restaurant restaurant = restaurantService.deleteById(id);
        logger.info("Restaurant with id {} deleted", id);
        return restaurant;
    }

    @RequestMapping(method = RequestMethod.PUT)

    public Restaurant updateRestaurant(@RequestParam("id") Long id,
                                       @RequestBody Restaurant updatedRestaurant) {
        logger.info("Updating restaurant (id:{}) with: {}", id, updatedRestaurant);
        Restaurant restaurant = restaurantService.updateRestaurantById(id, updatedRestaurant);
        logger.info("Restaurant with id {} updated", id);
        return restaurant;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}/menus")
    public List<Menu> findMenusByRestaurantId(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.findById(id);
        List<Menu> menuList = restaurant.getMenus();
        return menuList;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}/menus/{menuId}")
    public Menu deleteMenuById(@PathVariable("menuId") long menuId,
                               @PathVariable("id") Long id) {
        logger.info("Deleting menu with (menuId:{}) with restaurant id (id{})", menuId, id);
        Menu menu = menuService.deleteById(menuId);
        logger.info("Deleted menu with (id:{})",menuId);
        return menu;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}/menus")
    public Menu addMenu(@RequestBody Menu createdMenu,
                        @PathVariable("id") Long id) {
        logger.info("Creating menu in restaurant with (id:{})", id);
        Menu menu = menuService.createMenu(createdMenu, restaurantService, id);
        logger.info("Created Menu with (id:{})",menu.getId());
        return menu;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/menus/{menuId}")
    public Menu editMenu(@RequestBody Menu updatedMenu,
                         @PathVariable("menuId") Long menuId) {
        logger.info("Updating menu (id:{}) with {}", menuId,updatedMenu);
        Menu menu = menuService.editMenu(menuId, updatedMenu);
        logger.info("Updated menu with (id:{})", menuId);
        return menu;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}/menus/{menuId}/menuitems")
    public List<MenuItem> findMenuItemsByMenuId(@PathVariable("id") Long id,
                                                @PathVariable("menuId") Long menuId) {
        logger.info("Finding menu items in menu with (id:{})");
        List<MenuItem> menuItems = menuItemService.menuItemsByMenuId(menuId, menuService);
        logger.info("Number of menu items found: {}", menuItems == null ? 0 : menuItems.size());
        return menuItems;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}/menus/{menuId}/menuitems")
    public MenuItem createMenuItem(@PathVariable("id") Long id,
                                         @PathVariable("menuId") Long menuId,
                                         @RequestBody MenuItem menuItem) {
        logger.info("Creating menuitem in menu (id:{})", menuId);
        MenuItem item = menuItemService.createMenuItem(menuId, menuService, menuItem);
        logger.info("Create menu with (id:{})", menuItem==null? null:item.getId());
        return item;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}/menus/{menuId}/menuitems/{menuItemsId}")
    public MenuItem deleteMenuItem(@PathVariable("menuItemsId") Long menuItemsId,
                                   @PathVariable("menuId") Long menuId) {
        MenuItem menuItem = menuItemService.deleteMenuItem(menuItemsId);
        return menuItem;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/menus/{menuId}/menuitems/{menuItemsId}")
    public MenuItem updateMenuItem(@PathVariable("menuItemsId") Long menuItemsId,
                                   @RequestBody MenuItem menuItem) {
        MenuItem item = menuItemService.editMenuItem(menuItemsId, menuItem);
        return item;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/menus/{menuId}/menuitems")
    public List<MenuItem> findMenuItemsByName(@PathParam("name") String name) {
        List<MenuItem> menuItemList = menuItemService.findMenuItemByName(name);
        return menuItemList;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/menus")
    public List<Menu> findMenuByType(@PathParam("type") MenuType type) {
        List<Menu> menuList = menuService.findByType(type);
        return menuList;
    }

}
