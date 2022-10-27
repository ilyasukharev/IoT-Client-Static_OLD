package com.iot.controllers;

import com.iot.model.DataKeeper;
import com.iot.model.ServiceData;
import com.iot.scenes.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static com.iot.scenes.ScenesNames.*;

public class Service
{
    private Button stageBtn;
    private boolean moveUpState;
    private boolean moveDownState;
    private boolean autoModeState;



    protected void getButtonsClickedException()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Buttons active error");
        alert.setContentText("There`re buttons already working. Stop it!");
        alert.show();
    }
    protected void getSocketException()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText("Socket isn`t connected or it`s equals null");
        alert.show();

        try
        {
            connectionScene();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private void saveButtonsState()
    {
        ServiceData data = DataKeeper.getServiceInstance();

        data.setMoveUpState(moveUpState);
        data.setMoveDownState(moveDownState);
        data.setAutoModeState(autoModeState);
    }

    private Stage getThisStage()
    {
        return (Stage) stageBtn.getScene().getWindow();
    }

    @FXML
    protected void homeScene() throws Exception
    {
        saveButtonsState();
        new SceneChanger(MAIN).start(getThisStage());
    }

    @FXML
    protected void connectionScene() throws Exception
    {
        saveButtonsState();
        new SceneChanger(CONNECTION).start(getThisStage());
    }

    @FXML
    protected void contactScene() throws Exception
    {
        saveButtonsState();
        new SceneChanger(CONTACT).start(getThisStage());
    }


    @FXML
    protected void changeToAdvancedService() throws Exception
    {
        saveButtonsState();
        new SceneChanger(ADVANCED_SERVICE).start(getThisStage());
    }

    @FXML
    protected void changeToMainService() throws Exception
    {
        new SceneChanger(MAIN_SERVICE).start(getThisStage());
    }

    protected void setMoveUpState(boolean moveUpState)
    {
        this.moveUpState = moveUpState;
    }
    protected void setMoveDownState(boolean moveDownState)
    {
        this.moveDownState = moveDownState;
    }
    protected void setAutoModeState(boolean autoModeState)
    {
        this.autoModeState = autoModeState;
    }
    protected void setStageBtn(Button btn)
    {
        this.stageBtn = btn;
    }
    protected boolean getMoveUpState()
    {
        return moveUpState;
    }
    protected boolean getMoveDownState()
    {
        return moveDownState;
    }
    protected boolean getAutoModeState()
    {
        return autoModeState;
    }
}
