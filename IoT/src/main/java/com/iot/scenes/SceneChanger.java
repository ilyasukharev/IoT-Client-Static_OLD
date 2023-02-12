package com.iot.scenes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SceneChanger extends Application
{
    private final ScenesNames name;

    public SceneChanger (ScenesNames name)
    {
        if (name == null) throw new RuntimeException("Scene name is null");
        this.name = name;
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = null;
        switch (name)
        {
            case MAIN ->                loader = new FXMLLoader(SceneChanger.class.getResource("Main.fxml"));
            case SERVICE ->             loader = new FXMLLoader(SceneChanger.class.getResource("Service.fxml"));
            case CONNECTION ->          loader = new FXMLLoader(SceneChanger.class.getResource("Connection.fxml"));
            case CONTACT ->             loader = new FXMLLoader(SceneChanger.class.getResource("Contact.fxml"));
            case MAIN_SERVICE ->        loader = new FXMLLoader(SceneChanger.class.getResource("Service-Main.fxml"));
            case ADVANCED_SERVICE ->    loader = new FXMLLoader(SceneChanger.class.getResource("Service-Advanced.fxml"));
            case INPUT ->               loader = new FXMLLoader(SceneChanger.class.getResource("Input.fxml"));
            case RESET ->               loader = new FXMLLoader(SceneChanger.class.getResource("Reset.fxml"));
        }

        assert(loader != null);

        Scene scene = new Scene(loader.load(), 1280, 720);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("IotApp");
        stage.show();
    }

}
