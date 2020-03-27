package com.zeasn.union.window;

import com.zeasn.union.data.DataMgr;
import com.zeasn.union.data.LauncherProject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class CreateProjectPaneController {
    private Alert alert;
    @FXML
    Label mProjectSaveDirLabel;
    @FXML
    TextField mProjectNameTextField;
    @FXML
    Label mTemplateProjectDirLabel;

    private LauncherProject mLauncherProject;

    public CreateProjectPaneController() {
        mLauncherProject = new LauncherProject();
    }

    /**
     * 选择模板工程
     * @param actionEvent
     */
    @FXML
    public void onSelectTemplateProjectDirClicked(ActionEvent actionEvent){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");
        File directory = directoryChooser.showDialog(new Stage());
        if (directory != null) {
            mTemplateProjectDirLabel.setText(directory.getAbsolutePath());
            mLauncherProject.setTemplateDir(directory.getAbsolutePath());
        }
    }

    /**
     * 选择新创建工程存放目录
     * @param actionEvent
     */
    @FXML
    public void onSelectProjectSaveDirClicked(ActionEvent actionEvent){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");
        File directory = directoryChooser.showDialog(new Stage());
        if (directory != null) {
            String filePath = directory.getAbsolutePath();
            mLauncherProject.setRootDir(filePath);
            mProjectSaveDirLabel.setText(filePath);
        }
    }

    /**
     * 创建工程
     * @param actionEvent
     */
    @FXML
    public void onCreateProjectClicked(ActionEvent actionEvent){
        String projectName = mProjectNameTextField.getText();
        if(checkCreateProjectConditionPass(projectName)) {
            mLauncherProject.setName(projectName);
            try {
                File descFile = new File(mLauncherProject.getProjectDir() + File.separator + "temp");
                FileUtils.copyDirectory(new File(mLauncherProject.getTemplateDir()), descFile);
                descFile.renameTo(new File(mLauncherProject.getProjectFilePath()));
                DataMgr.getInstance().setLauncherProject(mLauncherProject);
                alert.close();
                alert(Alert.AlertType.INFORMATION,"恭喜","项目创建成功！");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    private boolean checkCreateProjectConditionPass(String projectName){
        String templateProjectDir = mLauncherProject.getTemplateDir();
        if(templateProjectDir==null || templateProjectDir.isEmpty()){
            alert(Alert.AlertType.WARNING,"注意","请选择模板工程");
            return false;
        }
        if(projectName==null || projectName.isEmpty()){
            alert(Alert.AlertType.WARNING,"注意","请输入项目名称");
            return false;
        }
        String projectDir = mLauncherProject.getProjectDir();
        if(projectDir==null || projectDir.isEmpty()){
            alert(Alert.AlertType.WARNING,"注意","请选择工程存放目录");
            return false;
        }
        if(new File(mLauncherProject.getProjectFilePath()).exists()){//工程目录已存在
            alert(Alert.AlertType.WARNING,"注意", mLauncherProject.getProjectFilePath()+"\n目录已存在");
            return false;
        }
        return true;
    }



    private void alert(Alert.AlertType type, String title, String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void bindDialog(Alert alert){
        this.alert = alert;
    }
}
