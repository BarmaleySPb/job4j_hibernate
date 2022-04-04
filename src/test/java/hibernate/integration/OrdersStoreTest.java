package hibernate.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private final BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/scripts/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void clear() throws SQLException {
        pool.getConnection().prepareStatement("DROP TABLE IF EXISTS orders").executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertEquals(1, all.size());
        assertEquals("description1", all.get(0).getDescription());
        assertEquals(1, all.get(0).getId());
    }

    @Test
    public void whenUpdateOrder() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name", "description"));
        Order order = Order.of("newName", "newDescription");
        store.updateOrder(1, order);
        assertEquals("newName", store.findById(1).getName());
    }

    @Test
    public void whenFindByName() {
        OrdersStore store = new OrdersStore(pool);
        Order firstOrder = Order.of("firstOrder", "description");
        Order secondOrder = Order.of("secondOrder", "description");
        Order thirdOrder = Order.of("secondOrder", "description");
        store.save(firstOrder);
        store.save(secondOrder);
        store.save(thirdOrder);
        assertEquals(store.findByName("firstOrder"), List.of(firstOrder));
        assertEquals(store.findByName("secondOrder"), List.of(secondOrder, thirdOrder));
    }
}