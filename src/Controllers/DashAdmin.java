package Controllers;


import Banco.BDCore;
import Models.Clientes;
import Models.Servicos;
import Models.Usuarios;
import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.util.Callback;

public class DashAdmin implements Initializable {



    @FXML
    private JFXListView<Label> listFuncionarios;


    //TABELA SERVIÇOS !!!!
    @FXML
    private TreeTableColumn<Servicos, String> cNome;

    @FXML
    private TreeTableColumn<Servicos, String> cEntrada;

    @FXML
    private TreeTableColumn<Servicos, String> cStatus;

    @FXML
    private TreeTableColumn<Servicos, String> cDesc;

    private TreeItem<Servicos> root = new TreeItem<>(new Servicos());

    @FXML
    private JFXTreeTableView tableServ;

    ////////////////////////////////////

    //TABELA Clientes !!!!
    @FXML
    private TreeTableColumn<Clientes, String> cCliNome;

    @FXML
    private TreeTableColumn<Clientes, String> cCliEmail;

    @FXML
    private TreeTableColumn<Clientes, String> cCliCelular;



    private TreeItem<Clientes> rootCli = new TreeItem<>(new Clientes(1,"nome",  "Sobrenome", "cpf", "celular", "email"));

    @FXML
    private JFXTreeTableView tableClientes;

    ////////////////////////////////////




    @FXML
    private JFXMasonryPane pane;




    //PANEIS PRINCIPAIS
    @FXML
    private AnchorPane dashPane;

    @FXML
    private AnchorPane ordemPane;

    @FXML
    private AnchorPane clientePane;

    ///////////////////////


    private String login;

    BDCore bd;

    @FXML
    private Label txt_nome;

    @FXML
    StackPane pane2;


    @FXML
    Label lcli,lserv,lmes;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //System.out.println("iniciou Login = " + login);

        pane.setHSpacing(20);
        pane.setVSpacing(20);


        bd = new BDCore();

        List<Usuarios> u = bd.getFuncionarios();
        for(int i = 0; i < u.size(); i++){
            Label lbl = new Label(u.get(i).getNome());
            try {
                lbl.setGraphic(new ImageView(new Image(new FileInputStream("D:/Documentos/Projeto Oficina/Desevolvimento/src/Imagens/User_25.png"))));

                lbl.setTextFill(Paint.valueOf("#ededed"));
                //listFuncionarios.setPrefHeight(listFuncionarios.getItems().size() * 24 + 2);
                listFuncionarios.getItems().add(lbl);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        carregaListaServ();


        //SET USUARIO !!!!


        Usuarios user = bd.getUserAtual();


        System.out.println("usuario = " + user.getLogin());
        txt_nome.setText(user.getLogin());



        carregaListCliente();
    }



    public void carregaListaServ(){
        List<Servicos> listServ = bd.getServicos();

        lserv.setText("" + listServ.size());
        lmes.setText("" + listServ.size());
        root.getChildren().clear();

        for(int i = 0; i < listServ.size(); i++) {
            TreeItem<Servicos> servi = new TreeItem<>(listServ.get(i));
            root.getChildren().add(servi);
        }




        tableServ.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                if (tableServ.getSelectionModel().getSelectedIndex() != -1) {
                    JFXDialogLayout content = new JFXDialogLayout();

                    System.out.println("Entrou");

                    Servicos s = listServ.get(tableServ.getSelectionModel().getSelectedIndex());
                    System.out.println("servi = " + tableServ.getSelectionModel().getSelectedIndex());

                    if (s.getStatusTecnico() != 2) {
                        content.setHeading(new Text("Serviço"));
                        content.setBody(new Text("Gostaria de continuar o serviço ?"));

                        pane2.setDisable(false);
                        pane2.setVisible(true);


                        JFXDialog dialog = new JFXDialog(pane2, content, JFXDialog.DialogTransition.CENTER);

                        JFXButton btokay = new JFXButton("Sim");
                        btokay.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {

                                try {


                                    if (s.getStatusTecnico() == 1) {

                                        Parent root = null;
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CreateServicos2.fxml"));
                                        loader.setController(new CreateServicos2(s.getId()));
                                        //root = FXMLLoader.load(getClass().getResource("../View/CreateCliente.fxml"));

                                        root = loader.load();

                                        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();


                                        pane2.setDisable(true);
                                        pane2.setVisible(false);

                                        stage.setScene(new Scene(root));
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                        JFXButton btfim = new JFXButton("Cancelar");

                        btfim.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {


                                pane2.setDisable(true);
                                pane2.setVisible(false);
                                dialog.close();


                            }
                        });

                        content.setActions(btokay, btfim);


                        dialog.show();


                        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
                            @Override
                            public void handle(JFXDialogEvent event) {
                                tableServ.getSelectionModel().clearSelection();


                                pane2.setDisable(true);
                                pane2.setVisible(false);


                            }
                        });


                    } else {
                        if (s.getStatusCliente() == 0) {

                            content.setHeading(new Text("Serviço"));
                            content.setBody(new Text("Gostaria de adicionar uma resposta do usuario ?"));

                            pane2.setDisable(false);
                            pane2.setVisible(true);


                            JFXDialog dialog = new JFXDialog(pane2, content, JFXDialog.DialogTransition.CENTER);

                            JFXButton btokay = new JFXButton("Sim");
                            btokay.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {

                                    dialog.close();

                                    carregaOps(dialog, s);

                                }
                            });

                            JFXButton btfim = new JFXButton("Cancelar");

                            btfim.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {


                                    pane2.setVisible(false);
                                    dialog.close();


                                }
                            });

