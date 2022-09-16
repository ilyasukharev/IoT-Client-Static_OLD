import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
public class Model
{
    private final static FileLogger log = new FileLogger();
    private Socket socket;

    private final String ipAddress;
    private final String port;


    public Model(String ipAddress, String port)
    {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    private class Service extends javafx.concurrent.Service<SocketWarnings>
    {
        @Override
        protected Task<SocketWarnings> createTask()
        {
            return new Task<>() {
                @Override
                protected SocketWarnings call(){
                    try {
                        socket = new Socket(ipAddress, Integer.parseInt(port));
                        if (socket.isConnected())
                        {
                            return SocketWarnings.SUCCESS;
                        } else
                        {
                            return SocketWarnings.NOCONNECTION;
                        }
                    } catch (UnknownHostException exception)
                    {
                        return SocketWarnings.IPADRESS;
                    } catch (IOException exception)
                    {
                        return SocketWarnings.IOEXCEPTION;
                    } catch (SecurityException exception)
                    {
                        return SocketWarnings.SECURITY;
                    } catch (IllegalArgumentException exception)
                    {
                        return SocketWarnings.PORT;
                    }
                }
            };
        }
    }

    public void socketConnect(ConnectionController controller)
    {
        if (ipAddress.equals("test") && port.equals("123")) changeStage(controller, SocketWarnings.SUCCESS);

        Service connectionService = new Service();

        connectionService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
        {
            @Override
            public void handle(WorkerStateEvent workerStateEvent)
            {
                if (workerStateEvent.getSource().getValue() != null) changeStage(controller, (SocketWarnings) workerStateEvent.getSource().getValue());
            }
        });
        connectionService.start();
    }

    private void changeStage(ConnectionController controller, SocketWarnings warning)
    {
        try
        {
            controller.handleServerConnection(warning, ipAddress, port);
        }catch(IOException e)
        {
            log.writeLogs(e.getMessage());
        }
    }
    private boolean checkConnection()
    {
        boolean stateOfConnection = false;
        try
        {
            BufferedWriter outServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));


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

        } catch(Exception e)
        {
            log.writeLogs(e.getMessage());
        }
        return stateOfConnection;
    }
    public String writeMessage(char message) throws Exception
    {
        if (!checkConnection()) {
            log.writeLogs("Connection reset by peer.Reconnecting.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Socket exception");
            alert.setContentText("You have no connection with server!");
            alert.showAndWait();

            return "NOCONNECTION";
        }
        BufferedWriter outServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        outServer.write(message);
        outServer.flush();

        char[] buff = new char[30];
        inServer.read(buff);
        StringBuffer arrivedStr = new StringBuffer();
        for (char sym : buff)
        {
            if (sym != 0)
            {
                arrivedStr.append(sym);
            }
        }
        return arrivedStr.toString();
    }

    public void closeSocket() throws Exception
    {
        if (socket.isConnected())
        {
            socket.close();
            log.writeLogs("Socket forcibly closed");
        }
    }
}