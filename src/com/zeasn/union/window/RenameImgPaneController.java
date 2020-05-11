package com.zeasn.union.window;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;

public class RenameImgPaneController implements IController{

    private Alert alert;
    private File[] mipmapFiles;
    private File[] imageFiles;
    private String editingFileName;

    @FXML
    Label mDirLabel;
    @FXML
    ListView listView;

    private ObservableList<String> datalist = FXCollections.observableArrayList();


    class MyCell extends ListCell<File> {
        @Override
        public void updateItem(File item, boolean empty){
            super.updateItem(item, empty);

            if(!empty && item != null){
                BorderPane cell = new BorderPane();
                Text title = new Text(item.getName());

                cell.setLeft(title);

                setGraphic(cell);
            }
        }
    }

    @Override
    public void init() {

    }

    private void updateList(File[] images) {
        this.imageFiles = images;
        listView.setItems(datalist);
        for(File f:images){
            datalist.add(f.getName());
        }
        listView.setCellFactory(TextFieldListCell.forListView());
        listView.setEditable(true);//设置列表可编辑
        listView.setOnEditStart(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> event) {
                editingFileName = imageFiles[event.getIndex()].getName();
            }
        });
        listView.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> event) {
                String newFielName = event.getNewValue();
                String  editingFileExtends = getExtends(editingFileName);
                if(!newFielName.endsWith(editingFileExtends)){
                    newFielName+=editingFileExtends;
                }
                for(File file:mipmapFiles){
                    if(file!=null && file.getName().endsWith("dpi")) {
                        System.out.println("file="+file);
                        for (File f : file.listFiles()) {
                            if (f!=null && editingFileName.equals(f.getName())) {
                                String fn = f.getAbsolutePath().replace(editingFileName, newFielName);
                                f.renameTo(new File(fn));
                            }
                        }
                    }
                }
                datalist.set(event.getIndex(),newFielName);
                listView.refresh();
            }
        });
    }

    private String getExtends(String file){
        return file.substring(file.lastIndexOf("."));
    }

    public void onSelectDirClicked(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");
        File directory = directoryChooser.showDialog(new Stage());
        if (directory != null) {
            mDirLabel.setText(directory.getAbsolutePath());
        }
        File rootDir = directory.getAbsoluteFile();
        if(rootDir.listFiles().length>0){
            mipmapFiles = rootDir.listFiles();
            File f = mipmapFiles[0];
            if(f.getName().startsWith("mipmap") && f.isDirectory()){
                File[] imageFiles = f.listFiles();
                updateList(imageFiles);
            }
        }
    }

    public void bindDialog(Alert alert){
        this.alert = alert;
    }

}
