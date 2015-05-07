package edu.chl.mailbowser;

import edu.chl.mailbowser.account.BackgroundFetching;
import edu.chl.mailbowser.account.factories.MailServerFactory;
import edu.chl.mailbowser.account.models.Account;
import edu.chl.mailbowser.email.models.Address;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage mainStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MainView.fxml"));
        mainStage.setTitle("MailBowser");

        Scene scene = new Scene(root, 960, 600);

        // Add fonts and styles to the scene
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto:400italic,300,700,400");

        //Initialize Account instance
        Account.INSTANCE.init(
                new Address("mailbows3r@gmail.com"),
                "VG5!qBY&#f$QCmV", // It really doesn't get more Open Source™ than this
                MailServerFactory.createIncomingServer(MailServerFactory.Type.GMAIL),
                MailServerFactory.createOutgoingServer(MailServerFactory.Type.GMAIL)
        );
        Thread backgroundFetching = new Thread(BackgroundFetching.getInstance());
        backgroundFetching.setDaemon(true);
        backgroundFetching.start();

        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        // used to launch a JavaFX application
        launch(args);
    }
}
