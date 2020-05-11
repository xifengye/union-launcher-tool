package com.zeasn.union.window;

import com.zeasn.union.db.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL location = getClass().getResource("main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        BorderPane root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.bindRootView(root);
        primaryStage.setTitle("大公版配置工具");
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        try {
            controller.init();
        }catch (Exception e){
            e.printStackTrace();
        }


    }



    public static void main(String[] args) {
        launch(args);
    }
}