                            content.setActions(btokay, btfim);


                            dialog.show();


                            dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
                                @Override
                                public void handle(JFXDialogEvent event) {
                                    tableServ.getSelectionModel().clearSelection();


                                    pane2.setVisible(false);


                                }
                            });

                            dialog.setOnDialogOpened(new EventHandler<JFXDialogEvent>() {
                                @Override
                                public void handle(JFXDialogEvent event) {
                                    //tableServ.getSelectionModel().clearSelection();

                                    //pane2.setDisable(false);
                                    pane2.setVisible(true);
                                }
                            });


                        }else {

                            content.setHeading(new Text("Serviço"));
                            content.setBody(new Text("Gostaria de continuar o serviço ?"));

                            pane2.setDisable(false);
                            pane2.setVisible(true);


                            JFXDialog dialog = new JFXDialog(pane2, content, JFXDialog.DialogTransition.CENTER);

                            JFXButton btokay = new JFXButton("Sim");
                            btokay.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {


                                    try {




                                        Parent root = null;
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CreateServicos3.fxml"));
                                        loader.setController(new createServicos3(s.getId()));
                                        //root = FXMLLoader.load(getClass().getResource("../View/CreateCliente.fxml"));

                                        root = loader.load();

                                        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();


                                        pane2.setDisable(true);
                                        pane2.setVisible(false);

                                        stage.setScene(new Scene(root));


                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }



                                }
                            });

                            JFXButton btfim = new JFXButton("Cancelar");

                            btfim.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {


                                    pane2.setVisible(false);
                                    dialog.close();


                                }
                            });

                            content.setActions(btokay, btfim);


                            dialog.show();


                            dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
                                @Override
                                public void handle(JFXDialogEvent event) {
                                    tableServ.getSelectionModel().clearSelection();


                                    pane2.setVisible(false);


                                }
                            });

                            dialog.setOnDialogOpened(new EventHandler<JFXDialogEvent>() {
                                @Override
                                public void handle(JFXDialogEvent event) {
                                    //tableServ.getSelectionModel().clearSelection();

                                    //pane2.setDisable(false);
                                    pane2.setVisible(true);
                                }
                            });





                        }





                    }


                }
            }
        });









        cNome.setStyle("-fx-text-fill:rgba(255, 255, 255, 0.795);");

        cNome.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Servicos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Servicos, String> param) {

                int id = param.getValue().getValue().getClienteId();

                Clientes cli = bd.getCliente(id);


                return new SimpleStringProperty(cli.getNome() + " " + cli.getSobrenome());
            }
        });

        cEntrada.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Servicos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Servicos, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getData());
            }
        });

        cStatus.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Servicos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Servicos, String> param) {

                int p = param.getValue().getValue().getStatusTecnico();

                if(p == 1) {
                    return new SimpleStringProperty("Analize");

                }else if(p == 2){
                    return new SimpleStringProperty("Orçamento");
                }else if(p == 3){
                    return new SimpleStringProperty("Finalizado");
                }else {
                    return new SimpleStringProperty("ERRO");
                }
            }
        });

        cDesc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Servicos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Servicos, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getDescricao());
            }
        });

        tableServ.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

            }
        });



        String style = "-fx-background-color: #070d1bad;" +
                "-fx-border-color: #070d1bad;";

        cNome.setStyle(style);
        cEntrada.setStyle(style);
        cStatus.setStyle(style);
        cDesc.setStyle(style);




        tableServ.setRoot(root);
        tableServ.setShowRoot(false);
    }

    private void carregaOps(JFXDialog dial, Servicos s){

        JFXDialogLayout content = new JFXDialogLayout();

        content.setHeading(new Text("Serviço"));
        content.setBody(new Text("Gostaria de adicionar uma resposta do usuario ?"));

        pane2.setDisable(false);
        pane2.setVisible(true);


        JFXDialog dialog = new JFXDialog(pane2, content, JFXDialog.DialogTransition.CENTER);


        VBox hb = new VBox();
        ToggleGroup group = new ToggleGroup();

        JFXRadioButton bt1 = new JFXRadioButton("Aceito");
        bt1.setToggleGroup(group);

        bt1.setPadding(new Insets(0,0,10,0));

        JFXRadioButton bt2 = new JFXRadioButton("Recusado");
        bt2.setToggleGroup(group);



        hb.getChildren().add(bt1);
        hb.getChildren().add(bt2);

        content.setBody(hb);


        JFXButton btokay = new JFXButton("Confirmar");
        btokay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                if(bt1.isSelected()){
                    System.out.println("bt1");
                    if(bd.updateOrcamentoStatusCli(s.getId(), 2)){
                        dialog.close();
                        dialog();
                    }


                }else if(bt2.isSelected()){
                    System.out.println("bt2");

                    if(bd.updateOrcamentoStatusCli(s.getId(), 1)){
                        dialog.close();
                        dialog();
                    }

                }else{
                    //dialog.close();
                }



            }
        });

        JFXButton btfim = new JFXButton("Cancelar");

        btfim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {



                pane2.setVisible(false);
                dialog.close();


            }
        });

        content.setActions(btokay, btfim);


        dialog.show();

        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                tableServ.getSelectionModel().clearSelection();



                pane2.setVisible(false);


            }
        });

        dialog.setOnDialogOpened(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                //tableServ.getSelectionModel().clearSelection();

                //dial.close();
                //pane2.setDisable(false);
                pane2.setVisible(true);
            }
        });


                    /*dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
                        @Override
                        public void handle(JFXDialogEvent event) {
                            tableServ.getSelectionModel().clearSelection();


                            pane2.setDisable(true);
                            pane2.setVisible(false);


                        }
                    });*/
    }

    private void dialog(){

        JFXDialogLayout content = new JFXDialogLayout();

        content.setHeading(new Text("Serviço"));
        content.setBody(new Text("Resposta inserida com sucesso"));

        pane2.setDisable(false);
        pane2.setVisible(true);


        JFXDialog dialog = new JFXDialog(pane2, content, JFXDialog.DialogTransition.CENTER);





        JFXButton btfim = new JFXButton("Okay");

        btfim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {



                pane2.setVisible(false);
                carregaListaServ();
                dialog.close();


            }
        });

        content.setActions(btfim);


        dialog.show();

        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                tableServ.getSelectionModel().clearSelection();



                pane2.setVisible(false);


            }
        });

        dialog.setOnDialogOpened(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                //tableServ.getSelectionModel().clearSelection();

                //dial.close();
                //pane2.setDisable(false);
                pane2.setVisible(true);
            }
        });


                    /*dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
                        @Override
                        public void handle(JFXDialogEvent event) {
                            tableServ.getSelectionModel().clearSelection();


                            pane2.setDisable(true);
                            pane2.setVisible(false);


                        }
                    });*/
    }


    private void carregaListCliente(){

        List<Clientes> cli = bd.getClientes();

        lcli.setText("" + cli.size());
        if(cli != null) {
            for (int i = 0; i < cli.size(); i++) {
                TreeItem<Clientes> clientes = new TreeItem<>(cli.get(i));
                rootCli.getChildren().add(clientes);
            }
        }

        cCliNome.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Clientes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Clientes, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getNome());
            }
        });

        cCliEmail.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Clientes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Clientes, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getEmail());
            }
        });

        cCliCelular.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Clientes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Clientes, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getCelular());
            }
        });


        String style = "-fx-background-color: #070d1bad;" +
                "-fx-border-color: #070d1bad;";

        cCliCelular.setStyle(style);
        cCliEmail.setStyle(style);
        cCliNome.setStyle(style);

        tableClientes.setRoot(rootCli);
        tableClientes.setShowRoot(false);

    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void handleClose(javafx.scene.input.MouseEvent mouseEvent) {
        System.exit(0);
    }



    public void Logout(MouseEvent mouseEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));

        Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();
        //stage.setMaximized(true);
        //stage.setFullScreen(true);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX((bounds.getWidth()/2)-350);
        stage.setY((bounds.getHeight()/2)-200);
        stage.setWidth(700);
        stage.setHeight(400);
        bd.logout();
        stage.setScene(new Scene(root));

    }


    public void DashButton(MouseEvent mouseEvent) {
        dashPane.setVisible(true);
        ordemPane.setVisible(false);
        clientePane.setVisible(false);
        listFuncionarios.getSelectionModel().clearSelection();
    }

    public void OrdemButton(MouseEvent mouseEvent) {
        dashPane.setVisible(false);
        ordemPane.setVisible(true);
        clientePane.setVisible(false);
        ordemPane.requestFocus();
        listFuncionarios.getSelectionModel().clearSelection();
    }

    public void ClienteButton(MouseEvent mouseEvent) {
        dashPane.setVisible(false);
        ordemPane.setVisible(false);
        clientePane.setVisible(true);
        listFuncionarios.getSelectionModel().clearSelection();
    }

    public void addFunci(MouseEvent mouseEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/CreateFuncionario.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void addCliente(MouseEvent mouseEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/CreateCliente.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void btCreateServico(MouseEvent mouseEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/CreateServicos.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
