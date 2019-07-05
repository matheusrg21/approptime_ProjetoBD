/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.enums;

/**
 *
 * @author mrg.estagiario
 */
public enum Actions {
    APAGAR("Apagar")
    ,EDITAR("Editar")
    ;

    private String descricao;

    private Actions(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
