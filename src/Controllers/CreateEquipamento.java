package Controllers;

import Banco.BDCore;
import Models.Clientes;
import Models.Equipamentos;
import Models.TipoEquipamento;
import Util.Valida;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateEquipamento implements Initializable {

    private int id_cliente;

    @FXML
    private JFXComboBox<String> bTipos = new JFXComboBox<String>();

    @FXML
    private JFXTextField ednome, eddescricao;

    BDCore bd;
    List<TipoEquipamento> list;

    @FXML
    private StackPane pane;

    @FXML
    private Label vNome, vDesc, vCombo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        bd = new BDCore();

        list = bd.getTipoEquipamentos();

        if(list != null){
            //bTipos.getItems().add

            ObservableList<TipoEquipamento> listString =  FXCollections.observableArrayList(list);


            for(int i = 0; i < list.size(); i++){
                String nome = list.get(i).getNome();
                //bTipos.getItems();

                bTipos.getItems().add(nome);

                System.out.println("i = " + i + " Nome = " + nome);
            }



/*
            bTipos.getItems().add("teste 1");
            bTipos.getItems().add("teste 2");
            bTipos.getItems().add("teste 3");*/

            bTipos.setPromptText("Selecione a categoria");




        }

        System.out.println("id = " + id_cliente);
    }


    public CreateEquipamento(int id_cliente) {
        this.id_cliente = id_cliente;
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

    public void Novobt(MouseEvent mouseEvent) {

        String nome, descricao;
        int i;
/*
        System.out.println("Teste = " + bTipos.getSelectionModel().getSelectedIndex());
        System.out.println("Teste 2 = " + list.get(bTipos.getSelectionModel().getSelectedIndex()).getNome());
        System.out.println("Teste 3 = " + bTipos.getSelectionModel().getSelectedItem());*/

        nome = ednome.getText();
        descricao = eddescricao.getText();






        if(!nome.equals("") && !descricao.equals("") && bTipos.getSelectionModel().getSelectedIndex() != -1) {

            Equipamentos equi = new Equipamentos();


            equi.setCliente_Id(this.id_cliente);
            equi.setDescricao(descricao);
            equi.setNome(nome);
            equi.setTipo_Id( list.get(bTipos.getSelectionModel().getSelectedIndex()).getId());

            if (bd.createEquipamento(equi)) {
                JFXDialogLayout content = new JFXDialogLayout();

                content.setHeading(new Text("Sucesso"));
                content.setBody(new Text("Gostaria de cadastrar mais equipamentos ?"));

                JFXDialog dialog = new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER);

                JFXButton btokay = new JFXButton("Sim");
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

                JFXButton btfim = new JFXButton("Finalizar");

                btfim.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            Parent root = null;
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Dash_admin.fxml"));
                            //root = FXMLLoader.load(getClass().getResource("../View/CreateCliente.fxml"));

                            root = loader.load();

                            Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();

                            stage.setScene(new Scene(root));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                content.setActions(btokay,btfim);

                dialog.show();
            }
        }else{


            if (nome.equals("")){
                vNome.setVisible(true);
            }else{
                vNome.setVisible(false);
            }

            if(descricao.equals("")){
                vDesc.setVisible(true);
            }else{
                vDesc.setVisible(false);
            }

            if(bTipos.getSelectionModel().getSelectedIndex() == -1){
                vCombo.setVisible(true);
            }else{
                vCombo.setVisible(false);
            }

        }



    }



}
