package com.zeasn.union.window;

import com.zeasn.union.window.img_selector.ActivedStateImage;
import com.zeasn.union.window.img_selector.FocusedStateImage;
import com.zeasn.union.window.img_selector.ImageSelector;
import com.zeasn.union.window.img_selector.NormalStateImage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class CreateImgSelectorPane implements IController{

    @FXML
    Label mDirLabel;
    @FXML
    ListView listView;
    @FXML
    TextField tfNormal;
    @FXML
    TextField tfFocused;
    @FXML
    TextField tfActived;
    String normalKey ;
    String focusedKey;
    String activedKey;

    private ObservableList<String> datalist = FXCollections.observableArrayList();
    Map<String,ImageSelector> imageSelectors;
    private String editingFileName;
    private Number lastSelectRowNumber;


    public void onSelectDirClicked(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");
        File directory = directoryChooser.showDialog(new Stage());
        if (directory != null) {
            mDirLabel.setText(directory.getAbsolutePath());
        }
        File rootDir = directory.getAbsoluteFile();
        imageSelectors = parse(rootDir);
        updateList(imageSelectors.keySet());
    }

    private Map<String,ImageSelector> parse(File rootDir){
        Map<String,ImageSelector> imageSelectorHashMap = new HashMap<>();
        if(rootDir.listFiles().length>0){
            File[] files = rootDir.listFiles();
            for(File f:files){
                if(f!=null && !f.isDirectory() && !f.getName().startsWith(".")) {
                    ImageSelector imageSelector = parse(f, files);
                    if (imageSelector != null) {
                        imageSelectorHashMap.put(imageSelector.getName(), imageSelector);
                    }
                }
            }
        }
        return imageSelectorHashMap;
    }

    private ImageSelector parse(File file,File[] sources){
        String fileName = file.getName();
        if(fileName.contains(normalKey)) {
            String selectorName = fileName.substring(0,fileName.lastIndexOf(normalKey))+"_selector";
            ImageSelector imageSelector = null;
            for (File f : sources) {
                if (f != null && !f.isDirectory() && !f.getName().startsWith(".")) {
                    String normalName = fileName;
                    String focusedName  = normalName.replace(normalKey, focusedKey);
                    if(f.getName().equals(focusedName)){
                        if(imageSelector==null){
                            imageSelector = new ImageSelector(selectorName);
                        }
                        imageSelector.addStateImage(new FocusedStateImage(f.getName()));
                        continue;
                    }
                    String activedName = normalName.replace(normalKey, activedKey);
                    if(f.getName().equals(activedName)){
                        if(imageSelector==null){
                            imageSelector = new ImageSelector(selectorName);
                        }
                        imageSelector.addStateImage(new ActivedStateImage(f.getName()));
                        continue;
                    }
                }
            }
            if(imageSelector!=null){
                imageSelector.addStateImage(new NormalStateImage(fileName));
                return imageSelector;
            }
        }
        return null;
    }

    @Override
    public void init() {
        normalKey = tfNormal.getText().trim();
        focusedKey = tfFocused.getText().trim();
        activedKey = tfActived.getText().trim();
    }

    private void updateList(Collection<String> images) {
        listView.setItems(datalist);
        for(String f:images){
            datalist.add(f);
        }
        listView.setCellFactory(TextFieldListCell.forListView());
        listView.setEditable(true);//设置列表可编辑
        listView.setOnEditStart(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> event) {
                editingFileName = event.getSource().getItems().get(event.getIndex());
                System.out.println("开始修改："+editingFileName);
            }
        });
        listView.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> event) {
                String newFielName = event.getNewValue();
                ImageSelector imageSelector = imageSelectors.remove(editingFileName);
                imageSelector.setName(newFielName);
                imageSelectors.put(imageSelector.getName(),imageSelector);
                datalist.set(event.getIndex(),newFielName);
                listView.refresh();
            }
        });
        listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lastSelectRowNumber = newValue;
            }
        });
    }

    public void onCreateSelected(ActionEvent actionEvent) {

    }

    public void onCreateAll(ActionEvent actionEvent) {
        String rootFilePath = mDirLabel.getText();
        for(ImageSelector imageSelector:imageSelectors.values()){
            imageSelector.create(rootFilePath);
        }
    }
}
