package calendar.day;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

import calendar.main.Calendar;
import calendar.main.Tools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class DayController implements Initializable {

    @FXML Button btnSave;
    @FXML TextArea pozn;
    @FXML Label textLabel;
    @FXML Label textTemp;
    @FXML ListView eventContainer;
    
    DayWindow dayInst;

    @FXML private void delEvent(ActionEvent e) {
        String selectedItem = (String) eventContainer.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String[] parts = selectedItem.split(" \\|");
            System.out.println(parts.length);
            if (parts.length>0) {
                
                String time = parts[0];
                System.out.println(textLabel.getText());
                System.out.println(time);
                Calendar.backend.deleteEvent(textLabel.getText(), time);
            }
        }
        refreshContent();
    }
    
    @FXML private void editEvent(ActionEvent e) {
        Tools.alert("Upozornění", "funkce není implementována");
        System.out.println("Not implemented.");
    }
    
    @FXML private void saveNotes(ActionEvent e) {
        String date = textLabel.getText();
        String text = pozn.getText();
        Calendar.backend.saveNote(text, date);
        refreshContent();
    }
    
    private void refreshContent() {
        ObservableList events = null;
        String notes = null;
        String text = textLabel.getText();
        
        try {
            notes = Calendar.backend.getNotes(text);            
            events = FXCollections.observableArrayList(Calendar.backend.getEvents(text));     
        } catch (NullPointerException ex) {
            events = null;
        }
        

        if (events != null) {
            ObservableList eventsText = FXCollections.observableArrayList(new ArrayList<String>());
            for (int i=0; i<events.size(); i++ ) {
                eventsText.add(events.get(i).toString());
            }
        
            eventContainer.setItems(eventsText);
        }
        
        if (notes != null) {
            pozn.setText(notes);
        }
    }
    
    private void initSetup() {
        String text = dayInst.getData().getText();
        textLabel.setText(text);
        
        refreshContent();
        
        /*Weather tmp = new Weather();
            String temp = tmp.getTemperature();
            textTemp.setText(temp);*/
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dayInst = DayWindow.getCurrentInstance();
        
        initSetup(); // inicializace obsahu

    }
    
}
