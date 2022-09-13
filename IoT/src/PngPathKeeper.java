import javafx.scene.image.Image;

public class PngPathKeeper {
    private String pathToMotorUpOn = "resources/motorUT.png";
    private String pathToMotorUpOff = "resources/motorUF.png";

    private String pathToMotorDownOn = "resources/motorDT.png";
    private String pathToMotorDownOff = "resources/motorDF.png";

    private String pathToLedOn = "resources/ledT.png";
    private String pathToLedOff = "resources/ledF.png";

    private String pathToAutoModeOn = "resources/autoT.png";
    private String pathToAutoModeOff = "resources/autoF.png";

    private Image img;

    public Image getMotorUpPath(boolean enabled){
        if (enabled) img = new Image(pathToMotorUpOn);
        else img = new Image(pathToMotorUpOff);
        return img;
    }

    public Image getMotorDownPath(boolean enabled){
        if (enabled) img = new Image(pathToMotorDownOn);
        else img = new Image(pathToMotorDownOff);
        return img;
    }

    public Image getLedPath(boolean enabled){
        if (enabled) img = new Image(pathToLedOn);
        else img = new Image(pathToLedOff);
        return img;
    }

    public Image getAutoPath(boolean enabled){
        if (enabled) img = new Image(pathToAutoModeOn);
        else img = new Image(pathToAutoModeOff);
        return img;
    }

}
