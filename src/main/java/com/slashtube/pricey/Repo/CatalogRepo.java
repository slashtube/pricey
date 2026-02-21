package com.slashtube.pricey.Repo;

import org.springframework.data.repository.CrudRepository;

import com.slashtube.pricey.Model.Catalog;

public interface CatalogRepo extends CrudRepository<Catalog, String>{
    
}
