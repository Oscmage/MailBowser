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

    private static ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();
    private static IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();
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
        load();
        backgroundFetching.start();
        // used to launch a JavaFX application
        launch(args);
        backgroundFetching.stop();
        // save serializable objects to disk
        save();
    }

    private static void load() {
        boolean loadAccountsSuccessful = accountHandler.readAccounts("Accounts.ser");
        boolean loadTagHandlerSuccessful = tagHandler.readTags("Tags.ser");

        if (!loadAccountsSuccessful) {
            System.out.println("load: failed to load accounts from Accounts.ser");
        } else {
            System.out.println("load: loaded accounts " + accountHandler.getAccounts());
        }

        if(!loadTagHandlerSuccessful){
            System.out.println("load: failed to load tags from Tags.ser");
        } else {
            System.out.println("load: loaded tags " + tagHandler.getTags());
        }
    }

    private static void save() {
        accountHandler.writeAccounts("Accounts.ser");
        tagHandler.writeTags("Tags.ser");
    }
}
