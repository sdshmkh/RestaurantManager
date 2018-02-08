package rm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.stereotype.Service;
import rm.dao.MenuItemsRepository;
import rm.model.Menu;
import rm.model.MenuItem;

import java.util.List;

/**
 * Created by Shahaji on 08/02/2018.
 */
@Service
public class MenuItemService {
    @Autowired
    private MenuItemsRepository menuItemsRepository;

    public List<MenuItem> menuItemsByMenuId(Long id, MenuService menuService){
        Menu menu = menuService.findById(id);
        List<MenuItem> menuItems = menu.getItems();
        return menuItems;
    }

    public List<MenuItem> findMenuItemByName(String name){
        List<MenuItem> menuItems = menuItemsRepository.findByNameLike("%"+name+"%");
        return menuItems;
    }

    public MenuItem createMenuItem(Long id, MenuService menuService, MenuItem menuItem){
        Menu menu = menuService.findById(id);
        menu.getItems().add(menuItem);
        menuService.saveMenu(menu);
        return menuItem;
    }

    public MenuItem editMenuItem(Long id, MenuItem menuItem){
        MenuItem item = menuItemsRepository.findOne(id);
        item.setName(menuItem.getName());
        item.setPrice(menuItem.getPrice());
        item.setDescription(menuItem.getDescription());
        item.setCalorieCount(menuItem.getCalorieCount());
        menuItemsRepository.save(item);
        return item;
    }

    public MenuItem deleteMenuItem(Long id){
        MenuItem menuItem = menuItemsRepository.findOne(id);
        if(menuItem != null){
            menuItemsRepository.delete(id);
        }else{
            throw  new RuntimeException("Menu Item with id"+ id +" does not exist");
        }
        return menuItem;
    }
}
