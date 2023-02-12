package com.iot.controllers;
import com.iot.HttpClient.HttpClient;
import com.iot.HttpClient.PostData;
import com.iot.Main;
import com.iot.scenes.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.iot.scenes.ScenesNames.*;

public class ConnectionController
{
    @FXML private Button connectMenuBtn;
    @FXML
    protected Button buttonRegister;
    @FXML protected Button buttonSendCodeVerify;
    @FXML
    protected TextField emailRegistration;
    @FXML
    protected TextField telepfoneRegistration;
    @FXML
    protected TextField passwordRegistration;
    @FXML
    protected TextField codeEmail;
    @FXML
    protected Text emailStatus;
    @FXML
    protected Text telepfoneStatus;
    @FXML
    protected Text passwordStatus;


    enum ButtonsStyle
    {
        ON
                {
                    @Override
                    public String toString()
                    {
                        return "-fx-background-color: #b352bf; -fx-text-fill: #560262;";
                    }
                },
        OFF
                {
                    @Override
                    public String toString()
                    {
                        return "-fx-background-color: #b352bf; -fx-text-fill: #ffffff;";
                    }
                }
    }
    private final PostData email=new PostData();
    private final PostData password=new PostData();
    private final PostData number=new PostData();
    private final PostData code=new PostData();
    private static final Logger logger=Logger.getLogger(Main.class.getName());
    @FXML
    protected void sendPostEmailCode() throws IOException {

        email.setEmail(emailRegistration.getText());
        JSONObject obj0 = new JSONObject();
        obj0.put("email", email);
        new HttpClient().makePostRequest(obj0,"code/send");
        logger.log(Level.INFO,"postEmail"+" "+email);
    }
    @FXML
    protected void sendPostEmailCodeVerify() throws IOException {
        email.setEmail(emailRegistration.getText());
        code.setCode(Integer.parseInt(codeEmail.getText()));
        JSONObject obj = new JSONObject();
        obj.put("email", email);
        obj.put("code", code);
        new HttpClient().makePostRequest(obj,"code/verify");
        logger.log(Level.INFO,"postEmail"+" "+email+" "+code);
    }
    private Stage getThisStage()
    {
        return (Stage) buttonRegister.getScene().getWindow();
    }
    @FXML
    protected void registration() throws Exception {
        String LoginRegister = emailRegistration.getText();
        boolean Login = true;
        String NumberRegister = telepfoneRegistration.getText();
        boolean Number = true;
        String PasswordRegister = passwordRegistration.getText();
        boolean Password = true;
        email.setEmail(LoginRegister);
        number.setNumber(NumberRegister);
        password.setPassword(PasswordRegister);
        JSONObject obj1 = new JSONObject();
        obj1.put("number", number);
        obj1.put("email", email);
        obj1.put("password", password);
        new HttpClient().makePostRequest(obj1,"account/register");
        logger.log(Level.INFO,"postRegister"+" "
                +number+" "+email+" "+password);
        homeScene();
    }
    @FXML
    public void inputScene() throws Exception {
        new SceneChanger(INPUT).start(getThisStage());
        logger.log(Level.INFO,"nextStr>"+"INPUT");
    }
    @FXML
    protected void homeScene() throws Exception
    {
        new SceneChanger(MAIN).start(getThisStage());
        logger.log(Level.INFO,"nextStr>"+"MAIN");
    }
    @FXML
    protected void serviceMainScene() throws Exception
    {
        new SceneChanger(SERVICE).start(getThisStage());
        logger.log(Level.INFO,"nextStr>"+"SERVICE");
    }
    @FXML
    protected void contactScene() throws Exception
    {
        new SceneChanger(CONTACT).start(getThisStage());
        logger.log(Level.INFO,"nextStr>"+"CONTACT");
    }
}