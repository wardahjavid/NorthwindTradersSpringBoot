package pluralsight.NorthwindTradersSpringBoot2.dao;

import com.pluralsight.NorthwindTradersSpringBoot2.models.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleProductDao implements ProductDao {

    private List<Product> products;
    private int counter = 1;

    public SimpleProductDao() {
        this.products = new ArrayList<>();

        // Initial sample products
        products.add(new Product(counter++, "Chai Tea", "Beverages", 18.00));
        products.add(new Product(counter++, "Chang", "Beverages", 19.00));
        products.add(new Product(counter++, "Grandma's Cookies", "Snacks", 12.50));
    }

    @Override
    public void add(Product product) {
        product.setProductId(counter++);
        products.add(product);
    }

    @Override
    public List<Product> getAll() {
        return products;
    }
}
