package com.zeasn.union.data;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;

public class WindowUtils {

    public static <T> T create(Class c, String fxmlFileName) throws IOException {
        FXMLLoader loader = createLoader(c,fxmlFileName);
        return loader.load();
    }

    public static FXMLLoader createLoader(Class c, String fxmlFileName){
        URL location = c.getResource("create_project_pane.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        return fxmlLoader;
    }

}
