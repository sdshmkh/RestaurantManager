package rm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rm.dao.MenuRepository;
import rm.model.Menu;
import rm.model.MenuType;
import rm.model.Restaurant;

import java.util.List;

/**
 * Created by Shahaji on 08/02/2018.
 */
@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public Menu findById(Long id){
        Menu menu = menuRepository.findOne(id);
        return menu;
    }

    public List<Menu> findByType(MenuType type){
        List<Menu> menuList = menuRepository.findByType(type);
        return menuList;
    }

    public Menu createMenu(Menu menu, RestaurantService restaurantService, Long restaurantId){
        Restaurant restaurant = restaurantService.findById(restaurantId);
        restaurant.getMenus().add(menu);
        restaurantService.saveRestaaurant(restaurant);
        return menu;
    }

    public Menu saveMenu(Menu menu){
        Menu savedMenu = menuRepository.save(menu);
        return savedMenu;
    }

    public Menu editMenu(Long id,Menu updatedMenu){
        Menu menu=menuRepository.findOne(id);
        menu.setType(updatedMenu.getType());
        menu.setItems(updatedMenu.getItems());
        menuRepository.save(menu);
        return menu;
    }

    public Menu deleteById(Long id){
        Menu menu = menuRepository.findOne(id);
        menuRepository.delete(id);
        return menu;
    }


}
