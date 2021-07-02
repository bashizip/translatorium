/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bashizip.translatorium;

import static com.bashizip.translatorium.LangTranslator.translate;
import java.io.File;

/**
 *
 * @author Rachel
 */
public class Main {

    //
    public static void main(String[] args) throws Exception {

        System.out.println("Utilisation: java -jar Translatorium.jar");

        String sourceLang = args[0];
        String destLang = args[1];
        String sourceFile = args[2];

        File f = translate(sourceLang, destLang, sourceFile);

        System.out.println("FINISHED !");
        System.out.println("Output file : " + f.getAbsolutePath());
    }
}
