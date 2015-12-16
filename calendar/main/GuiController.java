package calendar.main;

import calendar.day.DayData;
import calendar.day.DayWindow;
import calendar.event.EventData;
import calendar.event.EventWindow;
import calendar.notification.Notification;
import calendar.notification.NotificationData;
import calendar.notification.NotifierThread;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class GuiController implements Initializable {
    
    @FXML private GridPane grid;
    private ObservableList days; // zobrazovane dny
    private Contacts contacts;
    @FXML private ListView contactList;
    @FXML private Label topLabel;
    @FXML private Button testButton;
    @FXML private VBox weeksBox;
    
    ObservableList weeksBoxLabels;
    
    private int currYear = 2015;
    private int currMonth = 11;
    
    @FXML private void dayClicked(MouseEvent event) {
        VBox src = (VBox) event.getSource();
        int gridIndex = grid.getChildren().indexOf(src);
        Day.setSelected((Day) days.get(gridIndex));
        
        DayWindow n = new DayWindow();
        DayData test = new DayData();
        test.setText(Tools.getDateString(Day.getSelected().getDate()));
        n.show(test);
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
    
    public ListView getContactsContainer() { return contactList; }
    
    @FXML private void nextMonth(ActionEvent evt) {
        currMonth += 1;
        if (currMonth == 12) {
            currMonth = 0;
            currYear += 1;
        }
        Day.unselectDay();
        redrawGrid();
    }

    @FXML private void previousMonth(ActionEvent evt) {
        currMonth -= 1;
        if (currMonth == -1) {
            currMonth = 11;
            currYear -= 1;
        }
        Day.unselectDay();
        redrawGrid();
    }
    
    private void setWeekNumber(int weekIndex, int weekNumber) {
        Label currLabel = (Label) weeksBoxLabels.get(weekIndex);
        currLabel.setText(Integer.toString(weekNumber));
    }
    
    public void redrawGrid() {
        ArrayList displayedDays = Tools.getDaysGrid(currYear, currMonth);
        int firstDay = Tools.getFirstDayIndex(currYear, currMonth);
        int lastDay = Tools.getLastDayIndex(currYear, currMonth);
        
        topLabel.setText(Tools.monthToName(currMonth)+" "+Integer.toString(currYear));
        Day currDay;
        for(int i=0; i<days.size(); i++) {
            currDay = (Day) days.get(i);
            currDay.setDate( (Date) displayedDays.get(i) );
            if (i<firstDay || i>lastDay) currDay.setUnimportant(true);
            else currDay.setUnimportant(false);
            if ( Tools.getDateString(currDay.getDate()).equals(Tools.getDateString(Tools.now())) )  currDay.setToday(true);
            else currDay.setToday(false);
            
            //nastaveni cisla tydne
            if (i%7==0) {
                int weeknum = Tools.getWeekNumber(Tools.getDateString((Date) displayedDays.get(i)));
                setWeekNumber(i/7, weeknum);
            }
        }
    }
    
    public void reloadContacts() {
        ObservableList contactsText = FXCollections.observableArrayList(new ArrayList<String>());
        ArrayList contactsData = null;
        try {
            contactsData = Calendar.backend.getContacts();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (contactsData != null) {
            for (int i=0; i<contactsData.size(); i++) {
                contactsText.add(contactsData.get(i).toString());
            }
            contactList.setItems(contactsText);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NotifierThread notifier = new NotifierThread();
        notifier.setDaemon(true);
        notifier.start(); // START
        
        weeksBoxLabels = weeksBox.getChildren();
        
        ObservableList boxes = grid.getChildren();
        days = FXCollections.observableArrayList();
        Date currDay;
        
        for (int i=0; i<6*7; i++) {
            Day d = new Day(i, (VBox) boxes.get(i));
            days.add(d);
        }
        redrawGrid();
        
        contacts = new Contacts(this); // inicializace kontaktu
        reloadContacts();
        //Calendar.backend.deleteEvent("16.12.2015", "13:27");
        //Tools.getDateString(Day.getSelected().getDate());
    }    
    
}
