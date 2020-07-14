package com.ystartor.io;

import com.ystartor.util.IOUtil;
import sun.text.resources.iw.FormatData_iw_IL;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestReadFile {

    public static void main(String[] args) throws IOException {
        InputStream in = Resources.getResourceAsStream("testReadFile.xml");
        IOUtil.printFileContent(in);


        File file = Resources.getResourceAsFile("testReadFile.xml");
        IOUtil.printFileContent(file);

        Properties props = Resources.getResourceAsProperties("testReadFile.xml");
        System.out.println(props);
    }

}
