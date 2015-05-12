/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webSocket;

import ejb.IndexadorFacade;
import ejb.IndexadorFacadeRemote;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 *
 * @author user
 */
@Singleton
public class TimerIndexacion {

    @Inject
    @WBTimeEvent
    Event<IndexadorFacadeRemote> timeEvent;
    @EJB
    private IndexadorFacadeRemote idx;

    @Schedule(second = "0", minute = "*/1", hour = "*", persistent = false)
    public void atSchedule() {
        System.out.println("Call # ");
        timeEvent.fire(idx);
//        ArrayList<File> lista = new ArrayList<>();
//        File dir = new File("C:\\IDE");
//        File[] directoryListing = dir.listFiles();
//        int count = 0;
//        if (directoryListing != null) {
//            for (File child : directoryListing) {
////                count++;
////                if(count>1){
////                    break;
////                }
//                lista.add(child);
//            }
//            idx.saveCount(lista);
//            
//          //  indE.fire(idx);
//        } else {
//
//            System.out.println("NO EXISTE LA CARPETA");
//        }

    }
}
