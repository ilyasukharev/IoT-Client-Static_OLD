package Windows;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);
        new Connection().getStage().show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
