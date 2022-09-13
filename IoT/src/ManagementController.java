import Windows.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagementController implements Initializable{
    @FXML private ImageView motorUp;
    @FXML private ImageView motorDown;
    @FXML private ImageView autoMode;
    @FXML private ImageView led;
    @FXML private TextArea helpLabel;
    @FXML private Button closeHelpLblBtn;


    private final ModelData modelData = ModelData.getInstance();

    private Model model;

    private PngPathKeeper pngPathKeeper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = modelData.getModel();
        pngPathKeeper = new PngPathKeeper();
    }

    @FXML
    public void motorUpClicked() throws Exception{
        String state = model.writeMessage('u');
        if (state.equals("MOTORUPOFF")){
            motorUp.setImage(pngPathKeeper.getMotorUpPath(false));
            motorDown.setDisable(false);
        }
        else if (state.equals("MOTORUPON")){
            motorUp.setImage(pngPathKeeper.getMotorUpPath(true));
            motorDown.setDisable(true);
        }
        else if (state.equals("NOCONNECTION")){
            reconnect();
        }
        new FileLogger().writeLogs("Got and wrote state of IoT Element [Motor Up]");
    }
    @FXML
    public void motorDownClicked() throws Exception{
        String state = model.writeMessage('d');
        if (state.equals("MOTORDOWNOFF")){
            motorDown.setImage(pngPathKeeper.getMotorDownPath(false));
            motorUp.setDisable(false);
        }
        else if (state.equals("MOTORDOWNON")){
            motorDown.setImage(pngPathKeeper.getMotorDownPath(true));
            motorUp.setDisable(true);
        }
        else if (state.equals("NOCONNECTION")){
            reconnect();
        }
        new FileLogger().writeLogs("Got and wrote state of IoT Element [Motor Down]");
    }
    @FXML
    public void ledClicked() throws Exception{
        String state = model.writeMessage('l');
        if (state.equals("LEDOFF")){
            led.setImage(pngPathKeeper.getLedPath(false));
        }
        else if (state.equals("LEDON")){
            led.setImage(pngPathKeeper.getLedPath(true));
        }
        else if (state.equals("NOCONNECTION")){
            reconnect();
        }
        new FileLogger().writeLogs("Got and wrote state of IoT Element [LED]");
    }
    @FXML
    public void autoModeClicked() throws Exception{
        String state = model.writeMessage('a');
        if (state.equals("AUTOMODEON")){
            autoMode.setImage(pngPathKeeper.getAutoPath(true));

            motorUp.setDisable(true);
            motorUp.setImage(pngPathKeeper.getMotorUpPath(false));

            motorDown.setDisable(true);
            motorDown.setImage(pngPathKeeper.getMotorDownPath(false));

            led.setDisable(true);
            led.setImage(pngPathKeeper.getLedPath(false));
        }
        else if (state.equals("AUTOMODEOFF")){
            autoMode.setImage(pngPathKeeper.getAutoPath(false));
            motorUp.setDisable(false);
            motorDown.setDisable(false);
            led.setDisable(false);
        }
        else if (state.equals("NOCONNECTION")){
            reconnect();
        }
        new FileLogger().writeLogs("Got and wrote state of IoT Element [Auto Mode]");
    }

    @FXML
    public void clearCacheData(){
            try{
                String filePath = new FileLogger().getName();
                if (filePath != null){
                    File file = new File(filePath);
                    file.setWritable(true);
                    new BufferedWriter(new FileWriter(file)).write("");
                    file.setReadOnly();
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
                new FileLogger().writeLogs("'Cache Data' file successfully cleared");
            }
    }

    @FXML
    public void reconnect(){
        try{
            model.closeSocket();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        Stage stage = (Stage) motorUp.getScene().getWindow();
        stage.close();
        try{
            Platform.runLater( () -> {
                try {
                    new Main().start( new Stage() );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }catch(Exception e){
            System.out.println(e.getMessage());
            new FileLogger().writeLogs(e.getMessage());
        }
    }


    @FXML
    public void quitApp(){
        try{
            model.closeSocket();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        Stage stage = (Stage)motorUp.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void openHelpLabel(){
        helpLabel.setOpacity(1.0);
        helpLabel.setDisable(false);
        closeHelpLblBtn.setOpacity(1.0);
    }
    @FXML
    public void closeHelpLabel(){
        helpLabel.setOpacity(0.0);
        helpLabel.setDisable(true);
        closeHelpLblBtn.setOpacity(0.0);
    }

}
