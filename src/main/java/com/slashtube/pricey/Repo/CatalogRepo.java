package com.slashtube.pricey.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slashtube.pricey.Model.Catalog;

public interface CatalogRepo extends JpaRepository<Catalog, String>{
    
}
