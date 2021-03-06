package calendar.notification;

import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;

public class NotificationController implements Initializable {    
    
    @FXML Label textLabel;
    
    Notification notifInst;
    
    private void switchTimer() {
        Thread timer = new Thread(new Runnable() {
            @Override public void run() {
                try { Thread.sleep(Notification.msDuration); } catch (InterruptedException ex) {}
                Platform.runLater(new Runnable() {
                    @Override public void run() { notifInst.close(); }
                });
            }
        });
        timer.start();
    }
    
    private void initSetup() {
        String text = notifInst.getData().getText();
        textLabel.setText(text);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        notifInst = Notification.getCurrentInstance();
        Toolkit.getDefaultToolkit().beep(); // pipnuti
        
        initSetup(); // inicializace obsahu
        
        notifInst.getStage().addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent window) {
                Platform.runLater(new Runnable() {
                    @Override public void run() { switchTimer(); }
                });
            }
        });
    }
    
}
