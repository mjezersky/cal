package calendar.day;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class DayController implements Initializable {

    @FXML Button btnSave;
    @FXML TextArea pozn;
    @FXML Label textLabel;
    @FXML Label textTemp;
    
    DayWindow dayInst;

    private void initSetup() {
        String text = dayInst.getData().getText();
        textLabel.setText(text);

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
