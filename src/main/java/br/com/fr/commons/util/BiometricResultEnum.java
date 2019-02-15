package br.com.fr.commons.util;
/**
 *
 * @author Romeu Franzoia Jr
 */
public enum BiometricResultEnum {
    
    RB_SUCCESS(0, "Sucesso"), 
    RB_FAIL(1, "Falha"), 
    RB_ERROR(2, "Erro"),
    RB_CANCELAR(3, "Cancelar"),
    RB_SEM_BIOMETRIA(4, "Sem Biometria"),
    RB_NAO_EXISTE(4, "Candidato Não Existe");
    
    private int codigo;
    private String mensagem;

    BiometricResultEnum(int codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}