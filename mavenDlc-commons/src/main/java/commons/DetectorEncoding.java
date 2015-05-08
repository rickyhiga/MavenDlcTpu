/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.mozilla.universalchardet.UniversalDetector;

/**
 *
 * @author Nico
 */
public class DetectorEncoding {

    public static String getFileEncoding(File f) throws FileNotFoundException, IOException {
        byte[] buf = new byte[4096];
        //String fileName = "user_uploads/22975-8.txt";
        java.io.FileInputStream fis = new java.io.FileInputStream(f);

        // (1)
        UniversalDetector detector = new UniversalDetector(null);

        // (2)
        int nread;
        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        // (3)
        detector.dataEnd();

        // (4)
        String encoding = detector.getDetectedCharset();
        detector.reset();
        if (encoding != null) {
//      System.out.println("Detected encoding = " + encoding);
            return encoding;
        } else {
//      System.out.println("No encoding detected.");
            return "ISO-8859-1";
        }

    // (5)
    }

}
