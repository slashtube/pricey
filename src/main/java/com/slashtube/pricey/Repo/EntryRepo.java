package com.slashtube.pricey.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slashtube.pricey.Model.Entry;
import com.slashtube.pricey.Model.EntryKey;

public interface EntryRepo extends JpaRepository<Entry, EntryKey> {
    
}
