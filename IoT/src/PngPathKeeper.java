import javafx.scene.image.Image;

public class PngPathKeeper {
    private static final String pathToMotorUpOn = "resources/motorUT.png";
    private static final String pathToMotorUpOff = "resources/motorUF.png";

    private static final String pathToMotorDownOn = "resources/motorDT.png";
    private static final String pathToMotorDownOff = "resources/motorDF.png";

    private static final String pathToLedOn = "resources/ledT.png";
    private static final String pathToLedOff = "resources/ledF.png";

    private static final String pathToAutoModeOn = "resources/autoT.png";
    private static final String pathToAutoModeOff = "resources/autoF.png";

    private Image img;

    public Image getMotorUpPath(boolean enabled)
    {
        if (enabled)        img = new Image(pathToMotorUpOn);
        else                img = new Image(pathToMotorUpOff);

        return img;
    }

    public Image getMotorDownPath(boolean enabled)
    {
        if (enabled)        img = new Image(pathToMotorDownOn);
        else                img = new Image(pathToMotorDownOff);

        return img;
    }

    public Image getLedPath(boolean enabled)
    {
        if (enabled)        img = new Image(pathToLedOn);
        else                img = new Image(pathToLedOff);

        return img;
    }

    public Image getAutoPath(boolean enabled)
    {
        if (enabled)        img = new Image(pathToAutoModeOn);
        else                img = new Image(pathToAutoModeOff);

        return img;
    }

}
