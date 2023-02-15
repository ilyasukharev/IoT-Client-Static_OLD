package com.iot.controllers;
import com.iot.HttpClient.HttpClient;
import com.iot.scenes.SceneChanger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;

import static com.iot.scenes.ScenesNames.*;
import static com.iot.scenes.ScenesNames.CONTACT;
public class InputController {
    @FXML private Button contactMenuBtn;
    private Stage getThisStage()
    {
        return (Stage) contactMenuBtn.getScene().getWindow();
    }
    @FXML
    protected void homeScene() throws Exception
    {
        new SceneChanger(MAIN).start(getThisStage());
    }
    @FXML
    protected void confirmScene() throws Exception
    {
        new SceneChanger(CONFIRM_PASSWORD).start(getThisStage());
    }
    @FXML
    protected void serviceMainScene() throws Exception
    {
        new SceneChanger(MAIN_SERVICE).start(getThisStage());
    }
    @FXML
    protected void sendCodeToEmail(){
    }
    @FXML
    protected void codeVerify() throws Exception {
            confirmScene();
    }
    @FXML
    protected void changePassword() throws Exception
    {
        homeScene();
    }
    @FXML
    protected void contactScene() throws Exception
    {
        new SceneChanger(CONTACT).start(getThisStage());
    }
    @FXML
    protected void inputClicked() throws Exception
    {
        new SceneChanger(MAIN).start(getThisStage());
    }
    @FXML
    protected void passBtnReset() throws Exception
    {
        new SceneChanger(RESET_PASSWORD).start(getThisStage());
    }
    @FXML
    protected void registerBtnClicked() throws Exception
    {
        new SceneChanger(CONNECTION).start(getThisStage());
    }
}
