/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.dao.filter;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author mrg.estagiario
 */
public class AtividadeFilter {

   private Long idTarefaIgualA;
   private String descricaoTarefaContem;
   private String observacaoAtividadeContem;
   private Date dataInicioMaiorIgualA;
   private Date dataTerminoMenorIgualA;
   private Long idUsuarioIgualA;
   private Long numeroBaseParaBuscarNoBanco;
   private boolean emOrdemCronologica;

   public AtividadeFilter cloneData(){
      AtividadeFilter toReturn = new AtividadeFilter();

      toReturn.setIdTarefaIgualA(this.idTarefaIgualA);
      toReturn.setDescricaoTarefaContem(this.descricaoTarefaContem);
      toReturn.setObservacaoAtividadeContem(this.observacaoAtividadeContem);
      toReturn.setDataInicioMaiorQue(this.dataInicioMaiorIgualA);
      toReturn.setDataTerminoMenorQue(this.dataTerminoMenorIgualA);
      toReturn.setIdUsuarioIgualA(this.idUsuarioIgualA);
      toReturn.setNumeroBaseParaBuscarNoBanco(this.numeroBaseParaBuscarNoBanco);
      toReturn.setEmOrdemCronologica(this.emOrdemCronologica);

      return toReturn;
   }
   
   public String getObservacaoAtividadeContem() {
      return observacaoAtividadeContem;
   }

   public void setObservacaoAtividadeContem(String observacaoAtividadeContem) {
      this.observacaoAtividadeContem = observacaoAtividadeContem;
   }

   public boolean isEmOrdemCronologica() {
      return emOrdemCronologica;
   }

   public void setEmOrdemCronologica(boolean emOrdemCronologica) {
      this.emOrdemCronologica = emOrdemCronologica;
   }

   public Long getNumeroBaseParaBuscarNoBanco() {
      return numeroBaseParaBuscarNoBanco;
   }

   public void setNumeroBaseParaBuscarNoBanco(Long numeroBaseParaBuscarNoBanco) {
      this.numeroBaseParaBuscarNoBanco = numeroBaseParaBuscarNoBanco;
   }

   public Long getIdTarefaIgualA() {
      return idTarefaIgualA;
   }

   public void setIdTarefaIgualA(Long idTarefaIgualA) {
      this.idTarefaIgualA = idTarefaIgualA;
   }

   public String getDescricaoTarefaContem() {
      return descricaoTarefaContem;
   }

   public void setDescricaoTarefaContem(String descricaoTarefaContem) {
      this.descricaoTarefaContem = descricaoTarefaContem;
   }

   public Date getDataInicioMaiorIgualA() {
      return dataInicioMaiorIgualA;
   }

   public void setDataInicioMaiorQue(Date dataInicioMaiorQue) {
      this.dataInicioMaiorIgualA = dataInicioMaiorQue;
   }

   public Date getDataTerminoMenorIgualA() {
      return dataTerminoMenorIgualA;
   }

   public void setDataTerminoMenorQue(Date dataTerminoMenorQue) {
      this.dataTerminoMenorIgualA = dataTerminoMenorQue;
   }

   public Long getIdUsuarioIgualA() {
      return idUsuarioIgualA;
   }

   public void setIdUsuarioIgualA(Long idUsuarioIgualA) {
      this.idUsuarioIgualA = idUsuarioIgualA;
   }

   @Override
   public int hashCode() {
      int hash = 31;
      hash = 31 * hash + Objects.hashCode(this.idTarefaIgualA);
      hash = 31 * hash + Objects.hashCode(this.descricaoTarefaContem);
      hash = 31 * hash + Objects.hashCode(this.dataInicioMaiorIgualA);
      hash = 31 * hash + Objects.hashCode(this.dataTerminoMenorIgualA);
      hash = 31 * hash + Objects.hashCode(this.idUsuarioIgualA);
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
      final AtividadeFilter other = (AtividadeFilter) obj;
      if (!Objects.equals(this.idTarefaIgualA, other.idTarefaIgualA)) {
         return false;
      }
      if (!Objects.equals(this.descricaoTarefaContem, other.descricaoTarefaContem)) {
         return false;
      }
      if (!Objects.equals(this.dataInicioMaiorIgualA, other.dataInicioMaiorIgualA)) {
         return false;
      }
      if (!Objects.equals(this.dataTerminoMenorIgualA, other.dataTerminoMenorIgualA)) {
         return false;
      }
      if (!Objects.equals(this.idUsuarioIgualA, other.idUsuarioIgualA)) {
         return false;
      }
      return true;
   }

}
