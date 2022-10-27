package com.iot.model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import static com.iot.model.SocketExceptions.*;

public class Model
{
    private String ipAddress;
    private int port;
    private Socket socket;
    private SocketExceptions status;
    
    private class ConnectionService extends Service<SocketExceptions>
    {
        @Override
        protected Task<SocketExceptions> createTask()
        {
            return new Task<>()
            {
                @Override
                protected SocketExceptions call()
                {
                    try
                    {
                        socket = new Socket(ipAddress, port);

                        if (socket.isConnected())   return SUCCESS;
                        else                        return LOST_CONNECTION;
                    } catch (UnknownHostException e)
                    {
                        return IP_ADDRESS;
                    } catch (IOException e)
                    {
                        return IO;
                    } catch (IllegalArgumentException e)
                    {
                        return PORT;
                    } catch (SecurityException e)
                    {
                        return SECURITY;
                    }

                }
            };
        }
    }

    public void connect()
    {
        status = null;
        if (socket != null && socket.isConnected()) {
            try
            {
                socket.close();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        else socket = null;

        ConnectionService service = new ConnectionService();
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>()
        {
            @Override
            public void handle(WorkerStateEvent workerStateEvent)
            {
                if (workerStateEvent.getSource().getValue() != null)
                {
                    setStatus((SocketExceptions) workerStateEvent.getSource().getValue());
                }
            }
        });
        service.start();
    }

    public String sendMessage(char message)
    {
        if (socket == null || !socket.isConnected())
        {
            return "CLOSED";
        }

        try
        {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.write(message);
            out.flush();


            char[] buff = new char[30];
            in.read(buff);

            StringBuilder build = new StringBuilder();
            for (char sym : buff)
            {
                if (sym == 0)   break;
                else            build.append(sym);
            }

            return build.toString();

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public synchronized SocketExceptions getStatus()
    {
        return status;
    }
    private synchronized void setStatus (SocketExceptions status)
    {
        this.status = status;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public Socket getSocket()
    {
        return socket;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public int getPort()
    {
        return port;
    }
}
