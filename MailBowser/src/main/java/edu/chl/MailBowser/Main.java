package edu.chl.MailBowser;

import edu.chl.MailBowser.controllers.EmailController;
import edu.chl.MailBowser.models.Account;
import edu.chl.MailBowser.models.Address;
import edu.chl.MailBowser.models.IAccount;
import edu.chl.MailBowser.models.MailServer;
import edu.chl.MailBowser.views.SendEmailView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MailBowser.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        // used to launch a JavaFX application
        //launch(args);

        // create necessary views and controllers
        SendEmailView sendEmailView = new SendEmailView();
        EmailController emailController = new EmailController(sendEmailView);

        // create an account and add it to DataHandler
        IAccount account = new Account(
                new Address("mailbows3r@gmail.com"),
                "VG5!qBY&#f$QCmV",
                new MailServer(),
                new MailServer()
        );
        DataHandler dh = DataHandler.getInstance();
        dh.addAccount(account);

        // simulate writing an email
        sendEmailView.setEmailSubject("Subject");
        sendEmailView.setEmailContent("Content");
        sendEmailView.addEmailReceiver(new Address("mailbows3r@gmail.com"));
        sendEmailView.chooseAccount(0);

        // simulate a click on the send button
        sendEmailView.sendEmailButtonClicked();
    }
}
