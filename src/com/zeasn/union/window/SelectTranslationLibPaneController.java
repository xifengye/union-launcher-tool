package com.zeasn.union.window;

import com.zeasn.union.translater.model.Lan;
import com.zeasn.union.translater.model.TranslateItem;
import com.zeasn.union.translater.model.TranslateItemCell;
import com.zeasn.union.translater.model.Translater;
import com.zeasn.union.translater.parser.ExcelReader;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SelectTranslationLibPaneController {

    private Alert alert;

    @FXML
    Label mTranslationLibLabel;
    @FXML
    Label mTranslationOutLabel;

    @FXML
    TableView mTableView;

    Translater mTranslater;


    @FXML
    public void onSelectTranslationOutFileClicked(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");
        File directory = directoryChooser.showDialog(new Stage());
        if (directory != null) {
            mTranslationOutLabel.setText(directory.getAbsolutePath());
        }

        try {
            genAndroidTranslate(mTranslater.getLanList(), mTranslater.getTranslateItemList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void genAndroidTranslate(Collection<Lan> lans, List<TranslateItem> translateItemList) throws IOException {
        for (Lan lan : lans) {
            StringBuilder translatedResult = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<resources>\n");
            for (TranslateItem translateItem : translateItemList) {
                if (translateItem.getKey() != null && !translateItem.getKey().isEmpty()) {
                    translatedResult.append(String.format("\t<string name=\"%s\">%s</string>", translateItem.getKey(), translateItem.getTranslateByLan(lan.name()))).append("\n");
                }
            }
            translatedResult.append("</resources>");
            String saveFileName = String.format("%s/strings_%s.xml", mTranslationOutLabel.getText(), lan.name());
            FileUtils.write(new File(saveFileName), translatedResult.toString());
        }
        alert(Alert.AlertType.INFORMATION, "恭喜", "翻译完成");

    }


    private void alert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void bindDialog(Alert alert) {
        this.alert = alert;
    }


    public void onSelectTranslationLibFileClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择翻译库");
        File file = fileChooser.showOpenDialog(new Stage());
        String path = file.getAbsolutePath();
        if (file != null) {
            mTranslationLibLabel.setText(path);
        }
        try {
//            path = getClass().getResource("/resources/translate.xlsx").toString().replace("file:", "");
            Translater translater = new ExcelReader().read(path);
            fillDataToTableView(translater);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillDataToTableView(Translater translater) {
        if (translater.getTranslateItemList() != null) {
            mTranslater = translater;
//            List<TranslateItemCell> translateItemCells = new ArrayList<>();
            List<TableColumn> tableColumns = new ArrayList<>();
            Callback<TableColumn.CellDataFeatures<TranslateItem, String>, ObservableValue> cellValueFactory = new Callback<TableColumn.CellDataFeatures<TranslateItem, String>, ObservableValue>() {
                @Override
                public ObservableValue call(TableColumn.CellDataFeatures<TranslateItem, String> param) {
                    String columnName = param.getTableColumn().getText();
                    if ("KEY".equalsIgnoreCase(columnName)) {
                        return new SimpleStringProperty(param.getValue().getKey());
                    } else {
                        return new SimpleStringProperty(param.getValue().getTranslateByLan(columnName));
                    }
                }
            };
            TableColumn keyTableColumn = new TableColumn("KEY");
            tableColumns.add(keyTableColumn);
            keyTableColumn.setCellValueFactory(cellValueFactory);
            for (Lan lan : translater.getLanList()) {
                TableColumn tableColumn = new TableColumn(lan.name());

                tableColumn.setCellValueFactory(cellValueFactory);
                tableColumns.add(tableColumn);
            }
            mTableView.getColumns().setAll(tableColumns);
//            for (TranslateItem item : translater.getTranslateItemList()) {
//                translateItemCells.add(new TranslateItemCell(item));
//            }
            final ObservableList<TranslateItem> data = FXCollections.observableArrayList(translater.getTranslateItemList());
            mTableView.setItems(data);
        }

    }
}
