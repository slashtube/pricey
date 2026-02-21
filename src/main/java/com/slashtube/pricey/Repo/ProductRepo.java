package com.slashtube.pricey.Repo;

import org.springframework.data.repository.CrudRepository;

import com.slashtube.pricey.Model.Product;

public interface ProductRepo extends CrudRepository<Product, String>{
    
}
