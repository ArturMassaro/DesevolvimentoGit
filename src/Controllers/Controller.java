package Controllers;

import Models.Usuarios;
import Util.Valida;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Banco.BDCore;
import sample.Main;

public class Controller implements Initializable {

    @FXML
    private ImageView img;



    @FXML
    private JFXTextField edLogin;

    @FXML
    private JFXPasswordField edSenha;

    @FXML
    private JFXButton btLogin;

    BDCore bd;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //edSenha.requestFocus()
        bd = new BDCore();

        System.out.println(" int = " + Valida.isInteiro("35,3"));

    }
    public void handleClose(javafx.scene.input.MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void teclaPress(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            edSenha.requestFocus();
            //btLogin.requestFocus();
        }
    }


    public void handleLogin(MouseEvent mouseEvent) throws IOException {


        Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();

        login(stage);

    }

    public void TeclaLogin(KeyEvent keyEvent) throws IOException {
        if(keyEvent.getCode() == KeyCode.ENTER){


            Stage stage = (Stage) ((javafx.scene.Node) keyEvent.getSource()).getScene().getWindow();
            login(stage);
        }
    }

    public void login(Stage stage) throws IOException {

        String login,senha;

        login = edLogin.getText();
        senha = edSenha.getText();

        Usuarios u = bd.verifUser(login,senha);

        if(u != null) {


            System.out.println("verAdmin = " + u.getVerfAdmin());
            if(u.getVerfAdmin() == 1) {

                System.out.println("Entrando como Admin");
                bd.getUsers();


                //Parent root = FXMLLoader.load(getClass().getResource("../View/Dash_admin.fxml"));


                //stage.setResizable(true);
                //stage.setMaximized(true);
                //stage.initStyle(StageStyle.DECORATED);
                //stage.setFullScreen(true);

                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();



                FXMLLoader load = new FXMLLoader(getClass().getResource("../View/Dash_admin.fxml"));

                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());


                DashAdmin cntl = new DashAdmin();


                //load.setController(cntl);

                //controller.setLogin(login);

                stage.setScene(new Scene(load.load()));
            }else{

                System.out.println("entrando como usuario");
                bd.getUsers();


                //Parent root = FXMLLoader.load(getClass().getResource("../View/Dash_admin.fxml"));


                //stage.setResizable(true);
                //stage.setMaximized(true);
                //stage.initStyle(StageStyle.DECORATED);
                //stage.setFullScreen(true);

                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();



                FXMLLoader load = new FXMLLoader(getClass().getResource("../View/Dash_user.fxml"));

                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());




                //load.setController(cntl);

                //controller.setLogin(login);

                stage.setScene(new Scene(load.load()));
            }
        }
    }
}
