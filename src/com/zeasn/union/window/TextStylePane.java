package com.zeasn.union.window;

import com.zeasn.union.data.TextStyle;
import com.zeasn.union.db.DatabaseConnection;
import com.zeasn.union.widget.ColorTextField;
import com.zeasn.union.widget.IntegerTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.List;
import java.util.Optional;

public class TextStylePane implements IController {
    @FXML
    Button addBtn;
    @FXML
    ListView listView;

    class MyCell extends ListCell<TextStyle>{
        @Override
        public void updateItem(TextStyle item, boolean empty){
            super.updateItem(item, empty);

            if(!empty && item != null){
                BorderPane cell = new BorderPane();

                Text title = new Text(item.toString());
                if(item.getTypeFace() == TextStyle.TypeFace.Normal) {
                    title.setFont(Font.font(null, item.getTextSize()));
                }else {
                    if (item.getTypeFace() == TextStyle.TypeFace.Bold) {
                        title.setFont(Font.font(null, FontWeight.BOLD, item.getTextSize()));
                    }
                    if (item.getTypeFace() == TextStyle.TypeFace.Italic) {
                        title.setFont(Font.font(null, FontPosture.ITALIC, item.getTextSize()));
                    }
                }
                title.setFill(Color.web(item.getTextColor()));
                cell.setLeft(title);

                setGraphic(cell);
            }
        }
    }

    @Override
    public void init() {
        listView.setCellFactory(new Callback<ListView<TextStyle>, ListCell<TextStyle>>()
                                {
                                    @Override
                                    public ListCell<TextStyle> call(ListView<TextStyle> list)
                                    {
                                        return new MyCell();
                                    }
                                }
        );
        updateList();
    }






    class Cell extends ListCell<TextStyle> {
        @Override
        protected void updateItem(TextStyle item, boolean empty) {
            super.updateItem(item, empty);
           setText(item.getName());
        }
    }


    private void updateList(){
        List<TextStyle> textStyleList = DatabaseConnection.getInstance().getTextStyles();
        ObservableList<TextStyle> textStyles = FXCollections.observableList(textStyleList);
        listView.setItems(textStyles);
        listView.refresh();

    }

    public void onAddClicked(ActionEvent event){

        Dialog<TextStyle> dialog = new Dialog<>();
        dialog.setTitle("Dialog Test");
        dialog.setHeaderText("Please specify…");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField nameField = new TextField("主题名称");
        TextField textSizeField = new IntegerTextField();
        TextField textColorField = new ColorTextField();
        TextField letterSpaceField = new IntegerTextField();
        TextField lineSpaceField = new IntegerTextField();
        ObservableList<TextStyle.TypeFace> options =
                FXCollections.observableArrayList(TextStyle.TypeFace.values());
        ComboBox<TextStyle.TypeFace> comboBox = new ComboBox<>(options);
        comboBox.getSelectionModel().selectFirst();
        GridPane gridPane = new GridPane();
        gridPane.addRow(1,new Label("主题名称"),nameField);
        gridPane.addRow(2,new Label("字体大小"),textSizeField);
        gridPane.addRow(3,new Label("字体颜色"),textColorField);
        gridPane.addRow(4,new Label("字间距"),letterSpaceField);
        gridPane.addRow(5,new Label("行间距"),lineSpaceField);
        gridPane.addRow(6,new Label("字体风格"),comboBox);
        dialogPane.setContent(gridPane);
        Platform.runLater(nameField::requestFocus);
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new TextStyle(nameField.getText(),
                        comboBox.getValue(),Integer.valueOf(textSizeField.getText()),textColorField.getText(), Integer.valueOf(letterSpaceField.getText()),Integer.valueOf(lineSpaceField.getText()));
            }
            return null;
        });
        Optional<TextStyle> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((TextStyle results) -> {
            System.out.println(results.toString());
            boolean success = DatabaseConnection.getInstance().insertTextStyle(results);
            if(success) {
                updateList();
            }
        });
    }

}
