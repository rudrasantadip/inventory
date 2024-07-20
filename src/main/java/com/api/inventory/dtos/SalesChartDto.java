package com.api.inventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesChartDto {
    private String month;
    private Double totalSales;
    private Double topProductSales;


    public SalesChartDto(String month,Double totalSales)
    {
        this.month=month;
        this.totalSales=totalSales;
    }
}
