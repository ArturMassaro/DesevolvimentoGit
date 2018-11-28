package Controllers;

import Banco.BDCore;
import Models.Clientes;
import Util.Valida;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateCliente implements Initializable {


    @FXML
    JFXTextField ednome, edsobrenome, edemail, edcpf, edcelular;

    @FXML
    StackPane pane;

    @FXML
    Label vCpf, vNome, vEmail, vCelular, vSobrenome;

    BDCore bd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bd = new BDCore();

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

    public void createBt(MouseEvent mouseEvent) {
        String nome, sobrenome, email, cpf, celular;
        Clientes cli = new Clientes();

        nome = ednome.getText();
        sobrenome = edsobrenome.getText();
        email = edemail.getText();
        cpf = edcpf.getText();
        celular = edcelular.getText();


        System.out.println("Entrou 1");
        if(!nome.equals("") && !sobrenome.equals("") && !email.equals("") && !cpf.equals("") && !celular.equals("") && Valida.isCPF(cpf) && Valida.isEmail(email) && Valida.isInteiro(celular)) {


            cli.setNome(nome);
            cli.setSobrenome(sobrenome);
            cli.setEmail(email);
            cli.setCPF(cpf);
            cli.setCelular(celular);
            System.out.println("Entrou 2");

            int id_cliente = bd.createCliente(cli);
            if (id_cliente != -1) {
                System.out.println("Entrou 3");
                JFXDialogLayout content = new JFXDialogLayout();

                content.setHeading(new Text("Sucesso"));
                content.setBody(new Text("Cliente Cadastrado com sucesso"));

                JFXDialog dialog = new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER);

                JFXButton btokay = new JFXButton("Okay");
                btokay.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        try {
                            Parent root = null;
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CreateEquipamento.fxml"));
                            loader.setController(new CreateEquipamento(id_cliente));
                            //root = FXMLLoader.load(getClass().getResource("../View/CreateCliente.fxml"));

                            root = loader.load();

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


            if (nome.equals("")){
                vNome.setVisible(true);
            }else{
                vNome.setVisible(false);
            }

            if(sobrenome.equals("")){
                vSobrenome.setVisible(true);
            }else{
                vSobrenome.setVisible(false);
            }

            if(email.equals("") || !Valida.isEmail(email)){
                vEmail.setVisible(true);
            }else{
                vEmail.setVisible(false);

            }

            if (cpf.equals("") || !Valida.isCPF(cpf)){
                vCpf.setVisible(true);
            }else{
                vCpf.setVisible(false);

            }

            if(celular.equals("") || !Valida.isInteiro(celular)){
                vCelular.setVisible(true);
            }else{
                vCelular.setVisible(false);
            }

        }

    }
}
