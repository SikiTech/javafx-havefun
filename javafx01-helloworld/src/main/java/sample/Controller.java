package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller {

    @FXML
    private JFXButton button;

    @FXML
    private JFXTextField textField;

    @FXML
    void changeInput(ActionEvent event) {
        String text = textField.getText();
        if (text.equals("hello")){
            textField.setText("world!");
        }else{
            textField.setText("");
        }
    }

}


