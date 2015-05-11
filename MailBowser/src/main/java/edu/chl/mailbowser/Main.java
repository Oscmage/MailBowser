package edu.chl.mailbowser;

import edu.chl.mailbowser.account.BackgroundFetching;
import edu.chl.mailbowser.account.factories.MailServerFactory;
import edu.chl.mailbowser.account.handlers.AccountHandler;
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
        
        Thread backgroundFetching = BackgroundFetching.getInstance();
        backgroundFetching.setDaemon(true);
        backgroundFetching.start();

        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        // load serialized objects from disk
        load();
        // used to launch a JavaFX application
        launch(args);
        // save serializable objects to disk
        save();
    }

    private static void load() {
        boolean loadAccountSuccessful = AccountHandler.getInstance().readAccount("Account.ser");

        // create a new account if no account was found on disk
        if (!loadAccountSuccessful) {
            System.out.println("load: failed to load account");

            AccountHandler.getInstance().setAccount(new Account(
                    new Address("mailbows3r@gmail.com"),
                    "VG5!qBY&#f$QCmV", // It really doesn't get more Open Source™ than this
                    MailServerFactory.createIncomingServer(MailServerFactory.Type.GMAIL),
                    MailServerFactory.createOutgoingServer(MailServerFactory.Type.GMAIL)
            ));
        } else {
            System.out.println("load: loaded account from Account.ser");
            System.out.println(AccountHandler.getInstance().getAccount());
        }

        //FileInputStream f_in_TagHandler;
        //ObjectInputStream o_in_TagHandler;
        //try{
        //    f_in_TagHandler = new FileInputStream("TagHandler.ser");
        //    o_in_TagHandler = new ObjectInputStream(f_in_TagHandler);
        //    o_in_TagHandler.readObject();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //    System.out.println("read: failed reading TagHandler.ser");
        //}
    }

    private static void save() {
        AccountHandler.getInstance().writeAccount("Account.ser");

        //FileOutputStream f_out_TagHandler;
        //ObjectOutputStream o_out_TagHandler;
        //
        //try {
        //    f_out_TagHandler = new FileOutputStream("TagHandler.ser");
        //    o_out_TagHandler = new ObjectOutputStream(f_out_TagHandler);
        //    o_out_TagHandler.writeObject(TagHandler.getInstance());
        //    f_out_TagHandler.close();
        //    o_out_TagHandler.close();
        //} catch (IOException e){
        //    e.printStackTrace();
        //    System.out.print("write: failed writing TagHandler.ser");
        //}
    }
}
