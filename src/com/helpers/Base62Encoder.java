package com.helpers;

public class Base62Encoder {
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public static String encode(String longUrl) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            str.append(BASE62.indexOf((int)Math.random() * 62));
        }
        return str.toString();
    }
}
