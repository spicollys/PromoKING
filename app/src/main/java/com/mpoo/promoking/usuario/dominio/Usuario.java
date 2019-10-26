package com.mpoo.promoking.usuario.dominio;

import com.mpoo.promoking.cliente.dominio.Cliente;
import com.mpoo.promoking.estabelecimentoComercial.dominio.EstabelecimentoComercial;
import com.mpoo.promoking.infra.exception.IdTipoUsuarioInvalidoException;
import com.mpoo.promoking.infra.exception.UsuarioUsernameInvalidoException;
import com.mpoo.promoking.infra.exception.UsuarioSenhaInvalidaException;
import com.mpoo.promoking.util.EmailValidator;
import com.mpoo.promoking.infra.exception.UsuarioEmailInvalidoException;

public class Usuario {
    private TipoUsuario tipoUsuario;
    private String username;
    private String email;
    private String senha;
    private Cliente cliente;
    private EstabelecimentoComercial estabelecimentoComercial;

    public Usuario() {
    }

    public String getIdTipoUsuario() {
        return tipoUsuario.toString();
    }

    public void setIdTipoUsuario(TipoUsuario tipoUsuario) throws IdTipoUsuarioInvalidoException {
        if (tipoUsuario == null) {
            throw new IdTipoUsuarioInvalidoException("Tipo de usuário Inválido");
        }
        this.tipoUsuario = tipoUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws UsuarioUsernameInvalidoException {
        if (username == null) {
            throw new UsuarioUsernameInvalidoException("Nome de usuário inválido");
        } else if (username.length() < 8) {
            throw new UsuarioUsernameInvalidoException("O nome de usuário deve ter ao menos, 8 caracteres");
        }
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws UsuarioEmailInvalidoException {
        EmailValidator emailValidator = new EmailValidator();

        if (email == null || !emailValidator.isValidEmail(email)) {
            throw new UsuarioEmailInvalidoException("Endereço de email inválido");
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) throws UsuarioSenhaInvalidaException {
        if (senha == null) {
            throw new UsuarioSenhaInvalidaException("Senha inválida");
        } else if (senha.length() < 8) {
            throw new UsuarioSenhaInvalidaException("A senha deve ter ao menos, 8 caracteres");
        }
        this.senha = senha;
    }
}