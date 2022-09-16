import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;


public class ConnectionController implements Initializable {

    @FXML private TextField ipField;
    @FXML private TextField portField;
    @FXML private Button connectBtn;
    @FXML private CheckBox rememberBox;
    @FXML private Circle circle;
    private final static FileLogger log = new FileLogger();
    private final static ModelData modelData = ModelData.getInstance();
    private Positions positions;

    private enum Positions
    {
        IP,
        PORT,
        REMEMBER,
        CONNECT
    }

    @FXML
    public void connectToServer()
    {
        log.writeLogs("Trying to connect the server");

        connectBtn.setDisable(true);
        rememberData(false);

        String ip = ipField.getText();
        String port = portField.getText();

        modelData.setModel(new Model(ip, port));
        modelData.getModel().socketConnect(this);
    }

    public void handleServerConnection(SocketWarnings warning, String ip, String port) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        switch (warning)
        {
            case IOEXCEPTION ->
            {
                alert.setHeaderText("I/O Exceptions!");
                log.writeLogs("Connection to server failed. I/O Exceptions");
            }
            case IPADRESS ->
            {
                alert.setHeaderText("IP - address incorrect!");
                log.writeLogs("Connection to server failed. IP address is incorrect");
            }
            case PORT ->
            {
                alert.setHeaderText("Port incorrect!");
                log.writeLogs("Connection to server failed. Port is incorrect");
            }
            case NOCONNECTION ->
            {
                alert.setHeaderText("No internet connection!");
                log.writeLogs("Connection to server failed. No internet connection");
            }
            case SECURITY ->
            {
                alert.setHeaderText("Security exception!");
                log.writeLogs("Connection to server failed. Security exception");
            }
            case SUCCESS ->
            {
                alert.setHeaderText("Successfully connected!");
                Stage stage = (Stage) connectBtn.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("Managment.fxml"));
                stage.setTitle("Management");
                Scene scene = new Scene(root, 1280, 720);
                stage.setScene(scene);
                stage.setResizable(false);

                log.writeLogs("Successfully connected to: " + ip + ":" + port);
            }
            default -> log.writeLogs("Unknown state");
        }


        alert.setTitle("Socket connection");
        alert.setContentText("The data of connection: IP - Address - " + ip + "Port - " + port);
        alert.showAndWait();
        connectBtn.setDisable(false);
    }


    private void rememberData(boolean clearAll){

        try
        {
            File file = new File("Cache.txt");
            file.setWritable(true);

            BufferedWriter out = new BufferedWriter(new FileWriter(file));

            if (!clearAll)
            {
                String ip = "IP:" + ipField.getText();
                String port = "PORT:" + portField.getText();
                out.write(ip + "\n" + port);
            }
            else  out.write("");

            out.close();
            file.setReadOnly();

        }catch (IOException e)
        {
            log.writeLogs(e.getMessage());
        }

    }
    @FXML
    public void rememberOn()
    {
        rememberData(!rememberBox.isSelected());
        log.writeLogs("IP-address and port successfully saved to 'cacheData'");
    }

    @FXML
    public void ipMouseMoved()
    {
        if (!positions.equals(Positions.IP))
        {
            circleMovingHandler(-5.0);
            positions = Positions.IP;
        }
    }

    @FXML
    public void commonMouseExited()
    {
        circle.setFill(Color.BLACK);
        connectBtn.setStyle("-fx-text-fill: #f5f5f5; -fx-background-radius: 15; -fx-background-color: gray");
    }

    @FXML
    public void ipAndPortTextChanged()
    {
        circle.setFill(Color.WHITE);
    }

    @FXML
    public void rememberBoxMouseMoved()
    {
        if (!positions.equals(Positions.REMEMBER))
        {
            circleMovingHandler(175.0);
            positions = Positions.REMEMBER;
        }
    }

    @FXML
    public void portMouseMoved()
    {
        if (!positions.equals(Positions.PORT))
        {
            circleMovingHandler(110.0);
            positions = Positions.PORT;
        }
    }

    @FXML
    public void connectBtnMouseMoved()
    {
        if (!positions.equals(Positions.CONNECT))
        {
            circleMovingHandler(270.0);
            positions = Positions.CONNECT;
        }
        connectBtn.setStyle("-fx-text-fill: #78150c; -fx-background-radius: 15; -fx-background-color: gray;");
    }

    private void circleMovingHandler(double y)
    {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(circle);
        transition.setDuration(Duration.millis(1500));
        transition.setFromY(circle.getCenterY() + y);
        transition.setByY(10.5);
        transition.setAutoReverse(true);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        circleMovingHandler(-5.0);
        positions = Positions.IP;

        File dataFile = new File("Cache.txt");

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            for (Object objLine : reader.lines().toArray())
            {
                String line = (String) objLine;
                if      (line.startsWith("IP"))          setIP(line);
                else if (line.startsWith("PORT"))        setPORT(line);
                else                                     throw new RuntimeException("Incorrect or unknown file");
            }
            reader.close();

        }catch(IOException exception)
        {
            log.writeLogs(exception.getMessage());
        }
    }

    private void setIP(String str)
    {
        ipField.setText(str.split(":")[1]);
    }
    private void setPORT(String str)
    {
        portField.setText(str.split(":")[1]);
    }
}