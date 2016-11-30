package fr.univlyon1.sem.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilitaire pour la gestion des mots de passes
 */
public class PasswordUtil {

    public static final String SALT = "SeCuReSaLt2016Sem";

    /**
     * Calcul le hash sha1 d'une chaine (avec un salt)
     * @param str
     * @return La chaine hashé
     */
    public static String sha1(String str) {
        try {
            MessageDigest d = MessageDigest.getInstance("SHA-256");
            d.reset();
            d.update((SALT + str).getBytes());

            return bytesToHex(d.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Converti un tableau de byte en string
     * @param b
     * @return
     */
    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuffer buf = new StringBuffer();
        for (int j=0; j<b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString();
    }
}
