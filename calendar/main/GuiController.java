/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.main;

import calendar.notification.Notification;
import calendar.notification.NotificationData;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author ASUS
 */
public class GuiController implements Initializable {
    
    @FXML private GridPane grid;
    private ObservableList days; // zobrazovane dny
    
    @FXML private void dayClicked(MouseEvent event) {
        VBox src = (VBox) event.getSource();
        int gridIndex = grid.getChildren().indexOf(src);
        Day.setSelected((Day) days.get(gridIndex));
    }
    
    @FXML private void switchDemo(ActionEvent evt) {
        if (Day.getSelected() == null) return;
        
        Day test = Day.getSelected();
        int showCount = (test.eventCount()+1)%4;
        test.showEvents(showCount);
    }
    
    @FXML private void notifyTest(ActionEvent evt) {
        Notification n = new Notification();
        NotificationData test = new NotificationData();
        test.setText("Notification");
        n.show(test);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList boxes = grid.getChildren();
        
        days = FXCollections.observableArrayList();
        for (int i=0; i<6*7; i++) {
            Day d = new Day(i, (VBox) boxes.get(i));
            days.add(d);
        }
    }    
    
}
