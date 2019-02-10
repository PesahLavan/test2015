package ua.com.test.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MessageController {
    @FXML
    private Button btnOk;
    @FXML
    private Label labelMessage;


    public void setLabelMessage(String text) {
        labelMessage.setText(text);
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }


    public void actionSave(ActionEvent actionEvent) {
        actionClose(actionEvent);
    }
}
