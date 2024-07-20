package com.api.inventory.dtos;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalesDto {
    private List<ProductSaleDto> products;

    private String status;
    private Long count;


    public SalesDto(String status, Long count)
    {
        this.status=status;
        this.count=count;
    }
}
