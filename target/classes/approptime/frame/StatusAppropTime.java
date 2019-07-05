/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.frame;

import approptime.enums.TypeErro;
import java.util.List;

/**
 *
 * @author mrg.estagiario
 */
public class StatusAppropTime {
    
    private boolean sucesso;
    private String mensagem;
    private List<String> erros;
    private TypeErro typeErro;
    
    public StatusAppropTime() {
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<String> getErros() {
        return erros;
    }

    public void setErros(List<String> erros) {
        this.erros = erros;
    }

    public TypeErro getTypeErro() {
        return typeErro;
    }

    public void setTypeErro(TypeErro typeErro) {
        this.typeErro = typeErro;
    }
    
    
    
}
