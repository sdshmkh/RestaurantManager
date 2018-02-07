package rm.dao;

import org.springframework.stereotype.Repository;
import rm.model.MenuItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface MenuItemsRepository extends CrudRepository<MenuItem, Long> {
    List<MenuItem> findByNameLike(String name);
}
