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
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class DayController implements Initializable {

    @FXML Button btnSave;
    @FXML TextArea pozn;
    @FXML Label textLabel;
    @FXML Label textTemp;
    @FXML ListView eventContainer;
    
    DayWindow dayInst;

    private void initSetup() {
        String text = dayInst.getData().getText();
        textLabel.setText(text);
        ObservableList events = null;
        String notes = null;
        
        
        try {
            notes = Calendar.backend.getNotes("15.12.2015");            
            events = FXCollections.observableArrayList(Calendar.backend.getEvents("15.12.2015"));     
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DayController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(DayController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DayController.class.getName()).log(Level.SEVERE, null, ex);
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
