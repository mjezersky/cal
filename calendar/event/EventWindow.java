package calendar.event;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by Maros Janota (xjanot01) on 15.12.2015.
 */
public class EventWindow extends Application {

    private EventData data;
    private static EventWindow currInstance;

    public static EventWindow getCurrentInstance() { return currInstance; }
    public EventData getData() { return data; }

    public void show() {
        currInstance = this;
        Stage st = new Stage();
        try { start(st); }
        catch (Exception ex) { System.err.println("Notification error"); ex.printStackTrace(); }
    }

    @Override
    public void start(Stage stage1) throws Exception {
        Stage stage = stage1;
        Scene scene = new Scene(FXMLLoader.<Parent>load(getClass().getResource("eventFrame.fxml")));
        stage.setScene(scene);
        stage.setTitle("Add event");
        stage.setResizable(false);

        // Nastaveni na stred obrazovky
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        stage.setX((screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenSize.getHeight() - stage.getHeight()) / 2);
        stage.show();
    }
}
