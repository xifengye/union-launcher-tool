package com.zeasn.union.window;

import com.zeasn.union.data.ConfigNode;
import com.zeasn.union.data.DataMgr;
import com.zeasn.union.data.LauncherProject;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;


public class Controller {

    private BorderPane mBorderPane;


    /**
     * 打开工程菜单键
     * @param actionEvent
     */
    public void onOpenProjectMenuItemClicked(ActionEvent actionEvent) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择工程路径");
        File directory = directoryChooser.showDialog(new Stage());
        if (directory != null) {
            String absolutePath = directory.getAbsolutePath();
            int index = absolutePath.lastIndexOf(File.separator);
            String rootDir = absolutePath.substring(0,index-1);
            String projectName = absolutePath.substring(index+1);
            LauncherProject project = new LauncherProject();
            project.setName(projectName);
            project.setRootDir(rootDir);
            DataMgr.getInstance().setLauncherProject(project);
            onReadyProject();
        }
    }

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
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE){
            onReadyProject();
        }
    }

    public void bindRootView(BorderPane borderPane){
        this.mBorderPane = borderPane;
    }

    private void createTreeView(LauncherProject launcherProject){
        // TreeItem名字和图标
        Node rootIcon = new ImageView(new Image("resources/icon/ic_project.png"));
        TreeItem<String> rootItem = new TreeItem<> (launcherProject.getName(), rootIcon);
        rootItem.setExpanded(true);
        launcherProject.initTreeNodes();
        List<ConfigNode> children =launcherProject.getChildrenConfigNode();
        addChildrenTreeItem(rootItem,children);
        TreeView<String> tree = new TreeView<> (rootItem);
        mBorderPane.setLeft(tree);
    }

    private void addChildrenTreeItem(TreeItem<String> root,List<ConfigNode> childrenConfigNode){
        if(childrenConfigNode !=null && childrenConfigNode.size()>0){
            for (int i = 0; i < childrenConfigNode.size(); i++) {
                ConfigNode configNode = childrenConfigNode.get(i);
                TreeItem<String> item = new TreeItem<> (configNode.getName());
                addChildrenTreeItem(item,configNode.getChildren());
                root.getChildren().add(item);
            }
        }

    }

    private void onReadyProject(){
        LauncherProject launcherProject = DataMgr.getInstance().getProject();
        if(launcherProject !=null){
            createTreeView(launcherProject);
        }
    }

}
