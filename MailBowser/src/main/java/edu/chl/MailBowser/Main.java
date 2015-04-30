package edu.chl.MailBowser;

import edu.chl.MailBowser.factories.MailServerFactory;
import edu.chl.MailBowser.models.Account;
import edu.chl.MailBowser.models.Address;
import edu.chl.MailBowser.models.IAccount;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SendEmail.fxml"));
        primaryStage.setTitle("MailBowser");

        Scene scene = new Scene(root, 250, 300);

        // Add fonts and styles to the scene
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto:400italic,300,700,400");
        scene.getStylesheets().add("/css/style.css");

        // Create a default account
        IAccount account = new Account(
                new Address("mailbows3r@gmail.com"),
                "VG5!qBY&#f$QCmV", // It really doesn't get more Open Source™ than this
                MailServerFactory.createIncomingServer(MailServerFactory.Type.GMAIL),
                MailServerFactory.createOutgoingServer(MailServerFactory.Type.GMAIL)
        );

        // ... And put it in the DataHandler ("database")
        DataHandler.INSTANCE.addAccount(account);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // used to launch a JavaFX application
        launch(args);
    }
}
