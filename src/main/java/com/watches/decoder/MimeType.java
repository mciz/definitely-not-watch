package com.watches.decoder;

import java.util.stream.Stream;

public enum MimeType {

    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_GIF("image/gif");

    private final String value;

    MimeType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static MimeType fromMimeType(String value) {
        return Stream.of(values())
                .filter(it -> it.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No value for type [" + value + "]"));
    }

    public static boolean isValidType(String value) {
        return Stream
                .of(values())
                .anyMatch(it -> it.value.equals(value));
    }
}
