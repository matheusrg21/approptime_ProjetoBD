/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.util;

import approptime.frame.TelaPrincipal;
import java.io.Console;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author mrg.estagiario
 */
public class UtilMethods {

   //TRT10Util
   public static Date formataStringParaDate(String data, String pattern) {
      SimpleDateFormat df = new SimpleDateFormat(pattern);
      if (data != null && !data.trim().equals("") && !data.equals(Constantes.MASK_DATA_HORA)
              && !data.equals(Constantes.MASK_DATA) && !data.equals(Constantes.MASK_HORA)) {
         try {
            Date toReturn = df.parse(data);
            df.format(toReturn);
            return toReturn;
         } catch (ParseException ex) {
            if (TelaPrincipal.isDebugHabilitado()) {
               Logger.getLogger(UtilMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
         }
      }
      return null;
   }

   //TRT10Util
   public static String formataDateParaString(Date dt, String pattern) {
      DateFormat df = new SimpleDateFormat(pattern);
      if (dt != null) {
         return df.format(dt);
      } else {
         return null;
      }
   }

   /***
    *
    * @param dt Data que será formatada para dd/MM/yyyy
    * @param pattern formato em que se encontra a data passada no parâmetro dt
    * @return
    */
   public static String formatoDataStringBrasil(String dt, String pattern) {
      return formataDateParaString(formataStringParaDate(dt, pattern), "dd/MM/yyyy");
   }

   /**
    * *
    * Exemplo de retorno "2h10m10s"
    *
    * @param dataInicio
    * @param dataFinal
    * @return
    */
   public static String duracaoDatas(Date dataInicio, Date dataFinal) {
      long diff = dataFinal.getTime() - dataInicio.getTime();

      if (diff < 0) {
         return "";
      }

      long diffSeconds = diff / 1000 % 60;
      long diffMinutes = diff / (60 * 1000) % 60;
      long diffHours = diff / (60 * 60 * 1000) % 24;
      long diffDays = diff / (24 * 60 * 60 * 1000);

      String toReturn = String.valueOf(diffHours) + "h" + String.valueOf(diffMinutes) + "m" + String.valueOf(diffSeconds) + "s";

      return toReturn;
   }

   /**
    * Informa o tempo de processamento no formato: 00 hora(s), 00 minuto(s), 00
    * segundo(s) <br/>
    *
    * @param inicioProcesso -> Esse valor deve ser informado em milesegundos.
    * Ex: System.currentTimeMillis()
    * @return Tempo de processamento
    */
   public static String imprimirTempoProcessamento(long inicioProcesso) {
      long millis = System.currentTimeMillis() - inicioProcesso;
      if (millis < 0) {
         return "";
      }
      String valor = String.format("%dh%dm%ds",
              TimeUnit.MILLISECONDS.toHours(millis),
              TimeUnit.MILLISECONDS.toMinutes(millis) % 60,
              TimeUnit.MILLISECONDS.toSeconds(millis) % 60);
      return valor;
   }

   public static String calculaTempoEmMinutos(Long minutos) {

      if (minutos < 0) {
         return "";
      }
      String valor = String.format("%dh%dm",
              TimeUnit.MINUTES.toHours(minutos),
              TimeUnit.MINUTES.toMinutes(minutos) % 60);

      return valor;
   }

   public static Date getNextDay(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      cal.add(Calendar.DATE, 1);
      return cal.getTime();
   }

   public static String maxlength(String str, int limit) {
      String valor = "";
      if (str.length() > limit) {
         valor = str.substring(0, limit);
         str = valor;
      }
      return str;
   }

   public static boolean isEmailValid(String email) {
      EmailValidator emailValidator = EmailValidator.getInstance();

      return emailValidator.isValid(email);
   }

   public static String lerSenhaECriptografar() {
      Console cons;
      char[] passwd;
      if ((cons = System.console()) != null && (passwd = cons.readPassword()) != null) {
         return UtilMethods.criptografarSenha(passwd);
      }
      return null;
   }
   
   public static String criptografarSenha(char[] password){
     String senhaEmHash = CriptografiaUtil.senhaHash(String.copyValueOf(password));
     java.util.Arrays.fill(password, ' ');
     return senhaEmHash; 
   }

   public static String removeCodigosUnicode(String textoUnicode) {
      String retorno = textoUnicode
              .replaceAll("[^\\n\\r\\t\\p{Print}]", "") //Removendo todos os caracteres não imprimíveis exceto \n, \r, \t, \p
              .replaceAll("\\[\\d+m", ""); //Removendo o que sobrou dos códigos unicode

      return retorno;
   }

   /***
    * Utiliza o pattern "^[ \\t]*$" e substitui por "\n"
    * @param naoFormatada
    * @return string aplicada ao pattern
    */
   public static String retiraEspacosETabsEmLinhaVazia(String naoFormatada){
      if(Objects.isNull(naoFormatada)){
         return "";
      }
      String pattern = "^[ \\t]*$";
      Pattern compile = Pattern.compile(pattern, Pattern.MULTILINE);
      Matcher matcher = compile.matcher(naoFormatada);
      String group = matcher.replaceAll("\n");
      return group;
   }

   /***
    * Utiliza o pattern "\\n{3,}" e substitui por "\n\n"
    * @param naoFormatada
    * @return string aplicada ao pattern
    */
   public static String retiraTresOuMaisQuebrasDeLinha(String naoFormatada){
      String retorno = naoFormatada.replaceAll("\\n{3,}", "\n\n");
      return retorno;
   }

   /***
    * Utiliza o pattern '^\\n+' e substitui por uma string vazia
    * @param naoFormatada
    * @return string aplicada ao pattern
    */
   public static String retiraLinhaVaziaNoInicio(String naoFormatada){
      String retorno = naoFormatada.replaceAll("^\\n+", "");
      return retorno;
   }

   /***
    * Utiliza o pattern '\\n+$' e substitui por uma string vazia
    * @param naoFormatada
    * @return string aplicada ao pattern
    */
   public static String retiraLinhaVaziaNoFinal(String naoFormatada){
      String retorno = naoFormatada.replaceAll("\\n+$", "");
      return retorno;
   }

   public static String removeEspacosString(String str) {
      return str.trim().replaceAll(" ", "");
   }
}
