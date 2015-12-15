package calendar.main;

import calendar.day.DayData;
import calendar.day.DayWindow;
import calendar.event.EventData;
import calendar.event.EventWindow;
import calendar.notification.Notification;
import calendar.notification.NotificationData;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GuiController implements Initializable {
    
    @FXML private GridPane grid;
    private ObservableList days; // zobrazovane dny
    private Contacts contacts;
    @FXML private ListView list;
    @FXML private Button testButton;
    
    private int currYear = 2015;
    private int currMonth = 11;
    
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
        test.setText("asdasd");
        n.show(test);
    }

    @FXML private void dayTest(ActionEvent evt) {
        DayWindow n = new DayWindow();
        DayData test = new DayData();
        test.setText(Tools.getDateString(Day.getSelected().getDate()));
        n.show(test);
    }

    @FXML private void eventTest(ActionEvent actionEvent) {
        EventWindow eventWindow = new EventWindow();
        EventData eventData = new EventData();
        eventWindow.show();
    }
    
    public ListView getContactsContainer() { return list; }
    
    @FXML private void nextMonth(ActionEvent evt) {
        currMonth = Tools.getNextMonth(currMonth);
        redrawGrid();
    }

    @FXML private void previousMonth(ActionEvent evt) {
        currMonth = Tools.getPreviousMonth(currMonth);
        redrawGrid();
    }
    
    public void redrawGrid() {
        ArrayList displayedDays = Tools.getDaysGrid(currYear, currMonth);
        int firstDay = Tools.getFirstDayIndex(currYear, currMonth);
        int lastDay = Tools.getLastDayIndex(currYear, currMonth);
        
        Day currDay;
        
        for(int i=0; i<days.size(); i++) {
            currDay = (Day) days.get(i);
            currDay.setDate( (Date) displayedDays.get(i) );
            if (i<firstDay || i>lastDay) currDay.setUnimportant(true);
            else currDay.setUnimportant(false);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList boxes = grid.getChildren();
        days = FXCollections.observableArrayList();
        Date currDay;
        
        for (int i=0; i<6*7; i++) {
            Day d = new Day(i, (VBox) boxes.get(i));
            days.add(d);
        }
        redrawGrid();
        
        contacts = new Contacts(this); // inicializace kontaktu
        System.out.println();
        System.out.println(Tools.getLastDayIndex(2015, 11));
        
        //Tools.getDateString(Day.getSelected().getDate());
    }    
    
}
