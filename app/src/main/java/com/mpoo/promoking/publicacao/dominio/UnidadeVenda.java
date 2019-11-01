package com.mpoo.promoking.publicacao.dominio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum UnidadeVenda {
    MG,
    G,
    KG,
    ML,
    L,
    UNIDADE;

    public static List<String> listaUnidadeVendaValues() {
        List <UnidadeVenda> listaValues = Arrays.asList(UnidadeVenda.values());
        List <String> listaStringUnidadeVenda = new ArrayList<String>();
        for (UnidadeVenda unidadeVenda : listaValues) {
            listaStringUnidadeVenda.add(unidadeVenda.toString());
        }
        return listaStringUnidadeVenda;
    }
}
