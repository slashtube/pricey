package com.slashtube.pricey.Model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Catalogs")
@NoArgsConstructor
public class Catalog {
    @Id
    @Getter
    private String file;

    @OneToMany(mappedBy = "catalog")
    Set<Entry> entries;

    public Catalog(String file) {
        this.file = file;
    }

    
}