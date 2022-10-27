package com.iot.controllers;

import com.iot.model.DataKeeper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import static com.iot.controllers.AdvancedServiceController.ButtonsStyle.OFF;
import static com.iot.controllers.AdvancedServiceController.ButtonsStyle.ON;

public class AdvancedServiceController extends Service
{
    enum ButtonsStyle
    {
        ON
        {
            @Override
            public String toString()
            {
                return "-fx-background-color: #560262; -fx-border-color: #560262; -fx-border-width: 3.0; -fx-background-radius: 14.0; -fx-border-radius: 14.0;";
            }
        },
        OFF
        {
            @Override
            public String toString()
            {
                return "-fx-background-color: #b352bf; -fx-border-color: #560262; -fx-border-width: 3.0; -fx-background-radius: 14.0; -fx-border-radius: 14.0;";
            }
        }
    }
    @FXML
    private Button serviceMenuBtn;

    @FXML private Label innerSensorField;
    @FXML private Label externalSensorField;
    @FXML private Label hollSensorField;
    @FXML private Button innerSensorBtn;
    @FXML private Button externalSensorBtn;
    @FXML private Button hollBtn;

    @FXML
    protected void initialize()
    {
        super.setStageBtn(serviceMenuBtn);
    }
    @FXML protected void innerSensorBtnClicked()
    {
        if (DataKeeper.getInstance().getSocket() == null || !DataKeeper.getInstance().getSocket().isConnected()) getSocketException();
        else
        {
            String result = DataKeeper.getInstance().sendMessage('i');
            innerSensorField.setText(updateResult(result));
        }
    }
    @FXML protected void externalSensorBtnClicked()
    {
        if (DataKeeper.getInstance().getSocket() == null || !DataKeeper.getInstance().getSocket().isConnected()) getSocketException();
        else
        {
            String result = DataKeeper.getInstance().sendMessage('e');
            externalSensorField.setText(updateResult(result));
        }
    }
    @FXML protected void hollBtnClicked()
    {
        if (DataKeeper.getInstance().getSocket() == null || !DataKeeper.getInstance().getSocket().isConnected()) getSocketException();
        else
        {
            String result = DataKeeper.getInstance().sendMessage('h');
            hollSensorField.setText(result);
        }
    }

    @FXML protected void innerBtnMouseIs()
    {
        innerSensorBtn.setStyle(ON.toString());
    }
    @FXML protected void innerBtnMouseExited()
    {
        innerSensorBtn.setStyle(OFF.toString());
    }

    @FXML protected void externalBtnMouseIs() {externalSensorBtn.setStyle(ON.toString());}

    @FXML protected void externalBtnMouseExited() {externalSensorBtn.setStyle(OFF.toString());}

    @FXML protected void autoModeBtnMouseIs() {hollBtn.setStyle(ON.toString());}
    @FXML protected void autoModeBtnMouseExited() {hollBtn.setStyle(OFF.toString());}

    private String updateResult(String str)
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++)
        {
            char sym = str.charAt(i);
            if (Character.isDigit(sym)) result.append(sym);
        }
        return result.toString();
    }

}
