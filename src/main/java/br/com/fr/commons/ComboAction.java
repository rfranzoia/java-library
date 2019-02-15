/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class ComboAction {

    private int id;
    private String descricao;
    private Runnable comando;

    public ComboAction(int id, String descricao, Runnable comando) {
        this.id = id;
        this.descricao = descricao;
        this.comando = comando;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Runnable getComando() {
        return comando;
    }

    public void setComando(Runnable comando) {
        this.comando = comando;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ComboAction other = (ComboAction) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.descricao.trim();
    }
}