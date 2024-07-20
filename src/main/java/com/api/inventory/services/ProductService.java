package com.api.inventory.services;

import org.springframework.stereotype.Service;

import com.api.inventory.dtos.ProductStockDTO;
import com.api.inventory.entities.Product;
import com.api.inventory.repositories.ProductRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;




/**
 * Service class for managing products.
 */
@Service
public class ProductService {
    // Product Repository object
    @Autowired
    private ProductRepo productRepo;

    /**
     * Adds a new product to the repository.
     *
     * @param p the product to add
     * @return {@code true} if the product was added successfully, {@code false}
     *         otherwise
     * @implNote if the given entity is already present in the database,
     * the quantity of that entity is increased
     */
    public boolean addProduct(Product p) {

        try {
            Optional<Product> optionalProduct=productRepo.getProduct(p.getProductName());
            /*
             * if the product to be saved is already present
             * it increases the quantity of the available product
             */
            if(optionalProduct.isPresent())
            {
                optionalProduct.get().setQuantity(optionalProduct.get().getQuantity()+p.getQuantity());
                productRepo.save(optionalProduct.get());
                return true;
            }

            //or else save the new quantity
            productRepo.save(p);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Adds multiple products to the repository.
     *
     * @param products the list of products to add
     * @return {@code true} if the products were added successfully, {@code false}
     *         otherwise
     */
    public boolean addProducts(List<Product> products) {
        try {
            productRepo.saveAll(products);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all available products.
     *
     * @return a list of all products
     */
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    /**
     * Retrieves products by category name.
     *
     * @param categoryName the name of the category
     * @return a list of products in the specified category
     */
    public List<ProductStockDTO> getByCategory(String categoryName) {
        // return this.productRepo.findByCategorySecondary(categoryName);
        return this.productRepo.getByCategorySecondary(categoryName);
    }

    /**
     * Finds a product by its ID.
     *
     * @param id the ID of the product
     * @return the product if found, or {@code null} if not found
     */
    public Product findById(Long id) {

        Optional<Product> op = productRepo.findById(id);
        if (op.isEmpty()) {
            return null;
        }
        return op.get();
    }

    /**
     * Counts the total number of products and categories.
     *
     * @return a list containing the total number of products and the total number
     *         of secondary categories
     */
    public List<Long> productCount() {
        List<Long> counts = new ArrayList<>();
        counts.add(productRepo.count()); // total number of products
        counts.add(productRepo.categorySecondaryCount()); // total secondary categories
        return counts;
    }

    /**
     * Retrieves all category names.
     *
     * @return a list of all category names
     */
    public List<String> getAllCategories() {
        return productRepo.getAllCategories();
    }

     /**
     * Retrieves all product names.
     *
     * @return a list of all product names
     */
    public List<String> getAllProductNames() {
        return productRepo.getAllProductNames();
    }
   
    public Product getByName(String name)
    {
        return productRepo.getProduct(name).orElseThrow(
            ()-> new RuntimeException()
        );
    }

    public Map<String,Long> getProductDetails()
    {
        Map<String,Long> pDetails = new HashMap<>();
        pDetails.put("lowStocks", productRepo.getLowStockItems(30));
        pDetails.put("allCategories", productRepo.categorySecondaryCount());
        pDetails.put("AllProducts", productRepo.count());
        return pDetails;
    }

    public List<ProductStockDTO> primaryCategoryProp()
    {
        return productRepo.primaryCategoryProportions();
    }

    public List<ProductStockDTO> secondaryCategoryProp()
    {
        return productRepo.secondaryCategoryProportions();
    }

}
