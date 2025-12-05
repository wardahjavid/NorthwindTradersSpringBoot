package pluralsight.NorthwindTradersSpringBoot2.dao;

import com.pluralsight.NorthwindTradersSpringBoot2.dao.ProductDao;
import com.pluralsight.NorthwindTradersSpringBoot2.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class NorthwindTradersSpringBoot2Application implements CommandLineRunner {

    @Autowired
    private ProductDao productDao;

    public static void main(String[] args) {
        SpringApplication.run(NorthwindTradersSpringBoot2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Product Admin Menu ---");
            System.out.println("1. List Products");
            System.out.println("2. Add Product");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                System.out.println("\nProducts:");
                productDao.getAll().forEach(System.out::println);
            }
            else if (choice == 2) {
                System.out.print("Enter Name: ");
                String name = scanner.nextLine();

                System.out.print("Enter Category: ");
                String category = scanner.nextLine();

                System.out.print("Enter Price: ");
                double price = Double.parseDouble(scanner.nextLine());

                Product p = new Product(name, category, price);
                productDao.add(p);

                System.out.println("Product added!");
            }
            else if (choice == 3) {
                System.out.println("Goodbye!");
                return;
            }
            else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
