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
import javax.swing.AbstractAction;
import javax.swing.JTable;

/**
 *
 * @author mrg.estagiario
 */
public class EditAction extends AbstractAction {

   private final JTable table;
   private final AppropTimeBo appropTimeBo;
   private final TelaPrincipal telaPrincipal;
   private final TelaRelatorio telaRelatorio;

   public EditAction(JTable table, TelaPrincipal telaPrincipal, TelaRelatorio telaRelatorio, AppropTimeBo appropTimeBo) {
      this.table = table;
      this.telaPrincipal = telaPrincipal;
      this.telaRelatorio = telaRelatorio;
      this.appropTimeBo = appropTimeBo;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      int row = table.convertRowIndexToModel(table.getEditingRow());
      Object o = table.getModel().getValueAt(row, 0);

      telaPrincipal.setEditando(true);

      telaPrincipal.setAtividadeFromTelaRelatorio(appropTimeBo.getAtividadeById(Integer.valueOf(o.toString())));

      telaRelatorio.setVisible(false);
      telaPrincipal.setAutoRequestFocus(true);
      telaPrincipal.requestFocus();
      telaPrincipal.setVisible(true);

   }

}
