package com.iot.controllers;
import com.iot.HttpClient.HttpClient;
import com.iot.HttpClient.PostData;
import com.iot.Main;
import com.iot.scenes.SceneChanger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.iot.scenes.ScenesNames.*;
import static com.iot.scenes.ScenesNames.CONTACT;
public class InputController {
    @FXML protected Button register;
    @FXML protected  Button OkCodeResetPass;
    @FXML
    protected TextField emailText;
    @FXML
    protected TextField emailResetPassword;
    @FXML
    protected TextField codeResetPass;
    @FXML
    protected TextField passwordText;
    @FXML
    protected TextField passText;
    @FXML
    protected TextField confirmPassText;
    @FXML
    protected Text emailInfo;
    @FXML
    protected Text info;
    @FXML
    protected Text passwordInfo;
    private final PostData email=new PostData();
    private final PostData password=new PostData();
    private final PostData newPass=new PostData();
    private final PostData code=new PostData();
    private static final Logger logger=Logger.getLogger(Main.class.getName());
    private Stage getThisStage()
    {
        return (Stage) register.getScene().getWindow();
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
        new SceneChanger(MAIN_SERVICE).start(getThisStage());
        logger.log(Level.INFO,"nextStr>"+"MAIN_SERVICE");
    }
    @FXML
    protected void sendCodeToEmail(){
        if(!emailResetPassword.getText().equals("")){
            email.setEmail(emailResetPassword.getText());
            JSONObject obj = new JSONObject();
            obj.put("email", email);
            try {
                new HttpClient().makePostRequest(obj, "code/send");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            logger.log(Level.INFO, "send code");
        }else{
            info.setText("error email");
        }
    }
    @FXML
    protected void okCodeVerify() throws IOException {
        if (!codeResetPass.getText().equals("")) {
            JSONObject obj = new JSONObject();
            obj.put("email", email);
            obj.put("code", code);
            new HttpClient().makePostRequest(obj, "code/verify");
        } else {
            info.setText("wrong code");
        }
    }
    @FXML
    protected void newPass() throws Exception
    {
        if(((passText.getText().equals(confirmPassText.getText()))&&
                (!passText.getText().equals(""))&&(!confirmPassText.getText().equals("")))){
            newPass.setPassword(confirmPassText.getText());
            JSONObject obj = new JSONObject();
            obj.put("newPass", newPass);
            new HttpClient().makePostRequest(obj,"change/password");
            Stage stage = (Stage) OkCodeResetPass.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(SceneChanger.class.getResource("Main.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(scene);
            stage.show();
            logger.log(Level.INFO, "nextStr>" + "MAIN");
        }else{
            info.setText("error");
        }
    }
    @FXML
    protected void contactScene() throws Exception
    {
        new SceneChanger(CONTACT).start(getThisStage());
        logger.log(Level.INFO,"nextStr>"+"CONTACT");
    }
    @FXML
    protected void inputClicked() throws Exception
    {
        if(!emailText.getText().equals("")&&(!passwordText.getText().equals(""))){
        email.setEmail(emailText.getText());
        password.setPassword(passwordText.getText());
        passwordInfo.setText("OK");
        emailInfo.setText("OK");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        new HttpClient().makePostRequest(jsonObject, "acсount/login");
        new SceneChanger(MAIN_SERVICE).start(getThisStage());
        logger.log(Level.INFO, "nextStr>" + "MAIN_SERVICE");
    }else{
        passwordInfo.setText("error");
    }
    }
    @FXML
    protected void foPassReset() throws Exception
    {
        new SceneChanger(RESET).start(getThisStage());
        logger.log(Level.INFO,"nextStr>"+"RESET");
    }
    @FXML
    protected void registerClicked() throws Exception
    {
        new SceneChanger(CONNECTION).start(getThisStage());
        logger.log(Level.INFO,"nextStr>"+"CONNECTION");
    }
}
