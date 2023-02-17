package com.iot.controllers;
import com.iot.scenes.SceneChanger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.iot.scenes.ScenesNames.*;
import static com.iot.scenes.ScenesNames.CONTACT;
public class InputController {
    @FXML private Button homeMenuBtn;
    @FXML private Button sendCodeResetPasswordBtn;
    @FXML private Button codeVerifyBtn;
    @FXML private TextField codeResetPass;
    private Stage getThisStage()
    {
        return (Stage) homeMenuBtn.getScene().getWindow();
    }
    private void sleepTimer(Button btn){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(()->{
            btn.setOpacity(1.0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            btn.setOpacity(0.5);
        }).start();
    }
    @FXML
    protected void homeScene() throws Exception
    {
        new SceneChanger(MAIN).start(getThisStage());
    }
    @FXML
    protected void confirmPasswordScene() throws Exception
    {
        new SceneChanger(CONFIRM_PASSWORD).start(getThisStage());
    }
    @FXML
    protected void serviceMainScene() throws Exception
    {
        new SceneChanger(MAIN_SERVICE).start(getThisStage());
    }
    @FXML
    protected void sendCodeToEmail() {
        sleepTimer(sendCodeResetPasswordBtn);
        codeResetPass.setOpacity(1.0);
        codeVerifyBtn.setOpacity(1.0);
        codeResetPass.setEditable(true);
        codeVerifyBtn.setDisable(false);

    }
    @FXML
    protected void codeVerify() throws Exception {
        confirmPasswordScene();
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
        new SceneChanger(MAIN_AUTHORIZATION).start(getThisStage());
    }
    @FXML
    protected void passwordReset() throws Exception
    {
        new SceneChanger(RESET_PASSWORD).start(getThisStage());
    }
    @FXML
    protected void registerClicked() throws Exception
    {
        new SceneChanger(CONNECTION).start(getThisStage());
    }
}