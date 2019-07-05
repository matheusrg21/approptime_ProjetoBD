/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.tabela;

import approptime.bo.AppropTimeBo;
import approptime.frame.TelaPrincipal;
import approptime.frame.TelaRelatorio;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author mrg.estagiario
 */
public class DeleteAction extends AbstractAction {

   private final JTable table;
   private final AppropTimeBo appropTimeBo;
   private final TelaRelatorio telaRelatorio;

   public DeleteAction(JTable table, TelaRelatorio telaRelatorio, AppropTimeBo appropTimeBo) {
      this.table = table;
      this.telaRelatorio = telaRelatorio;
      this.appropTimeBo = appropTimeBo;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      int row = table.convertRowIndexToModel(table.getEditingRow());
      Object columnId = table.getModel().getValueAt(row, 0);
      Object columnTarefa = table.getModel().getValueAt(row, 1);
      StringBuilder mensagemDeConfirmacao = new StringBuilder();

      mensagemDeConfirmacao.append("Deseja realmente exluir esta atividade?");
      mensagemDeConfirmacao.append("\n");
      mensagemDeConfirmacao.append("Id: " + String.valueOf(columnId));
      mensagemDeConfirmacao.append("\n");
      mensagemDeConfirmacao.append("Tarefa vinculada: " + String.valueOf(columnTarefa));

      int confirmDialogResult = JOptionPane.showConfirmDialog(table, mensagemDeConfirmacao.toString(), "Excluir atividade", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      Long idAtividadeToDel = Long.valueOf(String.valueOf(columnId.toString()));

      if (confirmDialogResult == 0) {
         try {
            appropTimeBo.deleteAtividade(idAtividadeToDel);
            Thread.sleep(100l);
            telaRelatorio.filtrarAtividades();
         } catch (Exception ex) {
            if (TelaPrincipal.isDebugHabilitado()) {
               Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
      }
   }
}
