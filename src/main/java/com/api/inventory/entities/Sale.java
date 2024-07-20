package com.api.inventory.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_table")
@Data
@NoArgsConstructor
public class Sale 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;

    private Integer day;
    private Integer month;
    private Integer year;
    private Double total;
    private String deliveryStatus;


    @ManyToMany()
    @JoinTable(name = "sale_product", joinColumns = @JoinColumn(name="sale_id"),
    inverseJoinColumns = @JoinColumn(name="product_id"))
    private List<Product> products;

 


}
