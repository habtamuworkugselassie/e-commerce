package com.kifiya.order.config;

import com.kifiya.order.models.Product;
import com.kifiya.order.models.ProductCategory;
import com.kifiya.order.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class ProductDataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    public ProductDataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            log.info("Initializing product data...");
            List<Product> products = createInitialProducts();
            productRepository.saveAll(products);
            log.info("Successfully initialized {} products", products.size());
        } else {
            log.info("Products already exist in database. Skipping initialization.");
        }
    }

    private List<Product> createInitialProducts() {
        List<Product> products = new ArrayList<>();

        // Electronics
        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440001"),
                "Laptop Pro 15",
                ProductCategory.ELECTRONICS,
                new BigDecimal("1299.99"),
                25
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),
                "Wireless Mouse",
                ProductCategory.ELECTRONICS,
                new BigDecimal("29.99"),
                100
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440003"),
                "Mechanical Keyboard",
                ProductCategory.ELECTRONICS,
                new BigDecimal("89.99"),
                50
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440004"),
                "USB-C Hub",
                ProductCategory.ELECTRONICS,
                new BigDecimal("49.99"),
                75
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440005"),
                "Wireless Earbuds",
                ProductCategory.ELECTRONICS,
                new BigDecimal("79.99"),
                60
        ));

        // Home Goods
        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440006"),
                "Coffee Maker",
                ProductCategory.HOME_GOODS,
                new BigDecimal("89.99"),
                30
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440007"),
                "Desk Lamp",
                ProductCategory.HOME_GOODS,
                new BigDecimal("34.99"),
                45
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440008"),
                "Throw Pillow Set",
                ProductCategory.HOME_GOODS,
                new BigDecimal("24.99"),
                80
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440009"),
                "Storage Basket",
                ProductCategory.HOME_GOODS,
                new BigDecimal("19.99"),
                55
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440010"),
                "Wall Clock",
                ProductCategory.HOME_GOODS,
                new BigDecimal("39.99"),
                40
        ));

        // Stationery
        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440011"),
                "Notebook Set (3-pack)",
                ProductCategory.STATIONERY,
                new BigDecimal("12.99"),
                120
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440012"),
                "Ballpoint Pen (10-pack)",
                ProductCategory.STATIONERY,
                new BigDecimal("8.99"),
                200
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440013"),
                "Sticky Notes (5-pack)",
                ProductCategory.STATIONERY,
                new BigDecimal("6.99"),
                150
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440014"),
                "File Folder Set",
                ProductCategory.STATIONERY,
                new BigDecimal("14.99"),
                90
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440015"),
                "Desk Organizer",
                ProductCategory.STATIONERY,
                new BigDecimal("22.99"),
                65
        ));

        // Food
        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440016"),
                "Premium Coffee Beans (1lb)",
                ProductCategory.FOOD,
                new BigDecimal("18.99"),
                50
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440017"),
                "Organic Tea Selection (20 bags)",
                ProductCategory.FOOD,
                new BigDecimal("12.99"),
                75
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440018"),
                "Dark Chocolate Bar",
                ProductCategory.FOOD,
                new BigDecimal("4.99"),
                200
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440019"),
                "Granola Bars (12-pack)",
                ProductCategory.FOOD,
                new BigDecimal("9.99"),
                100
        ));

        products.add(createProduct(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440020"),
                "Trail Mix (16oz)",
                ProductCategory.FOOD,
                new BigDecimal("7.99"),
                85
        ));

        return products;
    }

    private Product createProduct(UUID id, String name, ProductCategory category, BigDecimal price, int stock) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setStock(stock);
        return product;
    }
}

