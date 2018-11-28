package Controllers;

import Banco.BDCore;
import Models.Usuarios;
import Util.Valida;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateFuncionario implements Initializable {



    @FXML
    JFXTextField edNome, edSenha,edCpf,edLogin;

    @FXML
    JFXRadioButton rCli,rOrca, rRel, rAdmin;

    @FXML
    StackPane pane;

    @FXML
    Label vNome, vLogin, vSenha, vCpf, vOpt;

    BDCore bd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bd = new BDCore();

        rAdmin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(rAdmin.isSelected()){
                    rCli.setDisable(true);
                    rOrca.setDisable(true);
                    rRel.setDisable(true);
                }else{
                    rCli.setDisable(false);
                    rOrca.setDisable(false);
                    rRel.setDisable(false);
                }
            }
        });
    }


    public void handleClose(MouseEvent mouseEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/Dash_admin.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void createBt(MouseEvent mouseEvent){





        String nome, login, senha, cpf;
        int cli,orca, rel, admin;

        Usuarios user = new Usuarios();

        nome = edNome.getText();
        login = edLogin.getText();

        senha = edSenha.getText();

        if(!nome.equals("") && !login.equals("") && !edCpf.getText().equals("") && Valida.isCPF(edCpf.getText()) && verfOps() && !senha.equals("")) {

            //cpf = Integer.parseInt(edCpf.getText());

            System.out.println("cpf = " + Valida.getFloat(edCpf.getText()));

            cpf = edCpf.getText();

            if (rCli.isSelected()) {
                cli = 1;
            } else {
                cli = 0;
            }

            if (rOrca.isSelected()) {
                orca = 1;
            } else {
                orca = 0;
            }

            if (rRel.isSelected()) {
                rel = 1;
            } else {
                rel = 0;
            }

            if (rAdmin.isSelected()) {
                admin = 1;
            } else {
                admin = 0;
            }

            //admin = 1;

            user.setNome(nome);
            user.setLogin(login);
            user.setSenha(senha);
            user.setCPF(cpf);

            user.setVerfAdmin(admin);
            user.setVerfCli(cli);
            user.setVerfOrc(orca);
            user.setVerfRelatorio(rel);


            if (bd.createUsuario(user)) {


                JFXDialogLayout content = new JFXDialogLayout();

                content.setHeading(new Text("Sucesso"));
                content.setBody(new Text("Funcionario Cadastrado com sucesso"));

                JFXDialog dialog = new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER);

                JFXButton btokay = new JFXButton("Okay");
                btokay.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        try {
                            Parent root = null;
                            root = FXMLLoader.load(getClass().getResource("../View/Dash_admin.fxml"));

                            Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();

                            stage.setScene(new Scene(root));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

                content.setActions(btokay);

                dialog.show();


            }


        }else{

            if(!verfOps()){
                vOpt.setVisible(true);
            }else{
                vOpt.setVisible(false);

            }

            // && !login.equals("") && !edCpf.getText().equals("") && Valida.isCPF(edCpf.getText())

            if(login.equals("")){
                vLogin.setVisible(true);
            }else {
                vLogin.setVisible(false);
            }

            if(edCpf.getText().equals("") || !Valida.isCPF(edCpf.getText())){
                vCpf.setVisible(true);
            }else {
                vCpf.setVisible(false);
            }


            if (senha.equals("")){
                vSenha.setVisible(true);
            }else{
                vSenha.setVisible(false);
            }

            if(nome.equals("")){
                vNome.setVisible(true);
            }else{
                vNome.setVisible(false);
            }


        }

    }


    public boolean verfOps(){
        if(rCli.isSelected() || rOrca.isSelected() ||  rRel.isSelected() || rAdmin.isSelected()){
            return true;
        }else{
            return false;
        }
    }

}
