/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.frame;

import approptime.bo.AppropTimeBo;
import approptime.entity.Atividade;
import approptime.entity.Departamento;
import approptime.entity.Projeto;
import approptime.entity.Role;
import approptime.entity.Tag;
import approptime.entity.Tarefa;
import approptime.entity.Team;
import approptime.entity.Usuario;
import approptime.entity.Workspace;
import approptime.util.AtualizaHoras;
import approptime.util.AutoCompleteContains;
import approptime.util.AutoCompleteContainsProjeto;
import approptime.util.AutoCompleteContainsTag;
import approptime.util.AutoCompleteContainsWorkspace;
import approptime.util.Constantes;
import approptime.util.CriptografiaUtil;
import approptime.util.MultiLineCellRenderer;
import approptime.util.UtilMethods;
import static approptime.util.UtilMethods.formataDateParaString;
import static approptime.util.UtilMethods.formataStringParaDate;
import approptime.util.ValidacaoDatas;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.DateValidator;

/**
 *
 * @author mrg.estagiario
 */
public final class TelaPrincipal extends javax.swing.JFrame {

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   /**
    * Creates new form TelaPrincipal
    */

   private boolean editando = false;
   private boolean continuarTarefa = false;
   private TelaRelatorio telaRelatorio = null;
   private static Usuario user = null;
   private static String login;
   private static String senha;
   private static Long interval = -1L;
   private static AppropTimeBo appropTimeBo = null;
   private AtualizaHoras ah;
   private Atividade atividadeAtual = null;
   private Tarefa tarefaAtual = null;
   private AutoCompleteContains autoCompleteInstance = null;
   private AutoCompleteContainsTag autoCompleteTag = null;
   private AutoCompleteContainsWorkspace autoCompleteWorkspace = null;
   private AutoCompleteContainsProjeto autoCompleteProjeto = null;
   private static boolean debugHabilitado = false;
   private static boolean autoOpen = false;
   private List<String> atividadesTabela = new ArrayList<>();
   private final Thread oculta = new Thread();
   private boolean sleeping = true;
   private static boolean autoFocusHabilitado = false;
   private InputStream imgPerfil = null;

   public TelaPrincipal() {
      initComponents();
      if (isAutoFocusHabilitado()) {
         setAutoRequestFocus(true);
      }
      loadInitialDataTelaPrincipal();
      setupShortCutTextArea();
   }

   public static boolean isAutoFocusHabilitado() {
      return autoFocusHabilitado;
   }

   public static boolean isAutoOpen() {
      return autoOpen;
   }

   public boolean isEditando() {
      return editando;
   }

   public void setEditando(boolean editando) {
      this.editando = editando;
   }

   public void setAtividadeFromTelaRelatorio(Atividade atividade) {
      this.atividadeAtual = atividade;
      this.tarefaAtual = atividade.getTarefa();
   }

   public static boolean isDebugHabilitado() {
      return debugHabilitado;
   }

   public boolean isContinuarTarefa() {
      return continuarTarefa;
   }

   public void setContinuarTarefa(boolean continuarTarefa) {
      this.continuarTarefa = continuarTarefa;
   }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogConfirmacaoSair = new javax.swing.JDialog(this);
        botaoDialogNao = new javax.swing.JButton();
        botaoDialogSim = new javax.swing.JButton();
        mensagemDialogSair = new javax.swing.JLabel();
        dialogObservacoes = new javax.swing.JDialog(this);
        labelDialogObservacoes = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaDeObservacoes = new javax.swing.JTable(){

            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    //comment row
                    if(rowIndex >= 0){
                        tip = getValueAt(rowIndex, colIndex).toString();
                    }
                } catch (RuntimeException e1) {
                    //catch null pointer exception if mouse is over an empty line
                }

