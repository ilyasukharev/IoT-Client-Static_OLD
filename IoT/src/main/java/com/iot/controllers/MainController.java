package com.iot.controllers;

import com.iot.model.DataKeeper;
import com.iot.scenes.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.Socket;

import static com.iot.controllers.MainController.ButtonsStyle.OFF;
import static com.iot.controllers.MainController.ButtonsStyle.ON;
import static com.iot.scenes.ScenesNames.*;

public class MainController
{
    enum ButtonsStyle
    {
        ON
                {
                    @Override
                    public String toString()
                    {
                        return "-fx-background-color: #b352bf; -fx-text-fill: #560262;";
                    }
                },
        OFF
                {
                    @Override
                    public String toString()
                    {
                        return "-fx-background-color: #b352bf; -fx-text-fill: #ffffff;";
                    }
                }
    }

    @FXML private Button homeMenuBtn;
    @FXML private Button connectBtn;

    @FXML
    protected void initialize()
    {
        Socket socket = DataKeeper.getInstance().getSocket();
        if (socket != null)
        {
            if (socket.isConnected())       connectBtn.setText("RECONNECT");
            else                            connectBtn.setText("CONNECT");
        }
        else connectBtn.setText("CONNECT");

    }

    private Stage getThisStage()
    {
        return (Stage) homeMenuBtn.getScene().getWindow();
    }
    @FXML
    protected void connectionScene() throws Exception
    {
        new SceneChanger(CONNECTION).start(getThisStage());
    }

    @FXML
    protected void serviceMainScene() throws Exception
    {
        new SceneChanger(MAIN_SERVICE).start(getThisStage());
    }

    @FXML
    protected void contactScene() throws Exception
    {
        new SceneChanger(CONTACT).start(getThisStage());
    }

    @FXML
    protected void connectBtnPushed() throws Exception
    {
        connectionScene();
    }

    @FXML
    protected void movedConnectBtn()
    {
        connectBtn.setStyle(ON.toString());
    }

    @FXML
    protected void exitedConnectBtn()
    {
        connectBtn.setStyle(OFF.toString());
    }

}

