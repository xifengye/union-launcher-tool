package com.zeasn.union.window;

import com.zeasn.union.data.DataMgr;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;


public class Controller {


    @FXML
    Button mButton;
    @FXML
    Label mTemplateProjectDirLabel;

    @FXML
    public void onSelectProjectSaveDirClicked(ActionEvent actionEvent){

    }
    @FXML
    public void onCreateProjectClicked(ActionEvent actionEvent){

    }
    @FXML
    public void onSelectTemplateProjectDirClicked(ActionEvent actionEvent){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");
        File directory = directoryChooser.showDialog(new Stage());
        if (directory != null) {
            mTemplateProjectDirLabel.setText(directory.getAbsolutePath());
            DataMgr.getInstance().setmTemplateProjectFilePath(directory.getAbsolutePath());
        }
    }
}
