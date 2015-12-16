package calendar.main;

import java.util.Date;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Day {
    private final VBox parent;
    private final int num;
    private final Label label;
    
    private Date date;
            
    private static Day selected = null;
    private int events = 0;
    
    Day(int number, VBox gridCell) {
        ObservableList elements = gridCell.getChildren();
        parent = gridCell;
        num = number;
        label = (Label) elements.get(0);
    }
    
    public Date getDate() { return date; }
    public void setDate(Date d) { 
        date = d;
        setLabel(Tools.getDayNumberString(date));
    }
    
    public int eventCount() { return events; }
    public static Day getSelected() { return selected; }
    
    public void setLabel(String text) { label.setText(text); }
    
    public void setUnimportant(boolean state) {
        parent.getStyleClass().remove("unimportant");
        if (state) parent.getStyleClass().add("unimportant");
    }
    
    public void setToday(boolean state) {
        label.getStyleClass().remove("today");
        if (state) label.getStyleClass().add("today");
    }
    
    public static void setSelected(Day d) {
        // odstranim zvyrazneni predchoziho vybraneho dne
        if (selected != null) selected.setSelectedStyle(false);
        // vyberu novy den a zvyraznim
        selected = d;
        selected.setSelectedStyle(true);
    }
    
    public static void unselectDay() {
        if (selected != null) selected.setSelectedStyle(false);
        selected = null;
    }
    
    public void setSelectedStyle(boolean state) {
        parent.getStyleClass().remove("selectedDay");
        if (state) parent.getStyleClass().add("selectedDay");
    }
    
    public void showEvents(int count) {
        events = count;
    }
    
    public void setHasEvent(boolean state) {
        parent.getStyleClass().remove("hasEvent");
        if (state) parent.getStyleClass().add("hasEvent");
    }
}
