package com.zeasn.union.window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectTranslationPaneController {

    private Alert alert;

    @FXML
    Label mWaitTranslationLabel;
    @FXML
    TextField mResultFileNameTextField;
    @FXML
    Label mTranslatedFileLabel;

    @FXML
    public void onSelectWaitTranslationFileClicked(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择待翻译文件");
        File file = fileChooser.showOpenDialog(new Stage());
        if(file!=null){
            mWaitTranslationLabel.setText(file.getAbsolutePath());
        }
    }

    @FXML
    public void onSelectTranslatedClicked(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择已翻译文件");
        File file = fileChooser.showOpenDialog(new Stage());
        if(file!=null){
            mTranslatedFileLabel.setText(file.getAbsolutePath());
        }
    }
    @FXML
    public void onTranslationClicked(ActionEvent actionEvent){
        StringBuilder sb = new StringBuilder(mWaitTranslationLabel.getText());
        sb.insert(mWaitTranslationLabel.getText().lastIndexOf("."),"_translated");
        String saveFileName = sb.toString();
        StringBuilder translatedResult = new StringBuilder("<resources>\n");

        try {
            List<String> waitTranslation = FileUtils.readLines(new File(mWaitTranslationLabel.getText()));
            int waitTranslationSize = 0;
            int translatedSize = 0;
            List<String> translatedList = FileUtils.readLines(new File(mTranslatedFileLabel.getText()));
            Map<String,String> map = new HashMap<>();
            for(String line:translatedList){
                String key = getStringValueKey(line);
                if(key!=null){
                    map.put(key,line);
                }
            }
            translatedList.clear();
            translatedList = null;

            if(waitTranslation!=null && waitTranslation.size()>0){
                for(String line:waitTranslation){
                    String key = getStringValueKey(line);
                    if(key!=null){
                        waitTranslationSize++;
                        if(map.containsKey(key)) {
                            translatedSize++;
                            String translateLie = map.get(key);
                            translatedResult.append(translateLie).append("\n");
                        }
                    }
                }
                translatedResult.append("</resources>");
            }
            map.clear();
            map = null;
            waitTranslation.clear();
            waitTranslation = null;
            FileUtils.write(new File(saveFileName),translatedResult.toString());
            if(translatedSize == waitTranslationSize){
                alert(Alert.AlertType.INFORMATION,"恭喜",translatedSize+"行翻译完成");
            }else{
                alert(Alert.AlertType.WARNING,"对不起",translatedSize+"行翻译完成，"+(waitTranslationSize-translatedSize)+"行未找到");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void alert(Alert.AlertType type, String title, String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(content);
        alert.showAndWait();
    }


    private String getStringValueKey(String line){
        try {
            int startIndex = line.indexOf("name=") + 5;
            int endIndex = line.indexOf("\">");
            String key = line.substring(startIndex, endIndex);
            return key;
        }catch (Exception e){

        }
        return null;
    }

    public void bindDialog(Alert alert){
        this.alert = alert;
    }


}
