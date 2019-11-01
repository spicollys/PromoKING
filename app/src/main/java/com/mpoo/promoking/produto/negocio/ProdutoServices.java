package com.mpoo.promoking.produto.negocio;

import androidx.core.util.Consumer;

import com.mpoo.promoking.infra.exception.MarcaJaCadastradaException;
import com.mpoo.promoking.infra.exception.ProdutoJaCadastradoException;
import com.mpoo.promoking.infra.exception.ProdutoNaoCadastradoException;
import com.mpoo.promoking.infra.persistencia.AbstractSQLite;
import com.mpoo.promoking.produto.dominio.Produto;
import com.mpoo.promoking.produto.persistencia.ProdutoDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class ProdutoServices extends AbstractSQLite {
    public ProdutoServices() {
    }

    public Produto getProduto(String tipoProduto) throws IOException {
        Produto result = null;
        ProdutoDAO produtoDAO = new ProdutoDAO();
        result = produtoDAO.get(tipoProduto);
        return result;
    }

    public Produto registrarProduto(Produto produto) throws IOException, ProdutoJaCadastradoException {
        if (getProduto(produto.getTipo()) != null) {
            throw new ProdutoJaCadastradoException("Produto já cadastrado.");
        }
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.insert(produto);
        return getProduto(produto.getTipo());
    }

    public void registrarMarca(Produto produto, String novaMarca) throws IOException, MarcaJaCadastradaException, ProdutoNaoCadastradoException {
        if (getProduto(produto.getTipo()) == null) {
            throw new ProdutoNaoCadastradoException("Produto não cadastrado");
        } else if (procuraMarcaExistente(novaMarca, produto.getMarcas())) {
            throw new MarcaJaCadastradaException("Marca já cadastrada");
        } else {
            String marcasAtualizadas = produto.getMarcas().toUpperCase() + novaMarca.toUpperCase() + " ";
            produto.setMarcas(marcasAtualizadas);
            ProdutoDAO produtoDAO = new ProdutoDAO();
            produtoDAO.update(produto);
        }
    }

    public boolean procuraMarcaExistente(String marcaChecada, String marcasConcatenadas) {
        Boolean result = false;
        if (marcasConcatenadas.toUpperCase().contains(marcaChecada.toUpperCase())) {
            result = true;
        }
        return result;
    }
    public List<String> listaMarcas (Produto produto) {
        List <String> lista = new ArrayList<String>(Arrays.asList(produto.getMarcas().split(" ")));
        Collections.sort(lista);
        List <String> lista_formatada = new ArrayList<String>();
        for (String marca : lista) {
            marca = StringUtils.capitalize(marca).replaceAll("_", " ");
            lista_formatada.add(marca);
        }
        return lista_formatada;
    }
    public List<Produto> retornarListaProdutosDisponiveis() throws IOException {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Produto> listaProdutos = produtoDAO.list();
        return listaProdutos;
    }
    public List<String> retornarListaStringProdutosDisponiveis() throws IOException {
        List <String> lista_formatada = new ArrayList<String>();
        for (Produto produto : retornarListaProdutosDisponiveis()) {
            lista_formatada.add(StringUtils.capitalize(produto.getTipo()).replaceAll("_", " "));
        }
        return lista_formatada;
    }
}


