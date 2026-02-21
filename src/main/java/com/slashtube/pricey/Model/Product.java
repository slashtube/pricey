package com.slashtube.pricey.Model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @Getter
    @GeneratedValue
    private String EAN;

    @Getter
    private String description;

    @OneToMany(mappedBy = "product")
    Set<Entry> entries;
    
}
