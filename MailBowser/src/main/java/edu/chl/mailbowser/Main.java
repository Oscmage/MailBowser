package edu.chl.mailbowser;

import edu.chl.mailbowser.account.BackgroundFetching;
import edu.chl.mailbowser.account.factories.MailServerFactory;
import edu.chl.mailbowser.account.models.Account;
import edu.chl.mailbowser.email.models.Address;
import edu.chl.mailbowser.tag.handlers.TagHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    @Override
    public void start(Stage mainStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MainView.fxml"));
        mainStage.setTitle("MailBowser");

        Scene scene = new Scene(root, 960, 600);

        // Add fonts and styles to the scene
        //scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto:400italic,300,700,400");

        //Initialize Account instance
        Account.createInstance(
                new Address("mailbows3r@gmail.com"),
                "VG5!qBY&#f$QCmV", // It really doesn't get more Open Source™ than this
                MailServerFactory.createIncomingServer(MailServerFactory.Type.GMAIL),
                MailServerFactory.createOutgoingServer(MailServerFactory.Type.GMAIL)
        );
        
        Thread backgroundFetching = BackgroundFetching.getInstance();
        backgroundFetching.setDaemon(true);
        backgroundFetching.start();

        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        // used to launch a JavaFX application
        try {
            read();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        launch(args);
        write();
    }

    private static void write(){
        FileOutputStream f_out_Account;
        FileOutputStream f_out_TagHandler;
        ObjectOutputStream o_out_Account;
        ObjectOutputStream o_out_TagHandler;
        try {
            f_out_Account = new FileOutputStream("Account.ser");
            f_out_TagHandler = new FileOutputStream("TagHandler.ser");
            o_out_Account = new ObjectOutputStream(f_out_Account);
            o_out_TagHandler = new ObjectOutputStream(f_out_TagHandler);
            o_out_Account.writeObject(Account.INSTANCE);
            o_out_TagHandler.writeObject(TagHandler.getInstance());
            f_out_Account.close();
            f_out_TagHandler.close();
            o_out_Account.close();
            o_out_TagHandler.close();
        }catch (IOException e){
            System.out.print("Fuck off");
        }
    }

    private static void read() throws ClassNotFoundException {
        FileInputStream f_in_Account;
        FileInputStream f_in_TagHandler;
        ObjectInputStream o_in_Account;
        ObjectInputStream o_in_TagHandler;
        try{
            f_in_Account = new FileInputStream("Account.ser");
            f_in_TagHandler = new FileInputStream("TagHandler.ser");
            o_in_Account = new ObjectInputStream(f_in_Account);
            o_in_TagHandler = new ObjectInputStream(f_in_TagHandler);

        }catch (IOException e){

        }
    }
}
