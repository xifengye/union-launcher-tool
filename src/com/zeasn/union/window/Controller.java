package com.zeasn.union.window;

import com.zeasn.union.data.DataMgr;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class Controller {

    @FXML
    Label mProjectSaveDirLabel;
    @FXML
    TextField mProjectNameTextField;
    @FXML
    Label mTemplateProjectDirLabel;

    @FXML
    public void onSelectProjectSaveDirClicked(ActionEvent actionEvent){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");
        File directory = directoryChooser.showDialog(new Stage());
        if (directory != null) {
            String filePath = directory.getAbsolutePath();
            DataMgr.getInstance().setProjectDir(filePath);
            mProjectSaveDirLabel.setText(filePath);
        }
    }
    @FXML
    public void onCreateProjectClicked(ActionEvent actionEvent){
        DataMgr.getInstance().setProjectName(mProjectNameTextField.getText());
        try {
            File descFile = new File(DataMgr.getInstance().getProjectDir()+File.separator+"temp");
            FileUtils.copyDirectory(new File(DataMgr.getInstance().getmTemplateProjectFilePath()), descFile);
            descFile.renameTo(new File(DataMgr.getInstance().getProjectFilePath()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
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
