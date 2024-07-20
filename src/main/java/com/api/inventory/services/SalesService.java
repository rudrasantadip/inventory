package com.api.inventory.services;



import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.inventory.repositories.ProductRepo;
import com.api.inventory.repositories.SalesRepo;


import com.api.inventory.dtos.ProductSaleDto;
import com.api.inventory.dtos.SalesChartDto;
import com.api.inventory.dtos.SalesDto;
import com.api.inventory.entities.Product;
import com.api.inventory.entities.ProductOrderCount;
import com.api.inventory.entities.Sale;

@Service
public class SalesService 
{
    @Autowired
    private SalesRepo salesRepo;
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderCountService oCountService;
  

 

    public Sale addSale(SalesDto sDto)
    {
        LocalDate ldDate = LocalDate.now();
        Sale s = new Sale();
        List<Product> products=new ArrayList<>();
        Double total=0.0d;
        
        for(ProductSaleDto pDto : sDto.getProducts())
        {  
            Product p = productRepo.getProduct(pDto.getProductName()).get();
            p.setQuantity(p.getQuantity()-pDto.getQuantityBought());
            products.add(p);
            total += (double) (p.getPrice()*pDto.getQuantityBought());

            //updating the order count of the given product
            oCountService.updateOrderCount(pDto);
        }

       

        s.setDay(ldDate.getDayOfMonth());
        s.setMonth(ldDate.getMonthValue());
        s.setYear(ldDate.getYear());
        s.setProducts(products);
        s.setTotal(total);
        s.setDeliveryStatus("Ordered");

       
        return salesRepo.save(s);
    }

    public List<Sale> findAllSales() {
       return salesRepo.findAll();
    }


    public List<ProductOrderCount> topThreeProducts(Integer limit)
    {
        return oCountService.getProductOrderCount(limit);
    }

    public Map<String,Long> deliveryStatus()
    {
        List<SalesDto> delStatus = salesRepo.getDeliveryStatus();
        Map<String,Long> statuses = new HashMap<>();

        statuses.put("ordered", 0l);
        statuses.put("shipped", 0l);
        statuses.put("delivered", 0l);
        statuses.put("cancelled", 0l);


        for(SalesDto s:delStatus)
        {
            statuses.put(s.getStatus().toLowerCase(), s.getCount());
        }
        return statuses;
    }

    public List<Sale> getSalesBy(Integer day, Integer month, Integer year)
    {
        return salesRepo.getBy(month, year);
    }

    public List<Sale> getSalesBy(Integer month, Integer year)
    {
        return salesRepo.getBy(month, year);
    }

    public  List<SalesChartDto> graph(Integer startMonth, Integer endMonth, Integer year)
    {
       List<SalesChartDto> salesChart= new ArrayList<>();
       List<ProductOrderCount> topProduct= oCountService.getProductOrderCount(1);
       Product topP = productService.getByName(topProduct.get(0).getProductName());
       topProduct = oCountService.topProductStats(topProduct.get(0).getProductName());

        for(int i=startMonth;i<=endMonth;i++)
        {
           salesChart.add(
            new SalesChartDto(
                Month.of(i).toString(),
                calcMonthlySales(i, year)
                ,0.0d)
           );
        }

           //adding the top product's sales data
           for(ProductOrderCount poc : topProduct)
           {
               salesChart.get(poc.getMonth()-1).setTopProductSales((double)poc.getOrderCount()*topP.getPrice());
           }
        return salesChart;
    }

    public ProductSaleDto topProductGraph(Integer startMonth, Integer endMonth, Integer year)
    {
       List<SalesChartDto> salesChartDtos= new ArrayList<>();
        List<ProductOrderCount> topProduct= oCountService.getProductOrderCount(1);
        Product topP = productService.getByName(topProduct.get(0).getProductName());
        topProduct = oCountService.topProductStats(topProduct.get(0).getProductName());

        System.out.println(topProduct);
        for(int i=startMonth;i<=endMonth;i++)
        {
            salesChartDtos.add(
                new SalesChartDto(Month.of(i).toString(), 0.0d)
            );
        }

        //adding the top product's sales data
        for(ProductOrderCount poc : topProduct)
        {
            salesChartDtos.get(poc.getMonth()-1).setMonth(Month.of(poc.getMonth()).toString());
            salesChartDtos.get(poc.getMonth()-1).setTotalSales((double)poc.getOrderCount()*topP.getPrice());
        }


        return new ProductSaleDto(topP.getProductName(), salesChartDtos);
    }

    Double calcMonthlySales(Integer month,Integer year)
    {
        Double total=0.0d;
        List<Sale> salesList = salesRepo.getBy(month, year);
        for(Sale s :salesList)
        {
            total+=s.getTotal();
        }
        return total;
    }



}
