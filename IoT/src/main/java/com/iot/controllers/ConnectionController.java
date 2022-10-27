package com.iot.controllers;

import com.iot.model.DataKeeper;
import com.iot.model.Model;
import com.iot.model.SocketExceptions;
import com.iot.scenes.SceneChanger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import static com.iot.controllers.ConnectionController.ButtonsStyle.OFF;
import static com.iot.controllers.ConnectionController.ButtonsStyle.ON;
import static com.iot.model.SocketExceptions.SUCCESS;
import static com.iot.scenes.ScenesNames.*;

public class ConnectionController
{
    @FXML private Button connectMenuBtn;
    @FXML private Button homeMenuBtn;
    @FXML private Button contactMenuBtn;
    @FXML private Button serviceMenuBtn;
    @FXML private Button connectBtn;
    @FXML private TextField ipAddressField;
    @FXML private TextField portField;
    @FXML private ImageView loadingGifPng;

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

    @FXML
    public void initialize()
    {

        Model model = DataKeeper.getInstance();
        if (model.getSocket() != null)
        {
            if (model.getSocket().isConnected())
            {
                ipAddressField.setText(model.getIpAddress());
                portField.setText(String.valueOf(model.getPort()));
            }
        }
    }

    @FXML protected void connectBtnClicked()
    {
        Model model = DataKeeper.getInstance();
        String ipAddress = ipAddressField.getText();
        String port = portField.getText();

        if (!checkAvailable(ipAddressField.getText()) || !checkAvailable(portField.getText()))   return ;

        model.setIpAddress(ipAddress);
        model.setPort(Integer.parseInt(port));
        model.connect();
        connectBtn.setDisable(true);

        new ConnectionStatusHandler().start();
    }

    private boolean checkAvailable(String labelContent)
    {
        for (char c : labelContent.toCharArray())
        {
            if (Character.isLetter(c))  return false;
        }
        return true;
    }

    private class ConnectionStatusHandler extends Thread
    {
        @Override
        public void run()
        {
            Model model = DataKeeper.getInstance();
            SocketExceptions status = null;
            switchOffAllMenuBtn(true);

            while(status == null)
            {
                status = model.getStatus();
                loadingGifPng.setOpacity(100.0);
            }

            connectBtn.setDisable(false);
            loadingGifPng.setOpacity(0.0);
            switchOffAllMenuBtn(false);

            SocketExceptions finalStatus = status;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    if (finalStatus.equals(SUCCESS))
                    {
                        try {
                            serviceMainScene();
                        } catch (Exception e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                    else    showAlertWindow(finalStatus);
                }
            });
        }
    }

    private void showAlertWindow(SocketExceptions status)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setResizable(false);
        alert.setHeaderText(status.name());
        alert.setContentText(status.toString());
        alert.show();
    }

    private void switchOffAllMenuBtn(boolean status)
    {
        homeMenuBtn.setDisable(status);
        contactMenuBtn.setDisable(status);
        serviceMenuBtn.setDisable(status);
    }
    private Stage getThisStage()
    {
        return (Stage) connectMenuBtn.getScene().getWindow();
    }
    @FXML
    protected void homeScene() throws Exception
    {
        new SceneChanger(MAIN).start(getThisStage());
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
    protected void ipAddressInput()
    {
        if (!checkAvailable(ipAddressField.getText()))
        {
            ipAddressField.setText("no letters");
        }
    }

    @FXML
    protected void portInput()
    {
        if (!checkAvailable(portField.getText()))
        {
            portField.setText("no letters");
        }
    }

    @FXML
    protected void clearIpAddressField()
    {
        ipAddressField.setText("");
    }

    @FXML
    protected void clearPortField()
    {
        portField.setText("");
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