/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author nhats
 */
public abstract class AlertGame {

    Alert alert;
    Optional<ButtonType> result;

    public AlertGame(String title, String text, AlertType typeAlert) {
        alert = new Alert(typeAlert);
        alert.setHeaderText(text);
        alert.setTitle(title);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/images/icon.png").toString()));
        result = alert.showAndWait();
        processResult();
    }

    public Optional<ButtonType> getResult() {
        return result;
    }

    public abstract void processResult();
}
