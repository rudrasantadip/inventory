package com.api.inventory.controllers;

import com.api.inventory.dtos.ProductSaleDto;
import com.api.inventory.dtos.SalesChartDto;
import com.api.inventory.dtos.SalesDto;
import com.api.inventory.entities.ProductOrderCount;
import com.api.inventory.entities.Sale;
import com.api.inventory.services.OrderCountService;
import com.api.inventory.services.SalesService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;






@CrossOrigin(origins = {"http://localhost:4200","http://192.168.0.100:4200"})
@RestController
@RequestMapping("inventory/sales")
public class SaleController 
{
    @Autowired
    private SalesService saleService;

    @Autowired
    private OrderCountService oCountService;

    @PostMapping("add")
    public Sale addSales(@RequestBody SalesDto saledDto)
    {
      return saleService.addSale(saledDto);  
    }

    @GetMapping("")
    public List<Sale> getAllSales()
    {
        return saleService.findAllSales();
    }

    //Getting the count of ordered products
    @GetMapping("/top/{limit}")
    public List<ProductOrderCount> topProducts(@PathVariable("limit") Integer limit)
    {
      return oCountService.getProductOrderCount(limit);
    }

    @GetMapping("delivery/status")
    public Map<String,Long> getDeliveryStatus()
    {
      return saleService.deliveryStatus();
    }

    @GetMapping("/stats")
    public List<Sale> getSales(@RequestParam(value = "day",required = false) Integer day,
    @RequestParam("month") Integer month,
    @RequestParam("year") Integer year)
    {
      return saleService.getSalesBy(day, month, year);
    }

    @GetMapping("chart")
    public  List<SalesChartDto> salesChart(@RequestParam(value = "start", required = false) Integer startMonth,
    @RequestParam(value = "end",required = false) Integer endMonth, @RequestParam(value = "year",required = false) Integer year)
    {
      return saleService.graph(startMonth,endMonth,year);
    }

    @GetMapping("chart/top")
    public  ProductSaleDto topSalesChart(@RequestParam(value = "start", required = false) Integer startMonth,
    @RequestParam(value = "end",required = false) Integer endMonth, @RequestParam(value = "year",required = false) Integer year)
    {
      return saleService.topProductGraph(startMonth, endMonth, year);
    }



}
