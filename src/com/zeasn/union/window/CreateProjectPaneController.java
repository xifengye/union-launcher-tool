package com.zeasn.union.window;

import com.zeasn.union.data.DataMgr;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
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
            DataMgr.getInstance().setmTemplateProjectFilePath(directory.getAbsolutePath());
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
            DataMgr.getInstance().setProjectDir(filePath);
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
            DataMgr.getInstance().setProjectName(projectName);
            try {
                File descFile = new File(DataMgr.getInstance().getProjectDir() + File.separator + "temp");
                FileUtils.copyDirectory(new File(DataMgr.getInstance().getmTemplateProjectFilePath()), descFile);
                descFile.renameTo(new File(DataMgr.getInstance().getProjectFilePath()));
                alert.close();
                alert(Alert.AlertType.INFORMATION,"恭喜","项目创建成功！");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    private boolean checkCreateProjectConditionPass(String projectName){
        String templateProjectDir = DataMgr.getInstance().getmTemplateProjectFilePath();
        if(templateProjectDir==null || templateProjectDir.isEmpty()){
            alert(Alert.AlertType.WARNING,"注意","请选择模板工程");
            return false;
        }
        if(projectName==null || projectName.isEmpty()){
            alert(Alert.AlertType.WARNING,"注意","请输入项目名称");
            return false;
        }
        String projectDir = DataMgr.getInstance().getProjectDir();
        if(projectDir==null || projectDir.isEmpty()){
            alert(Alert.AlertType.WARNING,"注意","请选择工程存放目录");
            return false;
        }
        if(new File(DataMgr.getInstance().getProjectFilePath()).exists()){//工程目录已存在
            alert(Alert.AlertType.WARNING,"注意",DataMgr.getInstance().getProjectFilePath()+"\n目录已存在");
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
