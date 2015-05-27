package edu.chl.mailbowser.main;


import edu.chl.mailbowser.backgroundfetcher.IBackgroundFetcher;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The main class to launch the application.
 */
public class Main extends Application {

    private static IBackgroundFetcher backgroundFetcher = MainHandler.INSTANCE.getBackgroundFetcher();

    /**
     * Loads the MainView fxml.
     * @param mainStage
     * @throws Exception thrown if FXMLLoader has troubles loading the MainView fxml.
     */
    @Override
    public void start(Stage mainStage) throws Exception {
        Font.loadFont(getClass().getClassLoader().getResource("fonts/fontawesome.ttf").toExternalForm(), 16);

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MainView.fxml"));

        EventBus.INSTANCE.publish(new Event(EventType.FXML_LOADED, mainStage));

        mainStage.setTitle("MailBowser");

        Scene scene = new Scene(root, 960, 600);

        // Add fonts and styles to the scene
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto:400italic,300,700,400");

        mainStage.setMinHeight(600);
        mainStage.setMinWidth(800);

        mainStage.setScene(scene);
        mainStage.show();
    }

    /**
     * Pre launch checks whether there are any serialized files in the main directory to load.
     * Main launches the application with background fetching.
     * After exit of application tries to save the data and exists background fetching.
     * @param args
     */
    public static void main(String[] args) {
        // load serialized objects from disk
        MainHandler.INSTANCE.loadComponents();

        // initiate background fetching
        backgroundFetcher.start();

        // used to launch a JavaFX application
        launch(args);
        // stop background fetching
        backgroundFetcher.stop();

        // save serializable objects to disk
        MainHandler.INSTANCE.saveComponents();
    }
}
