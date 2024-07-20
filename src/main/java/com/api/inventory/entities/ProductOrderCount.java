package com.api.inventory.entities;


import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "product_order_count")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductOrderCount 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCountId;
    private String productName;
    private Long orderCount;
    private Integer day;
    private Integer month;
    private Integer year;


    public ProductOrderCount(String productName, Long orderCount)
    {
        this.productName=productName;
        this.orderCount=orderCount;
    }

    public ProductOrderCount(String productName, Integer month,Long orderCount)
    {
        this.productName=productName;
        this.orderCount=orderCount;
        this.month=month;
    }



}
