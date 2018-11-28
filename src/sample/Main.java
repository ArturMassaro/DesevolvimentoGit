package sample;

import Banco.BDCore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    static private String login, senha;




    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root));

        firstRun();

        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }



    private void firstRun(){
        File file = new File("exec.txt");
        boolean firstRun = false;
        if(!file.exists()){
            firstRun = true;
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(firstRun){
            System.out.println("Primeira execução");
            BDCore bd = new BDCore();

            bd.init();


        }else{
            System.out.println("ja executado");
        }
    }

    public void setloginSenha(String login, String senha){
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
