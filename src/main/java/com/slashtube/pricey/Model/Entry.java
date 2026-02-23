package com.slashtube.pricey.Model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Entries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Entry {
    @EmbeddedId
    private EntryKey key;

    @ManyToOne
    @MapsId("EAN")
    @JoinColumn(name="EAN")
    private Product product;

    @ManyToOne
    @MapsId("File")
    @JoinColumn(name="File")
    private Catalog catalog;

    private double price;
    private String reference;



}
