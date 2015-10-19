/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.notification;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author ASUS
 */
public class Notification extends Application {
    
    public static final long msDuration = 1000;
    
    private NotificationData data;
    private Stage stage;
    private static Notification currInstance;
    
    public static Notification getCurrentInstance() { return currInstance; }
    public Stage getStage() { return stage; }
    public NotificationData getData() { return data; }
    
    // hlavni funkce pro zobrazeni dat... k datum se pristupuje z controlleru pres Notification.currInstance.getData()
    public void show(NotificationData nData) {
        data = nData;
        currInstance = this;
        Stage st = new Stage();
        try { start(st); }
        catch (Exception ex) { System.err.println("Notification error"); ex.printStackTrace(); }
        
    }
    
    public void close() {
        stage.hide();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("notification.fxml")));
        stage.setScene(scene);
        stage.setTitle("Notification");
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        
        // umistim notification do praveho dolniho rohu
        double maxX = Screen.getPrimary().getVisualBounds().getWidth();
        double maxY = Screen.getPrimary().getVisualBounds().getHeight();
        System.out.println(maxX);
        System.out.println(maxY);
        
        stage.show(); 

        // az po show jsou definovane vyska a sirka sceny
        double stageX = scene.getWidth();
        double stageY = scene.getHeight();
        System.out.println(stageX);
        System.out.println(stageY);
        stage.setX(maxX-stageX);
        stage.setY(maxY-stageY);
        
        
    }
    
}
