package com.zeasn.union.window;

import com.zeasn.union.data.ConfigNode;
import com.zeasn.union.data.DataMgr;
import com.zeasn.union.data.LauncherProject;
import com.zeasn.union.data.WindowUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class Controller {

    private BorderPane mBorderPane;
    private TreeView<ConfigNode> treeView;
    private EventHandler<MouseEvent> treeItemEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Node node = event.getPickResult().getIntersectedNode();
            ConfigNode configNode = treeView.getSelectionModel().getSelectedItem().getValue();
            onConfigNodeClicked(configNode);
        }
    };

    private void onConfigNodeClicked(ConfigNode configNode){
        System.out.println("configNode="+configNode);
        Label centerLabel = new Label(configNode.getName());
        centerLabel.setWrapText(true);
        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(Color.color(Math.random(),Math.random(),Math.random()),null,null)));
        pane.setPrefSize(500,500);
        pane.getChildren().add(centerLabel);
        mBorderPane.setCenter(pane);
    }


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
        FXMLLoader fxmlLoader = WindowUtils.createLoader(getClass(),"create_project_pane.fxml");
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
        TreeItem<ConfigNode> rootItem = new TreeItem<> (launcherProject, rootIcon);
        rootItem.setExpanded(true);
        launcherProject.initTreeNodes();
        List<ConfigNode> children =launcherProject.getChildrenConfigNode();
        addChildrenTreeItem(rootItem,children);
        treeView = new TreeView<> (rootItem);
        treeView.addEventFilter(MouseEvent.MOUSE_CLICKED, treeItemEventHandler);
        mBorderPane.setLeft(treeView);
    }

    private void addChildrenTreeItem(TreeItem<ConfigNode> root,List<ConfigNode> childrenConfigNode){
        if(childrenConfigNode !=null && childrenConfigNode.size()>0){
            for (int i = 0; i < childrenConfigNode.size(); i++) {
                ConfigNode configNode = childrenConfigNode.get(i);
                TreeItem<ConfigNode> item = new TreeItem<> (configNode);
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
