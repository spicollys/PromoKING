package com.mpoo.promoking.publicacao.negocio;

import com.mpoo.promoking.infra.exception.PublicacaoJaRealizadaException;
import com.mpoo.promoking.infra.persistencia.AbstractSQLite;
import com.mpoo.promoking.publicacao.dominio.Publicacao;
import com.mpoo.promoking.publicacao.persistencia.PublicacaoDAO;
import com.mpoo.promoking.usuario.dominio.TipoUsuario;
import com.mpoo.promoking.usuario.dominio.Usuario;

import java.io.IOException;
import java.util.List;

public class PublicacaoServices extends AbstractSQLite {
    public Publicacao getPublicacao(long publicacaoId) throws IOException {
        Publicacao result = null;
        PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
        result = publicacaoDAO.get(publicacaoId);
        return result;
    }
    public void salvarPublicacao(Publicacao publicacao, Usuario usuario) throws IOException, PublicacaoJaRealizadaException {
        if(getPublicacao(publicacao.getId()) != null) {
            throw new PublicacaoJaRealizadaException("Publicação já existe.");
        }
        PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
        long idPublicacao = publicacaoDAO.insert(publicacao);
        publicacao.setId(idPublicacao);
        if (usuario.getIdTipoUsuario().equals(TipoUsuario.CLIENTE)){
            usuario.getCliente().getArrayListIdPublicacoes().add(String.valueOf(idPublicacao));
        }else {
            usuario.getEstabelecimentoComercial().getArrayListIdPublicacoes().add(String.valueOf(idPublicacao));
        }
    }
    public void atualizarPublicacao(Publicacao publicacao) throws IOException {
        if (getPublicacao(publicacao.getId()) != null) {
            PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
            publicacaoDAO.update(publicacao);
        }
    }
    public void deletarPublicacao(Publicacao publicacao, Usuario usuario) throws IOException {
        if (getPublicacao(publicacao.getId()) != null) {
            PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
            long idPublicacao = publicacao.getId();
            if (usuario.getIdTipoUsuario().equals(TipoUsuario.CLIENTE)){
                long indexPublicacao = usuario.getCliente().getArrayListIdPublicacoes().indexOf(idPublicacao);
                usuario.getCliente().getArrayListIdPublicacoes().remove(indexPublicacao);
            }else {
                long indexPublicacao = usuario.getEstabelecimentoComercial().getArrayListIdPublicacoes().indexOf(idPublicacao);
                usuario.getEstabelecimentoComercial().getArrayListIdPublicacoes().remove(indexPublicacao);
            }
            publicacaoDAO.delete(publicacao);
        }
    }
    public List<Publicacao> retornarListaObjetosPublicacoes() throws IOException {
        PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
        List<Publicacao> listaPublicacoes = publicacaoDAO.list();
        return listaPublicacoes;
    }
}
