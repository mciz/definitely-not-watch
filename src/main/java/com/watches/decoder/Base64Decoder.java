package com.watches.decoder;

import org.apache.tika.Tika;

import java.util.Base64;

public class Base64Decoder {

    private Base64Decoder() {
    }

    public static String decodeMimeType(String encodedFile) {
        byte[] decodedFile = Base64.getMimeDecoder().decode(encodedFile);
        Tika tika = new Tika();
        return tika.detect(decodedFile);
    }
}
