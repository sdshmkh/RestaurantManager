package rm.dao;

import org.springframework.stereotype.Repository;
import rm.model.Menu;

import org.springframework.data.repository.CrudRepository;

import rm.model.MenuType;

import java.util.List;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Long> {
    List<Menu> findByType(MenuType type);
}
