import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Model{
    private Socket socket;

    private String ipAddress;
    private String port;

    private BufferedReader inServer;
    private BufferedWriter outServer;

    public Model(String ipAddress, String port){
        this.ipAddress = ipAddress;
        this.port = port;
    }
    public SocketWarnings socketConnect(){
        try {
            socket = new Socket(ipAddress, Integer.parseInt(port));
            if (socket.isConnected()) {
                return SocketWarnings.SUCCESS;
            } else {
                return SocketWarnings.NOCONNECTION;
            }
        } catch (UnknownHostException exception) {
            return SocketWarnings.IPADRESS;
        } catch (IOException exception) {
            return SocketWarnings.IOEXCEPTION;
        } catch (SecurityException exception){
            return SocketWarnings.SECURITY;
        } catch (IllegalArgumentException exception) {
            return SocketWarnings.PORT;
        }
    }
    private boolean checkConnection(){
        boolean stateOfConnection = false;
        try{
            outServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            outServer.write('1');
            outServer.flush();

            char[] buff = new char[1];
            inServer.read(buff);

            String inputStr = "";
            for (char sym : buff){
                if (sym != 0){
                    inputStr += sym;
                }
            }
            if (inputStr.equals("0")){
                stateOfConnection = true;
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
            new FileLogger().writeLogs(e.getMessage());
        }
        return stateOfConnection;
    }
    public String writeMessage(char message) throws Exception{
        if (!checkConnection()) {
            new FileLogger().writeLogs("Connection reset by peer.Reconnecting.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Socket exception");
            alert.setContentText("You have no connection with server!");
            alert.showAndWait();

            return "NOCONNECTION";
        }
        outServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        outServer.write(message);
        outServer.flush();

        char[] buff = new char[30];
        inServer.read(buff);
        String arrivedStr = "";
        for (char sym : buff){
            if (sym != 0){
                arrivedStr += sym;
            }
        }
        return arrivedStr;
    }

    public void closeSocket() throws Exception{
        if (socket.isConnected()){
            socket.close();
            new FileLogger().writeLogs("Socket forcibly closed");
        }
    }
}

class ModelData{
    private final static ModelData instance = new ModelData();
    private Model model;
    private ModelData(){}
    public static ModelData getInstance(){
        return instance;
    }
    public void setModel(Model model){ this.model = model; }
    public Model getModel(){ return model; }

}