package ua.com.test;

import ua.com.test.database.CreateDB;
import ua.com.test.database.SQLiteConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/ua/com/test/views/main.fxml"));
        primaryStage.setTitle("Test");
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(600);
        primaryStage.setScene(new Scene(root, 400, 325));
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            ResultSet rs = SQLiteConnection.getConnection().createStatement().executeQuery("select * from Main");
            rs.close();
        } catch (SQLException e) {
            CreateDB.createDB();
        }
        launch(args);
    }
}
