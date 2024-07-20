package com.api.inventory.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.inventory.dtos.ProductStockDTO;
import com.api.inventory.entities.Product;



public interface ProductRepo extends JpaRepository<Product,Long>
{

        //searching for a single product by name
        @Query(value="SELECT p FROM Product p WHERE p.productName = :productName")
        Optional<Product> getProduct(String productName);

        List<Product> findByProductName(String productName);

        //Getting the number of secondary categories from the table
        @Query(value = "SELECT COUNT( DISTINCT category_secondary ) FROM product_table", nativeQuery = true)
        Long categorySecondaryCount();

        @Query(value = "SELECT COUNT(p) FROM Product p where p.quantity < :range")
        Long getLowStockItems(Integer range);

        //Selecting all products by the secondary category
        List<Product> findByCategorySecondary(String categorySecondary);

        //Selecting all distinct categories
        @Query("select distinct p.categorySecondary from Product p")
        List<String> getAllCategories();


        // Selecting all products by the secondary category (custom implementation)
        @Query(value = "SELECT new com.api.inventory.dtos.ProductStockDTO(p.productName,p.quantity,p.unit) FROM Product p WHERE p. categorySecondary = :category")
        List<ProductStockDTO> getByCategorySecondary(@Param("category") String categorySecondary);

        @Query("SELECT p.productName from Product p")
        List<String> getAllProductNames();

        @Query(value = "SELECT new com.api.inventory.dtos.ProductStockDTO(p.categoryPrimary,COUNT(p.quantity)) FROM Product p GROUP BY p.categoryPrimary")
        List<ProductStockDTO> primaryCategoryProportions();

        @Query(value = "SELECT new com.api.inventory.dtos.ProductStockDTO(p.categoryPrimary,COUNT(p.quantity)) FROM Product p GROUP BY p.categorySecondary")
        List<ProductStockDTO> secondaryCategoryProportions();

        



}
