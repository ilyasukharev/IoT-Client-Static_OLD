import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;
    @FXML
    private Button connectBtn;
    @FXML
    private CheckBox rememberBox;
    private final static FileLogger log = new FileLogger();

    private File dataFile;
    private final static ModelData modelData = ModelData.getInstance();

    @FXML
    public void connectToServer(javafx.event.ActionEvent actionEvent) throws IOException
    {
        log.writeLogs("Trying to connect the server");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        connectBtn.setDisable(true);

        String ip = ipField.getText();
        String port = portField.getText();

        modelData.setModel(new Model(ip, port));


        SocketWarnings warning = modelData.getModel().socketConnect();

        switch (warning) {
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
                break;
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

                log.writeLogs("Successfully conected to: " + ip + ":" + port);
            }
            default ->
            {
                log.writeLogs("Unknown state");
            }
        }


        alert.setTitle("Socket connection");
        alert.setContentText("The data of connection: IP - Address - " + ip + "Port - " + port);
        alert.showAndWait();
        connectBtn.setDisable(false);
    }

    @FXML
    public void rememberOn()
    {
        dataFile = new File("cacheData");
        dataFile.setWritable(true);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile));
            if (rememberBox.isSelected()) {
                String ip = "IP:" + ipField.getText();
                String port = "PORT:" + portField.getText();
                writer.write(ip + "\n");
                writer.write(port);
            } else {
                writer.write("");
            }
            writer.close();
        } catch (IOException exep) {
            System.out.println(exep.getMessage());
        }
        dataFile.setReadOnly();
        log.writeLogs("IP-address and port saved to 'cacheData'");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        dataFile = new File("cacheData");

        try{
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
            System.out.println(exception.getMessage());
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