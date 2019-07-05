/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.util;

import approptime.frame.TelaPrincipal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;

/**
 *
 * @author mrg.estagiario
 */
public class AtualizaHoras extends Thread {

   private JLabel labelDuracao;
   private Date inicio;
   private boolean stop = true;

   public void setLabelDuracao(JLabel labelDuracao) {
      this.labelDuracao = labelDuracao;
   }

   public boolean isStop() {
      return stop;
   }

   public void setStop(boolean stop) {
      this.stop = stop;
   }

   public void setInicio(Date inicio) {
      this.inicio = inicio;
   }

   @Override
   public void run() {

      try {
         while (!stop) {
            Date atual = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            this.labelDuracao.setText(UtilMethods.imprimirTempoProcessamento(inicio.getTime()));
            Thread.sleep(1000);
         }
      } catch (InterruptedException ex) {
         if(TelaPrincipal.isDebugHabilitado()){
            System.out.println("Problema na atualização da data/hora");
         }
      }
      labelDuracao = null;
   }
}
