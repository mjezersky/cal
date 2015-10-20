package calendar.day;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DayController implements Initializable {
    
    @FXML Label textLabel;
    
    DayWindow dayInst;

    private void initSetup() {
        String text = dayInst.getData().getText();
        textLabel.setText(text);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dayInst = DayWindow.getCurrentInstance();
        
        initSetup(); // inicializace obsahu

    }
    
}
