package com.slashtube.pricey.Model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EntryKey implements Serializable{
    @Column(name = "EAN")
    private String EAN;

    @Column(name = "File")
    private String File;
    
}