                return tip;
            }
        };
        filtroObservacoes = new javax.swing.JTextField();
        dialogUsage = new javax.swing.JDialog(this);
        jScrollPane3 = new javax.swing.JScrollPane();
        textAreaUsage = new javax.swing.JTextPane();
        dialogDadosConexao = new javax.swing.JDialog();
        labelBanco = new javax.swing.JLabel();
        labelHost = new javax.swing.JLabel();
        labelUsuario = new javax.swing.JLabel();
        valorBanco = new javax.swing.JLabel();
        valorHost = new javax.swing.JLabel();
        valorUsuario = new javax.swing.JLabel();
        dialogPerfil = new javax.swing.JDialog();
        labelEmail = new javax.swing.JLabel();
        fieldEmail = new javax.swing.JTextField();
        labelSenha = new javax.swing.JLabel();
        fieldSenha = new javax.swing.JPasswordField();
        labelNome = new javax.swing.JLabel();
        fieldNome = new javax.swing.JTextField();
        btnSalvarPerfil = new javax.swing.JButton();
        btnCancelChangesPerfil = new javax.swing.JButton();
        btnDeleteContaPerfil = new javax.swing.JButton();
        btnEscolherImagem = new javax.swing.JButton();
        labelImage = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        fieldCargo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        fieldDepto = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        fieldEmpresa = new javax.swing.JTextField();
        comboTime = new javax.swing.JComboBox<>();
        dialogWorkspace = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listWorkspaces = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        fieldNomeWorkspace = new javax.swing.JTextField();
        AdicionarWorkspace = new javax.swing.JButton();
        RemoverWorkspace = new javax.swing.JButton();
        cancelarWorkspace = new javax.swing.JButton();
        dialogProject = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        listProjects = new javax.swing.JList<>();
        fieldNomeProjeto = new javax.swing.JTextField();
        btnAdicionarProjeto = new javax.swing.JButton();
        btnCancelarProjeto = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnRemoverProjeto = new javax.swing.JButton();
        dialogTag = new javax.swing.JDialog();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        listTags = new javax.swing.JList<>();
        removerTag = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        fieldNomeTag = new javax.swing.JTextField();
        adicionarTag = new javax.swing.JButton();
        cancelarTag = new javax.swing.JButton();
        dialogTeam = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        dialogAdmin = new javax.swing.JDialog();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        listCargo = new javax.swing.JList<>();
        removerCargo = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        fieldNomeCargo = new javax.swing.JTextField();
        btnAdicionarCargo = new javax.swing.JButton();
        btnCancelarCargo = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        listUsuarios = new javax.swing.JList<>();
        jLabel15 = new javax.swing.JLabel();
        comboCargos = new javax.swing.JComboBox<>();
        btnVincularCargo = new javax.swing.JButton();
        btnVincularDepto = new javax.swing.JButton();
        comboDepto = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        fieldNomeDepto = new javax.swing.JTextField();
        btnAdicionarDepto = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        removerDepto = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        listDepto = new javax.swing.JList<>();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        listTime = new javax.swing.JList<>();
        removerTime = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        fieldNomeTime = new javax.swing.JTextField();
        btnAdicionarTime = new javax.swing.JButton();
        btnCancelarDepto = new javax.swing.JButton();
        btnCancelarTime = new javax.swing.JButton();
        jPanelButtons = new javax.swing.JPanel();
        botaoOcultar = new javax.swing.JButton();
        botaoSalvar = new javax.swing.JButton();
        botaoSalvarECriarNova = new javax.swing.JButton();
        botaoReset = new javax.swing.JButton();
        labelUser = new javax.swing.JLabel();
        labelTarefa = new javax.swing.JLabel();
        labelInicio = new javax.swing.JLabel();
        labelObservacao = new javax.swing.JLabel();
        labelDuracao = new javax.swing.JLabel();
        labelTermino = new javax.swing.JLabel();
        jLabelValorDuracao = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaObservacao = new javax.swing.JTextArea();
        jLabelLoginUser = new javax.swing.JLabel();
        labelInicio1 = new javax.swing.JLabel();
        txtHoraInicio = new javax.swing.JFormattedTextField();
        labelInicio2 = new javax.swing.JLabel();
        txtHoraTermino = new javax.swing.JFormattedTextField();
        txtDataInicio = new javax.swing.JFormattedTextField();
        txtDataTermino = new javax.swing.JFormattedTextField();
        botaoInicioAgora = new javax.swing.JButton();
        botaoTerminoAgora = new javax.swing.JButton();
        labelStatusDataHora = new javax.swing.JLabel();
        labelStatusApp = new javax.swing.JLabel();
        autoCompleteInstance = new AutoCompleteContains();
        autoCompleteInstance.setIdUsuario(user.getId());
        autoCompleteInstance.setBo(appropTimeBo);
        comboTarefas = autoCompleteInstance;
        botaoTarefaAtual = new javax.swing.JButton();
        botaoDataUltimaTarefa = new javax.swing.JButton();
        botaoBucarObservacoes = new javax.swing.JButton();
        labelCodigoAtividade = new javax.swing.JLabel();
        valorCodigoAtividade = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        autoCompleteTag = new AutoCompleteContainsTag();
        autoCompleteTag.setIdUsuario(user.getId());
        autoCompleteTag.setBo(appropTimeBo);
        comboTags = autoCompleteTag;
        labelWorkspace = new javax.swing.JLabel();
        autoCompleteWorkspace = new AutoCompleteContainsWorkspace();
        autoCompleteWorkspace.setIdUsuario(user.getId());
        autoCompleteWorkspace.setBo(appropTimeBo);
        comboWorkspace = autoCompleteWorkspace;
        jLabel11 = new javax.swing.JLabel();
        autoCompleteProjeto = new AutoCompleteContainsProjeto();
        autoCompleteProjeto.setIdUsuario(user.getId());
        autoCompleteProjeto.setBo(appropTimeBo);
        comboProjeto = autoCompleteProjeto;
        menuBar = new javax.swing.JMenuBar();
        menuArquivo = new javax.swing.JMenu();
        opcaoSalvar = new javax.swing.JMenuItem();
        opcaoSalvarECriarNova = new javax.swing.JMenuItem();
        opcaoOcultar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        opcaoTarefaAtual = new javax.swing.JMenuItem();
        opcaoCancelarTarefaAtual = new javax.swing.JMenuItem();
        opcaoReset = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        opcaoExportarCSV = new javax.swing.JMenuItem();
        opcaoImportarCsv = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        opcaoSair = new javax.swing.JMenuItem();
        menuFerramentas = new javax.swing.JMenu();
        irParaHistorico = new javax.swing.JMenuItem();
        opcoesOcultar = new javax.swing.JMenu();
        opcaoCincoMinutos = new javax.swing.JMenuItem();
        opcaoDezMinutos = new javax.swing.JMenuItem();
        opcaoVinteMinutos = new javax.swing.JMenuItem();
        opcaoTrintaMinutos = new javax.swing.JMenuItem();
        opcaoQuarentaECincoMinutos = new javax.swing.JMenuItem();
        opcaoUmaHora = new javax.swing.JMenuItem();
        menuSobre = new javax.swing.JMenu();
        showUsage = new javax.swing.JMenuItem();
        showInstallation = new javax.swing.JMenuItem();
        opcaoDadosDeConexao = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        menuMeuPerfil = new javax.swing.JMenuItem();
        menuWorkspace = new javax.swing.JMenuItem();
        menuProjetos = new javax.swing.JMenuItem();
        menuTag = new javax.swing.JMenuItem();
        menuAdmin = new javax.swing.JMenuItem();

        dialogConfirmacaoSair.setTitle("Sair");
        dialogConfirmacaoSair.setModal(true);
        dialogConfirmacaoSair.setResizable(false);
        dialogConfirmacaoSair.setSize(new java.awt.Dimension(417, 120));
        dialogConfirmacaoSair.setLocationRelativeTo(this);

        botaoDialogNao.setText("Não");
        botaoDialogNao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoDialogNaoActionPerformed(evt);
            }
        });

        botaoDialogSim.setText("Sim");
        botaoDialogSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoDialogSimActionPerformed(evt);
            }
        });

        mensagemDialogSair.setText("Deseja realmente sair? Dados não salvos serão perdidos");

        javax.swing.GroupLayout dialogConfirmacaoSairLayout = new javax.swing.GroupLayout(dialogConfirmacaoSair.getContentPane());
        dialogConfirmacaoSair.getContentPane().setLayout(dialogConfirmacaoSairLayout);
        dialogConfirmacaoSairLayout.setHorizontalGroup(
            dialogConfirmacaoSairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogConfirmacaoSairLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoDialogNao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botaoDialogSim)
                .addGap(46, 46, 46))
            .addGroup(dialogConfirmacaoSairLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mensagemDialogSair)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        dialogConfirmacaoSairLayout.setVerticalGroup(
            dialogConfirmacaoSairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogConfirmacaoSairLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(mensagemDialogSair, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogConfirmacaoSairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoDialogNao)
                    .addComponent(botaoDialogSim))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        dialogObservacoes.setTitle("Minhas observações");
        dialogObservacoes.setAlwaysOnTop(true);
        dialogObservacoes.setAutoRequestFocus(false);
        dialogObservacoes.setModal(true);
        dialogObservacoes.setResizable(false);
        dialogObservacoes.setSize(new java.awt.Dimension(660, 300));
        dialogConfirmacaoSair.setLocationRelativeTo(this);
        dialogObservacoes.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                dialogObservacoesWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                dialogObservacoesWindowClosing(evt);
            }
        });
        dialogObservacoes.setLocationRelativeTo(this);

        labelDialogObservacoes.setText("Selecione a observação desejada: ");

        tabelaDeObservacoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaDeObservacoes.setDefaultRenderer(String.class, new MultiLineCellRenderer());
        tabelaDeObservacoes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabelaDeObservacoes.setAutoscrolls(false);
        tabelaDeObservacoes.setCellSelectionEnabled(true);
        tabelaDeObservacoes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaDeObservacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaDeObservacoesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabelaDeObservacoesMouseEntered(evt);
            }
        });
        tabelaDeObservacoes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaDeObservacoesKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaDeObservacoes);

        filtroObservacoes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtroObservacoesKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout dialogObservacoesLayout = new javax.swing.GroupLayout(dialogObservacoes.getContentPane());
        dialogObservacoes.getContentPane().setLayout(dialogObservacoesLayout);
        dialogObservacoesLayout.setHorizontalGroup(
            dialogObservacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogObservacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelDialogObservacoes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filtroObservacoes)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
        );
        dialogObservacoesLayout.setVerticalGroup(
            dialogObservacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogObservacoesLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(dialogObservacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDialogObservacoes)
                    .addComponent(filtroObservacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
        );

        dialogUsage.setTitle("Sobre");
        dialogUsage.setMinimumSize(new java.awt.Dimension(1120, 815));
        dialogUsage.setModal(true);

        textAreaUsage.setEditable(false);
        textAreaUsage.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jScrollPane3.setViewportView(textAreaUsage);

        javax.swing.GroupLayout dialogUsageLayout = new javax.swing.GroupLayout(dialogUsage.getContentPane());
        dialogUsage.getContentPane().setLayout(dialogUsageLayout);
        dialogUsageLayout.setHorizontalGroup(
            dialogUsageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1120, Short.MAX_VALUE)
        );
        dialogUsageLayout.setVerticalGroup(
            dialogUsageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
        );

        dialogDadosConexao.setTitle("Dados de conexão");
        dialogDadosConexao.setMinimumSize(new java.awt.Dimension(190, 100));
        dialogDadosConexao.setModal(true);
        dialogDadosConexao.setResizable(false);
        dialogDadosConexao.setSize(new java.awt.Dimension(190, 120));
        dialogDadosConexao.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                dialogDadosConexaoWindowActivated(evt);
            }
        });

        labelBanco.setText("Banco:");

        labelHost.setText("Host:");

        labelUsuario.setText("Usuário:");

        javax.swing.GroupLayout dialogDadosConexaoLayout = new javax.swing.GroupLayout(dialogDadosConexao.getContentPane());
        dialogDadosConexao.getContentPane().setLayout(dialogDadosConexaoLayout);
        dialogDadosConexaoLayout.setHorizontalGroup(
            dialogDadosConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogDadosConexaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogDadosConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogDadosConexaoLayout.createSequentialGroup()
                        .addComponent(labelUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valorUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dialogDadosConexaoLayout.createSequentialGroup()
                        .addComponent(labelHost)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valorHost, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dialogDadosConexaoLayout.createSequentialGroup()
                        .addComponent(labelBanco)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valorBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dialogDadosConexaoLayout.setVerticalGroup(
            dialogDadosConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogDadosConexaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogDadosConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelBanco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(valorBanco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(dialogDadosConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelHost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(valorHost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(dialogDadosConexaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(valorUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39))
        );

        dialogPerfil.setTitle("Perfil");
        dialogPerfil.setMinimumSize(new java.awt.Dimension(611, 428));
        dialogPerfil.setModal(true);
        dialogPerfil.setResizable(false);
        dialogPerfil.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                dialogPerfilWindowOpened(evt);
            }
        });

        labelEmail.setText("Email:");

        fieldEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldEmailActionPerformed(evt);
            }
        });

        labelSenha.setText("Senha:");

        labelNome.setText("Nome");

        fieldNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldNomeActionPerformed(evt);
            }
        });

        btnSalvarPerfil.setText("Salvar");
        btnSalvarPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarPerfilActionPerformed(evt);
            }
        });

        btnCancelChangesPerfil.setText("Cancelar");
        btnCancelChangesPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelChangesPerfilActionPerformed(evt);
            }
        });

        btnDeleteContaPerfil.setText("Apagar Conta");
        btnDeleteContaPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteContaPerfilActionPerformed(evt);
            }
        });

        btnEscolherImagem.setText("Escolher Imagem");
        btnEscolherImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscolherImagemActionPerformed(evt);
            }
        });

        jLabel16.setText("Cargo:");

        fieldCargo.setEditable(false);
        fieldCargo.setEnabled(false);

        jLabel17.setText("Time:");

        jLabel18.setText("Depto:");

        fieldDepto.setEditable(false);
        fieldDepto.setEnabled(false);

        jLabel19.setText("Empresa:");

        fieldEmpresa.setEditable(false);
        fieldEmpresa.setEnabled(false);

        javax.swing.GroupLayout dialogPerfilLayout = new javax.swing.GroupLayout(dialogPerfil.getContentPane());
        dialogPerfil.getContentPane().setLayout(dialogPerfilLayout);
        dialogPerfilLayout.setHorizontalGroup(
            dialogPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogPerfilLayout.createSequentialGroup()
                .addContainerGap(215, Short.MAX_VALUE)
                .addGroup(dialogPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogPerfilLayout.createSequentialGroup()
                        .addGroup(dialogPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btnEscolherImagem)
                            .addComponent(labelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(dialogPerfilLayout.createSequentialGroup()
                                .addComponent(btnSalvarPerfil)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelChangesPerfil))
                            .addComponent(btnDeleteContaPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(241, 241, 241))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogPerfilLayout.createSequentialGroup()
                        .addGroup(dialogPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(dialogPerfilLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fieldDepto))
                            .addGroup(dialogPerfilLayout.createSequentialGroup()
                                .addComponent(labelSenha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dialogPerfilLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fieldCargo)))
                        .addGap(32, 32, 32))))
            .addGroup(dialogPerfilLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(dialogPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dialogPerfilLayout.createSequentialGroup()
                        .addComponent(labelEmail)
                        .addGap(18, 18, 18)
                        .addComponent(fieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dialogPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(dialogPerfilLayout.createSequentialGroup()
                            .addComponent(jLabel19)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(fieldEmpresa))
                        .addGroup(dialogPerfilLayout.createSequentialGroup()
                            .addGroup(dialogPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelNome)
                                .addComponent(jLabel17))
                            .addGap(21, 21, 21)
                            .addGroup(dialogPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(fieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dialogPerfilLayout.setVerticalGroup(
            dialogPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPerfilLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEscolherImagem)
                .addGap(18, 18, 18)
                .addGroup(dialogPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelEmail)
                    .addComponent(fieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSenha)
                    .addComponent(fieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dialogPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNome)
                    .addComponent(fieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(fieldCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dialogPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(fieldDepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dialogPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(fieldEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(dialogPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSalvarPerfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelChangesPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteContaPerfil)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        dialogWorkspace.setTitle("Workspace");
        dialogWorkspace.setMinimumSize(new java.awt.Dimension(274, 245));
        dialogWorkspace.setResizable(false);
        dialogWorkspace.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                dialogWorkspaceWindowOpened(evt);
            }
        });

        jLabel1.setText("Workspaces:");

        listWorkspaces.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(listWorkspaces);
        listWorkspaces.setModel(new DefaultListModel<>());

        jLabel2.setText("Nome:");

        AdicionarWorkspace.setText("Adicionar");
        AdicionarWorkspace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdicionarWorkspaceActionPerformed(evt);
            }
        });

        RemoverWorkspace.setText("Remover");
        RemoverWorkspace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoverWorkspaceActionPerformed(evt);
            }
        });

        cancelarWorkspace.setText("Cancelar");
        cancelarWorkspace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarWorkspaceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogWorkspaceLayout = new javax.swing.GroupLayout(dialogWorkspace.getContentPane());
        dialogWorkspace.getContentPane().setLayout(dialogWorkspaceLayout);
        dialogWorkspaceLayout.setHorizontalGroup(
            dialogWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogWorkspaceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(dialogWorkspaceLayout.createSequentialGroup()
                        .addGroup(dialogWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(dialogWorkspaceLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(dialogWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(dialogWorkspaceLayout.createSequentialGroup()
                                        .addComponent(AdicionarWorkspace)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cancelarWorkspace))
                                    .addComponent(fieldNomeWorkspace)))
                            .addComponent(RemoverWorkspace))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        dialogWorkspaceLayout.setVerticalGroup(
            dialogWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogWorkspaceLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RemoverWorkspace)
                .addGap(38, 38, 38)
                .addGroup(dialogWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fieldNomeWorkspace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdicionarWorkspace)
                    .addComponent(cancelarWorkspace))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dialogProject.setTitle("Projetos");
        dialogProject.setMinimumSize(new java.awt.Dimension(233, 257));
        dialogProject.setResizable(false);
        dialogProject.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                dialogProjectWindowOpened(evt);
            }
        });

        jLabel3.setText("Nome:");

        listProjects.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(listProjects);
        listProjects.setModel(new DefaultListModel<>());

        btnAdicionarProjeto.setText("Adicionar");
        btnAdicionarProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarProjetoActionPerformed(evt);
            }
        });

        btnCancelarProjeto.setText("Cancelar");
        btnCancelarProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarProjetoActionPerformed(evt);
            }
        });

        jLabel4.setText("Projetos:");

        btnRemoverProjeto.setText("Remover");
        btnRemoverProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverProjetoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogProjectLayout = new javax.swing.GroupLayout(dialogProject.getContentPane());
        dialogProject.getContentPane().setLayout(dialogProjectLayout);
        dialogProjectLayout.setHorizontalGroup(
            dialogProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogProjectLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dialogProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dialogProjectLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(dialogProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(dialogProjectLayout.createSequentialGroup()
                                    .addComponent(btnAdicionarProjeto)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCancelarProjeto))
                                .addComponent(fieldNomeProjeto)))
                        .addComponent(jLabel4)
                        .addComponent(btnRemoverProjeto)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dialogProjectLayout.setVerticalGroup(
            dialogProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogProjectLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoverProjeto)
                .addGap(24, 24, 24)
                .addGroup(dialogProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fieldNomeProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionarProjeto)
                    .addComponent(btnCancelarProjeto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dialogTag.setTitle("Etiqueta");
        dialogTag.setMinimumSize(new java.awt.Dimension(310, 262));
        dialogTag.setResizable(false);
        dialogTag.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                dialogTagWindowOpened(evt);
            }
        });

        jLabel8.setText("Etiquetas:");

        jScrollPane8.setViewportView(listTags);
        listTags.setModel(new DefaultListModel<String>());

        removerTag.setText("Remover");
        removerTag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerTagActionPerformed(evt);
            }
        });

        jLabel9.setText("Nome da etiqueta:");

        adicionarTag.setText("Adicionar");
        adicionarTag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarTagActionPerformed(evt);
            }
        });

        cancelarTag.setText("Cancelar");
        cancelarTag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarTagActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogTagLayout = new javax.swing.GroupLayout(dialogTag.getContentPane());
        dialogTag.getContentPane().setLayout(dialogTagLayout);
        dialogTagLayout.setHorizontalGroup(
            dialogTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogTagLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8)
                    .addGroup(dialogTagLayout.createSequentialGroup()
                        .addGroup(dialogTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(removerTag))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(dialogTagLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dialogTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dialogTagLayout.createSequentialGroup()
                                .addComponent(adicionarTag)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancelarTag)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(fieldNomeTag))))
                .addContainerGap())
        );
        dialogTagLayout.setVerticalGroup(
            dialogTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogTagLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removerTag)
                .addGap(36, 36, 36)
                .addGroup(dialogTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(fieldNomeTag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adicionarTag)
                    .addComponent(cancelarTag))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dialogTeam.setTitle("Time");

        jLabel5.setText("Usuários:");

        jScrollPane6.setViewportView(jList1);

        jButton1.setText("Adicionar ao time");

        jLabel6.setText("Meu time:");

        jScrollPane7.setViewportView(jList2);

        jButton2.setText("Remover do time");

        jLabel7.setText("Projeto associado:");

        javax.swing.GroupLayout dialogTeamLayout = new javax.swing.GroupLayout(dialogTeam.getContentPane());
        dialogTeam.getContentPane().setLayout(dialogTeamLayout);
        dialogTeamLayout.setHorizontalGroup(
            dialogTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7)
            .addComponent(jScrollPane6)
            .addGroup(dialogTeamLayout.createSequentialGroup()
                .addGroup(dialogTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(dialogTeamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogTeamLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(dialogTeamLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        dialogTeamLayout.setVerticalGroup(
            dialogTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogTeamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(25, 25, 25))
        );

        dialogAdmin.setTitle("Admin");
        dialogAdmin.setLocationByPlatform(true);
        dialogAdmin.setMaximumSize(new java.awt.Dimension(561, 680));
        dialogAdmin.setMinimumSize(new java.awt.Dimension(561, 680));
        dialogAdmin.setModal(true);
        dialogAdmin.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                dialogAdminWindowOpened(evt);
            }
        });

        jLabel12.setText("Cargos:");

        listCargo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane9.setViewportView(listCargo);
        listCargo.setModel(new DefaultListModel<String>());

        removerCargo.setText("Remover Cargo");
        removerCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerCargoActionPerformed(evt);
            }
        });

        jLabel13.setText("Novo cargo:");

        btnAdicionarCargo.setText("Adicionar");
        btnAdicionarCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarCargoActionPerformed(evt);
            }
        });

        btnCancelarCargo.setText("Cancelar");
        btnCancelarCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCargoActionPerformed(evt);
            }
        });

        jLabel14.setText("Usuarios:");

        listUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane10.setViewportView(listUsuarios);
        listUsuarios.setModel(new DefaultListModel<String>());

        jLabel15.setText("Indicar Cargo:");

        btnVincularCargo.setText("Vincular");
        btnVincularCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVincularCargoActionPerformed(evt);
            }
        });

        btnVincularDepto.setText("Vincular");
        btnVincularDepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVincularDeptoActionPerformed(evt);
            }
        });

        jLabel20.setText("Indicar Depto:");

        btnAdicionarDepto.setText("Adicionar");
        btnAdicionarDepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarDeptoActionPerformed(evt);
            }
        });

        jLabel21.setText("Novo depto:");

        removerDepto.setText("Remover Depto");
        removerDepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerDeptoActionPerformed(evt);
            }
        });

        listDepto.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane11.setViewportView(listDepto);
        listDepto.setModel(new DefaultListModel<String>());

        jLabel22.setText("Departamentos");

        listTime.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane12.setViewportView(listTime);
        listTime.setModel(new DefaultListModel<String>());

        removerTime.setText("Remover Time");
        removerTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerTimeActionPerformed(evt);
            }
        });

        jLabel23.setText("Times:");

        jLabel24.setText("Novo time:");

        btnAdicionarTime.setText("Adicionar");
        btnAdicionarTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarTimeActionPerformed(evt);
            }
        });

        btnCancelarDepto.setText("Cancelar");
        btnCancelarDepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarDeptoActionPerformed(evt);
            }
        });

        btnCancelarTime.setText("Cancelar");
        btnCancelarTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarTimeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogAdminLayout = new javax.swing.GroupLayout(dialogAdmin.getContentPane());
        dialogAdmin.getContentPane().setLayout(dialogAdminLayout);
        dialogAdminLayout.setHorizontalGroup(
            dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogAdminLayout.createSequentialGroup()
                        .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12)
                            .addComponent(removerCargo)
                            .addGroup(dialogAdminLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(12, 12, 12)
                                .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dialogAdminLayout.createSequentialGroup()
                                        .addComponent(btnAdicionarCargo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCancelarCargo)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(fieldNomeCargo)))
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogAdminLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(95, 95, 95))
                            .addGroup(dialogAdminLayout.createSequentialGroup()
                                .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dialogAdminLayout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnVincularCargo)
                                            .addComponent(comboCargos, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(6, 6, 6))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogAdminLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnVincularDepto)
                            .addComponent(comboDepto, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(dialogAdminLayout.createSequentialGroup()
                        .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel22)
                            .addComponent(removerDepto)
                            .addGroup(dialogAdminLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(12, 12, 12)
                                .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dialogAdminLayout.createSequentialGroup()
                                        .addComponent(btnAdicionarDepto)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnCancelarDepto))
                                    .addComponent(fieldNomeDepto)))
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel23)
                            .addComponent(removerTime)
                            .addGroup(dialogAdminLayout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(12, 12, 12)
                                .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dialogAdminLayout.createSequentialGroup()
                                        .addComponent(btnAdicionarTime)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCancelarTime)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(fieldNomeTime)))
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 12, Short.MAX_VALUE))))
        );
        dialogAdminLayout.setVerticalGroup(
            dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dialogAdminLayout.createSequentialGroup()
                        .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dialogAdminLayout.createSequentialGroup()
                        .addComponent(removerCargo)
                        .addGap(32, 32, 32)
                        .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(fieldNomeCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(comboCargos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdicionarCargo)
                            .addComponent(btnCancelarCargo)
                            .addComponent(btnVincularCargo))
                        .addGap(18, 18, 18)
                        .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(comboDepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVincularDepto)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removerDepto)
                        .addGap(32, 32, 32)
                        .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(fieldNomeDepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdicionarDepto)
                            .addComponent(btnCancelarDepto)))
                    .addGroup(dialogAdminLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removerTime)
                        .addGap(32, 32, 32)
                        .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(fieldNomeTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dialogAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdicionarTime)
                            .addComponent(btnCancelarTime))))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("approptime"); // NOI18N
        setTitle(bundle.getString("versao")); // NOI18N
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(102, 102, 102));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(618, 339));
        setResizable(false);
        setSize(new java.awt.Dimension(618, 339));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        botaoOcultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/ocultar-icon-16.png"))); // NOI18N
        botaoOcultar.setMnemonic(KeyEvent.VK_O);
        botaoOcultar.setText("Ocultar");
        botaoOcultar.setToolTipText("Oculta a aplicação com base no intervalo passado por parâmetro");
        botaoOcultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoOcultarActionPerformed(evt);
            }
        });
        jPanelButtons.add(botaoOcultar);

        botaoSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Save-icon.png"))); // NOI18N
        botaoSalvar.setMnemonic(KeyEvent.VK_S);
        botaoSalvar.setText("Salvar");
        botaoSalvar.setToolTipText("Salva a atividade em questão");
        botaoSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarActionPerformed(evt);
            }
        });
        jPanelButtons.add(botaoSalvar);

        botaoSalvarECriarNova.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Success-icon.png"))); // NOI18N
        botaoSalvarECriarNova.setMnemonic(KeyEvent.VK_N);
        botaoSalvarECriarNova.setText("Salvar e criar nova");
        botaoSalvarECriarNova.setToolTipText("Finaliza/Atualiza a atividade em questão");
        botaoSalvarECriarNova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarECriarNovaActionPerformed(evt);
            }
        });
        jPanelButtons.add(botaoSalvarECriarNova);

        botaoReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/reset-icon-16.png"))); // NOI18N
        botaoReset.setText("Reset");
        botaoReset.setToolTipText("Restaura a tela para o estado imediatamente anterior às alterações");
        botaoReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoResetActionPerformed(evt);
            }
        });
        jPanelButtons.add(botaoReset);

        labelUser.setText("Usuário: ");

        labelTarefa.setText("Tarefa:");

        labelInicio.setText("Data início:");

        labelObservacao.setText("Observação:");

        labelDuracao.setText("Duração: ");

        labelTermino.setText("Data término:");

        jLabelValorDuracao.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N

        jTextAreaObservacao.setColumns(20);
        jTextAreaObservacao.setLineWrap(true);
        jTextAreaObservacao.setRows(5);
        jTextAreaObservacao.setTabSize(4);
        jTextAreaObservacao.setToolTipText("");
        jTextAreaObservacao.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextAreaObservacao);

        jLabelLoginUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/User-icon.png"))); // NOI18N

        labelInicio1.setText("Hora início:");

        try {
            txtHoraInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHoraInicio.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txtHoraInicio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoraInicioFocusLost(evt);
            }
        });
        txtHoraInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtHoraInicioMousePressed(evt);
            }
        });
        txtHoraInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHoraInicioKeyTyped(evt);
            }
        });

        labelInicio2.setText("Hora término:");

        try {
            txtHoraTermino.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHoraTermino.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txtHoraTermino.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoraTerminoFocusLost(evt);
            }
        });
        txtHoraTermino.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtHoraTerminoMousePressed(evt);
            }
        });
        txtHoraTermino.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHoraTerminoKeyTyped(evt);
            }
        });

        try {
            txtDataInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataInicio.setText("");
        txtDataInicio.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txtDataInicio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataInicioFocusLost(evt);
            }
        });
        txtDataInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtDataInicioMousePressed(evt);
            }
        });
        txtDataInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDataInicioKeyTyped(evt);
            }
        });

        try {
            txtDataTermino.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataTermino.setText("");
        txtDataTermino.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txtDataTermino.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataTerminoFocusLost(evt);
            }
        });
        txtDataTermino.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtDataTerminoMousePressed(evt);
            }
        });
        txtDataTermino.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDataTerminoKeyTyped(evt);
            }
        });

        botaoInicioAgora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Clock-icon.png"))); // NOI18N
        botaoInicioAgora.setText("Agora");
        botaoInicioAgora.setToolTipText("Preenche os campos de data de início com a data atual");
        botaoInicioAgora.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                botaoInicioAgoraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                botaoInicioAgoraFocusLost(evt);
            }
        });
        botaoInicioAgora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoInicioAgoraActionPerformed(evt);
            }
        });

        botaoTerminoAgora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Clock-icon.png"))); // NOI18N
        botaoTerminoAgora.setText("Agora");
        botaoTerminoAgora.setToolTipText("Preenche os campos de data de término com a data atual");
        botaoTerminoAgora.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                botaoTerminoAgoraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                botaoTerminoAgoraFocusLost(evt);
            }
        });
        botaoTerminoAgora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoTerminoAgoraActionPerformed(evt);
            }
        });

        labelStatusDataHora.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        labelStatusApp.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        comboTarefas.setEditable(true);

        botaoTarefaAtual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/working.png"))); // NOI18N
        botaoTarefaAtual.setText("Tarefa atual");
        botaoTarefaAtual.setToolTipText("Abre a tarefa em execução caso exista, caso contrário abre a tela vazia ");
        botaoTarefaAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoTarefaAtualActionPerformed(evt);
            }
        });

        botaoDataUltimaTarefa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Last-icon.png"))); // NOI18N
        botaoDataUltimaTarefa.setToolTipText("Preenche os campos de data/hora início com base na última tarefa");
        botaoDataUltimaTarefa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoDataUltimaTarefaActionPerformed(evt);
            }
        });

        botaoBucarObservacoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Last-icon.png"))); // NOI18N
        botaoBucarObservacoes.setToolTipText("Busca observações salvas com bas na tarefa preenchida");
        botaoBucarObservacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoBucarObservacoesActionPerformed(evt);
            }
        });

        labelCodigoAtividade.setText("Código:");

        jLabel10.setText("Etiquetas:");

        comboTags.setEditable(true);

        labelWorkspace.setText("Workspace:");

        comboWorkspace.setEditable(true);

        jLabel11.setText("Projeto:");

        comboProjeto.setEditable(true);

        menuBar.setToolTipText("Menu de opções");

        menuArquivo.setMnemonic(KeyEvent.VK_A);
        menuArquivo.setText("Arquivo");

        opcaoSalvar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        opcaoSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Save-icon.png"))); // NOI18N
        opcaoSalvar.setMnemonic(KeyEvent.VK_S);
        opcaoSalvar.setText("Salvar");
        opcaoSalvar.setToolTipText("Salva a atividade em criação/edição");
        opcaoSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoSalvarActionPerformed(evt);
            }
        });
        menuArquivo.add(opcaoSalvar);

        opcaoSalvarECriarNova.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        opcaoSalvarECriarNova.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Success-icon.png"))); // NOI18N
        opcaoSalvarECriarNova.setMnemonic(KeyEvent.VK_N);
        opcaoSalvarECriarNova.setText("Salvar e criar nova");
        opcaoSalvarECriarNova.setToolTipText("Salva a atividade em criação/edição e limpa os campos da tela");
        opcaoSalvarECriarNova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoSalvarECriarNovaActionPerformed(evt);
            }
        });
        menuArquivo.add(opcaoSalvarECriarNova);

        opcaoOcultar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        opcaoOcultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/ocultar-icon-16.png"))); // NOI18N
        opcaoOcultar.setMnemonic(KeyEvent.VK_O);
        opcaoOcultar.setText("Ocultar");
        opcaoOcultar.setToolTipText("Oculta a aplicação com base no intervalo passado por parâmetro");
        opcaoOcultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoOcultarActionPerformed(evt);
            }
        });
        menuArquivo.add(opcaoOcultar);
        menuArquivo.add(jSeparator1);

        opcaoTarefaAtual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/working.png"))); // NOI18N
        opcaoTarefaAtual.setText("Tarefa atual");
        opcaoTarefaAtual.setToolTipText("Traz para a tela a tarefa em que se está trabalhando. Caso não exist, a tela será limpa");
        opcaoTarefaAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoTarefaAtualActionPerformed(evt);
            }
        });
        menuArquivo.add(opcaoTarefaAtual);

        opcaoCancelarTarefaAtual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Delete-icon.png"))); // NOI18N
        opcaoCancelarTarefaAtual.setText("Cancelar tarefa atual");
        opcaoCancelarTarefaAtual.setToolTipText("Cancela e excluir do banco uma tarefa salva");
        opcaoCancelarTarefaAtual.setEnabled(false);
        opcaoCancelarTarefaAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoCancelarTarefaAtualActionPerformed(evt);
            }
        });
        menuArquivo.add(opcaoCancelarTarefaAtual);

        opcaoReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/reset-icon-16.png"))); // NOI18N
        opcaoReset.setText("Reset");
        opcaoReset.setToolTipText("Restaura os campos da tela para a última versão salva do registro (despreza as alterações não salvas)");
        opcaoReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoResetActionPerformed(evt);
            }
        });
        menuArquivo.add(opcaoReset);
        menuArquivo.add(jSeparator2);

        opcaoExportarCSV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/ExportCsv-icon.png"))); // NOI18N
        opcaoExportarCSV.setText("Exportar para arquivo CSV");
        opcaoExportarCSV.setToolTipText("Exporta as atividades do usuário para o formato CSV");
        opcaoExportarCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoExportarCSVActionPerformed(evt);
            }
        });
        menuArquivo.add(opcaoExportarCSV);

        opcaoImportarCsv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/ImportCsv-icon.png"))); // NOI18N
        opcaoImportarCsv.setText("Importar de arquivo CSV");
        opcaoImportarCsv.setToolTipText("Importa as atividades do usuário a partir de um arquivo CSV");
        opcaoImportarCsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoImportarCsvActionPerformed(evt);
            }
        });
        menuArquivo.add(opcaoImportarCsv);
        menuArquivo.add(jSeparator3);

        opcaoSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        opcaoSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Logout-icon.png"))); // NOI18N
        opcaoSair.setMnemonic(KeyEvent.VK_Q);
        opcaoSair.setText("Sair");
        opcaoSair.setToolTipText("Fecha a aplicação sem salvar alterações");
        opcaoSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoSairActionPerformed(evt);
            }
        });
        menuArquivo.add(opcaoSair);

        menuBar.add(menuArquivo);

        menuFerramentas.setMnemonic(KeyEvent.VK_F);
        menuFerramentas.setText("Ferramentas");

        irParaHistorico.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        irParaHistorico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Historicos-icon.png"))); // NOI18N
        irParaHistorico.setMnemonic(KeyEvent.VK_H);
        irParaHistorico.setText("Histórico");
        irParaHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                irParaHistoricoActionPerformed(evt);
            }
        });
        menuFerramentas.add(irParaHistorico);

        opcoesOcultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/ocultar-icon-16.png"))); // NOI18N
        opcoesOcultar.setText("Ocultar");

        opcaoCincoMinutos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, java.awt.event.InputEvent.CTRL_MASK));
        opcaoCincoMinutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Clock-icon.png"))); // NOI18N
        opcaoCincoMinutos.setMnemonic(KeyEvent.VK_F5);
        opcaoCincoMinutos.setText("5 minutos");
        opcaoCincoMinutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoCincoMinutosActionPerformed(evt);
            }
        });
        opcoesOcultar.add(opcaoCincoMinutos);

        opcaoDezMinutos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, java.awt.event.InputEvent.CTRL_MASK));
        opcaoDezMinutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Clock-icon.png"))); // NOI18N
        opcaoDezMinutos.setMnemonic(KeyEvent.VK_F10);
        opcaoDezMinutos.setText("10 minutos");
        opcaoDezMinutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoDezMinutosActionPerformed(evt);
            }
        });
        opcoesOcultar.add(opcaoDezMinutos);

        opcaoVinteMinutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Clock-icon.png"))); // NOI18N
        opcaoVinteMinutos.setText("20 minutos");
        opcaoVinteMinutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoVinteMinutosActionPerformed(evt);
            }
        });
        opcoesOcultar.add(opcaoVinteMinutos);

        opcaoTrintaMinutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Clock-icon.png"))); // NOI18N
        opcaoTrintaMinutos.setText("30 minutos");
        opcaoTrintaMinutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoTrintaMinutosActionPerformed(evt);
            }
        });
        opcoesOcultar.add(opcaoTrintaMinutos);

        opcaoQuarentaECincoMinutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Clock-icon.png"))); // NOI18N
        opcaoQuarentaECincoMinutos.setText("45 minutos");
        opcaoQuarentaECincoMinutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoQuarentaECincoMinutosActionPerformed(evt);
            }
        });
        opcoesOcultar.add(opcaoQuarentaECincoMinutos);

        opcaoUmaHora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/Clock-icon.png"))); // NOI18N
        opcaoUmaHora.setText("1 hora");
        opcaoUmaHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoUmaHoraActionPerformed(evt);
            }
        });
        opcoesOcultar.add(opcaoUmaHora);

        menuFerramentas.add(opcoesOcultar);

        menuBar.add(menuFerramentas);

        menuSobre.setMnemonic(KeyEvent.VK_S);
        menuSobre.setText("Sobre");

        showUsage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/info-16.png"))); // NOI18N
        showUsage.setText("Usage");
        showUsage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showUsageActionPerformed(evt);
            }
        });
        menuSobre.add(showUsage);

        showInstallation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/manual-icon-16.png"))); // NOI18N
        showInstallation.setText("Manual de instalação");
        showInstallation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showInstallationActionPerformed(evt);
            }
        });
        menuSobre.add(showInstallation);

        opcaoDadosDeConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/approptime/icon/database-configuration-16.png"))); // NOI18N
        opcaoDadosDeConexao.setText("Dados de conexão");
        opcaoDadosDeConexao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoDadosDeConexaoActionPerformed(evt);
            }
        });
        menuSobre.add(opcaoDadosDeConexao);

        menuBar.add(menuSobre);

        jMenu1.setText("Perfil");

        menuMeuPerfil.setText("Meu perfil");
        menuMeuPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMeuPerfilActionPerformed(evt);
            }
        });
        jMenu1.add(menuMeuPerfil);

        menuWorkspace.setText("Workspace");
        menuWorkspace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuWorkspaceActionPerformed(evt);
            }
        });
        jMenu1.add(menuWorkspace);

        menuProjetos.setText("Projetos");
        menuProjetos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProjetosActionPerformed(evt);
            }
        });
        jMenu1.add(menuProjetos);

        menuTag.setText("Etiquetas");
        menuTag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTagActionPerformed(evt);
            }
        });
        jMenu1.add(menuTag);

        menuAdmin.setText("Admin");
        if(Objects.nonNull(user.getNome())){
            menuAdmin.setEnabled(user.getCargo().getNome().equalsIgnoreCase("Admin"));
        }
        else {
            menuAdmin.setEnabled(false);
        }
        menuAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAdminActionPerformed(evt);
            }
        });
        jMenu1.add(menuAdmin);

        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelButtons, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(labelTermino)
                                        .addGap(6, 6, 6))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(labelObservacao, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(labelDuracao))
                                            .addComponent(labelTarefa)
                                            .addComponent(labelUser)
                                            .addComponent(labelCodigoAtividade)
                                            .addComponent(labelInicio)
                                            .addComponent(labelWorkspace)
                                            .addComponent(jLabel11))
                                        .addGap(14, 14, 14)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(botaoBucarObservacoes))
                                    .addComponent(jLabelValorDuracao, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabelLoginUser, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(botaoTarefaAtual))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(valorCodigoAtividade, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtDataTermino, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDataInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(105, 105, 105)
                                                .addComponent(labelStatusDataHora, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(labelInicio2)
                                                    .addComponent(labelInicio1))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txtHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtHoraTermino, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(botaoTerminoAgora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(botaoInicioAgora))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(botaoDataUltimaTarefa))))
                                    .addComponent(comboProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboTarefas, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(labelStatusApp, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(195, 195, 195))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(comboWorkspace, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboTags, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(237, 237, 237))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(labelStatusApp, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelUser, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelLoginUser, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoTarefaAtual))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboTags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(comboWorkspace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelWorkspace))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTarefa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTarefas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(valorCodigoAtividade, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCodigoAtividade))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelInicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botaoInicioAgora, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTermino, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataTermino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botaoDataUltimaTarefa, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoTerminoAgora, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labelInicio2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtHoraTermino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelValorDuracao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDuracao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelStatusDataHora, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoBucarObservacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(685, 509));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarActionPerformed
       labelStatusDataHora.setText("");
       if (validaSalvarAtividade("Salvar e sair")) {
          salvarAtividade();
       }
       atualizaObjetoAtividadeAtual();
    }//GEN-LAST:event_botaoSalvarActionPerformed

   /***
    * Método utilizado para salvar/atualizar atividades com as devidas verificações
    */
   private void salvarAtividade() {

      //atividadeAtual e tarefaAtual tem os últimos valores da tela
      atualizaObjetoAtividadeAtual();
      if (existeDuasAtividadesEmAberto()) {
         JOptionPane.showMessageDialog(this, "Não pode haver mais de uma atividade em aberto.", "Salvar atividade",
                                       JOptionPane.WARNING_MESSAGE);
         return;
      }

      Atividade atividadeSemTermino = atividadeEmAberto();
      boolean houveConflitoEntreAtividades = verificaConflitoEntreAtividades();

      if (houveConflitoEntreAtividades == false) {
         try{
            if(Objects.nonNull(atividadeSemTermino) || atividadeNaoPersistida() == false || editando){
               appropTimeBo.atualizaAtividade(atividadeAtual);
            }
            else{
               Tarefa tarefaASalvar = appropTimeBo.getTarefaByDescricao(tarefaAtual.getDescricao(), user.getId());
               if(Objects.isNull(tarefaASalvar)){
                  appropTimeBo.adicionaTarefa(tarefaAtual);
               }
               else{
                  tarefaAtual = tarefaASalvar;
               }
               atividadeAtual.setTarefa(tarefaAtual);
               String tagName = comboTags.getEditor().getItem().toString();
               Tag tag = null;
               if(tagName.equals("")){
                   tag = new Tag();
                   tag.setUsuario(user);
               }
               else {
                   tag = appropTimeBo.getTagByDescricao(tagName);
               }
               
               atividadeAtual.setTag(tag);
               appropTimeBo.adicionaAtividade(atividadeAtual);
            }

            labelStatusApp.setText("Atividade salva com sucesso!");
            labelStatusApp.setForeground(Color.BLUE);

            controleDeTempoDaMensagemDeSucesso();
            habilitaBotoesEOpcoes();
            desabilitaComboTarefa();
            labelStatusDataHora.setText("");
            valorCodigoAtividade.setText(String.valueOf(atividadeAtual.getId()));
         }
         catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Erro ao salvar atividade.", "Aviso", JOptionPane.ERROR_MESSAGE);
            if(TelaPrincipal.isDebugHabilitado()){
               Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
      }
   }

   private boolean existeDuasAtividadesEmAberto() {
      return Objects.isNull(appropTimeBo.getAtividadeById(atividadeAtual.getId()))
              && Objects.nonNull(atividadeEmAberto());
   }

    private void botaoSalvarECriarNovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarECriarNovaActionPerformed
       if (verificaConcluirTelaPrincipal()) {
          try {
             salvarAtividade();
             botaoTarefaAtualActionPerformed(evt);
             if(comboTarefas.isEnabled()){
                comboTarefas.requestFocusInWindow();
             }
             else{
                botaoTarefaAtual.requestFocusInWindow();
             }
          } catch (Exception ex) {
             JOptionPane.showMessageDialog(this, "Error ao concluir atividade", "Não foi possível salvar atividade!", JOptionPane.ERROR_MESSAGE);
             if (TelaPrincipal.isDebugHabilitado()) {
                Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
             }
          }
       }
       atualizaObjetoAtividadeAtual();
    }//GEN-LAST:event_botaoSalvarECriarNovaActionPerformed

    private void txtDataTerminoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataTerminoFocusLost
       habilitaBotoesEOpcoes();
       if (txtDataTermino.isEditValid() == false) {
          txtDataTermino.setText(null);
       }
       checaDataHora();
    }//GEN-LAST:event_txtDataTerminoFocusLost

    private void txtDataInicioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataInicioFocusLost
       if (txtDataInicio.isEditValid() == false) {
          txtDataInicio.setText(null);
       }
       habilitaBotoesEOpcoes();
       checaDataHora();
    }//GEN-LAST:event_txtDataInicioFocusLost

    private void txtHoraInicioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoraInicioFocusLost
       if (txtHoraInicio.isEditValid() == false) {
          txtHoraInicio.setText(null);
       }
       checaDataHora();
       habilitaBotoesEOpcoes();
    }//GEN-LAST:event_txtHoraInicioFocusLost

    private void txtHoraTerminoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoraTerminoFocusLost
       if (txtHoraTermino.isEditValid() == false) {
          txtHoraTermino.setText(null);
       }
       checaDataHora();
       habilitaBotoesEOpcoes();
    }//GEN-LAST:event_txtHoraTerminoFocusLost

    private void botaoInicioAgoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoInicioAgoraActionPerformed
       Date dt = new Date(System.currentTimeMillis());
       txtDataInicio.setText(UtilMethods.formataDateParaString(dt, Constantes.PATTERN_DATA));
       txtHoraInicio.setText(UtilMethods.formataDateParaString(dt, Constantes.PATTERN_HORA));
       atualizaObjetoAtividadeAtual();
       habilitaBotoesEOpcoes();
    }//GEN-LAST:event_botaoInicioAgoraActionPerformed

    private void botaoTerminoAgoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoTerminoAgoraActionPerformed
       Date dt = new Date(System.currentTimeMillis());
       txtDataTermino.setText(UtilMethods.formataDateParaString(dt, Constantes.PATTERN_DATA));
       txtHoraTermino.setText(UtilMethods.formataDateParaString(dt, Constantes.PATTERN_HORA));
       atualizaObjetoAtividadeAtual();
       habilitaBotoesEOpcoes();
    }//GEN-LAST:event_botaoTerminoAgoraActionPerformed

    private void botaoInicioAgoraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botaoInicioAgoraFocusLost
       habilitaBotoesEOpcoes();
       checaDataHora();
    }//GEN-LAST:event_botaoInicioAgoraFocusLost

    private void botaoTerminoAgoraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botaoTerminoAgoraFocusLost
       habilitaBotoesEOpcoes();
       checaDataHora();
    }//GEN-LAST:event_botaoTerminoAgoraFocusLost

    private void botaoInicioAgoraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botaoInicioAgoraFocusGained
       habilitaBotoesEOpcoes();
       checaDataHora();
    }//GEN-LAST:event_botaoInicioAgoraFocusGained

    private void botaoTerminoAgoraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botaoTerminoAgoraFocusGained
       habilitaBotoesEOpcoes();
       checaDataHora();
    }//GEN-LAST:event_botaoTerminoAgoraFocusGained

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       if(atividadeExcluida()){
          botaoTarefaAtualActionPerformed(null);
       }

       if(atividadeNaoPersistida() && continuarTarefa == false){
          return;
       }

       //Caso esteja editando uma atividade
       if (editando || continuarTarefa) {
          desabilitaComboTarefa();
       }

       populaTela();
       habilitaBotoesEOpcoes();
       comboTarefas.setPopupVisible(false);
       labelStatusApp.setText("");
    }//GEN-LAST:event_formWindowActivated

   /***
    * Verificar se existe uma atividadeAtual diferente de nulo
    */
   private void populaTela() {
      if (atividadeNaoPersistida() && continuarTarefa == false) {
         inicializaAtividadeAtual();
      }
      tarefaAtual = atividadeAtual.getTarefa();
      alteraValorComboDesabilitada();
      txtDataInicio.setText(UtilMethods.formataDateParaString(atividadeAtual.getInicio(), Constantes.PATTERN_DATA));
      txtDataTermino.setText(UtilMethods.formataDateParaString(atividadeAtual.getTermino(), Constantes.PATTERN_DATA));
      txtHoraInicio.setText(UtilMethods.formataDateParaString(atividadeAtual.getInicio(), Constantes.PATTERN_HORA));
      txtHoraTermino.setText(UtilMethods.formataDateParaString(atividadeAtual.getTermino(), Constantes.PATTERN_HORA));
      jTextAreaObservacao.setText(formataCampoObservacao(atividadeAtual.getObservacoes()));

      if(atividadeAtual.getId().equals(Constantes.ID_ATIVIDADE_NAO_SALVA) == false){
         valorCodigoAtividade.setText(String.valueOf(atividadeAtual.getId()));
      }
      else{
         valorCodigoAtividade.setText("");
      }

      if(atividadeNaoPersistida() && continuarTarefa == false){
         habilitaComboTarefa();
      }
      else{
         desabilitaComboTarefa();
         continuarTarefa = false;
      }
      checaDataHora();
      jLabelValorDuracao.setText(calculaDuracaoTelaPrincipal());
   }

   private void alteraValorComboDesabilitada() {
      habilitaComboTarefa();
      comboTarefas.setSelectedItem(tarefaAtual.getDescricao());
      desabilitaComboTarefa();
   }

   private void botaoDialogSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoDialogSimActionPerformed
      System.exit(0);
   }//GEN-LAST:event_botaoDialogSimActionPerformed

   private void botaoDialogNaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoDialogNaoActionPerformed
      dialogConfirmacaoSair.setVisible(false);
   }//GEN-LAST:event_botaoDialogNaoActionPerformed

   private void botaoTarefaAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoTarefaAtualActionPerformed
      editando = false;
      Atividade atividadeSemTermino = atividadeEmAberto();
      //Caso em que não há uma atividade em aberto
      if (Objects.isNull(atividadeSemTermino)) {
         telaEmBranco();
      } //Caso em que há uma atividade em aberto
      else {
         atividadeAtual = atividadeSemTermino.cloneData();
         atividadeAtual.setId(atividadeSemTermino.getId());
         tarefaAtual = atividadeSemTermino.getTarefa().cloneData();
         tarefaAtual.setId(atividadeSemTermino.getTarefa().getId());

         populaTela();
         habilitaBotoesEOpcoes();
         desabilitaComboTarefa();
      }
      labelStatusDataHora.setText("");
      labelStatusDataHora.setText("");
      calculaDuracaoTelaPrincipal();
   }//GEN-LAST:event_botaoTarefaAtualActionPerformed

   private static Atividade atividadeEmAberto() {
      return appropTimeBo.getAtividadeSemTermino(user.getId());
   }

   private void botaoDataUltimaTarefaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoDataUltimaTarefaActionPerformed
      Date novaDataHora = appropTimeBo.getUltimaAtividadeFinalizada(user.getId()).getTermino();

      txtHoraInicio.setText(formataDateParaString(novaDataHora, Constantes.PATTERN_HORA));
      txtDataInicio.setText(formataDateParaString(novaDataHora, Constantes.PATTERN_DATA));

      calculaDuracaoTelaPrincipal();
   }//GEN-LAST:event_botaoDataUltimaTarefaActionPerformed

   private void botaoBucarObservacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoBucarObservacoesActionPerformed
      atualizaObjetoAtividadeAtual();
      dialogObservacoes.setLocationRelativeTo(this);
      dialogObservacoes.setVisible(true);
   }//GEN-LAST:event_botaoBucarObservacoesActionPerformed

   private void dialogObservacoesWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dialogObservacoesWindowActivated
      DefaultTableModel tbl = (DefaultTableModel) tabelaDeObservacoes.getModel();
      cleanTable(tbl);
      String ds_tarefa = (String) comboTarefas.getSelectedItem();
      if (ds_tarefa.trim().equals("") == false) {
         atividadesTabela = appropTimeBo.getObservacoesAtividadesByTarefa(user.getId(), ds_tarefa, "");
      } else {
         atividadesTabela = new ArrayList<>();
      }
      populaTable(atividadesTabela, tbl);
   }//GEN-LAST:event_dialogObservacoesWindowActivated

   private void tabelaDeObservacoesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaDeObservacoesKeyPressed
      if (evt.getKeyCode() == evt.VK_ENTER) {
         atualizaObservacoes();
      }
   }//GEN-LAST:event_tabelaDeObservacoesKeyPressed

   private void tabelaDeObservacoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaDeObservacoesMouseClicked
      if (evt.getClickCount() == Constantes.DOUBLE_CLICK) {
         atualizaObservacoes();
      }
   }//GEN-LAST:event_tabelaDeObservacoesMouseClicked

   private void atualizaObservacoes() {
      int row = tabelaDeObservacoes.getSelectedRow();
      int column = tabelaDeObservacoes.getSelectedColumn();
      if (jTextAreaObservacao.getText().trim().isEmpty()) {
         jTextAreaObservacao.setText(String.valueOf(tabelaDeObservacoes.getValueAt(row, column)));
      } else {
         String minhaObservacao = formataCampoObservacao(jTextAreaObservacao.getText());
         jTextAreaObservacao.setText(formataCampoObservacao(minhaObservacao + "\n" + String.valueOf(tabelaDeObservacoes.getValueAt(row, column))));
      }
      atividadeAtual.setObservacoes(jTextAreaObservacao.getText());
      fechaDialogObservacoes();
   }

   private void fechaDialogObservacoes() {
      filtroObservacoes.setText("");
      dialogObservacoes.setVisible(false);
   }

   private void irParaHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_irParaHistoricoActionPerformed
      atualizaObjetoAtividadeAtual();
      setVisible(false);
      if (Objects.isNull(this.telaRelatorio)) {
         this.telaRelatorio = new TelaRelatorio(appropTimeBo, user, this);
      }
      telaRelatorio.setLocationRelativeTo(this);
      telaRelatorio.setVisible(true);
   }//GEN-LAST:event_irParaHistoricoActionPerformed

   private void botaoOcultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoOcultarActionPerformed
      atualizaObjetoAtividadeAtual();
      setVisible(false);
      if (TelaPrincipal.isAutoFocusHabilitado() == false) {
         this.setAutoRequestFocus(false);
      }
      verificaIntervaloDaAplicacao(interval);
      setVisible(true);
      labelStatusApp.setText("");
   }//GEN-LAST:event_botaoOcultarActionPerformed

   private void opcaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoSalvarActionPerformed
      botaoSalvarActionPerformed(evt);
   }//GEN-LAST:event_opcaoSalvarActionPerformed

   private void opcaoSalvarECriarNovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoSalvarECriarNovaActionPerformed
      botaoSalvarECriarNovaActionPerformed(evt);
   }//GEN-LAST:event_opcaoSalvarECriarNovaActionPerformed

   private void opcaoOcultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoOcultarActionPerformed
      botaoOcultarActionPerformed(evt);
   }//GEN-LAST:event_opcaoOcultarActionPerformed

   private void opcaoTarefaAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoTarefaAtualActionPerformed
      botaoTarefaAtualActionPerformed(evt);
   }//GEN-LAST:event_opcaoTarefaAtualActionPerformed

   private void opcaoExportarCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoExportarCSVActionPerformed
      if (Objects.nonNull(atividadeAtual) && Objects.nonNull(atividadeAtual.getInicio()) && Objects.isNull(atividadeAtual.getTermino())) {
         JOptionPane.showMessageDialog(this, "Existe uma atividade em aberto. Esta atividade não será exportada.", "Export", JOptionPane.WARNING_MESSAGE);
      }
      StatusAppropTime status = appropTimeBo.exportToCsv(false, "");
      JOptionPane.showMessageDialog(this, status.getMensagem(), "Export", JOptionPane.INFORMATION_MESSAGE);
   }//GEN-LAST:event_opcaoExportarCSVActionPerformed

   private void opcaoImportarCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoImportarCsvActionPerformed
      JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
      jfc.setDialogTitle("Selecione um arquivo Csv");
      jfc.setAcceptAllFileFilterUsed(false);
      FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv", "CSV");
      jfc.addChoosableFileFilter(filter);

      int returnValue = jfc.showOpenDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
         StatusAppropTime statusImportToCsv = appropTimeBo.importToCsv(jfc.getSelectedFile().getPath());
         JOptionPane.showMessageDialog(this, statusImportToCsv.getMensagem(), "ImportCsv", JOptionPane.INFORMATION_MESSAGE);
      }
      populaComboBox();
   }//GEN-LAST:event_opcaoImportarCsvActionPerformed

   private void opcaoSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoSairActionPerformed
      dialogConfirmacaoSair.setLocationRelativeTo(this);
      dialogConfirmacaoSair.setVisible(true);
   }//GEN-LAST:event_opcaoSairActionPerformed

   private void opcaoCincoMinutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoCincoMinutosActionPerformed
      Long cincoMinutos = 5L;
      ocultaTela(cincoMinutos);
   }//GEN-LAST:event_opcaoCincoMinutosActionPerformed

   private void opcaoDezMinutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoDezMinutosActionPerformed
      Long dezMinutos = 10L;
      ocultaTela(dezMinutos);
   }//GEN-LAST:event_opcaoDezMinutosActionPerformed

   private void opcaoVinteMinutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoVinteMinutosActionPerformed
      Long vinteMinutos = 20L;
      ocultaTela(vinteMinutos);
   }//GEN-LAST:event_opcaoVinteMinutosActionPerformed

   private void opcaoTrintaMinutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoTrintaMinutosActionPerformed
      Long trintaMinutos = 30L;
      ocultaTela(trintaMinutos);
   }//GEN-LAST:event_opcaoTrintaMinutosActionPerformed

   private void opcaoQuarentaECincoMinutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoQuarentaECincoMinutosActionPerformed
      Long quarentaECincoMinutos = 45L;
      ocultaTela(quarentaECincoMinutos);
   }//GEN-LAST:event_opcaoQuarentaECincoMinutosActionPerformed

   private void opcaoUmaHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoUmaHoraActionPerformed
      Long umaHora = 60L;
      ocultaTela(umaHora);
   }//GEN-LAST:event_opcaoUmaHoraActionPerformed

   private void opcaoCancelarTarefaAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoCancelarTarefaAtualActionPerformed
      appropTimeBo.deleteAtividade(atividadeAtual.getId());
      habilitaComboTarefa();
      labelStatusApp.setText("");
      opcaoCancelarTarefaAtual.setEnabled(false);
      atividadeAtual = null;
      tarefaAtual = null;
      cleanTelaPrincipal();
   }//GEN-LAST:event_opcaoCancelarTarefaAtualActionPerformed

   private void botaoResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoResetActionPerformed
      if (Objects.isNull(atividadeAtual)) {
         cleanTelaPrincipal();
         habilitaComboTarefa();
         populaComboBox();
      } else {
         String descricao = atividadeAtual.getTarefa().getDescricao();
         if(Objects.nonNull(descricao)){
            comboTarefas.setSelectedItem(descricao);
         }
         else{
            comboTarefas.setSelectedItem("");
         }
         if (Objects.nonNull(atividadeAtual.getInicio())) {
            txtDataInicio.setText(UtilMethods.formataDateParaString(atividadeAtual.getInicio(), Constantes.PATTERN_DATA));
            txtHoraInicio.setText(UtilMethods.formataDateParaString(atividadeAtual.getInicio(), Constantes.PATTERN_HORA));
         }
         if (Objects.nonNull(atividadeAtual.getTermino())) {
            txtDataTermino.setText(UtilMethods.formataDateParaString(atividadeAtual.getTermino(), Constantes.PATTERN_DATA));
            txtHoraTermino.setText(UtilMethods.formataDateParaString(atividadeAtual.getTermino(), Constantes.PATTERN_HORA));
         } else {
            txtDataTermino.setText("");
            txtHoraTermino.setText("");
         }
         jTextAreaObservacao.setText(formataCampoObservacao(atividadeAtual.getObservacoes()));
      }
      calculaDuracaoTelaPrincipal();
      checaDataHora();
      atualizaObjetoAtividadeAtual();
      habilitaBotoesEOpcoes();
   }//GEN-LAST:event_botaoResetActionPerformed

   private void opcaoResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoResetActionPerformed
      botaoResetActionPerformed(evt);
   }//GEN-LAST:event_opcaoResetActionPerformed

   private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
      formWindowActivated(evt);
      if (TelaPrincipal.isAutoFocusHabilitado() == false) {
         this.setAutoRequestFocus(false);
      }
   }//GEN-LAST:event_formWindowOpened

   private void showUsageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showUsageActionPerformed
      dialogUsage.setLocationRelativeTo(this);
      textAreaUsage.setText(UtilMethods.removeCodigosUnicode(manualDeUso()));
      dialogUsage.setVisible(true);
   }//GEN-LAST:event_showUsageActionPerformed

   private void showInstallationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showInstallationActionPerformed
      dialogUsage.setLocationRelativeTo(this);
      textAreaUsage.setText(manualDeInstalacao());
      dialogUsage.setVisible(true);
   }//GEN-LAST:event_showInstallationActionPerformed

   private void txtDataInicioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataInicioKeyTyped
      if (evt.getKeyChar() != Constantes.UNICODE_BACKSPACE && evt.getKeyChar() != Constantes.UNICODE_DELETE) {
         if (txtDataInicio.getText().length() == 2) {
            txtDataInicio.setText(txtDataInicio.getText().concat("/"));
         } else if (txtDataInicio.getText().length() == 5) {
            txtDataInicio.setText(txtDataInicio.getText().concat("/"));
         }
         txtDataInicio.setText(UtilMethods.maxlength(txtDataInicio.getText(), Constantes.MAX_LENGTH_TEXT_DATA));
      }
   }//GEN-LAST:event_txtDataInicioKeyTyped

   private void txtDataTerminoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataTerminoKeyTyped
      if (evt.getKeyChar() != Constantes.UNICODE_BACKSPACE && evt.getKeyChar() != Constantes.UNICODE_DELETE) {
         if (txtDataTermino.getText().length() == 2) {
            txtDataTermino.setText(txtDataTermino.getText().concat("/"));
         } else if (txtDataTermino.getText().length() == 5) {
            txtDataTermino.setText(txtDataTermino.getText().concat("/"));
         }
         txtDataTermino.setText(UtilMethods.maxlength(txtDataTermino.getText(), Constantes.MAX_LENGTH_TEXT_DATA));
      }
   }//GEN-LAST:event_txtDataTerminoKeyTyped

   private void txtHoraInicioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHoraInicioKeyTyped
      if (evt.getKeyChar() != Constantes.UNICODE_BACKSPACE && evt.getKeyChar() != Constantes.UNICODE_DELETE) {
         if (txtHoraInicio.getText().length() == 2) {
            txtHoraInicio.setText(txtHoraInicio.getText().concat(":"));
         } else if (txtHoraInicio.getText().length() == 5) {
            txtHoraInicio.setText(txtHoraInicio.getText().concat(":"));
         }
         txtHoraInicio.setText(UtilMethods.maxlength(txtHoraInicio.getText(), Constantes.MAX_LENGTH_TEXT_HORA));
      }
   }//GEN-LAST:event_txtHoraInicioKeyTyped

   private void txtHoraTerminoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHoraTerminoKeyTyped
      if (evt.getKeyChar() != Constantes.UNICODE_BACKSPACE && evt.getKeyChar() != Constantes.UNICODE_DELETE) {

         if (txtHoraTermino.getText().length() == 2) {
            txtHoraTermino.setText(txtHoraTermino.getText().concat(":"));
         } else if (txtHoraTermino.getText().length() == 5) {
            txtHoraTermino.setText(txtHoraTermino.getText().concat(":"));
         }
         txtHoraTermino.setText(UtilMethods.maxlength(txtHoraTermino.getText(), Constantes.MAX_LENGTH_TEXT_HORA));
      }
   }//GEN-LAST:event_txtHoraTerminoKeyTyped

   private void txtDataInicioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDataInicioMousePressed
      if (txtDataInicio.getText().equals(Constantes.MASK_DATA)) {
         txtDataInicio.setCaretPosition(0);
      }
   }//GEN-LAST:event_txtDataInicioMousePressed

   private void txtDataTerminoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDataTerminoMousePressed
      if (txtDataTermino.getText().equals(Constantes.MASK_DATA)) {
         txtDataTermino.setCaretPosition(0);
      }
   }//GEN-LAST:event_txtDataTerminoMousePressed

   private void txtHoraInicioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHoraInicioMousePressed
      if (txtHoraInicio.getText().equals(Constantes.MASK_HORA)) {
         txtHoraInicio.setCaretPosition(0);
      }
   }//GEN-LAST:event_txtHoraInicioMousePressed

   private void txtHoraTerminoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHoraTerminoMousePressed
      if (txtHoraTermino.getText().equals(Constantes.MASK_HORA)) {
         txtHoraTermino.setCaretPosition(0);
      }
   }//GEN-LAST:event_txtHoraTerminoMousePressed

   private void tabelaDeObservacoesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaDeObservacoesMouseEntered
      tabelaDeObservacoes.getToolTipText(evt);
   }//GEN-LAST:event_tabelaDeObservacoesMouseEntered

   private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
      this.requestFocus();
   }//GEN-LAST:event_formMouseClicked

   private void dialogObservacoesWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dialogObservacoesWindowClosing
      filtroObservacoes.setText("");
   }//GEN-LAST:event_dialogObservacoesWindowClosing

   private void filtroObservacoesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filtroObservacoesKeyReleased
      String tarefa = comboTarefas.getSelectedItem().toString();
      if (filtroObservacoes.getText().trim().isEmpty() == false && Objects.nonNull(tarefa) && tarefa.trim().equals("") == false) {
         DefaultTableModel tbl = (DefaultTableModel) tabelaDeObservacoes.getModel();
         cleanTable(tbl);
         String filtro = filtroObservacoes.getText();
         List<String> observacoes = appropTimeBo.getObservacoesAtividadesByTarefa(user.getId(), tarefa, filtro);
         populaTable(observacoes, tbl);
      }
   }//GEN-LAST:event_filtroObservacoesKeyReleased

   private void opcaoDadosDeConexaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoDadosDeConexaoActionPerformed
      dialogDadosConexao.setLocationRelativeTo(this);
      dialogDadosConexao.setVisible(true);
   }//GEN-LAST:event_opcaoDadosDeConexaoActionPerformed

   private void dialogDadosConexaoWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dialogDadosConexaoWindowActivated
      String[] dadosDeConexao = appropTimeBo.getDadosDeConexao();
      String banco = dadosDeConexao[0];
      String host = dadosDeConexao[1];
      String usuario = dadosDeConexao[2];
      valorBanco.setText(banco);
      valorHost.setText(host);
      valorUsuario.setText(usuario);
   }//GEN-LAST:event_dialogDadosConexaoWindowActivated

  private void fieldEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldEmailActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_fieldEmailActionPerformed

  private void dialogPerfilWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dialogPerfilWindowOpened
    fieldEmail.setText(login);
    fieldNome.setText(user.getNome());
    comboTime.removeAllItems();
    comboTime.addItem("Nenhum");
    appropTimeBo.getAllTeams().forEach((t) -> {
        comboTime.addItem(t.getNome());
    });
    
    if(Objects.nonNull(user.getCargo())){
        fieldCargo.setText(user.getCargo().getNome());
    }
    else{
        fieldCargo.setText("Nenhum");
    }
    
    if(Objects.nonNull(user.getDepartamento())){
        fieldDepto.setText(user.getDepartamento().getNome());
    }
    else{
        fieldDepto.setText("Nenhum");
    }
    
    if(Objects.nonNull(user.getTime())){
        comboTime.setSelectedItem((user.getTime().getNome()));
    }
    else{
        comboTime.setSelectedItem("Nenhum");
    }
    
    if(Objects.nonNull(user.getEmpresa())){
        fieldEmpresa.setText(user.getEmpresa().getNome());
    }
    else{
        fieldEmpresa.setText("Nenhuma");
    }
    
    if(Objects.nonNull(user.getImage())){
        File f = new File("img");
        f.deleteOnExit();
        try {
            FileUtils.copyInputStreamToFile(user.getImage(), f);
        } catch (IOException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelImage.setIcon(new ImageIcon(new ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
    }
  }//GEN-LAST:event_dialogPerfilWindowOpened

  private void menuMeuPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMeuPerfilActionPerformed
    dialogPerfil.setLocationRelativeTo(this);
    dialogPerfil.setVisible(true);
  }//GEN-LAST:event_menuMeuPerfilActionPerformed

  private void btnSalvarPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarPerfilActionPerformed
    String email = fieldEmail.getText();
    String nome = fieldNome.getText();
    char[] passwd = fieldSenha.getPassword();
    
    if(StringUtils.isBlank(email) || StringUtils.isBlank(nome) || passwd.length == 0){
      JOptionPane.showMessageDialog(this, "Não pode haver campos em branco.", "Atualizar perfil",
                                       JOptionPane.WARNING_MESSAGE);
    }
    else if(UtilMethods.isEmailValid(email) == false){
      JOptionPane.showMessageDialog(this, "Digite um email válido.", "Atualizar perfil",
                                       JOptionPane.WARNING_MESSAGE);
    }
    else{
      if(comboTime.getSelectedItem().toString().equalsIgnoreCase("Nenhum") == false){
          Team team = appropTimeBo.getTeamByDescricao(comboTime.getSelectedItem().toString());
          user.setTime(team);
          appropTimeBo.atualizaUsuarioTeam(user);
      }
      else{
          user.setTime(null);
      }
      user.setLogin(email);
      user.setNome(nome);
      user.setPassword(UtilMethods.criptografarSenha(passwd));
      user.setImage(imgPerfil);
      appropTimeBo.atualizaUsuario(user);
      JOptionPane.showMessageDialog(this, "Atualizado com sucesso", "Atualizar perfil", JOptionPane.INFORMATION_MESSAGE);
      dialogPerfil.setVisible(false);
      fieldSenha.setText("");
      jLabelLoginUser.setText(user.getLogin());
      login = user.getLogin();
      senha = user.getPassword();      
    }
  }//GEN-LAST:event_btnSalvarPerfilActionPerformed

  private void fieldNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNomeActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_fieldNomeActionPerformed

  private void btnCancelChangesPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelChangesPerfilActionPerformed
    fieldEmail.setText(login);
    fieldNome.setText(user.getNome());
    fieldSenha.setText("");
    if(Objects.nonNull(labelImage.getIcon())){
        File f = new File("");
        try {
            FileUtils.copyInputStreamToFile(user.getImage(), f);
        } catch (IOException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelImage.setIcon(new ImageIcon(new ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
    }
  }//GEN-LAST:event_btnCancelChangesPerfilActionPerformed

  private void btnDeleteContaPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteContaPerfilActionPerformed
    int confirmDialogResult = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar a conta associada?"
                                                                  + " A aplicação fechará após a ação!",
            "Excluir Perfil", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    
    if(confirmDialogResult == 0){
      appropTimeBo.deleteUsuario(user);
      System.exit(0);
    }
  }//GEN-LAST:event_btnDeleteContaPerfilActionPerformed

    private void btnEscolherImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEscolherImagemActionPerformed
       JFileChooser fileChooser = new JFileChooser();
       fileChooser.showOpenDialog(null);
       File file = fileChooser.getSelectedFile();
       if(Objects.nonNull(file)){
        String fileName = file.getAbsolutePath();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(fileName).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        labelImage.setIcon(imageIcon);
           try {
               imgPerfil = new FileInputStream(file);
           } catch (FileNotFoundException ex) {
                if(TelaPrincipal.debugHabilitado){
                    Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
       }
    }//GEN-LAST:event_btnEscolherImagemActionPerformed

    private void menuWorkspaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuWorkspaceActionPerformed
        dialogWorkspace.setLocationRelativeTo(this);
        dialogWorkspace.setVisible(true);
    }//GEN-LAST:event_menuWorkspaceActionPerformed

    private void RemoverWorkspaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoverWorkspaceActionPerformed
        int confirmDialogResult = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este Workspace?",
            "Excluir Workspace", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    
        if(confirmDialogResult == 0){
            String name = "";
            if(listWorkspaces.getSelectedIndex() > -1){
                name = listWorkspaces.getSelectedValue();
                appropTimeBo.deleteWorkspace(appropTimeBo.getWorkspaceByDescricao(name).getId());
            }
            JOptionPane.showMessageDialog(this, "Removido com sucesso", "Workspace", JOptionPane.INFORMATION_MESSAGE);

            DefaultListModel dlm = (DefaultListModel) listWorkspaces.getModel();
            dlm.remove(dlm.indexOf(name));
        }
        populaComboBox();   
    }//GEN-LAST:event_RemoverWorkspaceActionPerformed

    private void AdicionarWorkspaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdicionarWorkspaceActionPerformed
       String nomeWorkspace = fieldNomeWorkspace.getText();
       Workspace workspace = new Workspace();
       workspace.setNome(nomeWorkspace);
       workspace.setUsr(user);
       appropTimeBo.adicionaWorkspace(workspace);
       listWorkspaces.updateUI();
       JOptionPane.showMessageDialog(this, "Adicionado com sucesso", "Workspace", JOptionPane.INFORMATION_MESSAGE);
       DefaultListModel dlm = (DefaultListModel) listWorkspaces.getModel();
       dlm.add(dlm.getSize(), workspace.getNome());
       fieldNomeWorkspace.setText("");
       populaComboBox();
    }//GEN-LAST:event_AdicionarWorkspaceActionPerformed

    private void dialogWorkspaceWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dialogWorkspaceWindowOpened
        List<Workspace> allWorkspaces = appropTimeBo.getAllWorkspacesByUsuario(user);
        DefaultListModel dlm = (DefaultListModel) listWorkspaces.getModel();
      
        for (Workspace workspace : allWorkspaces) {
            dlm.addElement(workspace.getNome());
        }
    }//GEN-LAST:event_dialogWorkspaceWindowOpened

    private void cancelarWorkspaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarWorkspaceActionPerformed
        fieldNomeWorkspace.setText("");
        dialogWorkspace.setVisible(false);
    }//GEN-LAST:event_cancelarWorkspaceActionPerformed

    private void menuProjetosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProjetosActionPerformed
        dialogProject.setLocationRelativeTo(this);
        dialogProject.setVisible(true);
    }//GEN-LAST:event_menuProjetosActionPerformed

    private void btnRemoverProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverProjetoActionPerformed
        int confirmDialogResult = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este Projeto?",
            "Excluir Projeto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    
        if(confirmDialogResult == 0){
            String name = "";
            if(listProjects.getSelectedIndex() > -1){
                name = listProjects.getSelectedValue();
                appropTimeBo.deleteProjeto(appropTimeBo.getProjetoByDescricao(name).getId());
            }
            JOptionPane.showMessageDialog(this, "Removido com sucesso", "Projeto", JOptionPane.INFORMATION_MESSAGE);

            DefaultListModel dlm = (DefaultListModel) listProjects.getModel();
            dlm.remove(dlm.indexOf(name));
            populaComboBox();
        }
        
    }//GEN-LAST:event_btnRemoverProjetoActionPerformed

    private void btnAdicionarProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarProjetoActionPerformed
       String nomeProjeto = fieldNomeProjeto.getText();
       Projeto proj = new Projeto();
       proj.setNome(nomeProjeto);
       proj.setUsr(user);
       appropTimeBo.adicionaProjeto(proj);
       listProjects.updateUI();
       JOptionPane.showMessageDialog(this, "Adicionado com sucesso", "Projeto", JOptionPane.INFORMATION_MESSAGE);
       DefaultListModel dlm = (DefaultListModel) listProjects.getModel();
       dlm.add(dlm.getSize(), proj.getNome());
       fieldNomeProjeto.setText("");
       populaComboBox();
    }//GEN-LAST:event_btnAdicionarProjetoActionPerformed

    private void btnCancelarProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarProjetoActionPerformed
        fieldNomeProjeto.setText("");
        dialogProject.setVisible(false);
    }//GEN-LAST:event_btnCancelarProjetoActionPerformed

    private void removerTagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerTagActionPerformed
        int confirmDialogResult = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar esta etiqueta?",
            "Excluir Etiqeta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    
        if(confirmDialogResult == 0){
            String name = "";
            if(listTags.getSelectedIndex() > -1){
                name = listTags.getSelectedValue();
                appropTimeBo.deleteTag(appropTimeBo.getTagByDescricao(name).getId());
            }
            JOptionPane.showMessageDialog(this, "Removido com sucesso", "Etiquetas", JOptionPane.INFORMATION_MESSAGE);

            DefaultListModel dlm = (DefaultListModel) listTags.getModel();
            dlm.remove(dlm.indexOf(name));
        }
        populaComboBox();
    }//GEN-LAST:event_removerTagActionPerformed

    private void adicionarTagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarTagActionPerformed
       String nomeTag = fieldNomeTag.getText();
       Tag tag = new Tag();
       tag.setNome(nomeTag);
       tag.setUsuario(user);
       appropTimeBo.adicionaTag(tag);
       JOptionPane.showMessageDialog(this, "Adicionado com sucesso", "Etiquetas", JOptionPane.INFORMATION_MESSAGE);
       DefaultListModel dlm = (DefaultListModel) listTags.getModel();
       dlm.add(dlm.getSize(), tag.getNome());
       fieldNomeTag.setText("");
    }//GEN-LAST:event_adicionarTagActionPerformed

    private void cancelarTagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarTagActionPerformed
        fieldNomeTag.setText("");
        dialogTag.setVisible(false);
    }//GEN-LAST:event_cancelarTagActionPerformed

    private void menuTagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTagActionPerformed
        dialogTag.setLocationRelativeTo(this);
        dialogTag.setVisible(true);
    }//GEN-LAST:event_menuTagActionPerformed

    private void dialogProjectWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dialogProjectWindowOpened
        List<Projeto> allProjetos = appropTimeBo.getAllProjetosByUsuario(user);
        DefaultListModel dlm = (DefaultListModel) listProjects.getModel();
      
        for (Projeto proj : allProjetos) {
            dlm.addElement(proj.getNome());
        }
    }//GEN-LAST:event_dialogProjectWindowOpened

    private void dialogTagWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dialogTagWindowOpened
        List<Tag> allTags = appropTimeBo.getAllTagsByUsuario(user);
        DefaultListModel dlm = (DefaultListModel) listTags.getModel();
      
        for (Tag tag : allTags) {
            dlm.addElement(tag.getNome());
        }
    }//GEN-LAST:event_dialogTagWindowOpened

    private void menuAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAdminActionPerformed
        if(menuAdmin.isEnabled()){
            dialogAdmin.setLocationRelativeTo(this);
            dialogAdmin.setVisible(true);
        }
    }//GEN-LAST:event_menuAdminActionPerformed

    private void btnAdicionarCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarCargoActionPerformed
       String nomeCargo = fieldNomeCargo.getText();
       Role cargo = new Role();
       cargo.setNome(nomeCargo);
       appropTimeBo.adicionaCargo(cargo);
       JOptionPane.showMessageDialog(this, "Adicionado com sucesso", "Cargo", JOptionPane.INFORMATION_MESSAGE);
       DefaultListModel dlm = (DefaultListModel) listCargo.getModel();
       dlm.add(dlm.getSize(), cargo.getNome());
       fieldNomeCargo.setText("");
        comboCargos.removeAllItems();
        appropTimeBo.getAllRoles().forEach( (role) -> {
            comboCargos.addItem(role.getNome());
        });
    }//GEN-LAST:event_btnAdicionarCargoActionPerformed

    private void btnCancelarCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCargoActionPerformed
        fieldNomeCargo.setText("");
    }//GEN-LAST:event_btnCancelarCargoActionPerformed

    private void removerCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerCargoActionPerformed
        int confirmDialogResult = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este Cargo?",
            "Excluir Cargo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    
        if(confirmDialogResult == 0){
            String name = "";
            if(listCargo.getSelectedIndex() > -1){
                name = listCargo.getSelectedValue();
                appropTimeBo.deleteRole(appropTimeBo.getRoleByDescricao(name).getId());
            }
            JOptionPane.showMessageDialog(this, "Removido com sucesso", "Cargos", JOptionPane.INFORMATION_MESSAGE);

            DefaultListModel dlm = (DefaultListModel) listCargo.getModel();
            dlm.remove(dlm.indexOf(name));
        }
        comboCargos.removeAllItems();
        appropTimeBo.getAllRoles().forEach( (role) -> {
            comboCargos.addItem(role.getNome());
        });
    }//GEN-LAST:event_removerCargoActionPerformed

    private void btnVincularCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVincularCargoActionPerformed
        Role role = appropTimeBo.getRoleByDescricao(comboCargos.getEditor().getItem().toString());
        if(listCargo.getSelectedIndex() > -1){
            String name = listUsuarios.getSelectedValue();
            Usuario usr = appropTimeBo.getUsuarioByEmail(name);
            usr.setCargo(role);
            appropTimeBo.atualizaUsuarioCargo(usr);
            
        }
        JOptionPane.showMessageDialog(this, "Vinculado com sucesso", "Cargos", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnVincularCargoActionPerformed

    private void dialogAdminWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dialogAdminWindowOpened
        List<Usuario> allUsuarios = appropTimeBo.getAllUsuarios();
        DefaultListModel dlm = (DefaultListModel) listUsuarios.getModel();
      
        for (Usuario usr : allUsuarios) {
            dlm.addElement(usr.getLogin());
        }
        
        List<Role> allRoles = appropTimeBo.getAllRoles();
        DefaultListModel dlm2 = (DefaultListModel) listCargo.getModel();
      
        for (Role role : allRoles) {
            dlm2.addElement(role.getNome());
        }
        appropTimeBo.getAllRoles().forEach( (role) -> {
            comboCargos.addItem(role.getNome());
        });
        
        List<Departamento> allDeptos = appropTimeBo.getAllDeptos();
        DefaultListModel dlm3 = (DefaultListModel) listDepto.getModel();
      
        for (Departamento depto : allDeptos) {
            dlm3.addElement(depto.getNome());
        }
        
        appropTimeBo.getAllDeptos().forEach( (d) -> {
            comboDepto.addItem(d.getNome());
        });
        
        List<Team> allTeams = appropTimeBo.getAllTeams();
        DefaultListModel dlm4 = (DefaultListModel) listTime.getModel();
      
        for (Team t : allTeams) {
            dlm4.addElement(t.getNome());
        }
        
        
    }//GEN-LAST:event_dialogAdminWindowOpened

    private void btnVincularDeptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVincularDeptoActionPerformed
        Departamento depto = appropTimeBo.getDeptoByDescricao(comboDepto.getSelectedItem().toString());
        if(listUsuarios.getSelectedIndex() > -1){
            String name = listUsuarios.getSelectedValue();
            Usuario usr = appropTimeBo.getUsuarioByEmail(name);
            usr.setDepartamento(depto);
            appropTimeBo.atualizaUsuarioDepto(usr); 
        }
        JOptionPane.showMessageDialog(this, "Vinculado com sucesso", "Departamento", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnVincularDeptoActionPerformed

    private void btnAdicionarDeptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarDeptoActionPerformed
       String nomeDepto = fieldNomeDepto.getText();
       Departamento depto = new Departamento();
       depto.setNome(nomeDepto);
       appropTimeBo.adicionaDepto(depto);
       JOptionPane.showMessageDialog(this, "Adicionado com sucesso", "Departamento", JOptionPane.INFORMATION_MESSAGE);
       DefaultListModel dlm = (DefaultListModel) listDepto.getModel();
       dlm.add(dlm.getSize(), depto.getNome());
       fieldNomeDepto.setText("");
       comboDepto.removeAllItems();
        appropTimeBo.getAllDeptos().forEach( (d) -> {
            comboDepto.addItem(d.getNome());
        });
    }//GEN-LAST:event_btnAdicionarDeptoActionPerformed

    private void removerDeptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerDeptoActionPerformed
        String name = "";
        if(listDepto.getSelectedIndex() > -1){
            int confirmDialogResult = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este Departamento?",
                "Excluir Departamento", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if(confirmDialogResult == 0){
                    name = listDepto.getSelectedValue();
                    appropTimeBo.deleteDepto(appropTimeBo.getDeptoByDescricao(name).getId());
                }
                JOptionPane.showMessageDialog(this, "Removido com sucesso", "Departamento", JOptionPane.INFORMATION_MESSAGE);

                DefaultListModel dlm = (DefaultListModel) listDepto.getModel();
                dlm.remove(dlm.indexOf(name));
            comboDepto.removeAllItems();
            appropTimeBo.getAllDeptos().forEach( (d) -> {
                comboDepto.addItem(d.getNome());
            });
        }
    }//GEN-LAST:event_removerDeptoActionPerformed

    private void removerTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerTimeActionPerformed
        int confirmDialogResult = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este Time?",
            "Excluir Time", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    
        if(confirmDialogResult == 0){
            String name = "";
            if(listTime.getSelectedIndex() > -1){
                name = listTime.getSelectedValue();
                appropTimeBo.deleteTeam(appropTimeBo.getTeamByDescricao(name).getId());
            }
            JOptionPane.showMessageDialog(this, "Removido com sucesso", "Times", JOptionPane.INFORMATION_MESSAGE);

            DefaultListModel dlm = (DefaultListModel) listTime.getModel();
            dlm.remove(dlm.indexOf(name));
        }
    }//GEN-LAST:event_removerTimeActionPerformed

    private void btnAdicionarTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarTimeActionPerformed
        String nomeTime = fieldNomeTime.getText();
       Team time = new Team();
       time.setNome(nomeTime);
       appropTimeBo.adicionaTeam(time);
       JOptionPane.showMessageDialog(this, "Adicionado com sucesso", "Time", JOptionPane.INFORMATION_MESSAGE);
       DefaultListModel dlm = (DefaultListModel) listTime.getModel();
       dlm.add(dlm.getSize(), time.getNome());
       fieldNomeTime.setText("");
    }//GEN-LAST:event_btnAdicionarTimeActionPerformed

    private void btnCancelarDeptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarDeptoActionPerformed
        fieldNomeDepto.setText("");
    }//GEN-LAST:event_btnCancelarDeptoActionPerformed

    private void btnCancelarTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarTimeActionPerformed
       fieldNomeTime.setText("");
    }//GEN-LAST:event_btnCancelarTimeActionPerformed

   /**
    * @param args the command line arguments
    */
   public static void main(final String args[]) {

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {

            Scanner ler = new Scanner(System.in);

            try {
               for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
               if (TelaPrincipal.isDebugHabilitado()) {
                  Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
               }
            }

            CommandLine commandLine;
            Option option_username = Option.builder("u")
                    .hasArg()
                    .required(false)
                    .desc("Login do usuario")
                    .longOpt("username")
                    .build();

            Option option_password = Option.builder("p")
                    .hasArg()
                    .required(false)
                    .desc("Password do usuario")
                    .longOpt("password")
                    .build();

            Option option_interval = Option.builder("i")
                    .required(false)
                    .desc("Intervalo de reabertura da aplicação")
                    .longOpt("interval")
                    .hasArg()
                    .build();

            Option option_banco = Option.builder("b")
                    .required(false)
                    .desc("URL do banco de dados")
                    .longOpt("urlsgbd")
                    .hasArg()
                    .build();

            Option option_help = Option.builder("h")
                    .required(false)
                    .desc("Manual de uso da aplicação")
                    .longOpt("help")
                    .hasArg(false)
                    .build();

            Option option_debug = Option.builder()
                    .required(false)
                    .desc("Modo debug")
                    .longOpt("debug")
                    .hasArg(false)
                    .build();

            Option option_installation = Option.builder()
                    .required(false)
                    .desc("Exibe o manual de instalação da aplicação")
                    .longOpt("installation")
                    .hasArg(false)
                    .build();

            Option option_export = Option.builder("X")
                    .required(false)
                    .desc("Faz o export das atividades por linha de comando")
                    .longOpt("X")
                    .hasArg(true)
                    .optionalArg(true)
                    .build();

            Option option_ddl = Option.builder()
                    .required(false)
                    .desc("Comandos DDL da base")
                    .longOpt("getddl")
                    .hasArg(false)
                    .build();

            Option option_autoOpen = Option.builder("a")
                    .required(false)
                    .longOpt("autoopen")
                    .hasArg(false)
                    .build();

            Option option_autoFocus = Option.builder("f")
                    .required(false)
                    .longOpt("autofocus")
                    .hasArg(false)
                    .build();

            Options options = new Options();
            CommandLineParser parser = new DefaultParser();

            options.addOption(option_username);
            options.addOption(option_password);
            options.addOption(option_interval);
            options.addOption(option_banco);
            options.addOption(option_help);
            options.addOption(option_debug);
            options.addOption(option_installation);
            options.addOption(option_export);
            options.addOption(option_ddl);
            options.addOption(option_autoOpen);
            options.addOption(option_autoFocus);

            String urlsgbd = "";
            boolean exportInline = false;
            try {
               commandLine = parser.parse(options, args);
               if (commandLine.hasOption("debug")) {
                  debugHabilitado = true;
               }

               if (commandLine.hasOption("h") == true) {
                  System.out.println("");
                  System.out.println(mensagemConsole(manualDeUso()));
                  return;
               }

               if (commandLine.hasOption("getddl")) {
                  if (commandLine.hasOption("X") || commandLine.hasOption("a") || commandLine.hasOption("installation")
                          || commandLine.hasOption("u") || commandLine.hasOption("p") || commandLine.hasOption("i")
                          || commandLine.hasOption("b")) {
                     System.err.println("\n" + mensagemConsole(Constantes.ANSI_RED + "Argumentos inválidos" + Constantes.ANSI_RESET));
                     System.out.println("\n");
                     System.out.println(mensagemConsole(manualDeUso()));
                  } else {
                     File ddl = AppropTimeBo.getDDL();
                     System.out.println(mensagemConsole(Constantes.ANSI_GREEN + Constantes.ANSI_BOLD + "Arquivo com comandos DDL criado em: "));
                     System.out.println(mensagemConsole(Constantes.ANSI_YELLOW + ddl.getAbsolutePath() + Constantes.ANSI_RESET_BOLD +
                                        Constantes.ANSI_RESET));
                  }

                  System.exit(0);
               }

               if (commandLine.hasOption("X") == true) {
                  if (commandLine.hasOption("b") == false || commandLine.hasOption("u") == false || commandLine.hasOption("installation") == true
                          || commandLine.hasOption("p") == false || commandLine.hasOption("i") == true) {

                     System.err.println("\n" + mensagemConsole(Constantes.ANSI_RED + "Argumentos inválidos" + Constantes.ANSI_RESET));
                     System.out.println("\n");
                     System.out.println(mensagemConsole(manualDeUso()));
                     return;
                  }
                  exportInline = true;
               }

               if (commandLine.hasOption("installation") == true) {
                  if (commandLine.hasOption("b") == true || commandLine.hasOption("u") == true || commandLine.hasOption("X") == true
                          || commandLine.hasOption("p") == true || commandLine.hasOption("i") == true || commandLine.hasOption("getddl") == true) {
                     System.err.println("\n" + mensagemConsole(Constantes.ANSI_RED + "Argumentos inválidos" + Constantes.ANSI_RESET));
                     System.out.println("\n");
                     System.out.println(mensagemConsole(manualDeUso()));
                     System.exit(0);
                  } else {
                     System.out.println(manualDeInstalacao());
                     System.exit(0);
                  }
               }

               if (commandLine.hasOption("autofocus")) {
                  autoFocusHabilitado = true;
               }
               
                if(Constantes.DEFAULT_URLSGB == false){
                    if (commandLine.hasOption("b") == true) {
                       urlsgbd = String.valueOf(commandLine.getOptionValue("b"));
                       String[] splitUrlsgbd = urlsgbd.split(":");
                       if (splitUrlsgbd.length != 4) {
                          throw new IllegalArgumentException("A url de conexão com banco de dados está inválida");
                       }
                    } else {
                       System.out.println(mensagemConsole(Constantes.ANSI_GREEN + "Usuario do banco: " + Constantes.ANSI_RESET));
                       urlsgbd = ler.nextLine();
                       urlsgbd += ":";
                       System.out.println(mensagemConsole(Constantes.ANSI_GREEN + "Senha do banco: " + Constantes.ANSI_RESET));

                       Console cons;
                       char[] passwd;
                       if ((cons = System.console()) != null && (passwd = cons.readPassword()) != null) {
                          urlsgbd += String.copyValueOf(passwd);
                          java.util.Arrays.fill(passwd, ' ');
                       }
                       urlsgbd += ":";

                       System.out.println(mensagemConsole(Constantes.ANSI_GREEN + "Host do banco: " + Constantes.ANSI_RESET));
                       urlsgbd += ler.nextLine();
                       System.out.println(mensagemConsole(Constantes.ANSI_GREEN + "Port do banco: " + Constantes.ANSI_RESET));
                       urlsgbd += ":";
                       urlsgbd += ler.nextLine();
                    }

                    urlsgbd = "root:matheus*007:localhost:3306";
                }
                try {
                   appropTimeBo = AppropTimeBo.conectarBanco(urlsgbd);
                   if (Objects.isNull(appropTimeBo) && exportInline == false) {
                      AppropTimeBo.criarBanco();
                      appropTimeBo = AppropTimeBo.conectarBanco(urlsgbd);
                   } else if (Objects.isNull(appropTimeBo) && exportInline == true) {
                      System.err.println("\n" + mensagemConsole(Constantes.ANSI_RED + "Argumentos de conexão com banco inválidos" + Constantes.ANSI_RESET));
                      System.out.println("\n");
                      System.out.println(mensagemConsole(manualDeUso()));
                      System.exit(0);
                   }
                } catch (Exception ex) {
                   System.err.println(mensagemConsole(Constantes.ANSI_RED + "Não foi possivel conectar ao Banco de Dados." + Constantes.ANSI_RESET));
                   if (TelaPrincipal.isDebugHabilitado()) {
                      Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
                   }
                   ler.close();
                   return;
                }

               if (commandLine.hasOption("u") == true) {
                  login = String.valueOf(commandLine.getOptionValue("u"));
               } else {
                  System.out.println(mensagemConsole(Constantes.ANSI_GREEN + "Usuario(email): " + Constantes.ANSI_RESET));
                  login = ler.nextLine();
               }

               if (UtilMethods.isEmailValid(login) == false) {
                  System.out.print("Login: " + login);
                  System.out.println(mensagemConsole(Constantes.ANSI_RED + " Email inválido!" + Constantes.ANSI_RESET));
                  System.exit(0);
               }

               if (commandLine.hasOption("p") == true) {
                  senha = CriptografiaUtil.senhaHash(commandLine.getOptionValue("p"));
               } else {
                  System.out.println(mensagemConsole(Constantes.ANSI_GREEN + "Senha: " + Constantes.ANSI_RESET));
                  senha = UtilMethods.lerSenhaECriptografar();
               }

               if (commandLine.hasOption("i") == true) {
                  interval = Long.valueOf(commandLine.getOptionValue("i"));
                  if (interval < 0) {
                     throw new IllegalArgumentException("O valor do parâmetro -i deve ser maior ou igual a 0 (zero).");
                  }
                  if (commandLine.hasOption("X") || commandLine.hasOption("getddl")) {
                     System.err.println("\n" + mensagemConsole(Constantes.ANSI_RED + "Argumentos inválidos" + Constantes.ANSI_RESET));
                     System.out.println("\n");
                     System.out.println(mensagemConsole(manualDeUso()));
                     System.exit(0);
                  }
               }

               if (commandLine.hasOption("a") == true) {
                  if (commandLine.hasOption("X") || commandLine.hasOption("getddl")) {
                     System.err.println("\n" + mensagemConsole(Constantes.ANSI_RED + "Argumentos inválidos" + Constantes.ANSI_RESET));
                     System.out.println("\n");
                     System.out.println(mensagemConsole(manualDeUso()));
                     System.exit(0);
                  }
                  autoOpen = true;
               }

               user = appropTimeBo.verificaLogin(login, senha);

            } catch (Exception exception) {
               if (TelaPrincipal.isDebugHabilitado()) {
                  Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, exception);
               }
               System.err.println(mensagemConsole(Constantes.ANSI_RED + "Exception: " + exception.getLocalizedMessage() + Constantes.ANSI_RESET));
               System.out.println("\n");
               System.out.println(mensagemConsole(manualDeUso()));
               return;
            }
            if (Objects.isNull(user) && exportInline == false) {
               login = "";
               senha = "";
               boolean cadastrou = cadastroUsuario();
               if (cadastrou == false) {
                  System.out.println(mensagemConsole(Constantes.ANSI_YELLOW + "O usuário não foi cadastrado." + Constantes.ANSI_RESET));
                  return;
               }
            } else if (Objects.isNull(user) && exportInline == true) {
               System.err.println("\n" + mensagemConsole(Constantes.ANSI_RED + "Argumentos de login/senha inválidos" + Constantes.ANSI_RESET));
               System.out.println("\n");
               System.out.println(mensagemConsole(manualDeUso()));
               return;
            }
            try {
               user = appropTimeBo.verificaLogin(login, senha);
               boolean reabrirAplicacao = false;
               if (exportInline == false) {
                  File lock = new File("lock_" + user.getLogin());
                  boolean existsLockFile = appropTimeBo.verificaLockFile(lock);
                  if (existsLockFile) {
                     if (TelaPrincipal.isAutoOpen() == false) {
                        String answer;
                        System.out.println(mensagemConsole(Constantes.ANSI_YELLOW + "Já exite uma instância da aplicação aberta." + Constantes.ANSI_RESET));
                        System.out.println(mensagemConsole(Constantes.ANSI_GREEN + "Deseja reabrir a aplicação? [yes/no]" + Constantes.ANSI_RESET));
                        System.out.println("#?");
                        answer = ler.nextLine();

                        if (answer.equals("yes")) {
                           reabrirAplicacao = true;
                           alteraLockEReabreApp();
                        } else if (answer.equals("no")) {
                           System.exit(0);
                        } else {
                           System.err.println("\n" + mensagemConsole(Constantes.ANSI_RED + "Resposta inválida. Apenas [yes/no]" + Constantes.ANSI_RESET));
                           System.out.println("\n");
                           System.exit(0);
                        }
                     } else {
                        System.out.println(mensagemConsole(Constantes.ANSI_GREEN + Constantes.ANSI_BOLD + "Já existe uma instância do approptime em execução. "
                                + "Foi enviado um sinal para que ela seja recarregada. Caso a aplicação não abra em até "
                                + Constantes.TEMPO_MINIMO_PARA_REABRIR_APLICACAO + " segundos, apague o arquivo lock localizado em: "
                                + Constantes.ANSI_YELLOW + lock.getAbsolutePath() + Constantes.ANSI_GREEN + " e tente novamente" + Constantes.ANSI_RESET));
                        reabrirAplicacao = true;
                        alteraLockEReabreApp();
                     }
                  }
               }

               if (Objects.isNull(user)) {
                  return;
               }
               if (exportInline == false) {
                  if (reabrirAplicacao == false) {
                     new TelaPrincipal().setVisible(true);
                  } else {
                     System.exit(0);
                  }
               } else {
                  if (option_export.hasArg()) {
                     String pathToExport = commandLine.getOptionValue("X");
                     if (Objects.nonNull(commandLine.getOptionValue("X"))) {
                        StatusAppropTime status = appropTimeBo.exportToCsv(true, pathToExport);
                        System.out.println(mensagemConsole(Constantes.ANSI_YELLOW + status.getMensagem() + Constantes.ANSI_RESET));
                        System.exit(0);
                     } else {
                        StatusAppropTime status = appropTimeBo.exportToCsv(true, "");
                        System.out.println(mensagemConsole(Constantes.ANSI_YELLOW + status.getMensagem() + Constantes.ANSI_RESET));
                        System.exit(0);
                     }
                  }

               }
               ler.close();
            } catch (Exception ex) {
               if (TelaPrincipal.isDebugHabilitado()) {
                  Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
               }
               ler.close();
               return;
            }
         }
      }
      );
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AdicionarWorkspace;
    private javax.swing.JButton RemoverWorkspace;
    private javax.swing.JButton adicionarTag;
    private javax.swing.JButton botaoBucarObservacoes;
    private javax.swing.JButton botaoDataUltimaTarefa;
    private javax.swing.JButton botaoDialogNao;
    private javax.swing.JButton botaoDialogSim;
    private javax.swing.JButton botaoInicioAgora;
    private javax.swing.JButton botaoOcultar;
    private javax.swing.JButton botaoReset;
    private javax.swing.JButton botaoSalvar;
    private javax.swing.JButton botaoSalvarECriarNova;
    private javax.swing.JButton botaoTarefaAtual;
    private javax.swing.JButton botaoTerminoAgora;
    private javax.swing.JButton btnAdicionarCargo;
    private javax.swing.JButton btnAdicionarDepto;
    private javax.swing.JButton btnAdicionarProjeto;
    private javax.swing.JButton btnAdicionarTime;
    private javax.swing.JButton btnCancelChangesPerfil;
    private javax.swing.JButton btnCancelarCargo;
    private javax.swing.JButton btnCancelarDepto;
    private javax.swing.JButton btnCancelarProjeto;
    private javax.swing.JButton btnCancelarTime;
    private javax.swing.JButton btnDeleteContaPerfil;
    private javax.swing.JButton btnEscolherImagem;
    private javax.swing.JButton btnRemoverProjeto;
    private javax.swing.JButton btnSalvarPerfil;
    private javax.swing.JButton btnVincularCargo;
    private javax.swing.JButton btnVincularDepto;
    private javax.swing.JButton cancelarTag;
    private javax.swing.JButton cancelarWorkspace;
    private javax.swing.JComboBox<String> comboCargos;
    private javax.swing.JComboBox<String> comboDepto;
    private javax.swing.JComboBox<String> comboProjeto;
    private javax.swing.JComboBox<String> comboTags;
    private javax.swing.JComboBox<String> comboTarefas;
    private javax.swing.JComboBox<String> comboTime;
    private javax.swing.JComboBox<String> comboWorkspace;
    private javax.swing.JDialog dialogAdmin;
    private javax.swing.JDialog dialogConfirmacaoSair;
    private javax.swing.JDialog dialogDadosConexao;
    private javax.swing.JDialog dialogObservacoes;
    private javax.swing.JDialog dialogPerfil;
    private javax.swing.JDialog dialogProject;
    private javax.swing.JDialog dialogTag;
    private javax.swing.JDialog dialogTeam;
    private javax.swing.JDialog dialogUsage;
    private javax.swing.JDialog dialogWorkspace;
    private javax.swing.JTextField fieldCargo;
    private javax.swing.JTextField fieldDepto;
    private javax.swing.JTextField fieldEmail;
    private javax.swing.JTextField fieldEmpresa;
    private javax.swing.JTextField fieldNome;
    private javax.swing.JTextField fieldNomeCargo;
    private javax.swing.JTextField fieldNomeDepto;
    private javax.swing.JTextField fieldNomeProjeto;
    private javax.swing.JTextField fieldNomeTag;
    private javax.swing.JTextField fieldNomeTime;
    private javax.swing.JTextField fieldNomeWorkspace;
    private javax.swing.JPasswordField fieldSenha;
    private javax.swing.JTextField filtroObservacoes;
    private javax.swing.JMenuItem irParaHistorico;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelLoginUser;
    private javax.swing.JLabel jLabelValorDuracao;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTextArea jTextAreaObservacao;
    private javax.swing.JLabel labelBanco;
    private javax.swing.JLabel labelCodigoAtividade;
    private javax.swing.JLabel labelDialogObservacoes;
    private javax.swing.JLabel labelDuracao;
    private javax.swing.JLabel labelEmail;
    private javax.swing.JLabel labelHost;
    private javax.swing.JLabel labelImage;
    private javax.swing.JLabel labelInicio;
    private javax.swing.JLabel labelInicio1;
    private javax.swing.JLabel labelInicio2;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelObservacao;
    private javax.swing.JLabel labelSenha;
    private javax.swing.JLabel labelStatusApp;
    private javax.swing.JLabel labelStatusDataHora;
    private javax.swing.JLabel labelTarefa;
    private javax.swing.JLabel labelTermino;
    private javax.swing.JLabel labelUser;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JLabel labelWorkspace;
    private javax.swing.JList<String> listCargo;
    private javax.swing.JList<String> listDepto;
    private javax.swing.JList<String> listProjects;
    private javax.swing.JList<String> listTags;
    private javax.swing.JList<String> listTime;
    private javax.swing.JList<String> listUsuarios;
    private javax.swing.JList<String> listWorkspaces;
    private javax.swing.JLabel mensagemDialogSair;
    private javax.swing.JMenuItem menuAdmin;
    private javax.swing.JMenu menuArquivo;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFerramentas;
    private javax.swing.JMenuItem menuMeuPerfil;
    private javax.swing.JMenuItem menuProjetos;
    private javax.swing.JMenu menuSobre;
    private javax.swing.JMenuItem menuTag;
    private javax.swing.JMenuItem menuWorkspace;
    private javax.swing.JMenuItem opcaoCancelarTarefaAtual;
    private javax.swing.JMenuItem opcaoCincoMinutos;
    private javax.swing.JMenuItem opcaoDadosDeConexao;
    private javax.swing.JMenuItem opcaoDezMinutos;
    private javax.swing.JMenuItem opcaoExportarCSV;
    private javax.swing.JMenuItem opcaoImportarCsv;
    private javax.swing.JMenuItem opcaoOcultar;
    private javax.swing.JMenuItem opcaoQuarentaECincoMinutos;
    private javax.swing.JMenuItem opcaoReset;
    private javax.swing.JMenuItem opcaoSair;
    private javax.swing.JMenuItem opcaoSalvar;
    private javax.swing.JMenuItem opcaoSalvarECriarNova;
    private javax.swing.JMenuItem opcaoTarefaAtual;
    private javax.swing.JMenuItem opcaoTrintaMinutos;
    private javax.swing.JMenuItem opcaoUmaHora;
    private javax.swing.JMenuItem opcaoVinteMinutos;
    private javax.swing.JMenu opcoesOcultar;
    private javax.swing.JButton removerCargo;
    private javax.swing.JButton removerDepto;
    private javax.swing.JButton removerTag;
    private javax.swing.JButton removerTime;
    private javax.swing.JMenuItem showInstallation;
    private javax.swing.JMenuItem showUsage;
    private javax.swing.JTable tabelaDeObservacoes;
    private javax.swing.JTextPane textAreaUsage;
    private javax.swing.JFormattedTextField txtDataInicio;
    private javax.swing.JFormattedTextField txtDataTermino;
    private javax.swing.JFormattedTextField txtHoraInicio;
    private javax.swing.JFormattedTextField txtHoraTermino;
    private javax.swing.JLabel valorBanco;
    private javax.swing.JLabel valorCodigoAtividade;
    private javax.swing.JLabel valorHost;
    private javax.swing.JLabel valorUsuario;
    // End of variables declaration//GEN-END:variables

   private void habilitaComboTarefa() {
      comboTarefas.setEditable(true);
      comboTarefas.setEnabled(true);
//      comboTags.setEditable(true);
//      comboTags.setEnabled(true);
   }

   private void desabilitaComboTarefa() {
      comboTarefas.setEditable(false);
      comboTarefas.setEnabled(false);
//      comboTags.setEditable(false);
//      comboTags.setEnabled(false);
   }

   public void loadInitialDataTelaPrincipal() {

      try {
         jLabelLoginUser.setText(login);

         atividadeAtual = atividadeEmAberto();
         if (Objects.nonNull(atividadeAtual)) {
            txtDataInicio.requestFocus();

            //Inicializa atividade como Atividade ainda em aberto
            tarefaAtual = appropTimeBo.getTarefaById(atividadeAtual.getTarefa().getId());

            String data = formataDateParaString(atividadeAtual.getInicio(), Constantes.PATTERN_DATA);
            String hora = formataDateParaString(atividadeAtual.getInicio(), Constantes.PATTERN_HORA);

            txtDataInicio.setText(data);
            txtHoraInicio.setText(hora);

            comboTarefas.setEnabled(false);
            comboTarefas.setSelectedItem(atividadeAtual.getTarefa().getDescricao());
            jTextAreaObservacao.setText(formataCampoObservacao(atividadeAtual.getObservacoes()));
            comboTarefas.setPopupVisible(false);

         } else {
            atualizaObjetoAtividadeAtual();
            cleanTelaPrincipal();
         }

         labelStatusApp.setText("");
         labelStatusDataHora.setText("");

         botaoSalvarECriarNova.setEnabled(verificaConcluirTelaPrincipal());
         opcaoSalvarECriarNova.setEnabled(botaoSalvarECriarNova.isEnabled());

         initListenersTelaPrincipal();
         if (Constantes.CARREGA_TODAS_TAREFAS_NO_INICIO) {
            populaComboBox();
         }

         if (interval.equals(-1L)) {
            botaoOcultar.setEnabled(false);
            opcaoOcultar.setEnabled(false);
         }

      } catch (Exception ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class
                    .getName()).log(Level.SEVERE, null, ex);
         }
         System.out.println("Não foi possível carregar os dados iniciais da tela.");
      }
   }

   private void cleanTelaPrincipal() {
      txtDataInicio.setText("");
      txtHoraInicio.setText("");
      txtDataTermino.setText("");
      txtHoraTermino.setText("");
      jTextAreaObservacao.setText("");
      comboTarefas.setSelectedItem("");
      jLabelValorDuracao.setText(calculaDuracaoTelaPrincipal());
      valorCodigoAtividade.setText("");
   }

   /***
    * Atualiza a atividade atual de acordo com os valores presentes nos campos da tela
    */
   private void atualizaObjetoAtividadeAtual() {
      if (atividadeNaoPersistida()) {
         inicializaAtividadeAtual();
      }
      String dataInicio = txtDataInicio.getText();
      String dataTermino = txtDataTermino.getText();
      String horaInicio = txtHoraInicio.getText();
      String horaTermino = txtHoraTermino.getText();
      atividadeAtual.setInicio(formataStringParaDate(dataInicio.concat(" ").concat(horaInicio), Constantes.PATTERN_DATA_HORA));
      atividadeAtual.setTermino(formataStringParaDate(dataTermino.concat(" ").concat(horaTermino), Constantes.PATTERN_DATA_HORA));

      String observacoes = jTextAreaObservacao.getText();
      atividadeAtual.setObservacoes(observacoes);

      atualizaObjetoTarefaAtual();

      atividadeAtual.setTarefa(tarefaAtual);
   }

   private void inicializaAtividadeAtual() {
      if (atividadeNaoPersistida()) {
         tarefaAtual = new Tarefa();
         tarefaAtual.setId(-1L);
         atividadeAtual = new Atividade();
         atividadeAtual.setId(-1L);
         atividadeAtual.setTarefa(tarefaAtual);
      }
   }

   private void atualizaObjetoTarefaAtual() {
      String descricao = comboTarefas.getEditor().getItem().toString();
      if (tarefaNaoPersistida()) {
         tarefaAtual = new Tarefa();
         tarefaAtual.setId(-1L);
         tarefaAtual.setDescricao(descricao);
         tarefaAtual.setUser(user);
      }
      else{
         tarefaAtual = appropTimeBo.getTarefaByDescricao(descricao, user.getId());
      }
   }

   /***
    * Limpa todos os campos da tela (campos de data e hora, observação e tarefa)
    */
   private void telaEmBranco() {
      habilitaComboTarefa();
      labelStatusApp.setText("");
      labelStatusDataHora.setText("");

      initListenersTelaPrincipal();
      if (Constantes.CARREGA_TODAS_TAREFAS_NO_INICIO) {
         populaComboBox();
      }
      habilitaBotoesEOpcoes();
      cleanTelaPrincipal();
      atividadeAtual = null;
      tarefaAtual = null;
      atualizaObjetoAtividadeAtual();
   }

   /**
    * *
    * Habilita ou desabilita as opções e os botões (ocultar, salvar e criar
    * nova, cancelar tarefa atual) de acordo com as regras da aplicação
    */
   private void habilitaBotoesEOpcoes() {
      botaoSalvarECriarNova.setEnabled(verificaConcluirTelaPrincipal());
      opcaoSalvarECriarNova.setEnabled(botaoSalvarECriarNova.isEnabled());

      if (interval.equals(-1L)) {
         botaoOcultar.setEnabled(false);
         opcaoOcultar.setEnabled(false);
      }
      if (tarefaNaoPersistida()) {
         opcaoCancelarTarefaAtual.setEnabled(false);
         opcaoCancelarTarefaAtual.setEnabled(false);
      } else {
         opcaoCancelarTarefaAtual.setEnabled(true);
         opcaoCancelarTarefaAtual.setEnabled(true);
      }
   }

   private boolean tarefaNaoPersistida() {
      return Objects.isNull(tarefaAtual) || Objects.isNull(appropTimeBo.getTarefaById(tarefaAtual.getId()));
   }

   private boolean atividadeNaoPersistida() {
      return Objects.isNull(atividadeAtual) || Objects.isNull(appropTimeBo.getAtividadeById(atividadeAtual.getId()));
   }

   private boolean atividadeExcluida() {
      return Objects.nonNull(atividadeAtual) && Objects.isNull(appropTimeBo.getAtividadeById(atividadeAtual.getId()))
               && (atividadeAtual.getId().equals(-1L) == false);
   }

   private void initListenersTelaPrincipal() {

      comboTarefas.addItemListener(new ItemListener() {
         @Override
         public void itemStateChanged(ItemEvent e) {
            botaoSalvarECriarNova.setEnabled(verificaConcluirTelaPrincipal());
            opcaoSalvarECriarNova.setEnabled(botaoSalvarECriarNova.isEnabled());
         }
      });

      comboTarefas.addKeyListener(new KeyListener() {
         @Override
         public void keyTyped(KeyEvent e) {
         }

         @Override
         public void keyPressed(KeyEvent e) {
         }

         @Override
         public void keyReleased(KeyEvent e) {
            if (comboTarefas.getSelectedItem().toString().length() > Constantes.MAX_LENGTH_TEXT_TAREFA) {
               comboTarefas.getEditor().setItem(UtilMethods.maxlength(comboTarefas.getEditor().getItem().toString(), Constantes.MAX_LENGTH_TEXT_TAREFA));
            }
         }
      });

      jTextAreaObservacao.addKeyListener(new KeyListener() {
         @Override
         public void keyTyped(KeyEvent e) {
         }

         @Override
         public void keyPressed(KeyEvent e) {
         }

         @Override
         public void keyReleased(KeyEvent e) {
            if (jTextAreaObservacao.getText().length() > Constantes.MAX_LENGTH_TEXT_OBSERVACAO) {
               jTextAreaObservacao.setText(formataCampoObservacao(UtilMethods.maxlength(jTextAreaObservacao.getText(), Constantes.MAX_LENGTH_TEXT_OBSERVACAO)));
            }
         }
      });

      botaoInicioAgora.addMouseListener(new MouseListener() {
         @Override
         public void mouseClicked(MouseEvent e) {
            botaoSalvarECriarNova.setEnabled(verificaConcluirTelaPrincipal());
            opcaoSalvarECriarNova.setEnabled(botaoSalvarECriarNova.isEnabled());
         }

         @Override
         public void mousePressed(MouseEvent e) {
         }

         @Override
         public void mouseReleased(MouseEvent e) {
         }

         @Override
         public void mouseEntered(MouseEvent e) {
         }

         @Override
         public void mouseExited(MouseEvent e) {
         }
      });

      botaoTerminoAgora.addMouseListener(new MouseListener() {
         @Override
         public void mouseClicked(MouseEvent e) {
            botaoSalvarECriarNova.setEnabled(verificaConcluirTelaPrincipal());
            opcaoSalvarECriarNova.setEnabled(botaoSalvarECriarNova.isEnabled());
         }

         @Override
         public void mousePressed(MouseEvent e) {
         }

         @Override
         public void mouseReleased(MouseEvent e) {
         }

         @Override
         public void mouseEntered(MouseEvent e) {
         }

         @Override
         public void mouseExited(MouseEvent e) {
         }
      });

      txtDataInicio.addKeyListener(new KeyListener() {
         @Override
         public void keyTyped(KeyEvent e) {
            char keyChar = e.getKeyChar();

         }

         @Override
         public void keyPressed(KeyEvent e) {
         }

         @Override
         public void keyReleased(KeyEvent e) {
         }
      });

   }

   private void populaComboBox() {
      DefaultComboBoxModel dtmTarefas = (DefaultComboBoxModel) comboTarefas.getModel();
      List<String> allTarefas = new LinkedList<>();
      comboTarefas.removeAllItems();

      DefaultComboBoxModel dtmTag = (DefaultComboBoxModel) comboTags.getModel();
      List<String> allTags = new LinkedList<>();
      comboTags.removeAllItems();
      
      DefaultComboBoxModel dtmWorkspace = (DefaultComboBoxModel) comboWorkspace.getModel();
      List<String> allWorkspace = new LinkedList<>();
      comboWorkspace.removeAllItems();
      
      DefaultComboBoxModel dtmProjeto = (DefaultComboBoxModel) comboProjeto.getModel();
      List<String> allProjeto = new LinkedList<>();
      comboProjeto.removeAllItems();
      
      comboTarefas.addItem("");
      comboTags.addItem("");
      comboWorkspace.addItem("");
      comboProjeto.addItem("");
      
      appropTimeBo.getTarefaByRelevancia(user.getId()).stream().map((tarefa) -> tarefa.getDescricao()).map((desc) -> {
          dtmTarefas.addElement(desc);
           return desc;
       }).forEachOrdered((desc) -> {
           allTarefas.add(desc);
       });
      
      appropTimeBo.getAllTagsByUsuario(user).stream().map((tag) -> tag.getNome()).map((desc) -> {
          dtmTag.addElement(desc);
           return desc;
       }).forEachOrdered((desc) -> {
           allTags.add(desc);
       });

      appropTimeBo.getAllWorkspacesByUsuario(user).stream().map((workspace) -> workspace.getNome()).map((desc) -> {
          dtmWorkspace.addElement(desc);
           return desc;
       }).forEachOrdered((desc) -> {
           allWorkspace.add(desc);
       });
      
      appropTimeBo.getAllProjetosByUsuario(user).stream().map((proj) -> proj.getNome()).map((desc) -> {
          dtmProjeto.addElement(desc);
           return desc;
       }).forEachOrdered((desc) -> {
           allProjeto.add(desc);
       });
      

      autoCompleteInstance.setAllItens(allTarefas);
      autoCompleteTag.setAllItens(allTags);
      autoCompleteWorkspace.setAllItens(allWorkspace);
      autoCompleteProjeto.setAllItens(allProjeto);
   }

   private String calculaDuracaoTelaPrincipal() {

      if (Objects.isNull(ah)) {
         ah = new AtualizaHoras();
      }

      if (verificaCampoDataHoraInicioVazioOuNulo() == false) {
         if (verificaCampoDataHoraTerminoVazioOuNulo() == true) {
            ah.setLabelDuracao(jLabelValorDuracao);
            ah.setInicio(UtilMethods.formataStringParaDate(txtDataInicio.getText() + " " + txtHoraInicio.getText(), Constantes.PATTERN_DATA_HORA));

            if (ah.isStop()) {
               ah.setStop(false);
               ah.start();
            }
         } else {
            if (verificaCampoDataHoraTerminoVazioOuNulo() == false) {
               ah.setStop(true);
               ah = new AtualizaHoras();
               try {
                  ah.interrupt();

               } catch (Exception ex) {
                  if (TelaPrincipal.isDebugHabilitado()) {
                     Logger.getLogger(AppropTimeBo.class
                             .getName()).log(Level.SEVERE, null, ex);
                  }
               }
            }
         }
      } else {
         ah.setStop(true);
         ah = new AtualizaHoras();
         try {
            ah.interrupt();

         } catch (Exception ex) {
            if (TelaPrincipal.isDebugHabilitado()) {
               Logger.getLogger(AppropTimeBo.class
                       .getName()).log(Level.SEVERE, null, ex);
            }
         }
      }

      if (verificaCampoDataHoraVazioOuNulo() == true) {
         return "";
      }

      SimpleDateFormat format = new SimpleDateFormat(Constantes.PATTERN_DATA_HORA);

      String dataInicio = formataDateParaString(formataStringParaDate(txtDataInicio.getText() + " " + txtHoraInicio.getText(), Constantes.PATTERN_DATA_HORA),
              Constantes.PATTERN_DATA_HORA);

      String dataTermino;

      try {
         Date d1 = null;
         Date d2 = null;

         if (txtDataInicio.getText().equals(Constantes.MASK_DATA) || txtHoraInicio.getText().equals(Constantes.MASK_HORA)) {
            return "";
         }

         if (txtDataTermino.getText().equals(Constantes.MASK_DATA) || txtHoraTermino.getText().equals(Constantes.MASK_HORA)) {
            String hojeDataHora = formataDateParaString(new Date(System.currentTimeMillis()), Constantes.PATTERN_DATA_HORA);
            dataTermino = hojeDataHora;
         } else {
            dataTermino = formataDateParaString(formataStringParaDate(txtDataTermino.getText() + " " + txtHoraTermino.getText(), Constantes.PATTERN_DATA_HORA),
                    Constantes.PATTERN_DATA_HORA);
         }

         d1 = format.parse(dataInicio);
         d2 = format.parse(dataTermino);

         String valorDuracao = UtilMethods.duracaoDatas(d1, d2);
         if (valorDuracao.equals("")) {
            return null;
         }

         return valorDuracao;

      } catch (Exception e) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class
                    .getName()).log(Level.SEVERE, null, e);
         }
         return "";
      }
   }

   /**
    * @return true se algum dos campos for vazio ou nulo e false caso contrário
    */
   private boolean verificaCampoDataHoraTerminoVazioOuNulo() {
      if (Objects.isNull(txtDataTermino) || Objects.isNull(txtHoraTermino)) {
         return true;
      }

      if (txtDataTermino.getText().equals(Constantes.MASK_DATA) || txtHoraTermino.getText().equals(Constantes.MASK_HORA)) {
         return true;
      }

      return false;
   }

   /**
    * @return true se algum dos campos for vazio ou nulo e false caso contrário
    */
   private boolean verificaCampoDataHoraInicioVazioOuNulo() {
      if (Objects.isNull(txtDataInicio) || Objects.isNull(txtHoraInicio)) {
         return true;
      }

      if (txtDataInicio.getText().equals(Constantes.MASK_DATA) || txtHoraInicio.getText().equals(Constantes.MASK_HORA)) {
         return true;
      }

      return false;
   }

   /**
    * @return true se algum dos campos for vazio ou nulo e false caso contrário
    */
   private boolean verificaCampoDataHoraVazioOuNulo() {
      return verificaCampoDataHoraInicioVazioOuNulo() || verificaCampoDataHoraTerminoVazioOuNulo();
   }

   private StatusAppropTime checaDataHora() {

      Date dataInicioDigitada = null;
      Date dataTerminoDigitada = null;

      Date horaInicioDigitada = null;
      Date horaTerminoDigitada = null;

      DateValidator dv = new DateValidator();

      if (Objects.isNull(dv.validate(txtDataInicio.getText(), Constantes.PATTERN_DATA))) {
         txtDataInicio.setText(null);
      } else {
         dataInicioDigitada = formataStringParaDate(txtDataInicio.getText(), Constantes.PATTERN_DATA);
      }
      if (Objects.isNull(dv.validate(txtDataTermino.getText(), Constantes.PATTERN_DATA))) {
         txtDataTermino.setText(null);
      } else {
         dataTerminoDigitada = formataStringParaDate(txtDataTermino.getText(), Constantes.PATTERN_DATA);
      }
      if (Objects.isNull(dv.validate(txtHoraInicio.getText(), Constantes.PATTERN_HORA))) {
         txtHoraInicio.setText(null);
      } else {
         horaInicioDigitada = formataStringParaDate(txtHoraInicio.getText(), Constantes.PATTERN_HORA);
      }
      if (Objects.isNull(dv.validate(txtHoraTermino.getText(), Constantes.PATTERN_HORA))) {
         txtHoraTermino.setText(null);
      } else {
         horaTerminoDigitada = formataStringParaDate(txtHoraTermino.getText(), Constantes.PATTERN_HORA);
      }

      ValidacaoDatas validacaoDatas = new ValidacaoDatas(horaInicioDigitada, horaTerminoDigitada, dataInicioDigitada, dataTerminoDigitada);

      StatusAppropTime status = appropTimeBo.validaDataHora(validacaoDatas);

      labelStatusDataHora.setText(status.getMensagem());

      if (status.isSucesso() == false) {
         if (Objects.nonNull(horaTerminoDigitada) && Objects.nonNull(dataTerminoDigitada)) {
            botaoSalvar.setEnabled(false);
         }
         labelStatusDataHora.setForeground(Color.red);
      } else {
         botaoSalvar.setEnabled(true);
      }
      jLabelValorDuracao.setText(calculaDuracaoTelaPrincipal());
      return status;
   }

   /**
    * *
    * Verifica se houve conflito entre a atividade que deseja salvar e alguma
    * atividade salva no banco de dados
    *
    * @return true se houver conflito e false caso contrário
    */
   private boolean verificaConflitoEntreAtividades() {

      try {

         StatusAppropTime status = appropTimeBo.verificaConflitoDataAtividade(atividadeAtual);

         if (status.isSucesso() == false) {
            JOptionPane.showMessageDialog(this, status.getMensagem(), "Colisão de atividades", JOptionPane.ERROR_MESSAGE);

            txtHoraInicio.requestFocus();
            botaoSalvarECriarNova.setEnabled(false);
            opcaoSalvarECriarNova.setEnabled(botaoSalvarECriarNova.isEnabled());

            return true;

         }
         return false;

      } catch (Exception ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class
                    .getName()).log(Level.SEVERE, null, ex);
         }
         return true;
      }
   }

   private boolean verificaConcluirTelaPrincipal() {
      StatusAppropTime validacaoDataHora = checaDataHora();
      if (validacaoDataHora.isSucesso() == false) {
         botaoSalvarECriarNova.setEnabled(false);
         opcaoSalvarECriarNova.setEnabled(botaoSalvarECriarNova.isEnabled());
         return false;
      }

      if (!txtDataInicio.getText().equals(Constantes.MASK_DATA) && !txtDataTermino.getText().equals(Constantes.MASK_DATA)
              && !txtHoraInicio.getText().equals(Constantes.MASK_HORA) && !txtHoraTermino.getText().equals(Constantes.MASK_HORA)) {

         if (Objects.nonNull(comboTarefas.getSelectedItem())) {
            if (!comboTarefas.getEditor().getItem().equals("") || !comboTarefas.getSelectedItem().equals("")) {
               return true;
            } else {
               return false;
            }
         }
      }
      return false;
   }

   private static boolean cadastroUsuario() {
      Scanner read = new Scanner(System.in);

      System.out.println(mensagemConsole(Constantes.ANSI_GREEN + "Usuario/senha estão incorretos" + Constantes.ANSI_RESET));
      System.out.println(mensagemConsole(Constantes.ANSI_GREEN + "Deseja cadastrar novo usuário? (yes/no): " + Constantes.ANSI_RESET));

      String answer = read.nextLine();
      if (answer.equals("yes")) {
         System.out.println(mensagemConsole(Constantes.ANSI_GREEN + "Nome usuario: " + Constantes.ANSI_RESET));
         String nome = read.nextLine();

         System.out.println(mensagemConsole(Constantes.ANSI_GREEN + "Login (email): " + Constantes.ANSI_RESET));
         login = read.nextLine();

         System.out.println(mensagemConsole(Constantes.ANSI_GREEN + "Senha: " + Constantes.ANSI_RESET));
         senha = UtilMethods.lerSenhaECriptografar();

         if (UtilMethods.isEmailValid(login) == false) {
            throw new IllegalArgumentException("O email informado não é válido");
         }

         Usuario novoUser = new Usuario();
         novoUser.setLogin(login);
         novoUser.setPassword(senha);
         novoUser.setNome(nome);
         long result = adicionaUser(novoUser);

         if (result < 1) {
            try {
               throw new InstantiationException("Não foi possível criar um novo usuário com os dados informados!");

            } catch (InstantiationException ex) {
               if (TelaPrincipal.isDebugHabilitado()) {
                  Logger.getLogger(AppropTimeBo.class
                          .getName()).log(Level.SEVERE, null, ex);
               }
               return false;
            }
         }
         try {
            user = appropTimeBo.getUsuarioById(result);

         } catch (Exception ex) {
            if (TelaPrincipal.isDebugHabilitado()) {
               Logger.getLogger(AppropTimeBo.class
                       .getName()).log(Level.SEVERE, null, ex);
            }
         }

      }

      if (answer.equals("no")) {
         return false;
      }
      return true;
   }

   private static Long adicionaUser(Usuario novoUsuario) {
      return appropTimeBo.adicionaUsuario(novoUsuario);
   }

   private void verificaIntervaloDaAplicacao(Long intervalo) {
      sleeping = true;
      if (intervalo > 0) {
         try {
            //Create a File filter
            IOFileFilter directoryLock;
            directoryLock = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(),
                    HiddenFileFilter.VISIBLE);

            IOFileFilter lockFile = FileFilterUtils.and(
                    FileFilterUtils.fileFileFilter(),
                    FileFilterUtils.nameFileFilter("lock_" + user.getLogin()));

            IOFileFilter filter = FileFilterUtils.or(directoryLock, lockFile);
            // Create the File system observer and register File Listeners
            String userPath = System.getProperty("user.dir");

            FileAlterationObserver observer = new FileAlterationObserver(new File(userPath), filter);
            observer.addListener(new FileAlterationListener() {
               @Override
               public void onStart(FileAlterationObserver fao) {
               }

               @Override
               public void onDirectoryCreate(File file) {
               }

               @Override
               public void onDirectoryChange(File file) {
               }

               @Override
               public void onDirectoryDelete(File file) {
               }

               @Override
               public void onFileCreate(File file) {
               }

               @Override
               public void onFileChange(File file) {
                  //Verifica valor da variável 'visible'
                  try {
                     FileReader fr = new FileReader(file.getPath());
                     BufferedReader br = new BufferedReader(fr);
                     String readLine = br.readLine();

                     if (Objects.nonNull(readLine)) {
                        if (readLine.equals(Constantes.VISIBLE_TRUE)) {
                           sleeping = false;
                           appropTimeBo.escreveVisibleFalseNoLock(file);
                           setVisible(true);
                        } else {
                           appropTimeBo.escreveVisibleFalseNoLock(file);

                        }
                     }

                  } catch (Exception ex) {
                     if (TelaPrincipal.isDebugHabilitado()) {
                        Logger.getLogger(TelaPrincipal.class
                                .getName()).log(Level.SEVERE, null, ex);
                     }
                  }
               }

               @Override
               public void onFileDelete(File file
               ) {
                  try {
                     file.createNewFile();
                     file.deleteOnExit();
                     appropTimeBo.escreveVisibleFalseNoLock(file);

                  } catch (Exception ex) {
                     if (TelaPrincipal.isDebugHabilitado()) {
                        Logger.getLogger(TelaPrincipal.class
                                .getName()).log(Level.SEVERE, null, ex);
                     }
                  }
               }

               @Override
               public void onStop(FileAlterationObserver fao
               ) {
               }
            });

            FileAlterationMonitor monitor = new FileAlterationMonitor(Constantes.INTERVALO_MONITOR_LOCK_FILE, observer);
            monitor.start();

            //O valor da variável 'sleeping' é alterado apenas quando o valor do arquivo lock
            // é alterado para 'visible=TRUE' pela aplicação (ao tentar reabrir a aplicação)
            // ou pelo usuário direto no arquivo.
            //O loop deixa a aplicação oculta enquanto o tempo de intervalo não for atingido
            // e enquanto o usuário não tentar reabrir a aplicação
            Long timeInSleep = 0L;
            while (sleeping && (intervalo * Constantes.MILISSEGUNDOS_PARA_MINUTOS) > timeInSleep) {
               oculta.sleep(Constantes.TIME_SLEEP_UM_SEGUNDO);
               timeInSleep += Constantes.TIME_SLEEP_UM_SEGUNDO;

            }
         } catch (Exception ex) {
            if (TelaPrincipal.isDebugHabilitado()) {
               Logger.getLogger(AppropTimeBo.class
                       .getName()).log(Level.SEVERE, null, ex);
            }
         }
      } else {
         System.exit(0);
      }
   }

   private void ocultaTela(Long intervalo) {
      setVisible(false);
      verificaIntervaloDaAplicacao(intervalo);
      setVisible(true);
      labelStatusApp.setText("");
   }

   private void cleanTable(DefaultTableModel dtm) {
      while (dtm.getRowCount() > 0) {
         dtm.removeRow(0);
      }
   }

   private void populaTable(List<String> lista, DefaultTableModel dtm) {
      int lines = 1;
      int maxHeight = 1;

      if (Objects.isNull(lista)) {
         return;
      }
      if (lista.isEmpty()) {
         return;
      }

      for (String conteudo : lista) {
         if (conteudo.trim().isEmpty() == false) {
            if (conteudo.contains("\n")) {
               String[] split = conteudo.split("\n");
               lines = split.length;
            }
            if (conteudo.length() > Constantes.NUMERO_DE_CARACTERES_PARA_QUEBRA_DE_LINHA) {
               lines += conteudo.length() / Constantes.NUMERO_DE_CARACTERES_PARA_QUEBRA_DE_LINHA;
            }
            if (lines > maxHeight) {
               maxHeight = lines;
            }
            dtm.addRow(new Object[]{conteudo});
            tabelaDeObservacoes.setRowHeight(Constantes.NUMERO_BASE_TAMANHO_CELL_OBSERVACOES * maxHeight);
         }
      }
   }

   private static void alteraLockEReabreApp() {
      File file = new File("lock_" + user.getLogin());
      appropTimeBo.escreveVisibleTrueNoLock(file);
   }

   private void controleDeTempoDaMensagemDeSucesso() {
      TimerTask task = new TimerTask() {
         @Override
         public void run() {
            if (labelStatusApp.getText().equals("") == false) {
               labelStatusApp.setText("");
            }
         }
      };

      Timer timer = new Timer();
      timer.schedule(task, 10000L);
   }

   private boolean validaSalvarAtividade(String titleDialog) {
      String desc;

      if (Objects.nonNull(comboTarefas.getSelectedItem())) {
         desc = comboTarefas.getEditor().getItem().toString();
         if (desc.equals("") && comboTarefas.isEnabled()) {
            JOptionPane.showMessageDialog(this, "O campo tarefa não pode ser vazio", titleDialog, JOptionPane.ERROR_MESSAGE);
            comboTarefas.requestFocus();
            return false;
         }
      } else {
         JOptionPane.showMessageDialog(this, "O campo tarefa não pode ser vazio", titleDialog, JOptionPane.ERROR_MESSAGE);
         comboTarefas.requestFocus();
         return false;
      }

      if (verificaCampoDataHoraInicioVazioOuNulo()) {
         JOptionPane.showMessageDialog(this, "Os campos data e hora de início não podem ser vazio", titleDialog, JOptionPane.ERROR_MESSAGE);
         return false;
      }

      if (txtDataTermino.getText().equals(Constantes.MASK_DATA) == false && txtHoraTermino.getText().equals(Constantes.MASK_HORA)) {
         JOptionPane.showMessageDialog(this, "O campo hora de término não pode ser vazio", titleDialog, JOptionPane.ERROR_MESSAGE);
         return false;
      }

      if (txtDataTermino.getText().equals(Constantes.MASK_DATA) && txtHoraTermino.getText().equals(Constantes.MASK_HORA) == false) {
         JOptionPane.showMessageDialog(this, "O campo data de término não pode ser vazio", titleDialog, JOptionPane.ERROR_MESSAGE);
         return false;
      }

      if (txtDataTermino.equals(Constantes.MASK_DATA) == false && txtHoraTermino.getText().equals(Constantes.MASK_HORA) == false) {
         if (txtDataTermino.getText().equals(txtDataInicio.getText()) == false) {
            JOptionPane.showMessageDialog(this, "As datas de início e de término são diferentes", titleDialog, JOptionPane.ERROR_MESSAGE);
            return false;
         }
      }

      if (editando == true && verificaConcluirTelaPrincipal() == false && Objects.nonNull(atividadeEmAberto())) {
         JOptionPane.showMessageDialog(this, "Não pode haver mais de uma atividade em aberto", "Salvar e Sair", JOptionPane.ERROR_MESSAGE);
         return false;
      }

      return true;
   }

   /**
    * *
    * Realiza o pré-processamento da string passada nesta ordem: 1- Elimina
    * espaços e tabs em linhas vazias; 2- Substitui 3 ou mais quebras de linha
    * por 2 quebras de linha; 3- Elimina linhas vaizas no início da string; 4-
    * Elimina linhas vaizas no final da string.
    *
    * @param observacaoNaoFormatada
    * @return string formatada de acordo com a descrição
    */
   private String formataCampoObservacao(String observacaoNaoFormatada) {
      String retorno = UtilMethods.retiraEspacosETabsEmLinhaVazia(observacaoNaoFormatada);
      retorno = UtilMethods.retiraTresOuMaisQuebrasDeLinha(retorno);
      retorno = UtilMethods.retiraLinhaVaziaNoInicio(retorno);
      retorno = UtilMethods.retiraLinhaVaziaNoFinal(retorno);

      return retorno;
   }

   private static String manualDeUso() {
      StringBuilder manual = new StringBuilder();

      manual.append(Constantes.ANSI_YELLOW + Constantes.ANSI_BOLD + "Manual de uso: \n\n" + Constantes.ANSI_RESET);
      manual.append(Constantes.ANSI_RED + Constantes.ANSI_BOLD + "Utilidade:\n" + Constantes.ANSI_RESET);
      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\tAplicação destinada a facilitar o registro de atividades"
              + " diárias realizadas, facilitando a apropriação de horas trabalhadas. Permite geração de relatórios e exportação de dados para "
              + "planilhas eletrônicas via arquivo CSV.\n\n" + Constantes.ANSI_RESET);

      manual.append(Constantes.ANSI_RED + Constantes.ANSI_BOLD + "Sintaxe de uso:\n" + Constantes.ANSI_RESET);
      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\tjava -jar AppropTime.jar -u <username> -p <password> -b <urlsgbd> "
              + "( [-i <intervalo>] [-a] | [-X | -X <filepath>] ) [--debug]\n\n"
              + "\tjava -jar AppropTime.jar ( [--getddl | --installation ] ) [--debug]\n\n" + Constantes.ANSI_RESET);
      manual.append(Constantes.ANSI_GREEN + Constantes.ANSI_BOLD + "\t[...] " + Constantes.ANSI_BLUE + "         Opcional\n\n" + Constantes.ANSI_RESET);
      manual.append(Constantes.ANSI_GREEN + Constantes.ANSI_BOLD + "\t[... | ...]"
              + Constantes.ANSI_BLUE + "    Opcional um ou outro (não podem ser usados juntos)\n\n" + Constantes.ANSI_RESET);
      manual.append(Constantes.ANSI_GREEN + Constantes.ANSI_BOLD + "\t(... | ...)"
              + Constantes.ANSI_BLUE + "    Obrigatório um ou outro (não podem ser usados juntos)\n\n" + Constantes.ANSI_RESET);

      manual.append(Constantes.ANSI_RED + Constantes.ANSI_BOLD + "Parâmetros:\n" + Constantes.ANSI_RESET);

      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t-h, --help ");
      manual.append(Constantes.ANSI_RESET_BOLD + "\n\t\tExibe o manual de uso da aplicação (esta tela).\n\n" + Constantes.ANSI_RESET);

      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t-u " + Constantes.ANSI_UNDERLINE + "USERNAME" + Constantes.ANSI_RESET_UNDERLINE
              + ", --username " + Constantes.ANSI_UNDERLINE + "USERNAME" + Constantes.ANSI_RESET_UNDERLINE);
      manual.append(Constantes.ANSI_RESET_BOLD + "\n\t\tLogin de usuário da aplicação (formato de email).\n\n" + Constantes.ANSI_RESET);

      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t-p " + Constantes.ANSI_UNDERLINE + "PASSWORD" + Constantes.ANSI_RESET_UNDERLINE
              + ", --password " + Constantes.ANSI_UNDERLINE + "PASSWORD" + Constantes.ANSI_RESET_UNDERLINE);
      manual.append(Constantes.ANSI_RESET_BOLD + "\n\t\tSenha do usuário da aplicação informado no parâmetro " + Constantes.ANSI_BOLD
              + "-u|--username.\n\n" + Constantes.ANSI_RESET);

      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t-i " + Constantes.ANSI_UNDERLINE + "NUM" + Constantes.ANSI_RESET_UNDERLINE
              + ", --interval " + Constantes.ANSI_UNDERLINE + "NUM" + Constantes.ANSI_RESET_UNDERLINE);
      manual.append(Constantes.ANSI_RESET_BOLD + "\n\t\tIntervalo de abertura da aplicação (em minutos). Deve ser maior que 0 (zero).\n"
              + Constantes.ANSI_RESET);
      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t\tObservação: " + Constantes.ANSI_RESET_BOLD);
      manual.append(Constantes.ANSI_BLUE + "Esta opção não pode ser usada com os parâmetros " + Constantes.ANSI_BOLD + "-X, --installation e/ou --getddl.\n\n"
              + Constantes.ANSI_RESET);

      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t-b " + Constantes.ANSI_UNDERLINE + "URLSGBD" + Constantes.ANSI_RESET_UNDERLINE
              + ", --urlsgbd " + Constantes.ANSI_UNDERLINE + "URLSGBD" + Constantes.ANSI_RESET_UNDERLINE);
      manual.append(Constantes.ANSI_RESET_BOLD + "\n\t\tUrl de conexão com o banco. Deve estar no formato: " + Constantes.ANSI_BOLD
              + "username:password:host:port\n\n" + Constantes.ANSI_RESET);

      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t-a, --autoopen ");
      manual.append(Constantes.ANSI_RESET_BOLD + "\n\t\tNo caso de já existir uma instância em execução da aplicação, carregará a instância "
              + "antiga sem perguntar.\n" + Constantes.ANSI_RESET);
      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t\tObservação: " + Constantes.ANSI_RESET_BOLD);
      manual.append(Constantes.ANSI_BLUE + "Esta opção não pode ser usada com os parâmetros " + Constantes.ANSI_BOLD + "-X, --installation "
              + "e/ou --getddl.\n\n" + Constantes.ANSI_RESET);

      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t-f, --autofocus ");
      manual.append(Constantes.ANSI_RESET_BOLD + "\n\t\tCaptura o foco do teclado para a aplicação sempre que ela for "
              + "carregada/recarregada.\n" + Constantes.ANSI_RESET);

      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t-X, -X " + Constantes.ANSI_UNDERLINE + "PATH" + Constantes.ANSI_RESET_UNDERLINE);
      manual.append(Constantes.ANSI_RESET_BOLD + "\n\t\tFaz o export para o formato CSV das atividades do usuário via linha de comando.\n"
              + Constantes.ANSI_RESET);
      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t\tObservação: " + Constantes.ANSI_RESET_BOLD
              + "Esta opção exige os parâmetros" + Constantes.ANSI_BOLD + " -b, -u e -p. " + Constantes.ANSI_RESET);
      manual.append(Constantes.ANSI_BLUE + "E não pode ser usada com os parâmetros " + Constantes.ANSI_BOLD + "-a, --autofocus -i, --installation "
              + "e/ou --getddl.\n\n" + Constantes.ANSI_RESET);

      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t--debug ");
      manual.append(Constantes.ANSI_RESET_BOLD + "\n\t\tHabilita a saída de logs erros para o desenvolvedor.\n\n" + Constantes.ANSI_RESET);

      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t--getddl");
      manual.append(Constantes.ANSI_RESET_BOLD + "\n\t\tGera um arquivo de nome approptime_ddl.sql na pasta local com os comandos "
              + "DDL da base de dados do Approptime.\n" + Constantes.ANSI_RESET);
      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t\tObservação: " + Constantes.ANSI_RESET_BOLD);
      manual.append(Constantes.ANSI_BLUE + "Esta opção não pode ser usada com parâmetros diferentes de " + Constantes.ANSI_BOLD
              + "-h e/ou --debug.\n\n" + Constantes.ANSI_RESET);

      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t--installation");
      manual.append(Constantes.ANSI_RESET_BOLD + "\n\t\tExibe o manual de intalação da aplicação." + Constantes.ANSI_RESET);
      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\n\t\tObservação: " + Constantes.ANSI_RESET_BOLD);
      manual.append(Constantes.ANSI_BLUE + "Esta opção não pode ser usada com parâmetros diferentes de " + Constantes.ANSI_BOLD
              + "-h e/ou --debug.\n\n" + Constantes.ANSI_RESET);

      manual.append(Constantes.ANSI_RED + Constantes.ANSI_BOLD + "Exemplos:\n" + Constantes.ANSI_RESET);
      manual.append(Constantes.ANSI_BLUE + Constantes.ANSI_BOLD + "\t java -jar AppropTime.jar -u exemplo.teste@email.com -p senhateste -i 20 -b root:root:localhost:3306\n"
              + Constantes.ANSI_RESET);

      if(isWindows()){
         return retiraAnsiCodes(manual.toString());
      }
      else{
         return manual.toString();
      }

   }

   private static String manualDeInstalacao() {
      StringBuilder manual = new StringBuilder();
      manual.append("\nManual de instalação:\n");
      manual.append("\n");
      manual.append("Pré-requisitos:\n");
      manual.append(" -Java instalado: java versão 8 (para verificar se já possui o Java em sua máquina execute o comando: java -version).\n");
      manual.append(" -Versão mínima do Java: 1.8.X.\n");
      manual.append(" -Caso ainda não possua o java instalado, instale java (link: http://www.oracle.com/technetwork/java/javase/downloads/index.html).\n");
      manual.append(" -Acesso a banco de dados MySQL.\n");
      manual.append("\n");
      manual.append("Instalação:\n");
      manual.append("  1 - Banco de Dados:\n");
      manual.append("     1.1 - Deixar a aplicação criar o banco (recomendado):\n");
      manual.append("       1.1.1 - Requisitos:\n");
      manual.append("                -Usuário de banco de dados com permissão de comando de DDL para criar as tabelas que serão usadas.\n");
      manual.append("                -Como criar o banco de dados approtime: Ao executar a aplicação, será pedido tais dados para o usuário.\n");
      manual.append("                   -Uma outra maneira seria passar os dados do usuário de banco de dados com permissão de comandos DDL "
              + "através do parâmetro -b, no seguinte formato: username:password:host:port\n");
      manual.append("\n");
      manual.append("     1.2 - Pedir DDL do banco para a aplicação para o próprio usuário criar o banco:\n");
      manual.append("       1.2.1 - Requisitos:\n");
      manual.append("                 -Usuário de banco com permissão de comando de DML (insert, update, delete) E DQL (select).\n");
      manual.append("                 -Obtenha os DDL's da base de dados através do parametro --getddl da aplicação.\n");
      manual.append("                    -Exemplo: java -jar approptime.jar --getddl\n");
      manual.append("                 -Execute o DDL fornecido acima pela aplicação em um banco de dados MySQL.\n");
      manual.append("                 -Crie um usuário de banco de dados com acesso exclusivo à base approptime criada por meio do DDL informado pela aplicação\n");
      manual.append("                 -Observação: o usuário de banco de dados criado deve ter permissão de execução de comando "
              + "DML (insert, update, delete) E DQL (select).\n");
      manual.append("\n");
      manual.append("  2 - Aplicação:\n");
      manual.append("      -Não é necessário fazer instalação, pois trata-se de uma aplicação Java.\n");
      manual.append("      -Para executar a aplicação basta digitar no terminal o comando: java -jar approptime.jar\n");
      manual.append("          -Nesse modo de execução a aplicação vai te pedir os dados de conexão com o banco de dados e de usuário de aplicação\n");
      manual.append("              -Alternativamente você pode passar os parâmetros direto na linha de comando\n");
      manual.append("\n");
      manual.append("                -u USERNAME, --username USERNAME\n");
      manual.append("                    Login do usuário de aplicação (Deve ser formato de email).\n");
      manual.append("\n");
      manual.append("                -p PASSWORD, --password PASSWORD\n");
      manual.append("                    Senha do usuário da aplicação informado no parâmetro acima.\n");
      manual.append("\n");
      manual.append("                -i NUM, --interval NUM\n");
      manual.append("                    Intervalo de abertura da aplicação (em minutos). Deve ser maior que 0 (zero).\n");
      manual.append("\n");
      manual.append("                -b URLSGBD, --urlsgbd URLSGBD\n");
      manual.append("                    Url de conexão com o banco. Deve estar no formato: username:password:host:port.\n");
      manual.append("\n");
      manual.append("                --getddl\n");
      manual.append("                    Gera um arquivo de nome approptime_ddl.sql na pasta local com os comandos DDL da base de dados do Approptime.\n");
      manual.append("\n");
      manual.append("          -Exemplo: java -jar AppropTime.jar -u exemplo.teste@email.com -p senhateste -i 20 -b root:root:localhost:3306\n");
      manual.append("      -Para maiores informações a respeito dos comandos aceitos pela aplicação execute o comando: java -jar approptime.jar --help\n");

      return manual.toString();

   }

   private static String getSistemaOperacional(){
      return System.getProperty("os.name");
   }
   private static boolean isWindows() {
      String sistemaOperacional = getSistemaOperacional();
      return StringUtils.containsIgnoreCase(sistemaOperacional, Constantes.SISTEMA_OPERACIONAL_WINDOWS);
   }

   public static String mensagemConsole(String texto){
      if(isWindows()){
         return retiraAnsiCodes(texto);
      }
      return texto;
   }

   private static String retiraAnsiCodes(String texto) {
      if(Objects.nonNull(texto) && texto.isEmpty() == false){
         texto = texto.replace(Constantes.ANSI_BLACK, "");
         texto = texto.replace(Constantes.ANSI_BLUE, "");
         texto = texto.replace(Constantes.ANSI_BOLD, "");
         texto = texto.replace(Constantes.ANSI_CYAN, "");
         texto = texto.replace(Constantes.ANSI_GREEN, "");
         texto = texto.replace(Constantes.ANSI_PURPLE, "");
         texto = texto.replace(Constantes.ANSI_RED, "");
         texto = texto.replace(Constantes.ANSI_RESET, "");
         texto = texto.replace(Constantes.ANSI_RESET_BOLD, "");
         texto = texto.replace(Constantes.ANSI_RESET_UNDERLINE, "");
         texto = texto.replace(Constantes.ANSI_UNDERLINE, "");
         texto = texto.replace(Constantes.ANSI_WHITE, "");
         texto = texto.replace(Constantes.ANSI_YELLOW, "");
         texto = texto.replace(Constantes.ANSI_BOLD, "");
      }
      return texto;
   }

   private void setupShortCutTextArea() {

      List<JTextComponent> listaDeComponentes = new ArrayList<>();

      listaDeComponentes.add(jTextAreaObservacao);
      listaDeComponentes.add(txtDataInicio);
      listaDeComponentes.add(txtDataTermino);
      listaDeComponentes.add(txtHoraInicio);
      listaDeComponentes.add(txtHoraTermino);

      adicionaShortCut(listaDeComponentes);
   }

   private void adicionaShortCut(List<JTextComponent> components) {

      String historico = "Histórico";
      String sair = "Sair";
      String salvarECriarNova = "Salvar e criar nova";
      String salvar = "Salvar";
      String ocultar = "Ocultar";

      Action shortCutHistorico = new AbstractAction(historico) {

         @Override
         public void actionPerformed(ActionEvent evt) {
            irParaHistoricoActionPerformed(evt);
         }
      };

      Action shortCutSair = new AbstractAction(sair) {

         @Override
         public void actionPerformed(ActionEvent evt) {
            opcaoSairActionPerformed(evt);
         }
      };

      Action shortCutSalvarECriarNova = new AbstractAction(salvarECriarNova) {

         @Override
         public void actionPerformed(ActionEvent evt) {
            opcaoSalvarECriarNovaActionPerformed(evt);
         }
      };

      Action shortCutSalvar = new AbstractAction(salvar) {

         @Override
         public void actionPerformed(ActionEvent evt) {
            opcaoSalvarActionPerformed(evt);
         }
      };

      Action shortCutOcultar = new AbstractAction(ocultar) {

         @Override
         public void actionPerformed(ActionEvent evt) {
            if (opcaoOcultar.isEnabled()) {
               opcaoOcultarActionPerformed(evt);
            }
         }
      };

      for (JTextComponent component : components) {
         component.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK), historico);
         component.getActionMap().put(historico, shortCutHistorico);

         component.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK), sair);
         component.getActionMap().put(sair, shortCutSair);

         component.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), salvarECriarNova);
         component.getActionMap().put(salvarECriarNova, shortCutSalvarECriarNova);

         component.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), salvar);
         component.getActionMap().put(salvar, shortCutSalvar);

         component.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK), ocultar);
         component.getActionMap().put(ocultar, shortCutOcultar);
      }
   }
}
