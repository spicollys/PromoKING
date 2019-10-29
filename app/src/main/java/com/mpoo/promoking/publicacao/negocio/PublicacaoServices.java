package com.mpoo.promoking.publicacao.negocio;

import com.mpoo.promoking.infra.exception.PublicacaoJaRealizadaException;
import com.mpoo.promoking.infra.persistencia.AbstractSQLite;
import com.mpoo.promoking.publicacao.dominio.Publicacao;
import com.mpoo.promoking.publicacao.persistencia.PublicacaoDAO;

import java.io.IOException;

public class PublicacaoServices extends AbstractSQLite {
    public Publicacao getPublicacao(long publicacaoId) throws IOException {
        Publicacao result = null;
        PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
        result = publicacaoDAO.get(publicacaoId);
        return result;
    }
    public Publicacao salvarPublicacao(Publicacao publicacao) throws IOException, PublicacaoJaRealizadaException {
        if(getPublicacao(publicacao.getId()) != null) {
            throw new PublicacaoJaRealizadaException("Publicação já existe.");
        }
        PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
        publicacaoDAO.insert(publicacao);
        return getPublicacao(publicacao.getId());
    }
    public void atualizarPublicacao(Publicacao publicacao) throws IOException {
        if (getPublicacao(publicacao.getId()) != null) {
            PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
            publicacaoDAO.update(publicacao);
        }
    }
    public void deletarPublicacao(Publicacao publicacao) throws IOException {
        if (getPublicacao(publicacao.getId()) != null) {
            PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
            publicacaoDAO.delete(publicacao);
        }
    }
}
