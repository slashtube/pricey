package com.slashtube.pricey.Model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EntryKey implements Serializable{
    @Column(name = "EAN")
    private String EAN;

    @Column(name = "File")
    private String file;
    
}
