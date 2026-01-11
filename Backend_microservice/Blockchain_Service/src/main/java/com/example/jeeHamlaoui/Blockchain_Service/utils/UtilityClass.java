package com.example.jeeHamlaoui.Blockchain_Service.utils;
import java.util.Base64;

public class UtilityClass {
    /**
     * Checks if the provided string is a valid Base64-encoded value.
     * @param str the string to validate as Base64
     * @return {@code true} if the input string is valid Base64; {@code false} otherwise
     */
    public static boolean isValidBase64(String str) {
        try {
            Base64.getDecoder().decode(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Coverts Byte Array into Valid Hexadecimal String seq
     * @param bytes byte array
     * @return {@code hexString}
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Converts Hexadecimal String into Valid Byte Array
     * @param hex hexadecimal string
     * @return {@code byte[]}
     */
    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
