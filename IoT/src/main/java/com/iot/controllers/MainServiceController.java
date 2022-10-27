package com.iot.controllers;

import com.iot.model.DataKeeper;
import com.iot.model.Model;
import com.iot.model.ServiceData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;


import static com.iot.controllers.MainServiceController.ButtonsStyle.OFF;
import static com.iot.controllers.MainServiceController.ButtonsStyle.ON;


public class MainServiceController extends Service
{
    enum ButtonsStyle
    {
        ON
                {
                    @Override
                    public String toString()
                    {
                        return "-fx-background-color: #560262; -fx-text-fill: #b352bf; -fx-background-radius: 15.0;";
                    }

                },
        OFF
                {
                    @Override
                    public String toString()
                    {
                        return "-fx-background-color: #560262; -fx-text-fill: #FFFAFA; -fx-background-radius: 15.0;";
                    }

                }
    }

    @FXML
    private Button serviceMenuBtn;
    @FXML
    private ToggleButton moveUpBtn;
    @FXML private ToggleButton moveDownBtn;
    @FXML private ToggleButton autoModeBtn;


    @FXML
    protected void initialize()
    {
        super.setStageBtn(serviceMenuBtn);

        ServiceData data = DataKeeper.getServiceInstance();

        if (data.getMoveUpState())
        {
            moveUpBtn.setStyle(ON.toString());
            setMoveUpState(true);
        }
        if (data.getMoveDownState())
        {
            moveDownBtn.setStyle(ON.toString());
            setMoveDownState(true);
        }
        if (data.getAutoModeState())
        {
            autoModeBtn.setStyle(ON.toString());
            setAutoModeState(true);
        }
    }

    @FXML
    protected void moveUpAction()
    {
        if (DataKeeper.getInstance().getSocket() == null || !DataKeeper.getInstance().getSocket().isConnected())    getSocketException();
        else if (getMoveDownState() || getAutoModeState())                                                          getButtonsClickedException();
        else
        {
            Model model = DataKeeper.getInstance();
            String result = model.sendMessage('u');

            if (result.equals("MOTORUPON"))
            {
                moveUpBtn.setStyle(ON.toString());
                setMoveUpState(true);
            }
            else if (result.equals("MOTORUPOFF"))
            {
                moveUpBtn.setStyle(OFF.toString());
                setMoveUpState(false);
            }
            else  getSocketException();
        }
    }
    @FXML
    protected void moveDownAction()
    {
        if (DataKeeper.getInstance().getSocket() == null || !DataKeeper.getInstance().getSocket().isConnected())    getSocketException();
        else if (getMoveUpState() || getAutoModeState())                                                            getButtonsClickedException();
        else
        {
            Model model = DataKeeper.getInstance();
            String result = model.sendMessage('d');

            if (result.equals("MOTORDOWNON"))
            {
                moveDownBtn.setStyle(ON.toString());
                setMoveDownState(true);
            }
            else if (result.equals("MOTORDOWNOFF"))
            {
                moveDownBtn.setStyle(OFF.toString());
                setMoveDownState(false);
            }
            else getSocketException();
        }
    }
    @FXML
    protected void autoModeAction()
    {
        if (DataKeeper.getInstance().getSocket() == null || !DataKeeper.getInstance().getSocket().isConnected())    getSocketException();
        else if (getMoveUpState() || getMoveDownState())                                                            getButtonsClickedException();
        else
        {
            Model model = DataKeeper.getInstance();
            String result = model.sendMessage('a');


            if (result.equals("AUTOMODEON"))
            {
                autoModeBtn.setStyle(ON.toString());
                setAutoModeState(true);
            }
            else if (result.equals("AUTOMODEOFF"))
            {
                autoModeBtn.setStyle(OFF.toString());
                setAutoModeState(false);
            }
            else getSocketException();

        }
    }
}
