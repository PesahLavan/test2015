package ua.com.test.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractController {
    private static final String PATH = "/ua/com/test/views/";
    protected void showElement(ActionEvent actionEvent, String fileName, String title) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(PATH + fileName));
        stage.setTitle(title);
        stage.setMinHeight(150);
        stage.setMinWidth(300);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        stage.show();
    }
}
