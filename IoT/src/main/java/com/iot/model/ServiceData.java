package com.iot.model;

public class ServiceData
{
    private boolean moveUpState;
    private boolean moveDownState;
    private boolean autoModeState;


    public boolean getMoveUpState()
    {
        return moveUpState;
    }
    public boolean getMoveDownState()
    {
        return moveDownState;
    }
    public boolean getAutoModeState()
    {
        return autoModeState;
    }

    public void setMoveDownState(boolean moveDownState) {
        this.moveDownState = moveDownState;
    }

    public void setAutoModeState(boolean autoModeState) {
        this.autoModeState = autoModeState;
    }

    public void setMoveUpState(boolean moveUpState) {
        this.moveUpState = moveUpState;
    }
}
