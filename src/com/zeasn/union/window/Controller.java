package com.zeasn.union.window;

import com.zeasn.union.data.DataMgr;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;


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
        CreateProjectPaneController controller = fxmlLoader.getController();
        controller.bindDialog(alert);
        alert.getDialogPane().getButtonTypes().addAll(new ButtonType("取消",ButtonBar.ButtonData.CANCEL_CLOSE));
        alert.getDialogPane().setContent(root);
        alert.showAndWait();
    }

}
