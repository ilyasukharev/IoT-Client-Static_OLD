package com.iot.controllers;
import com.iot.scenes.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

import static com.iot.scenes.ScenesNames.*;

public class ConnectionController {
    @FXML private Button sendCodeBtn;
    @FXML private Button homeMenuBtn;
    @FXML private Button sendCodeVerifyBtn;
    @FXML private TextField telepfoneRegistration;
    @FXML private TextField codeEmail;
    @FXML private TextField passwordRegistration;


    enum ButtonsStyle {
        ON {
            @Override
            public String toString() {
                return "-fx-background-color: #b352bf; -fx-text-fill: #560262;";
            }
        },
        OFF {
            @Override
            public String toString() {
                return "-fx-background-color: #b352bf; -fx-text-fill: #ffffff;";
            }
        }
    }

    @FXML
    protected void sendPostEmailCode() {
        sleepTimer(sendCodeBtn);
        codeEmail.setOpacity(1.0);
        codeEmail.setEditable(true);
        sendCodeVerifyBtn.setDisable(false);
    }

    @FXML
    protected void sendPostEmailCodeVerify() {
        sleepTimer(sendCodeVerifyBtn);
        telepfoneRegistration.setOpacity(1.0);
        passwordRegistration.setOpacity(1.0);
        telepfoneRegistration.setEditable(true);
        passwordRegistration.setEditable(true);
    }

    private Stage getThisStage() {
        return (Stage) homeMenuBtn.getScene().getWindow();
    }

    private void sleepTimer(Button btn) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
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
    protected void register() throws Exception {
        homeScene();
    }

    @FXML
    protected void inputScene() throws Exception {
        new SceneChanger(AUTHORIZATION).start(getThisStage());
    }

    @FXML
    protected void homeScene() throws Exception {
        new SceneChanger(MAIN).start(getThisStage());
    }
}