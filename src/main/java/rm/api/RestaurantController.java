package rm.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuItemService menuItemService;

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
        Restaurant savedRestaurant = restaurantService.saveRestaaurant(restaurant);
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
        List<Menu> menuList = restaurant.getMenus();
        return menuList;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}/menus/{menu_id}")
    public Menu findMenuById(@PathVariable("menu_id") long menu_id) {
        Menu menu = menuService.deleteById(menu_id);
        return menu;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}/menus")
    public Menu addMenu(@RequestBody Menu createdMenu,
                        @PathVariable("id") Long id) {
        Menu menu = menuService.createMenu(createdMenu, restaurantService, id);
        return menu;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/menus/{menu_id}")
    public Menu editMenu(@RequestBody Menu updatedMenu,
                         @PathVariable("menu_id") Long menuId) {
        Menu menu = menuService.editMenu(menuId, updatedMenu);
        return menu;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}/menus/{menu_id}/menuitems")
    public List<MenuItem> findMenuItemsByMenuId(@PathVariable("id") Long id,
                                                @PathVariable("menu_id") Long menuId) {
        List<MenuItem> menuItems = menuItemService.menuItemsByMenuId(menuId, menuService);
        return menuItems;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}/menus/{menu_id}/menuitems")
    public MenuItem createMenuItem(@PathVariable("id") Long id,
                                         @PathVariable("menu_id") Long menuId,
                                         @RequestBody MenuItem menuItem) {
        MenuItem item = menuItemService.createMenuItem(menuId, menuService, menuItem);
        return item;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}/menus/{menu_id}/menuitems/{menuitems_id}")
    public MenuItem deleteMenuItem(@PathVariable("menuitems_id") Long menuItemsId,
                                   @PathVariable("menu_id") Long menuId) {
        MenuItem menuItem = menuItemService.deleteMenuItem(menuItemsId);
        return menuItem;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/menus/{menu_id}/menuitems/{menuitems_id}")
    public MenuItem updateMenuItem(@PathVariable("menuitems_id") Long menuItemsId,
                                   @RequestBody MenuItem menuItem) {
        MenuItem item = menuItemService.editMenuItem(menuItemsId, menuItem);
        return item;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/menus/{menu_id}/menuitems")
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
