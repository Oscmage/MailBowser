package edu.chl.mailbowser;


import edu.chl.mailbowser.account.IBackgroundFetcher;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.tag.handlers.ITagHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private static IBackgroundFetcher backgroundFetching = MainHandler.INSTANCE.getBackgroundFetching();

    @Override
    public void start(Stage mainStage) throws Exception {
        Font.loadFont(getClass().getClassLoader().getResource("fonts/fontawesome.ttf").toExternalForm(), 16);

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MainView.fxml"));

        EventBus.INSTANCE.publish(new Event(EventType.FXML_LOADED, mainStage));

        mainStage.setTitle("MailBowser");

        Scene scene = new Scene(root, 960, 600);

        // Add fonts and styles to the scene
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto:400italic,300,700,400");
        scene.getStylesheets().add("css/style.css");

        mainStage.setMinHeight(600);
        mainStage.setMinWidth(800);

        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        // load serialized objects from disk
        MainHandler.INSTANCE.loadComponents();

        // initiate background fetching
        backgroundFetching.start();

        // used to launch a JavaFX application
        launch(args);
        // stop background fetching
        backgroundFetching.stop();

        // save serializable objects to disk
        MainHandler.INSTANCE.saveComponents();
    }
}
