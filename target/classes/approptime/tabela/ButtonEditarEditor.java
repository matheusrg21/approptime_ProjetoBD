/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.tabela;

import approptime.bo.AppropTimeBo;
import approptime.frame.TelaPrincipal;
import approptime.frame.TelaRelatorio;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractCellEditor;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author mrg.estagiario
 */
public class ButtonEditarEditor extends AbstractCellEditor implements TableCellEditor {

   private final MyTableButton btn = new MyTableButton();
   private final JTable table;
   private Object o;

   public ButtonEditarEditor(JTable table, TelaPrincipal telaPrincipal, TelaRelatorio telaRelatorio, AppropTimeBo appropTimeBo) {
      super();
      this.table = table;
      btn.setAction(new EditAction(table, telaPrincipal, telaRelatorio, appropTimeBo));

      EditingStopHandler handler = new EditingStopHandler();

      btn.addMouseListener(handler);
      btn.addActionListener(handler);
   }

   private class EditingStopHandler extends MouseAdapter implements ActionListener {

      @Override
      public void mousePressed(MouseEvent e) {
         Object o = e.getSource();
         if (o instanceof TableCellEditor) {
            actionPerformed(null);
         } else if (o instanceof JButton) {
            ButtonModel m = ((JButton) e.getComponent()).getModel();
            if (m.isPressed() && table.isRowSelected(table.getEditingRow()) && e.isControlDown()) {
               btn.setBackground(table.getBackground());
            }
         }
      }

      @Override
      public void actionPerformed(ActionEvent e) {
         EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
               fireEditingStopped();
            }
         });
      }
   }

   @Override
   public Component getTableCellEditorComponent(
           JTable table, Object value, boolean isSelected, int row, int column) {
      btn.setBackground(table.getSelectionBackground());
      o = value;
      return btn;
   }

   @Override
   public Object getCellEditorValue() {
      return o;
   }

}
