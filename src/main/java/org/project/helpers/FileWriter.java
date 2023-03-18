package org.project.helpers;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileWriter {

    public static void saveFile(String result, String filePath){
        try {
            Scanner scanner = new Scanner(System.in);
            try{
                File file = new File(filePath);
                java.io.FileWriter escribir = new java.io.FileWriter(file, true);
                escribir.write(result);
                escribir.close();
                System.out.println("It was successfully saved.");
            }
            catch (Exception e){
                System.out.println("Error when written");
            }
        }
        catch (Exception e) {
            System.out.println("Error when written");
        }

    }
    public static void clearFile(String filePath)
    {
        try{
            java.io.FileWriter fw = new java.io.FileWriter(filePath, false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            pw.close();
            fw.close();
        }catch(Exception exception){
            System.out.println("Exception have been caught");
        }
    }
}
