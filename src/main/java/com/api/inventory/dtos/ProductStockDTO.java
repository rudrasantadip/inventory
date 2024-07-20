package com.api.inventory.dtos;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductStockDTO
{
    private String productName;
    private Long quantity;
    private String unit;
    private String category;


    public ProductStockDTO(String productName,Long quantity, String unit)
    {
        this.productName=productName;
        this.quantity=quantity;
        this.unit=unit;
    }

    public ProductStockDTO(String category,Long quantity)
    {
        this.category=category;
        this.quantity=quantity;   
    }
}