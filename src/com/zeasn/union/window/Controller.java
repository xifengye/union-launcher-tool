package com.zeasn.union.window;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.net.URL;


public class Controller {

    /**
     * 创建工程菜单键
     * @param actionEvent
     */
    public void onCreateProjectMenuItemClicked(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("创建工程");
        alert.setHeaderText("");
        URL location = getClass().getResource("create_project_pane.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
//        alert.getDialogPane().getButtonTypes().remove(0);
        alert.getDialogPane().getButtonTypes().addAll(new ButtonType("创建工程",ButtonBar.ButtonData.OK_DONE));
        alert.getDialogPane().setContent(root);
        alert.showAndWait();
    }


}
