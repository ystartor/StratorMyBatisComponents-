package com.ystartor.util;

import java.io.*;

public class IOUtil {

    public static void printFileContent(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        byte[] fileContent = new byte[1024];
        inputStream.read(fileContent);
        inputStream.close();
        System.out.println(new String(fileContent));
    }

    public static void printFileContent(InputStream inputStream) throws IOException {
        byte[] fileContent = new byte[1024];
        inputStream.read(fileContent);
        inputStream.close();
        System.out.println(new String(fileContent));
    }


}
