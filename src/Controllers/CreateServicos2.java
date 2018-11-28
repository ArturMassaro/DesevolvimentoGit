package Controllers;

import Banco.BDCore;
import Models.Clientes;
import Models.OrcamentoEquipamento;
import Models.Servicos;
import Util.Valida;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateServicos2 implements Initializable {

    private int id_servico;

    @FXML
    JFXListView<Label> list;

    @FXML
    Label btAdd;

    @FXML
    Label vEquipamento;

    @FXML
    JFXComboBox<String> cEquipamentos;

    BDCore bd = new BDCore();

    @FXML
    StackPane pane;

    List<OrcamentoEquipamento> listOrc = new ArrayList<>();

    List<OrcamentoEquipamento> lEquipamentos = new ArrayList<>();

    int tamlist;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btAdd.setDisable(true);
        carregaList();

    }

    public CreateServicos2(int id_servico) {
        this.id_servico = id_servico;
    }


    public void carregaList(){

        listOrc = bd.getOrcamentoEquip(id_servico);

        if(listOrc != null){

            tamlist = listOrc.size();
            for(int i = 0; i < listOrc.size(); i++){
                int equipamento_id = listOrc.get(i).getEquipamento_id();

                String nome = bd.getEquipamento(equipamento_id).getNome();

                cEquipamentos.getItems().add(nome);

            }

        }


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

        if(lEquipamentos.size() != 0 && lEquipamentos.size() == tamlist){


            if(bd.updateOrcamento(id_servico)){

                JFXDialogLayout content = new JFXDialogLayout();

                String precos = "";
                for(int i = 0; i < lEquipamentos.size(); i++){
                    precos = precos + " " + bd.getEquipamento(lEquipamentos.get(i).getEquipamento_id()).getNome() + "  |  R$" + String.format("%.2f", lEquipamentos.get(i).getValor()) + "  |  " + lEquipamentos.get(i).getDiagnosticoTec() + ";";
                }




                String msg = "Olá, seu(s) orçamento(s) fora(m) finalizado(s): " + precos + "\nPor Favor entre em contato para confirmar o serviço";

                Clientes cliente = bd.getCliente(bd.getServico(id_servico).getClienteId());

                System.out.println(msg);
                Valida.enviaSms(msg, cliente.getCelular());


                content.setHeading(new Text("Finalizar"));
                content.setBody(new Text("Voce deve esperar a Resposta do Cliente no momento"));

                pane.setDisable(false);
                pane.setVisible(true);



                JFXDialog dialog = new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER);

                JFXButton btokay = new JFXButton("Okay");
                btokay.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {




                            Parent root = null;
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Dash_admin.fxml"));
                            //root = FXMLLoader.load(getClass().getResource("../View/CreateCliente.fxml"));

                            root = loader.load();

                            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();



                            stage.setScene(new Scene(root));


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });



                content.setActions(btokay);
                dialog.show();



            }


        }else if(lEquipamentos.size() == 0){
            JFXDialogLayout content = new JFXDialogLayout();




            content.setHeading(new Text("Orçamento"));
            content.setBody(new Text("Nenhum orçamento foi dado"));

            pane.setDisable(false);
            pane.setVisible(true);



            JFXDialog dialog = new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER);

            JFXButton btokay = new JFXButton("Okay");
            btokay.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialog.close();

                }
            });



            content.setActions(btokay);



            //content.setBody(vb);


            dialog.show();
        }else if(lEquipamentos.size() != tamlist){

            System.out.println("Entrou if");
            JFXDialogLayout content = new JFXDialogLayout();

            content.setHeading(new Text("Orçamento"));
            content.setBody(new Text("Ainda há equipamentos para Fornecer Orçamento"));

            pane.setDisable(false);
            pane.setVisible(true);



            JFXDialog dialog = new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER);

            JFXButton btokay = new JFXButton("Okay");
            btokay.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialog.close();

                }
            });



            content.setActions(btokay);
            dialog.show();
        }


        System.out.println("list = " + lEquipamentos.size());
        System.out.println("tam = " + tamlist);
    }

    public void enableAdd(ActionEvent actionEvent){
        btAdd.setDisable(false);
    }

    public void addClick(MouseEvent mouseEvent) {


        JFXDialogLayout content = new JFXDialogLayout();

        System.out.println("Entrou");


        JFXTextField valor = new JFXTextField();
        valor.setPromptText("Valor");
        valor.setLabelFloat(true);
        valor.setPadding(new Insets(0,0,0,15));

        JFXTextArea rel = new JFXTextArea();
        rel.setPromptText("Relatorio tecnico");
        rel.setLabelFloat(true);
        rel.setPrefRowCount(3);
        rel.setPadding(new Insets(0,0,0,15));


        HBox hbval = new HBox();
        Label vValor = new Label("*");
        vValor.setTextFill(Paint.valueOf("#ff0000"));

        HBox hbrel = new HBox();
        Label vRel = new Label("*");
        vRel.setTextFill(Paint.valueOf("#ff0000"));


        vValor.setVisible(false);
        vRel.setVisible(false);

        hbval.getChildren().add(vValor);
        hbval.getChildren().add(valor);

        hbrel.getChildren().add(vRel);
        hbrel.getChildren().add(rel);
        hbrel.setPadding(new Insets(30,0,0,0));


        VBox vb = new VBox();

        vb.getChildren().add(hbval);
        vb.getChildren().add(hbrel);


        content.setHeading(new Text("Orçamento"));
        //content.setBody(new Text("Gostaria de continuar o serviço ?"));

        pane.setDisable(false);
        pane.setVisible(true);



        JFXDialog dialog = new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER);

        JFXButton btokay = new JFXButton("Inserir");
        btokay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String val, relatorio;
                val = valor.getText();

                relatorio = rel.getText();


                if(!val.equals("") && Valida.isInteiro(val) && !relatorio.equals("")) {





                    OrcamentoEquipamento orc = listOrc.get(cEquipamentos.getSelectionModel().getSelectedIndex());
                    listOrc.remove(cEquipamentos.getSelectionModel().getSelectedIndex());

                    orc.setStatusTec(2);
                    orc.setDiagnosticoTec(rel.getText());
                    orc.setValor(Valida.getFloat(val));

                    //orc.setValor(320);
                    if (bd.updateOrcamentoEquip(orc)) {

                        lEquipamentos.add(orc);


                        Label lb = new Label(cEquipamentos.getSelectionModel().getSelectedItem() + "\t|\tR$" + orc.getValor());

                        cEquipamentos.getItems().remove(cEquipamentos.getSelectionModel().getSelectedIndex());

                        lb.setTextFill(Paint.valueOf("#ededed"));

                        list.getItems().add(lb);


                    }


                    dialog.close();
                }else{

                    /**
                     * caso sem escrito
                     *
                     * !val.equals("") && Valida.isInteiro(val) && !relatorio.equals("")
                     *
                     */


                    if(val.equals("") || !Valida.isInteiro(val)){
                        vValor.setVisible(true);
                    }else{
                        vValor.setVisible(false);
                    }
                    if(relatorio.equals("")){
                        vRel.setVisible(true);
                    }else{
                        vRel.setVisible(false);
                    }






                }



            }
        });

        JFXButton btfim = new JFXButton("Cancelar");

        btfim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {




                //pane.setDisable(true);
                //pane.setVisible(false);
                dialog.close();



            }
        });

        content.setActions(btokay,btfim);






        content.setBody(vb);


        dialog.show();


    }
}
