/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.main;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author ASUS
 */
public class Day {
    private final VBox parent;
    private final int num;
    private final Label label;
    private final Button event1;
    private final Button event2;
    private final Button more;
            
    private static Day selected = null;
    private int events = 0;
    
    Day(int number, VBox gridCell) {
        ObservableList elements = gridCell.getChildren();
        parent = gridCell;
        num = number;
        label = (Label) elements.get(0);
        event1 = (Button) elements.get(1);
        event2 = (Button) elements.get(2);
        more = (Button) elements.get(3);
        
        event1.setVisible(false);
        event2.setVisible(false);
        more.setVisible(false);
    }
    
    public int eventCount() { return events; }
    public static Day getSelected() { return selected; }
    
    public static void setSelected(Day d) {
        // odstranim zvyrazneni predchoziho vybraneho dne
        if (selected != null) selected.parent.getStyleClass().remove("selectedDay");
        // vyberu novy den a zvyraznim
        selected = d;
        selected.parent.getStyleClass().add("selectedDay");
    }
    
    public void showEvents(int count) {
        events = count;
        if (count == 0) {
            event1.setVisible(false);
            event2.setVisible(false);
            more.setVisible(false);
        }
        else if (count == 1) {
            event1.setVisible(true);
            event2.setVisible(false);
            more.setVisible(false);
        }
        else if (count == 2) {
            event1.setVisible(true);
            event2.setVisible(true);
            more.setVisible(false);
        }
        else {
            event1.setVisible(true);
            event2.setVisible(true);
            more.setVisible(true);
        }
    }
}
