package com.api.inventory.controllers;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.inventory.dtos.ProductStockDTO;
import com.api.inventory.entities.Product;
import com.api.inventory.services.ProductService;


@CrossOrigin(origins = {"http://localhost:4200","http://192.168.0.100:4200"})
@RestController
@RequestMapping("inventory/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("add")
    public ResponseEntity<Boolean> addProduct(@RequestBody Product product) {
        boolean isSuccess = productService.addProduct(product);
        if (isSuccess == true) {
            return ResponseEntity.ok(isSuccess);
        }
        return ResponseEntity.badRequest().body(isSuccess);
    }

    @PostMapping("addmany")
    public ResponseEntity<Boolean> addProduct(@RequestBody List<Product> product) {
        boolean isSuccess = productService.addProducts(product);
        if (isSuccess == true) {
            return ResponseEntity.ok(isSuccess);
        }
        return ResponseEntity.badRequest().body(isSuccess);
    }

    @GetMapping("count")
    public List<Long> productCount() {
        return productService.productCount();
    }

    //Get All Products...
    @GetMapping("")
    public List<Product> getAll()
    {
        return productService.getAllProducts();
    }

    @GetMapping("/categories")
    public List<String> getAllCategories()
    {
        return productService.getAllCategories();
    }

    @GetMapping("/categories/{category}")
    public List<ProductStockDTO> getAll(@PathVariable("category") String categoryName)
    {
        return productService.getByCategory(categoryName);
    }


    @GetMapping("/names")
    public List<String> getproductNames()
    {
        List<String> productsName=productService.getAllProductNames();
        return productsName;
    }

    @GetMapping("{name}")
    public Product getProductsbyName(@PathVariable("name") String name)
    {
        return productService.getByName(name);
    }

    //Product Details -> Low Stock items, all categories , all items, unconfirmed items
    @GetMapping("details")
    public ResponseEntity<Map<String,Long>> getDetails()
    {
        return ResponseEntity.ok(productService.getProductDetails());
    }


    @GetMapping("proportions/primary")
    public List<ProductStockDTO> primaryProportions()
    {
        return this.productService.primaryCategoryProp();
    }

    @GetMapping("proportions/secondary")
    public List<ProductStockDTO> secondaryProportions()
    {
        return this.productService.secondaryCategoryProp();
    }

}
