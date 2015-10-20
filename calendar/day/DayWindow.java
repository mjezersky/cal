package calendar.day;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DayWindow extends Application{


    private DayData data;
    private static DayWindow currInstance;

    public static DayWindow getCurrentInstance() { return currInstance; }
    public DayData getData() { return data; }

    // hlavni funkce pro zobrazeni dat... k datum se pristupuje z controlleru pres Notification.currInstance.getData()
    public void show(DayData nData) {
        data = nData;
        currInstance = this;
        Stage st = new Stage();
        try { start(st); }
        catch (Exception ex) { System.err.println("Notification error"); ex.printStackTrace(); }

    }

    @Override
    public void start(Stage stage1) throws Exception {
        Stage stage = stage1;
        Scene scene = new Scene(FXMLLoader.<Parent>load(getClass().getResource("dayFrame.fxml")));
        stage.setScene(scene);
        stage.setTitle("Day");
        scene.setFill(Color.TRANSPARENT);

        // Nastaveni na stred obrazovky
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        stage.setX((screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenSize.getHeight() - stage.getHeight()) / 2);
        stage.show();

    }
}
