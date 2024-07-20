package com.api.inventory.services;

import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.inventory.dtos.ProductSaleDto;
import com.api.inventory.entities.Product;
import com.api.inventory.entities.ProductOrderCount;
import com.api.inventory.repositories.OrderCountRepo;
import com.api.inventory.repositories.ProductRepo;

@Service
public class OrderCountService 
{
    @Autowired
    OrderCountRepo oCountRepo;
    @Autowired
    ProductRepo productRepo;

    /**
     *@param p the ProductSaleDTo Object
     *@return true if the order is updated , false if it is not updated
     @apiNote it updates if the quantity is already existing in the database
     but creates a new quantity if not
     */
    public Boolean updateOrderCount(ProductSaleDto p)
    {
        Boolean flag = false;
        Optional<Product> op=productRepo.getProduct(p.getProductName());
        LocalDate currDate =LocalDate.now();
        if(op.isPresent())
        {
        ProductOrderCount poc= oCountRepo.
        getBy(p.getProductName(),
        currDate.getDayOfMonth(),
        currDate.getMonthValue(),
        currDate.getYear());

        if(poc!=null) //if the product is present  only increase the order count
        {
            poc.setOrderCount(poc.getOrderCount()+p.getQuantityBought());
        }
        else{ //else create a new product count entry
            poc = new ProductOrderCount();

            poc.setProductName(p.getProductName());
            poc.setDay(currDate.getDayOfMonth());
            poc.setMonth(currDate.getMonthValue());
            poc.setYear(currDate.getYear());
            poc.setOrderCount(p.getQuantityBought());
        }
        oCountRepo.save(poc);
        flag=true;
        }
        return flag;
    }


    public List<ProductOrderCount> getProductOrderCount(Integer limit)
    {
        List<ProductOrderCount> allProductCounts =  oCountRepo.topProducts();
        return allProductCounts.subList(0, limit);
    }

    public List<ProductOrderCount> topProductStats(String name)
    {
        return oCountRepo.topProductStats(name);
    }

    public List<ProductOrderCount> getAllProductCounts()
    {
        return oCountRepo.findAll();
    }

}
