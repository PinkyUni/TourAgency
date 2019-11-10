package com.PinkyUni.model.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashService {

    private static final String HASH_ALGORITHM = "SHA-1";

    private static MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String getHash(String data) {
        byte[] hash = messageDigest.digest(data.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte hash1 : hash) {
            String s = Integer.toHexString(0xff & hash1);
            s = (s.length() == 1) ? "0" + s : s;
            sb.append(s);
        }
        return sb.toString();
    }

}
