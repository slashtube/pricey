package com.slashtube.pricey.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slashtube.pricey.Model.Product;

public interface ProductRepo extends JpaRepository<Product, String>{
    
}
