package calendar.event;

import calendar.day.DayWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Created by Maros Janota (xjanot01) on 15.12.2015.
 */
public class EventController implements Initializable {

    @FXML Button btnAddEvent;
    @FXML DatePicker datePicker;
    @FXML TextArea eventString;

    EventWindow eventWindow;

    private void initSetup() {

        /*Weather tmp = new Weather();
        String temp = tmp.getTemperature();
        textTemp.setText(temp);*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        eventWindow = EventWindow.getCurrentInstance();

        datePicker.setValue(LocalDate.now());
        System.out.println(LocalDate.now());
        System.out.println(LocalTime.now());

        initSetup(); // inicializace obsahu

    }
}
