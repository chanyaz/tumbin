package com.sakuna63.tumbin.data.util;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class TestUtils {

    private static String resolveFilePath(@NonNull String fileName) {
        final String basePath = "./src/test/assets/";
        return basePath + fileName;
    }

    private static InputStream readFile(@NonNull String filePath) {
        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readJsonAssets(@NonNull ObjectMapper objectMapper,
                                       @NonNull TypeReference<T> type,
                                       @NonNull final String fileName) {
        InputStream in = readFile(resolveFilePath(fileName));
        try {
            return objectMapper.readValue(in, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
