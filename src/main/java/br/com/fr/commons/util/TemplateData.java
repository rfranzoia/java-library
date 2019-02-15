package br.com.fr.commons.util;
import java.util.Objects;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class TemplateData {
    private Integer id;
    private String nome;
    private Template template;
    private byte[] imagem;
    private Integer status;

    public TemplateData(){
    }
    
    public TemplateData(Integer id, String nome, Template template, byte[] imagem, Integer status) {
        this.id = id;
        this.nome = nome;
        this.template = template;
        this.imagem = imagem;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    
    @Override
    public String toString() {
        return id.toString() + " | " + nome.trim() + " | " + template.getSize() + " | " + template.getQuality() + " | " + status.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final TemplateData other = (TemplateData) obj;
        if (!Objects.equals(this.id, other.getId())) {
            return false;
        }
        return true;
    }
    
    
}
