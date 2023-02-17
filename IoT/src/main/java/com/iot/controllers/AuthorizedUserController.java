package com.iot.controllers;
import com.iot.scenes.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import static com.iot.scenes.ScenesNames.*;

public class AuthorizedUserController{
    @FXML private Button contactMenuBtn;
    @FXML private Button serviceMenuBtn;
    private Stage getThisStage() {
        return (Stage) contactMenuBtn.getScene().getWindow();
    }
    @FXML
    protected void homeScene1() throws Exception {
        new SceneChanger(MAIN_AUTHORIZATION).start(getThisStage());
    }
    @FXML
    protected void serviceMainScene1() throws Exception
    {
        new SceneChanger(MAIN_SERVICE).start(getThisStage());
    }
}
