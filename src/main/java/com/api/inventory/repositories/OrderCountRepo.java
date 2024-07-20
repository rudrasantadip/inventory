package com.api.inventory.repositories;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.inventory.entities.ProductOrderCount;

public interface OrderCountRepo extends JpaRepository<ProductOrderCount,Long>
{
    @Query(value = "SELECT op FROM ProductOrderCount op WHERE op.productName = :name")
    ProductOrderCount getByName(String name);

    @Query(value = "SELECT op FROM ProductOrderCount op WHERE op.productName = :name AND op.day = :day AND op.month = :month AND op.year = :year")
    ProductOrderCount getBy(String name, Integer day,Integer month, Integer year);

    @Query(value = "SELECT new com.api.inventory.entities.ProductOrderCount(poc.productName,SUM(poc.orderCount)) FROM ProductOrderCount poc GROUP BY poc.productName ORDER BY SUM(poc.orderCount) DESC")
    List<ProductOrderCount> topProducts();

    List<ProductOrderCount> findAllByOrderByOrderCountDesc(Pageable p);

    @Query("SELECT NEW com.api.inventory.entities.ProductOrderCount(poc.productName,poc.month,SUM(poc.orderCount)) FROM ProductOrderCount poc WHERE poc.productName = :productName GROUP BY poc.month ORDER BY poc.month")
    List<ProductOrderCount> topProductStats(String productName);
}
