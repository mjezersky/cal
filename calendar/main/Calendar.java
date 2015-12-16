package calendar.main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Calendar extends Application {
    
    public static final Backend backend = new Backend();
    public static boolean mainWindowClosed = false;
    public static Stage primaryStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        
        Scene scene = new Scene(root);
        primaryStage = stage;
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("images/btns/favico.png"));
        
        scene.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                mainWindowClosed = true;
            }
        });
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
