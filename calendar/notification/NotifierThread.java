/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.notification;

import calendar.main.Calendar;
import calendar.main.Event;
import calendar.main.Tools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author ASUS
 */
public class NotifierThread extends Thread {
    @Override
    public void run() {
        while (true) {
            ArrayList events = null;
            try {
                Thread.sleep(1000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            // vezmu seznam udalosti za dnesni den
            try {
                events = Calendar.backend.getEvents(Tools.getDateString(Tools.now()));
            } catch(ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(NotifierThread.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (events != null) {
                if (!events.isEmpty()) {
                    Event currEvt = (Event) events.get(0);
                    Date now = Tools.now();
                    Date evt = Tools.stringToDateTime(currEvt.date + " " + currEvt.time);
                    if (now.compareTo(evt) >= 0) {
                        Notification n = new Notification();
                        NotificationData test = new NotificationData();
                        test.setText(currEvt.date+" "+currEvt.time);
                        n.show(test);
                    }
                }
            }
            
        }
    }
}
