/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.util;

/**
 *
 * @author mrg.estagiario
 */
public class Constantes {

   // Informa que todas as tarefas que existirem no banco de dados serão trazidas do banco no carregamento da aplicação
   // ou quando a combo 'tarefas' estiver vazia
   public static final boolean CARREGA_TODAS_TAREFAS_NO_INICIO = true;

   public static final String ANSI_RESET = "\u001B[0m";
   public static final String ANSI_BLACK = "\u001B[30m";
   public static final String ANSI_RED = "\u001B[31m";
   public static final String ANSI_GREEN = "\u001B[32m";
   public static final String ANSI_YELLOW = "\u001B[33m";
   public static final String ANSI_BLUE = "\u001B[34m";
   public static final String ANSI_PURPLE = "\u001B[35m";
   public static final String ANSI_CYAN = "\u001B[36m";
   public static final String ANSI_WHITE = "\u001B[37m";

   public static final String ANSI_BOLD = "\033[1m";
   public static final String ANSI_UNDERLINE = "\033[4m";
   public static final String ANSI_RESET_BOLD = "\033[22m";
   public static final String ANSI_RESET_UNDERLINE = "\033[24m";

   public static final String COLOR_SUCCESS = "\u001B[34m";
   public static final String COLOR_ERROR = "\u001B[31m";


   public static final int MAX_LENGTH_TEXT_OBSERVACAO = 2000;
   public static final int MAX_LENGTH_TEXT_TAREFA = 250;
   public static final int MAX_LENGTH_TEXT_DATA = 10;
   public static final int MAX_LENGTH_TEXT_HORA = 8;

   public static final String WORD_CODE = "adkfhKJHGJDJHVJH24674JHGVJKLKLG@#$#sdfkljghsdfjklhaadkfhKJHGJDJHVJH24674JHGVJKLKLG@#$#sdfkljghsdfjkl";

   public static final int INDICE_CSV_CAMPO_ID_ATIVIDADE = 1;
   public static final int INDICE_CSV_CAMPO_DT_INICIO_ATIVIDADE = 3;
   public static final int INDICE_CSV_CAMPO_DT_TERMINO_ATIVIDADE = 5;
   public static final int INDICE_CSV_CAMPO_OBSERVACAO_ATIVIDADE = 7;
   public static final int INDICE_CSV_CAMPO_ID_TAREFA = 9;
   public static final int INDICE_CSV_CAMPO_DESCRICAO_TAREFA = 11;
   public static final int INDICE_CSV_CAMPO_ID_USUARIO = 13;
   public static final int INDICE_CSV_CAMPO_NOME_USUARIO = 15;
   public static final int INDICE_CSV_CAMPO_LOGIN_USUARIO = 17;
   public static final int INDICE_CSV_CAMPO_PASSWORD_USUARIO = 19;
   public static final int INDICE_CSV_QTD_CAMPOS = INDICE_CSV_CAMPO_PASSWORD_USUARIO;

   public static final int INDICE_CAMPO_USER_URLSGBD = 0;
   public static final int INDICE_CAMPO_PASSWORD_URLSGBD = 1;
   public static final int INDICE_CAMPO_HOST_URLSGBD = 2;
   public static final int INDICE_CAMPO_PORT_URLSGBD = 3;

   public static final int MILISSEGUNDOS_PARA_MINUTOS = 60000;

   public static final int MINIMO_DE_CARACTERES_PARA_BUSCA_BD = 3;

   public static final int DOUBLE_CLICK = 2;

   public static final int MAXIMO_DE_ATIVIDADES_POR_PAGINA_TABELA = 15;

   public static final String BASE_NAME = "approptime";

   public static final String SIMBOLO_NOVA_LINHA = "@@##!!__NOVA_LINHA_!!##@@";

   public static final String VISIBLE_TRUE = "visible=TRUE";
   public static final String VISIBLE_FALSE = "visible=FALSE";

   public static final Long INTERVALO_MONITOR_LOCK_FILE = 3000l;
   public static final Long TIME_SLEEP_UM_SEGUNDO = 1000l;

   public static final String NOME_ARQUIVO_DDL_GERADO = "approptime_ddl.sql";

   public static final String AGREGADOR_GLOBAL = "Global";
   public static final String AGREGADOR_ANUAL = "Ano";
   public static final String AGREGADOR_MENSAL = "Mês";
   public static final String AGREGADOR_SEMANAL = "Semana";
   public static final String AGREGADOR_DIARIO = "Dia";

   public static final String NOME_COLUNA_TAREFA_RELATORIO = "Tarefa";
   public static final String NOME_COLUNA_TEMPO_GASTO_RELATORIO = "Tempo gasto";

   public static final int TEMPO_MINIMO_PARA_REABRIR_APLICACAO = 2;

   public static final char UNICODE_DELETE = '\u007f';
   public static final char UNICODE_BACKSPACE = '\b';

   public static final int INICIO_PERIODO_SEMANA_DO_ANO = 1;
   public static final int FIM_PERIODO_SEMANA_DO_ANO = 7;

   public static final String MASK_DATA_HORA = "  /  /       :  :  ";
   public static final String MASK_DATA = "  /  /    ";
   public static final String MASK_HORA = "  :  :  ";

   public static final String PATTERN_DATA_HORA = "dd/MM/yyyy HH:mm:ss";
   public static final String PATTERN_DATA = "dd/MM/yyyy";
   public static final String PATTERN_HORA = "HH:mm:ss";

   public static final int NUMERO_BASE_TAMANHO_CELL_OBSERVACOES = 16;
   public static final int NUMERO_DE_CARACTERES_PARA_QUEBRA_DE_LINHA = 80;

   public static final String SISTEMA_OPERACIONAL_WINDOWS = "Windows";
   public static final String SISTEMA_OPERACIONAL_LINUX = "Linux";

   public static final Long ID_ATIVIDADE_NAO_SALVA = -1L;
   public static final boolean DEFAULT_URLSGB = false;
}
