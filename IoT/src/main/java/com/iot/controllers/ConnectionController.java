package com.iot.controllers;
import com.iot.HttpClient.HttpClient;
import com.iot.scenes.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import java.io.IOException;

import static com.iot.scenes.ScenesNames.*;

public class ConnectionController
{
    @FXML
    private Button RegisterBtn;

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
    protected void sendPostEmailCode(){
    }
    @FXML
    protected void sendPostEmailCodeVerify(){
    }
    protected Stage getThisStage()
    {
        return (Stage) RegisterBtn.getScene().getWindow();
    }
    @FXML
    protected void register() throws Exception {
        homeScene();
    }
    @FXML
    protected void inputScene() throws Exception {
        new SceneChanger(AUTHORIZATION).start(getThisStage());
    }
    @FXML
    protected void homeScene() throws Exception
    {
        new SceneChanger(MAIN).start(getThisStage());
    }
    @FXML
    protected void serviceMainScene() throws Exception
    {
        new SceneChanger(SERVICE).start(getThisStage());
    }
    @FXML
    protected void contactScene() throws Exception
    {
        new SceneChanger(CONTACT).start(getThisStage());
    }
}