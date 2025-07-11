package com.github.slashtube.CatalogoManager;

import java.util.HashMap;
import java.util.List;

import com.github.slashtube.GUI.PriceyBar;
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

    public List<Prodotto> getProdotti() {
        List<Prodotto> prodotti;

        prodotti = list.entrySet().stream()
            .map(kv -> kv.getValue())
            .sorted(new OrdinaPerDescrizione())
            .toList();

        PriceyBar.increaseProgress();
        return prodotti;
    } 


}
