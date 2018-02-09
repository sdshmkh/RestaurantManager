package rm.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import rm.model.Restaurant;
import rm.util.ObjectUtil;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@Transactional
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantRepositoryTest.class);

    private Long id;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Before
    public void setUp() {

        Restaurant restaurant = ObjectUtil.createRestaurant("test restaurant",new Long(1));
        logger.info("Created resturant with (id:{})", restaurant == null? null: restaurant.getId());
        restaurant = restaurantRepository.save(restaurant);
        id = restaurant.getId();
        logger.info("Restaurant saved with (id:{}) ",restaurant == null? null: restaurant.getId());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void findByNameLike() throws Exception {
        List<Restaurant> restaurants = restaurantRepository.findByNameLike("test");

        // verify that each restaurant object contains the word test
        restaurants.stream()
                .forEach(restaurant ->
                        Assert.assertTrue("restaurant does not contain word test", restaurant.getName().contains("test")));
    }
    @Test
    public void findByNameLikeNegative() throws Exception {
        List<Restaurant> restaurants = restaurantRepository.findByNameLike("%"+"test"+"%");

        // verify that each restaurant object contains the word test
        Assert.assertTrue("restaurant does not contain word test", restaurants.size()!=0);
    }


}