package Controllers;

import Banco.BDCore;
import Models.Clientes;
import Models.Equipamentos;
import Models.OrcamentoEquipamento;
import Models.Servicos;
import Util.Valida;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CreateServicos implements Initializable {

    @FXML
    JFXListView<Label> list;

    @FXML
    JFXComboBox<String> cCliente, cEquipamentos;

    @FXML
    Label btAdd;

    @FXML
    JFXTextArea eDescricao;

    @FXML
    Label vCliente, vEquipamento, vDescricao;

    @FXML
    StackPane pane;

    List<Clientes> cli;


    List<Equipamentos> lEquipamentos = new ArrayList<>();
    List<Equipamentos> equi;

    BDCore bd = new BDCore();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btAdd.setDisable(true);

        cli = bd.getClientes();


        if(cli != null){


            for(int i = 0; i < cli.size(); i++){
                String nome = cli.get(i).getNome() + " " + cli.get(i).getSobrenome();
                //bTipos.getItems();

                cCliente.getItems().add(nome);

                System.out.println("i = " + i + " Nome = " + nome);
            }


        }



        


    }

    public void createBt(MouseEvent mouseEvent) {
        int iCliente;
        String descricao;


        iCliente = cCliente.getSelectionModel().getSelectedIndex();

        descricao = eDescricao.getText();

        if(iCliente != -1 && !descricao.equals("") && lEquipamentos.size() != 0){
            vEquipamento.setVisible(false);
            vDescricao.setVisible(false);
            vCliente.setVisible(false);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime date = LocalDateTime.now();

            System.out.println("Data = " + dtf.format(date));

            Servicos servicos = new Servicos();





            servicos.setClienteId(cli.get(iCliente).getId());
            servicos.setDescricao(descricao);
            servicos.setStatusTecnico(1);
            servicos.setFuncionario_id(bd.getUserAtual().getId());
            servicos.setData(dtf.format(date));

            int ser_id = bd.createServico1(servicos);

            boolean x = true;
            if(ser_id != -1) {

                for (int i = 0; i < lEquipamentos.size(); i++) {
                    OrcamentoEquipamento orc = new OrcamentoEquipamento();

                    orc.setEquipamento_id(lEquipamentos.get(i).getId());
                    orc.setOrcamento_id(ser_id);

                    if (!bd.createOrcamentoEquipamento(orc)) {
                        System.out.println("ERRO");

                        x = false;
                        break;
                    }

                }

                System.out.println("Antes");
                if(x){

                    Valida.enviaSms("Olá, seu(s) equipamento(s) foram entregue para manutenção ", cli.get(iCliente).getCelular());

                    System.out.println("Depois");
                    JFXDialogLayout content = new JFXDialogLayout();

                    content.setHeading(new Text("Sucesso"));
                    content.setBody(new Text("Gostaria de continuar o serviço ?"));



                    JFXDialog dialog = new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER);

                    JFXButton btokay = new JFXButton("Sim");
                    btokay.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {

                            try {
                                Parent root = null;
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CreateServicos2.fxml"));
                                loader.setController(new CreateServicos2(ser_id));
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

            }




        }else{
            if(iCliente == -1){
                vCliente.setVisible(true);
            }else{
                vCliente.setVisible(false);
            }


            if(descricao.equals("")){
                vDescricao.setVisible(true);
            }else{
                vDescricao.setVisible(false);
            }

            if(lEquipamentos.size() == 0){
                vEquipamento.setVisible(true);
            }else{
                vEquipamento.setVisible(false);
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

    public void clienteSelec(ActionEvent actionEvent) {

        cEquipamentos.getItems().clear();
        list.getItems().clear();

        lEquipamentos.clear();

        equi = bd.getEquipamentos(cli.get(cCliente.getSelectionModel().getSelectedIndex()).getId());

        System.out.println("index = " + cCliente.getSelectionModel().getSelectedIndex());
        System.out.println("index = " + cli.get(cCliente.getSelectionModel().getSelectedIndex()  ).getNome());



        if(equi != null) {

            System.out.println("Entrooou");
            for (int i = 0; i < equi.size(); i++) {
                String nome = equi.get(i).getNome();
                //bTipos.getItems();

                cEquipamentos.getItems().add(nome);

                System.out.println("i = " + i + " Nome = " + nome);
            }
        }

    }

    public void addClick(MouseEvent mouseEvent) {

        Equipamentos equipamento = equi.get(cEquipamentos.getSelectionModel().getSelectedIndex());

        lEquipamentos.add(equipamento);

        equi.remove(cEquipamentos.getSelectionModel().getSelectedIndex());
        cEquipamentos.getItems().remove(cEquipamentos.getSelectionModel().getSelectedIndex());

        Label lb = new Label(equipamento.getNome());


        lb.setTextFill(Paint.valueOf("#ededed"));

        list.getItems().add(lb);



    }

    public void enableAdd(ActionEvent actionEvent) {

        btAdd.setDisable(false);

    }
}
