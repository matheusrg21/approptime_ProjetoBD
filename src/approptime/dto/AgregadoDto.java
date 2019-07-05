/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.dto;

import approptime.util.Constantes;
import approptime.util.UtilMethods;

/**
 *
 * @author mrg.estagiario
 */
public class AgregadoDto {

   private String ds_tarefa;
   private String agregador;
   private String tipo;
   private Long totalMinutos;

   public static String getCsvHeader(String separador, String agregador) {
      if(agregador.equals(Constantes.AGREGADOR_GLOBAL)){
         return Constantes.NOME_COLUNA_TAREFA_RELATORIO + separador
                 + Constantes.NOME_COLUNA_TEMPO_GASTO_RELATORIO;
      }
      else{
         return Constantes.NOME_COLUNA_TAREFA_RELATORIO + separador
                 + agregador + separador
                 + Constantes.NOME_COLUNA_TEMPO_GASTO_RELATORIO;
      }
   }

   public String toCsv(String separador, String delimitador, String agregador) {
      if(agregador.equals(Constantes.AGREGADOR_GLOBAL)){
         return delimitador + ds_tarefa + delimitador + separador
                 + delimitador + UtilMethods.calculaTempoEmMinutos(totalMinutos) + delimitador;
      }else if(agregador.equals(Constantes.AGREGADOR_DIARIO)){
         return delimitador + ds_tarefa + delimitador + separador
                 + delimitador + UtilMethods.formatoDataStringBrasil(this.agregador, "yyyy-MM-dd") + delimitador + separador
                 + delimitador + UtilMethods.calculaTempoEmMinutos(totalMinutos) + delimitador;
      }
      else{
         return delimitador + ds_tarefa + delimitador + separador
                 + delimitador + this.agregador + delimitador + separador
                 + delimitador + UtilMethods.calculaTempoEmMinutos(totalMinutos) + delimitador;
      }
   }

   public AgregadoDto() {
   }

   public String getDsTarefa() {
      return ds_tarefa;
   }

   public void setDsTarefa(String ds_tarefa) {
      this.ds_tarefa = ds_tarefa;
   }

   public String getAgregador() {
      return agregador;
   }

   public void setAgregador(String agregador) {
      this.agregador = agregador;
   }

   public String getTipo() {
      return tipo;
   }

   public void setTipo(String tipo) {
      this.tipo = tipo;
   }

   public Long getTotalMinutos() {
      return totalMinutos;
   }

   public void setTotalMinutos(Long totalMinutos) {
      this.totalMinutos = totalMinutos;
   }

}
