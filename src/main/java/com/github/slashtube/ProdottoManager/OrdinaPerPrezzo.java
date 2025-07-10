package com.github.slashtube.ProdottoManager;

import java.util.Comparator;

public class OrdinaPerPrezzo implements Comparator<ProdottoEntry> {

    @Override
    public int compare(ProdottoEntry o1, ProdottoEntry o2) {
        Double price1 = o1.getPrice();
        Double price2 = o2.getPrice();

        return price1.compareTo(price2);
    }

}
