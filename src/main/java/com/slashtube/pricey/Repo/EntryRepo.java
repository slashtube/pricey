package com.slashtube.pricey.Repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.slashtube.pricey.Model.Entry;
import com.slashtube.pricey.Model.EntryKey;


public interface EntryRepo extends JpaRepository<Entry, EntryKey>{
    @Query(
        value = "select distinct e.EAN, p.description, e.price, e.file, e.reference from entries e inner join products p on p.EAN = e.EAN ORDER BY p.description",
        nativeQuery = true
    )
    List<Entry> findEntryWithDescription();

    @Query(
        value = "select * from entries e where e.EAN = :ean order by e.price asc",
        nativeQuery = true
    )
    List<Entry> findEntryByEAN(@Param("ean") String EAN);
}
