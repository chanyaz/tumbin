package com.sakuna63.tumbin.data.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

object TestUtils {

    private fun resolveFilePath(fileName: String): String {
        val basePath = "./src/test/assets/"
        return basePath + fileName
    }

    private fun readFile(filePath: String): InputStream {
        try {
            return FileInputStream(filePath)
        } catch (e: FileNotFoundException) {
            throw RuntimeException(e)
        }

    }

    fun <T> readJsonAssets(objectMapper: ObjectMapper,
                           type: TypeReference<T>,
                           fileName: String): T {
        val `in` = readFile(resolveFilePath(fileName))
        try {
            return objectMapper.readValue<T>(`in`, type)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }
}
