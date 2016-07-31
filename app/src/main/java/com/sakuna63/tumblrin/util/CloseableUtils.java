package com.sakuna63.tumblrin.util;

import java.io.Closeable;
import java.io.IOException;

public class CloseableUtils {
    public static void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
