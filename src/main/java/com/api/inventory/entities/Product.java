package com.api.inventory.entities;

import java.util.List;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_table")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product 
{
    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    private Long productId;
    private String productName;
    private String categoryPrimary;
    private String categorySecondary;
    private String productType;
    
    @Column(name = "price_per_kg")
    private Long price;

    @Column(name = "total_quantity")
    private Long quantity;
    
    private String unit;
    

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<Sale> sale;


   
    
    @Transient
    @JsonIgnore
    private final String [] productTypes = {"solid", "liquid"};
    
    public Product(String productName, String categoryPrimary, String categorySecondary,Long price, Long quantity, String productType, String unit) {
        this.productName = productName;
        this.categoryPrimary = categoryPrimary;
        this.categorySecondary = categorySecondary;
        this.price = price;
        this.quantity = quantity;
        this.productType=productType;
        this.unit=unit;
        checkType(productType);
    }


    private void checkType(String productType)
    {
        if(productType.equalsIgnoreCase(productTypes[0]))
        {
            this.productType=productTypes[0];
        }
        else if(productType.equalsIgnoreCase(productTypes[1]))
        {
            this.productType=productTypes[1];
        }
        else
        {
            this.productType="";
        }

    }


    @Override
    public String toString() {
        return "Product [productId=" + productId + ", productName=" + productName + ", categoryPrimary="
                + categoryPrimary + ", categorySecondary=" + categorySecondary + ", productType=" + productType
                + ", price=" + price + ", quantity=" + quantity + ", unit=" + unit 
                ;
    }


    
    

}
