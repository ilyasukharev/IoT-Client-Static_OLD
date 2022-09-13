package Windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Connection implements StagesComponent{
        private Stage primaryStage;

        @Override
        public Stage getStage() throws Exception{
            primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/Connection.fxml"));
            primaryStage.setTitle("Connection");
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.setResizable(false);
            return primaryStage;
        }

}
