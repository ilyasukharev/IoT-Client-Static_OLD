package com.iot.controllers;

import com.iot.scenes.SceneChanger;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static com.iot.scenes.ScenesNames.*;

public class ContactController
{
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
    protected void serviceMainScene() throws Exception
    {
        new SceneChanger(MAIN_SERVICE).start(getThisStage());
    }

    @FXML
    protected void connectionScene() throws Exception
    {
        new SceneChanger(CONNECTION).start(getThisStage());
    }


}
