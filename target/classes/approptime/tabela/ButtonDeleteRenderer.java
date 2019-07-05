/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.tabela;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author mrg.estagiario
 */
public class ButtonDeleteRenderer implements TableCellRenderer {
   private final MyTableButton button = new MyTableButton(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Delete-icon.png")));

   public ButtonDeleteRenderer() {
      button.setToolTipText("Apaga a atividade em questão");
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object value,
           boolean isSelected, boolean hasFocus, int row, int column) {

      button.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
      return button;
   }
}
