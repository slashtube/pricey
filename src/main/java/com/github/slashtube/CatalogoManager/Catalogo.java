package com.github.slashtube.CatalogoManager;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.slashtube.ProdottoManager.OrdinaPerDescrizione;
import com.github.slashtube.ProdottoManager.Prodotto;

public class Catalogo {
    final private HashMap<String, Prodotto> list;

    public Catalogo() {
        this.list = new HashMap<>();
    }

    public HashMap<String, Prodotto> getList() {
        return this.list;
    }

    public ArrayList<Prodotto> getProdotti() {
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        for(var prodotto : list.entrySet()) {
            prodotti.add(prodotto.getValue());
        }

        prodotti.sort(new OrdinaPerDescrizione());

        return prodotti;
    } 


}
