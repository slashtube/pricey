package com.github.slashtube.ProdottoManager;

import java.util.Comparator;

public class OrdinaPerDescrizione implements Comparator<Prodotto> {

    @Override
    public int compare(Prodotto o1, Prodotto o2) {
        String descrizione1 = o1.getDescrizione();
        String descrizione2 = o2.getDescrizione();

        return descrizione1.compareTo(descrizione2);
    }


}
