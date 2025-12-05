package pluralsight.NorthwindTradersSpringBoot2.dao;

import com.pluralsight.NorthwindTradersSpringBoot2.models.Product;
import java.util.List;

public interface ProductDao {
    void add(Product product);
    List<Product> getAll();

    Product findById(int id);
    void delete(int id);
    void update(Product product);
    List<Product> searchByName(String name);

}
