package com.github.retrooper.packetevents.protocol.util;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class NbtCodecException extends RuntimeException {

    public NbtCodecException(String message) {
        super(message);
    }

    public NbtCodecException(String message, Throwable cause) {
        super(message, cause);
    }

    public NbtCodecException(Throwable cause) {
        super(cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this; // no stack trace for us
    }
}
