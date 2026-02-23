package com.slashtube.pricey.Model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Products")
@NoArgsConstructor
public class Product {
    @Id
    @Getter
    private String EAN;

    @Getter
    private String description;

    @OneToMany(mappedBy = "product")
    Set<Entry> entries;

    public Product(String EAN, String description) {
        this.EAN = EAN;
        this.description = description;
    }
    
}