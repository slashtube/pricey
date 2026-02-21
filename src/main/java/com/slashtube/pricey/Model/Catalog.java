package com.slashtube.pricey.Model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "Catalogs")
public class Catalog {
    @Id
    @Getter
    @GeneratedValue
    private String file;

    @Getter
    private String name;

    @OneToMany(mappedBy = "catalog")
    Set<Entry> entries;

    
}
