package com.slashtube.pricey.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.slashtube.pricey.Model.Product;

public interface ProductRepo extends JpaRepository<Product, String>{
    @Query(
        value = "select distinct p.EAN, p.description from products p inner join entries e on p.EAN = e.EAN ORDER BY p.description",
        nativeQuery = true
    )
    List<Product> findEntryProducts();
    
}
