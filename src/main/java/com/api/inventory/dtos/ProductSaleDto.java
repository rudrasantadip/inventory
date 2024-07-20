package com.api.inventory.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductSaleDto 
{
    private String productName;
    private Long quantityBought;
    private List<SalesChartDto> salesChart;

    public ProductSaleDto(String productName,List<SalesChartDto> salesChart)
    {
        this.productName=productName;
        this.salesChart=salesChart;
    }
}
