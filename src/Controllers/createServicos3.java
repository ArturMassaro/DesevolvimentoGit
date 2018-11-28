package Controllers;

import Banco.BDCore;
import Models.OrcamentoEquipamento;
import Models.Servicos;
import Util.Valida;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.events.JFXDialogEvent;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class createServicos3 implements Initializable {

    private int serv_id;

    @FXML
    Label lStatus;

    @FXML
    JFXTextArea eObs;

    @FXML
    StackPane pane;


    BDCore bd = new BDCore();

    Servicos servico_atual;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        servico_atual = bd.getServico(serv_id);

        if(servico_atual.getStatusCliente() == 1){
            lStatus.setText("Rejeitado");
            lStatus.setTextFill(Paint.valueOf("#f81b1b83"));
        }else if(servico_atual.getStatusCliente() == 2){
            lStatus.setText("Aprovado");
            lStatus.setTextFill(Paint.valueOf("#22f81b83"));
        }


    }


    public createServicos3(int serv_id) {
        this.serv_id = serv_id;
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

        String obs = eObs.getText();

        if(!obs.equals("")){
            if (bd.finalizaOrcamento2(serv_id, obs)){
                dialog();
            }
        }else{
            if (bd.finalizaOrcamento(serv_id)){
                dialog();
            }
        }



    }


    private void dialog(){


        List<OrcamentoEquipamento> lista = bd.getOrcamentoEquip(serv_id);


        float valor = 0;
        for(int i = 0; i < lista.size(); i++){
            valor = valor + lista.get(i).getValor();
        }


        JFXDialogLayout content = new JFXDialogLayout();

        content.setHeading(new Text("Serviço"));

        if(servico_atual.getStatusCliente() == 2) {

            content.setBody(new Text("Serviço Finalizado com sucesso no Valor de R$" + String.format("%.2f", valor)));

            Valida.enviaSms("Serviço finalizado no valor de R$" + String.format("%.2f", valor) + "\n Favor retirar os equipamentos em nossa loja", bd.getCliente(servico_atual.getClienteId()).getCelular());

        }else{
            content.setBody(new Text("Serviço Finalizado sem execução"));
            Valida.enviaSms("Serviço finalizado sem execução, favor retirar os equipamentos em nossa loja", bd.getCliente(servico_atual.getClienteId()).getCelular());

        }
        pane.setDisable(false);
        pane.setVisible(true);


        JFXDialog dialog = new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER);





        JFXButton btfim = new JFXButton("Okay");

        btfim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {



                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../View/Dash_admin.fxml"));
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        });

        content.setActions(btfim);


        dialog.show();

    }
}
