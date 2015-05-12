/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webSocket;

import beans.NotificacionBean;
import ejb.IndexadorFacade;
import ejb.IndexadorFacadeRemote;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Singleton
@ServerEndpoint("/mediatorendpoint")
public class SocketMediator {

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public String onMessage(String message, Session session) {
        System.out.println("message received " + message);
        for (Session peer : peers) {
            if (!peer.equals(session)) {
                try {
                    peer.getBasicRemote().sendText(message + " - retweet");
                } catch (IOException ex) {
                    Logger.getLogger(SocketMediator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return "message was received and processed: " + message;
    }

    @OnOpen
    public void onOpen(Session peer) {
        peers.add(peer);
    }

    public void onTimeEvent(@Observes @WBTimeEvent IndexadorFacadeRemote event) {

        System.out.println("Time Event observed ");

        for (Session peer : peers) {
            try {
                boolean indexo = false;
                List<NotificacionBean> notificaciones = event.indexarArchivosDeCarpeta();
                for (NotificacionBean not : notificaciones) {
                    peer.getBasicRemote().sendText(""+not);
                    indexo = true;
                }
//                if (!indexo) {
//                    peer.getBasicRemote().sendText("No se indexo");
//                }
            } catch (IOException ex) {
                Logger.getLogger(SocketMediator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @OnClose
    public void onClose(Session peer) {
        peers.remove(peer);
    }
}
