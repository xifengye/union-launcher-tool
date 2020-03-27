package com.zeasn.union.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL location = getClass().getResource("sample.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 600, 400);
        createMenu(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createMenu(Parent root){
        MenuBar menuBar = new MenuBar();

        Menu baseMenu = new Menu("Base");

        Menu homeActivityMenu = new Menu("HomeActivity");

        Menu detailActivityMenu = new Menu("DetailActivity");

        Menu searchActivityMenu = new Menu("SearchActivity");

        menuBar.getMenus().addAll(baseMenu, homeActivityMenu, detailActivityMenu);
//        root.getChildren().addAll(menuBar);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
