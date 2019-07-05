/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author mrg.estagiario
 */
public class CriptografiaUtil {

    /**
     * Criptografa o valor passado com parametro utilizando o algoritimo MD5. E
     * retorna uma String.
     *
     * @param vl
     * @return valor criptografado.
     * @throws NoSuchAlgorithmException
     */
    //Code from TRT10Util.CriptografiaUtil
    public static String getMD5H(String vl) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(vl.getBytes());
        return stringHexa(md.digest());
    }

    /**
     * Transforma o array de bytes em uma String
     *
     * @param Array bytes
     * @return String
     */
    //Code from TRT10Util.CriptografiaUtil
    private static String stringHexa(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
            int parteBaixa = bytes[i] & 0xf;
            if (parteAlta == 0) {
                s.append('0');
            }
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }

    /**
     * Gerador de hash para senha de usuarios AppropTime
     *
     * @param txt
     * @return
     * @throws NoSuchAlgorithmException
     */
    //Code from TRT10Util.CriptografiaUtil
    public static String senhaHash(String txt) {
        try {
            return CriptografiaUtil.getMD5H(Constantes.WORD_CODE.concat(txt));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Erro ao carregar algoritmo de criptografia.");
        }
    }
}
