package com.github.slashtube.CatalogoManager;

import java.util.HashMap;
import java.util.List;

import com.github.slashtube.GUI.PriceyBar;
import com.github.slashtube.ProdottoManager.OrdinaPerDescrizione;
import com.github.slashtube.ProdottoManager.Prodotto;

public class Catalogo {
    final private HashMap<String, Prodotto> map;

    public Catalogo() {
        this.map = new HashMap<>();
    }

    public HashMap<String, Prodotto> getList() {
        return this.map;
    }

    public List<Prodotto> getProdotti() {
        List<Prodotto> prodotti;

        prodotti = map.entrySet().stream()
            .map(kv -> kv.getValue())
            .sorted(new OrdinaPerDescrizione())
            .toList();

        PriceyBar.increaseProgress();
        return prodotti;
    } 


}
