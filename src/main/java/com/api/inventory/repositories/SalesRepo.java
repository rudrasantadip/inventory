package com.api.inventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.inventory.dtos.SalesDto;
import com.api.inventory.entities.Sale;

public interface SalesRepo extends JpaRepository<Sale,Long>
{
    @Query(value = "SELECT NEW com.api.inventory.dtos.SalesDto(s.deliveryStatus,COUNT(*)) FROM Sale s GROUP BY s.deliveryStatus")
    public List<SalesDto> getDeliveryStatus();


    @Query(value = "SELECT s FROM Sale s WHERE s.month = :month AND s.year = :year")
    public List<Sale> getBy(Integer month,Integer year);
}
