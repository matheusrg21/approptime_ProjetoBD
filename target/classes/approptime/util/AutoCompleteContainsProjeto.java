/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.util;

import approptime.bo.AppropTimeBo;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author mrg.estagiario
 */
public class AutoCompleteContainsProjeto extends JComboBox {

   //Itens do comboBox
   private List<String> allItens = new ArrayList<>();
   private long idUsuario = -1;
   private AppropTimeBo appropTimeBo = null;
   private String palavraNaoEncontrada = "";

   public void setBo(AppropTimeBo appropTimeBo) {
      this.appropTimeBo = appropTimeBo;
   }

   public void setIdUsuario(long id) {
      this.idUsuario = id;
   }

   public void setAllItens(List<String> itens) {
      this.allItens = itens;
   }

   public AutoCompleteContainsProjeto() {
      super();

      setEditable(true);

      for (String item : allItens) {
         addItem(item);
      }

      Component c = getEditor().getEditorComponent();

      if (c instanceof JTextComponent) {

         final JTextComponent tc = (JTextComponent) c;

         tc.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent arg0) {
               update();
            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
               update();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
               update();
            }

            public void update() {

               //perform separately, as listener conflicts between the editing component
               //and JComboBox will result in an IllegalStateException due to editing
               //the component when it is locked.
               SwingUtilities.invokeLater(new Runnable() {

                  @Override
                  public void run() {
                     if (tc.getText().length() > Constantes.MAX_LENGTH_TEXT_TAREFA) {
                        tc.setText(UtilMethods.maxlength(tc.getText(), Constantes.MAX_LENGTH_TEXT_TAREFA));
                     }

                     if (tc.getText().length() < Constantes.MINIMO_DE_CARACTERES_PARA_BUSCA_BD) {
                        setPopupVisible(false);
//                        return;
                     }

                     if (Objects.nonNull(appropTimeBo) && (StringUtils.isEmpty(palavraNaoEncontrada) ||
                                                           tc.getText().length() == Constantes.MINIMO_DE_CARACTERES_PARA_BUSCA_BD)) {
                        //todos itens que contêm as palavras digitadas pelo usuario (case insensitive)
                        allItens = appropTimeBo.getDescricaoTagsByUsuario(idUsuario);
                     }

                     List<String> itensFounds = new ArrayList<>(searchItens(tc.getText(), allItens));

                     if (Objects.nonNull(itensFounds) && itensFounds.isEmpty()) {
                        palavraNaoEncontrada = tc.getText();
                     } else {
                        palavraNaoEncontrada = "";
                     }

                     setEditable(false);

                     removeAllItems();

                     //if itensFounds contains the search text, then only add once.
                     //Novo item digitado

//                     for (String itensFound : itensFounds) {
//                        if(StringUtils.containsIgnoreCase(itensFound, tc.getText()) == false){
//                           addItem(tc.getText());
//                        }
//                     }



                     if (itensFounds.contains(tc.getText()) == false) {
                        addItem(tc.getText());
                     }


                     //Adiciona itens que contêm as palavras digitadas
                     for (String s : itensFounds) {
                        addItem(s);
                     }

                     setEditable(true);
                     if (isEnabled()) {
                        setPopupVisible(true);
                     }

                     tc.requestFocus();
                  }

               });

            }

         });

         //When the text component changes, focus is gained
         //and the menu disappears. To account for this, whenever the focus
         //is gained by the JTextComponent and it has searchable values, we show the popup.
         tc.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent arg0) {
               if (tc.getText().length() > 0 && !allItens.contains(tc.getText()) && getSelectedIndex() != -1) {
                  setPopupVisible(true);
               }
            }

            @Override
            public void focusLost(FocusEvent arg0) {
               setPopupVisible(false);
            }

         });

      } else {
         throw new IllegalStateException("Editing component is not a JTextComponent!");
      }

   }

   //Percorre itens buscando itens que contém value(letras digitadas pelo usuario)
   public Collection<String> searchItens(String value, List<String> allItens) {

      List<String> founds = new ArrayList<>();

      for (String s : allItens) {
         if (s.toLowerCase().contains(value.toLowerCase())) {
            founds.add(s);
         }
      }
      return founds;
   }
}
