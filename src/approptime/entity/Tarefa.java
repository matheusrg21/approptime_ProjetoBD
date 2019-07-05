/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.entity;

import approptime.util.Constantes;
import java.util.Objects;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author mrg.estagiario
 */
public class Tarefa {
    
    private Long id;
    private Usuario user;
    private String descricao;

    public Tarefa() {
    }
    
    public Tarefa cloneData(){
        Tarefa toReturn = new Tarefa();
        toReturn.setId(null);
        toReturn.setUser(this.user);
        toReturn.setDescricao(this.descricao);
        
        return toReturn;
    }
    
    public static String getCsvHeader(String separador){
        return "id_tarefa"          + separador
               + "descricao_tarefa" + separador
               + Usuario.getCsvHeader(separador);
    }
    
    public String toCsv(String separador, String delimitador){
        return    delimitador +  id        + delimitador + separador
               +  delimitador + descricao  + delimitador + separador
               +  user.toCsv(separador, delimitador);
    }
    
    public static Tarefa getTarefaFromCSVLine(String linhaCsv, String delimitador){
        Tarefa retorno = new Tarefa();
        
        String[] elementos = linhaCsv.split(delimitador);
        
        if(elementos.length < Constantes.INDICE_CSV_CAMPO_DESCRICAO_TAREFA){
            throw new IllegalArgumentException("Dados obrigatórios para extração da tarefa não fornecidos no csv: " + elementos.length + " campos recebidos");
        }
        
        if(StringUtils.isEmpty(elementos[Constantes.INDICE_CSV_CAMPO_ID_TAREFA])){
            throw new IllegalArgumentException("Id da tarefa não fornecido no csv");
        }
        else if(StringUtils.isNumeric(elementos[Constantes.INDICE_CSV_CAMPO_ID_TAREFA]) == false){
            throw new IllegalArgumentException("Id da tarefa é inválido: precisa ser um número inteiro");
        }
        else{
            retorno.setId(Long.valueOf(elementos[Constantes.INDICE_CSV_CAMPO_ID_TAREFA]));
        }
        
        if(StringUtils.isEmpty(elementos[Constantes.INDICE_CSV_CAMPO_DESCRICAO_TAREFA])){
            throw new IllegalArgumentException("Descrição da tarefa não fornecida no csv");
        }
        else{
            retorno.setDescricao(elementos[Constantes.INDICE_CSV_CAMPO_DESCRICAO_TAREFA]);
        }
            
        return retorno;
    }
    
    public Tarefa(Usuario user, String descricao) {
        this.user = user;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
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
        final Tarefa other = (Tarefa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
