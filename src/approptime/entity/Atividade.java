/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.entity;

import approptime.util.Constantes;
import approptime.util.UtilMethods;
import java.util.Date;
import java.util.Objects;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author mrg.estagiario
 */
public class Atividade {

   private Long id;
   private Tarefa tarefa;
   private Date inicio;
   private Date termino;
   private String observacoes;
   private Tag tag;

   public static String getCsvHeader(String separador) {
      return "id_atividade" + separador
              + "dt_inicio_atividade" + separador
              + "dt_termino_atividade" + separador
              + "observacoes_atividade" + separador
              + Tarefa.getCsvHeader(separador);
   }

   public String toCsv(String separador, String delimitador) {
      if(Objects.isNull(this.termino)){
         return "";
      }
      String observacoesContinuo = observacoes.replaceAll("(\\r|\\n)", Constantes.SIMBOLO_NOVA_LINHA);
      return delimitador + id + delimitador + separador
              + delimitador + inicio + delimitador + separador
              + delimitador + termino + delimitador + separador
              + delimitador + observacoesContinuo + delimitador + separador
              + tarefa.toCsv(separador, delimitador);
   }

   public Atividade() {
   }

   public Atividade(Tarefa tarefa, Date inicio, Date termino, String observacoes) {
      this.tarefa = tarefa;
      this.inicio = inicio;
      this.termino = termino;
      this.observacoes = observacoes;
   }

   public Atividade cloneData() {
      Atividade toReturn = new Atividade();
      toReturn.setId(-1L);
      toReturn.setInicio(this.inicio);
      toReturn.setTermino(this.termino);
      toReturn.setTarefa(this.tarefa);
      toReturn.setObservacoes(this.observacoes);

      return toReturn;
   }

   public static Atividade getAtividadeFromCSVLine(String linhaCsv, String delimitador) {
      Atividade retorno = new Atividade();

      String[] elementos = linhaCsv.split(delimitador);

      if (elementos.length < Constantes.INDICE_CSV_CAMPO_OBSERVACAO_ATIVIDADE) {
         throw new IllegalArgumentException("Dados obrigatórios para extração da atividade não fornecidos no csv: " + elementos.length + " campos recebidos");
      }

      if (StringUtils.isEmpty(elementos[Constantes.INDICE_CSV_CAMPO_ID_ATIVIDADE])) {
         throw new IllegalArgumentException("Id da atividade não fornecido no csv");
      } else if (StringUtils.isNumeric(elementos[Constantes.INDICE_CSV_CAMPO_ID_ATIVIDADE]) == false) {
         throw new IllegalArgumentException("Id da atividade é inválido: precisa ser um número inteiro");
      } else {
         retorno.setId(Long.valueOf(elementos[Constantes.INDICE_CSV_CAMPO_ID_ATIVIDADE]));
      }

      if (StringUtils.isEmpty(elementos[Constantes.INDICE_CSV_CAMPO_DT_INICIO_ATIVIDADE])) {
         throw new IllegalArgumentException("Data da atividade não fornecida no csv");
      } else {
         Date inicio = UtilMethods.formataStringParaDate(elementos[Constantes.INDICE_CSV_CAMPO_DT_INICIO_ATIVIDADE], "yyyy-MM-dd HH:mm:ss.S");
         if (Objects.isNull(inicio)) {
            throw new IllegalArgumentException("Data da atividade é incompatível");
         } else {
            retorno.setInicio(inicio);
         }
      }

      if (StringUtils.isEmpty(elementos[Constantes.INDICE_CSV_CAMPO_DT_TERMINO_ATIVIDADE])) {
         throw new IllegalArgumentException("Data da atividade não fornecida no csv");
      } else {
         Date termino = UtilMethods.formataStringParaDate(elementos[Constantes.INDICE_CSV_CAMPO_DT_TERMINO_ATIVIDADE], "yyyy-MM-dd HH:mm:ss.S");
         if (Objects.isNull(termino)) {
            throw new IllegalArgumentException("Data da atividade é incompatível");
         } else {
            retorno.setTermino(termino);
         }
      }

      String observacoesComQuebra = elementos[Constantes.INDICE_CSV_CAMPO_OBSERVACAO_ATIVIDADE].replaceAll(Constantes.SIMBOLO_NOVA_LINHA, System.lineSeparator());
      retorno.setObservacoes(observacoesComQuebra);

      return retorno;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Tarefa getTarefa() {
      return tarefa;
   }

   public void setTarefa(Tarefa tarefa) {
      this.tarefa = tarefa;
   }

   public Date getInicio() {
      return inicio;
   }

   public void setInicio(Date inicio) {
      this.inicio = inicio;
   }

   public Date getTermino() {
      return termino;
   }

   public void setTermino(Date termino) {
      this.termino = termino;
   }

   public String getObservacoes() {
      return observacoes;
   }

   public void setObservacoes(String observacoes) {
      this.observacoes = observacoes;
   }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
