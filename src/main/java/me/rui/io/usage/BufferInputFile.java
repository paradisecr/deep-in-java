package me.rui.io.usage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by caorui on 2017/6/4.
 */
public class BufferInputFile {
    public static String read(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String str;
            StringBuilder sb = new StringBuilder();
            while((str = reader.readLine()) != null){
                sb.append(str);
                sb.append("\r\n");
            }
            return sb.toString();
        }

    public static void main(String[] args) throws IOException{
        String path = System.getenv("PATH");
        System.out.println("Env.PATH:" + path);
        String classPathEnv = System.getenv("CLASSPATH");
        System.out.println("Env.CLASSPATH:" + classPathEnv);
        String usrDir = System.getProperty("user.dir");
        System.out.println("User dir: " + usrDir);
        File file = new File(".");
        String[] fileList = file.list();
        System.out.println(Arrays.asList(fileList));Thread.currentThread().getId();
        ClassLoader classLoader = BufferInputFile.class.getClassLoader();
        String classRootPath = classLoader.getResource("").getPath();
        System.out.println("Class root path:" + classRootPath);
        String curClassPath = BufferInputFile.class.getResource("").getPath();
        System.out.println("Current class path:" + curClassPath);
        String fileName = classLoader.getResource("io/usage/BufferInputFile.txt").getPath();
        System.out.println("File absolutely path:" + fileName);
        System.out.println(read(fileName));
    }
}
