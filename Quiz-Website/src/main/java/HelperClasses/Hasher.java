package HelperClasses;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    private static final String ALGORITHM = "SHA";

    /*
     Given a byte[] array, produces a hex String,
     such as "234a6f". with 2 chars for each byte in the array.
     (provided code)
    */
    public static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val<16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    /** function generates hash for received password using "SHA" algorithm, we also use "salt" technique to become
     * hash more safe
     */
    public static String generateHash(String password, long id){

        try{

            MessageDigest md = MessageDigest.getInstance(ALGORITHM);

            password = id + password;

            byte[] bytes = password.getBytes();
            md.update(bytes);

            byte[] hashedPassword = md.digest();
            return hexToString(hashedPassword);

        }catch (NoSuchAlgorithmException ignored){ }

        return "";
    }
}
