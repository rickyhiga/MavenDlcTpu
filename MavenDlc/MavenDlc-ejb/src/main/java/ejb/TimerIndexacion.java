/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Singleton
public class TimerIndexacion {

    @EJB
    private IndexadorFacadeRemote idx;

    @Schedule(second = "*", minute = "*/1", hour = "*", persistent = false)
    public void atSchedule() {
        long tiempoInicio = System.currentTimeMillis();

        ArrayList<File> lista = new ArrayList<>();
        File dir = new File("C:\\IDE");
        File[] directoryListing = dir.listFiles();
        int count = 0;
        if (directoryListing != null) {
            for (File child : directoryListing) {
//                count++;
//                if(count>5){
//                    break;
//                }
                lista.add(child);
            }
            idx.saveCount(lista);
        } else {

            System.out.println("NO EXISTE LA CARPETA");
        }
        
    }
}
