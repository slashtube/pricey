package com.slashtube.pricey.Model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "Entries")
public class Entry {
    @EmbeddedId
    private EntryKey key;

    @ManyToOne
    @MapsId("EAN")
    @JoinColumn(name="EAN")
    private String barcode;

    @ManyToOne
    @MapsId("File")
    @JoinColumn(name="File")
    private String filename;
    
    @Getter
    private float price;
}
