package br.com.fr.commons;

import java.io.Serializable;

public class UserLogin implements Serializable {

    private static final long serialVersionUID = -2780831342417493634L;

    private String login;
    private String senha;
    private String nome;
    private String perfil;
    private String acoes;
    private String funcoes;
    private String sistema;
    private String erro;

    public UserLogin() {
    }

    public UserLogin(String login) {
        super();
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPeril(String peril) {
        this.perfil = peril;
    }

    public String getAcoes() {
        return acoes;
    }

    public void setAcoes(String acoes) {
        this.acoes = acoes;
    }

    public String getFuncoes() {
        return funcoes;
    }

    public void setFuncoes(String funcoes) {
        this.funcoes = funcoes;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    @Override
    public String toString() {
        return "UsuarioLogin [getLogin()=" + getLogin() + ", getNome()=" + getNome() + ", getPeril()=" + getPerfil()
                + ", getAcoes()=" + getAcoes() + ", getFuncoes()=" + getFuncoes() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((acoes == null) ? 0 : acoes.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((perfil == null) ? 0 : perfil.hashCode());
        result = prime * result + ((senha == null) ? 0 : senha.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserLogin other = (UserLogin) obj;
        if (acoes == null) {
            if (other.acoes != null) {
                return false;
            }
        } else if (!acoes.equals(other.getAcoes())) {
            return false;
        }
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!login.equals(other.getLogin())) {
            return false;
        }
        if (nome == null) {
            if (other.nome != null) {
                return false;
            }
        } else if (!nome.equals(other.getNome())) {
            return false;
        }
        if (perfil == null) {
            if (other.perfil != null) {
                return false;
            }
        } else if (!perfil.equals(other.getPerfil())) {
            return false;
        }
        if (senha == null) {
            if (other.senha != null) {
                return false;
            }
        } else if (!senha.equals(other.getSenha())) {
            return false;
        }
        return true;
    }
}
