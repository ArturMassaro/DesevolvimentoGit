package Banco;

import Models.*;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BDCore {

    private Connection conn = null;

    public BDCore() {


    }

    public void init(){
        openconnect();
        createTables();
        closeConnect();
    }


    public void openconnect() {
        try{
            String Url = "jdbc:sqlite:bd.db";
            conn = DriverManager.getConnection(Url);

            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void closeConnect(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void createTables(){
        String sql = "CREATE TABLE IF NOT EXISTS `Clientes` (\n" +
                "  `Id` INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
                "  `Nome` TEXT NOT NULL,\n" +
                "  `Sobrenome` TEXT NULL,\n" +
                "  `CPF` TEXT NOT NULL,\n" +
                "  `Celular` TEXT NULL,\n" +
                "  `Email` TEXT NULL)";

        String sql2 = "CREATE TABLE IF NOT EXISTS `Equipamentos` (\n" +
                "  `Id` INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT,\n" +
                "  `Nome` VARCHAR(45) NULL,\n" +
                "  `Descricao` LONGTEXT NULL,\n" +
                "  `Tipo_Id` INT NOT NULL,\n" +
                "  `Funcionario_Id` INT NOT NULL,\n" +
                "  `Cliente_Id` INT NULL);";

        String sql3 = "CREATE TABLE IF NOT EXISTS `OrcamentoEquipamento` (\n" +
                "  `Id` INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
                "  `Orcamento_Id` INTEGER NOT NULL ,\n" +
                "  `Equipamento_Id` INT NOT NULL ,\n" +
                "  `Valor` REAL NULL,\n" +
                "  `DiagnosticoTec` TEXT NULL,\n" +
                "  `Observacoes` TEXT NULL,\n" +
                "  `StatusTec`  INT NULL)";

        String sql4 = "CREATE TABLE IF NOT EXISTS `Orcamentos` (\n" +
                "  `Id` INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
                "  `StatusCliente` int NULL,\n" +
                "  `StatusTec` int NULL,\n" +
                "  `Cliente_Id` INT NOT NULL,\n" +
                "  `Funcionario_Id` INT NOT NULL,\n" +
                "  `Descricao` text NULL,\n" +
                "  `Data` text NULL," +
                "  `Descricao_final` text NULL);";


        String sql5 = "CREATE TABLE IF NOT EXISTS `TipoEquipamento` (\n" +
                "  `Id` INTEGER primary key AUTOINCREMENT,\n" +
                "  `Nome` text NULL)";
        String sql6 = "CREATE TABLE IF NOT EXISTS `Usuarios` (\n" +
                "  `Id` INTEGER NOT NULL primary key AUTOINCREMENT,\n" +
                "  `Login` VARCHAR(45) NOT NULL,\n" +
                "  `Senha` VARCHAR(45) NOT NULL,\n" +
                "  `Nome` VARCHAR(45) NOT NULL,\n" +
                "  `CPF` TEXT NOT NULL,\n" +
                "  `verfCli` int ," +
                "  `verfOrc` int ," +
                "  `verfRelatorio` int ," +
                "  `verfAdmin` int ," +
                "  `Atual` int)";

        String sql7="INSERT INTO `usuarios`(`Login`, `Senha`, `Nome`, `CPF`, `verfAdmin`) VALUES ('admin', 'admin', 'admin', 123, 1);";
        //String sql8="INSERT INTO `usuarios` (`Id`, `Login`, `Senha`, `Nome`, `CPF`, `verfAdmin`) VALUES ('2', 'teste', 'teste', 'teste', '123', '1');";

/*
        String sql = "CREATE TABLE IF NOT EXISTS `Cliente`(\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL);\n";*/


        String sql18 = "INSERT INTO `TipoEquipamento` (`Nome`) VALUES ('Computador')";
        String sql19 = "INSERT INTO `TipoEquipamento` (`Nome`) VALUES ('Celular Android')";
        String sql20 = "INSERT INTO `TipoEquipamento` (`Nome`) VALUES ('Iphone')";
        String sql21 = "INSERT INTO `TipoEquipamento` (`Nome`) VALUES ('Notebook')";


        try {
            Statement stat = conn.createStatement();
            stat.execute(sql);
            stat.execute(sql2);
            stat.execute(sql3);
            stat.execute(sql4);
            stat.execute(sql5);
            stat.execute(sql6);
            stat.execute(sql18);
            stat.execute(sql19);
            stat.execute(sql20);
            stat.execute(sql21);

            stat.execute(sql7);
            //stat.execute(sql8);

            getUsers();

            System.out.println("Table criada");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuarios verifUser(String login, String senha){
        openconnect();
        String sql = "SELECT * FROM usuarios where Login = \"" + login + "\" and Senha = \""+ senha + "\"";

        //String sql = "select * from usuarios";

        try {
            ResultSet result = conn.createStatement().executeQuery(sql);

            /*while(result.next()){
                System.out.println("login = " + result.getString("Login"));
            }*/
            if(result.next()){
                System.out.println("Login aceito");

                Usuarios u = new Usuarios();

                u.setId(result.getInt("Id"));
                u.setLogin(result.getString("Login"));
                u.setSenha(result.getString("Senha"));
                u.setNome(result.getString("Nome"));
                u.setCPF(result.getString("CPF"));
                u.setAtual(result.getInt("Atual"));
                u.setVerfCli(result.getInt("verfCli"));
                u.setVerfOrc(result.getInt("verfOrc"));
                u.setVerfRelatorio(result.getInt("verfRelatorio"));
                u.setVerfAdmin(result.getInt("verfAdmin"));



                sql = "UPDATE `usuarios` SET `Atual` = '1' WHERE Login = \"" + login + "\" and Senha = \""+ senha + "\"";

                conn.createStatement().execute(sql);
                closeConnect();
                return u;
            }else{
                System.out.println("Login rejeitado");
                closeConnect();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnect();
        return null;

    }


    public Usuarios getUserAtual(){
        openconnect();

        String sql = "SELECT * FROM usuarios WHERE Atual = 1";


        /**
         * "  `Id` INTEGER NOT NULL primary key AUTOINCREMENT,\n" +
         *                 "  `Login` VARCHAR(45) NOT NULL,\n" +
         *                 "  `Senha` VARCHAR(45) NOT NULL,\n" +
         *                 "  `Nome` VARCHAR(45) NOT NULL,\n" +
         *                 "  `CPF` TEXT NOT NULL,\n" +
         *                 "  `verfCli` int ," +
         *                 "  `verfOrc` int ," +
         *                 "  `verfRelatorio` int ," +
         *                 "  `verfAdmin` int ," +
         *                 "  `Atual` int)";
         */




        try {
            ResultSet result = conn.createStatement().executeQuery(sql);
            if (result.next()) {
                Usuarios user = new Usuarios();

                user.setLogin(result.getString("Login"));
                user.setNome(result.getString("Nome"));
                user.setSenha(result.getString("Senha"));
                user.setCPF(result.getString("CPF"));
                user.setId(result.getInt("Id"));
                user.setVerfAdmin(result.getInt("verfAdmin"));
                user.setVerfCli(result.getInt("verfCli"));
                user.setVerfOrc(result.getInt("verfOrc"));
                user.setVerfRelatorio(result.getInt("verfRelatorio"));
                //user.setTipo(result.getInt("Tipo"));

                user.setAtual(result.getInt("Atual"));
                closeConnect();
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnect();
        return null;
    }

    public void getUsers(){

        openconnect();
        String sql = " SELECT * FROM usuarios";

        try {
            ResultSet result = conn.createStatement().executeQuery(sql);

            while (result.next()){
                System.out.println("User = " + result.getString("Login"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnect();
    }

    public void logout(){
        openconnect();
        String sql = "UPDATE `usuarios` SET `Atual` = 0 WHERE Atual=1";

        try {
            conn.createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnect();
    }


    public int createCliente(Clientes cli){

        openconnect();

        String sql = "INSERT INTO `Clientes` (`Nome`, `Sobrenome`, `CPF`, `Celular`, `Email`) VALUES ('"+ cli.getNome() +"', '"+ cli.getSobrenome() +"', '"+cli.getCPF()+"', '"+cli.getCelular()+"', '"+cli.getEmail()+"');";
        //String sql2 = "SELECT Id from `Clientes` where `Nome` = `"+ cli.getNome() +"`";
        String sql2 = "SELECT last_insert_rowid() FROM Clientes";

        try {
            conn.createStatement().execute(sql);
            ResultSet result = conn.createStatement().executeQuery(sql2);


            if(result.next()){
                int x = result.getInt(1);
                System.out.println("x = " + x);
                closeConnect();
                return x;
            }
            closeConnect();
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnect();
        return -1;
    }


    /**
     *"  `Id` INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
     *                 "  `Nome` TEXT NOT NULL,\n" +
     *                 "  `Sobrenome` TEXT NULL,\n" +
     *                 "  `CPF` TEXT NOT NULL,\n" +
     *                 "  `Celular` TEXT NULL,\n" +
     *                 "  `Email` TEXT NULL)";
     */





public List<TipoEquipamento> getTipoEquipamentos(){
    openconnect();

    String sql = "SELECT * FROM TipoEquipamento";

    List<TipoEquipamento> list = new ArrayList<>();

    try {
        ResultSet resultSet = conn.createStatement().executeQuery(sql);

        while (resultSet.next()){
            TipoEquipamento n = new TipoEquipamento(resultSet.getInt(1), resultSet.getString(2));
            list.add(n);
        }

        closeConnect();
        return list;

    } catch (SQLException e) {
        e.printStackTrace();
        closeConnect();
        return null;
    }


}





    public List<Clientes> getClientes(){
        openconnect();

        String sql = "SELECT * FROM Clientes";

        List<Clientes> cli = new ArrayList<Clientes>();

        try {
            ResultSet result = conn.createStatement().executeQuery(sql);

            while(result.next()){
                Clientes c = new Clientes();

                c.setId(result.getInt("Id"));
                c.setNome(result.getString("Nome"));
                c.setSobrenome(result.getString("Sobrenome"));
                c.setCPF(result.getString("CPF"));
                c.setEmail(result.getString("Email"));
                c.setCelular(result.getString("Celular"));

                cli.add(c);

            }

            closeConnect();
            return cli;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        closeConnect();
        return null;
    }

    public boolean createUsuario(Usuarios user){
        openconnect();

        String sql = "INSERT INTO `usuarios` (`Login`, `Senha`, `Nome`, `CPF`, `verfAdmin`, `verfCli`, `verfOrc`, `verfRelatorio`) VALUES ( '"+ user.getLogin()+"', '"+user.getSenha()+"', '"+user.getNome()+"', '"+user.getCPF()+"', '"+user.getVerfAdmin()+"', '"+user.getVerfCli()+"', '"+user.getVerfOrc()+"', '"+user.getVerfRelatorio()+"');";


        try {
            conn.createStatement().execute(sql);
            closeConnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            closeConnect();
            return false;
        }

    }


    public List<Usuarios> getFuncionarios(){

        openconnect();

        String sql = "Select * from `usuarios`";

        List<Usuarios> user = new ArrayList<Usuarios>();

        try {
            ResultSet result = conn.createStatement().executeQuery(sql);

            while(result.next()){

                Usuarios u = new Usuarios();

                u.setId(result.getInt("Id"));
                u.setLogin(result.getString("Login"));
                u.setSenha(result.getString("Senha"));
                u.setNome(result.getString("Nome"));
                u.setCPF(result.getString("CPF"));
                u.setAtual(result.getInt("Atual"));
                u.setVerfCli(result.getInt("verfCli"));
                u.setVerfOrc(result.getInt("verfOrc"));
                u.setVerfRelatorio(result.getInt("verfRelatorio"));
                u.setVerfAdmin(result.getInt("verfAdmin"));

                user.add(u);


            }


            closeConnect();

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;

    }

    public boolean createEquipamento(Equipamentos equi){

        openconnect();


        String sql = "INSERT INTO `Equipamentos` (`Nome`, `Descricao`, `Tipo_Id`, `Funcionario_Id`, `Cliente_Id`) VALUES ('" + equi.getNome() +"', '" + equi.getDescricao() +"', '" + equi.getTipo_Id() +"', '" + equi.getFuncionario_Id() +"', '" + equi.getCliente_Id() +"')";

        try {
            conn.createStatement().execute(sql);
            closeConnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            closeConnect();
            return false;
        }

    }


    public List<Servicos> getServicos(){
        openconnect();

        String sql = "SELECT * FROM Orcamentos";
/*


        ``
        "  `` int NOT NULL,\n" +
                "  `` int NULL,\n" +
                "  `` INT NOT NULL,\n" +
                "  `` INT NOT NULL,\n" +
                "  `` text NULL,\n" +
                "  `` text NULL);";
        */




        try {
            ResultSet resultSet = conn.createStatement().executeQuery(sql);

            List<Servicos> list = new ArrayList<>();

            while (resultSet.next()){

                Servicos servicos = new Servicos();


                servicos.setId(resultSet.getInt("Id"));
                servicos.setClienteId(resultSet.getInt("Cliente_Id"));
                servicos.setData(resultSet.getString("Data"));
                servicos.setDescricao(resultSet.getString("Descricao"));
                servicos.setFuncionario_id(resultSet.getInt("Funcionario_Id"));
                servicos.setStatusCliente(resultSet.getInt("StatusCliente"));
                servicos.setStatusTecnico(resultSet.getInt("StatusTec"));

                list.add(servicos);

            }
            closeConnect();

            return list;

        } catch (SQLException e) {
            e.printStackTrace();

            closeConnect();
            return null;
        }

    }


    public List<Equipamentos> getEquipamentos(int id){
        openconnect();

        String sql = "SELECT * FROM Equipamentos WHERE `Cliente_Id` = '"+ id +"'";


        /*

        "  `` INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT,\n" +
                "  `` VARCHAR(45) NULL,\n" +
                "  `` LONGTEXT NULL,\n" +
                "  `` INT NOT NULL,\n" +
                "  `` INT NOT NULL,\n" +
                "  `` INT NULL);";
         */





        List<Equipamentos> list = new ArrayList<>();
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(sql);

            while(resultSet.next()){
                Equipamentos equi = new Equipamentos();


                equi.setTipo_Id(resultSet.getInt("Tipo_Id"));
                equi.setDescricao(resultSet.getString("Descricao"));
                equi.setCliente_Id(resultSet.getInt("Cliente_Id"));
                equi.setNome(resultSet.getString("Nome"));
                equi.setFuncionario_Id(resultSet.getInt("Funcionario_Id"));
                equi.setId(resultSet.getInt("Id"));

                list.add(equi);

            }

            closeConnect();
            return list;

        } catch (SQLException e) {
            e.printStackTrace();

            closeConnect();
            return null;
        }

    }

    public Equipamentos getEquipamento(int id){
        openconnect();

        String sql = "SELECT * FROM Equipamentos WHERE `Id` = '"+ id +"'";


        /*

        "  `` INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT,\n" +
                "  `` VARCHAR(45) NULL,\n" +
                "  `` LONGTEXT NULL,\n" +
                "  `` INT NOT NULL,\n" +
                "  `` INT NOT NULL,\n" +
                "  `` INT NULL);";
         */





        try {
            ResultSet resultSet = conn.createStatement().executeQuery(sql);

            if(resultSet.next()){
                Equipamentos equi = new Equipamentos();


                equi.setTipo_Id(resultSet.getInt("Tipo_Id"));
                equi.setDescricao(resultSet.getString("Descricao"));
                equi.setCliente_Id(resultSet.getInt("Cliente_Id"));
                equi.setNome(resultSet.getString("Nome"));
                equi.setFuncionario_Id(resultSet.getInt("Funcionario_Id"));
                equi.setId(resultSet.getInt("Id"));

                closeConnect();
                return equi;

            }

            closeConnect();
            return null;

        } catch (SQLException e) {
            e.printStackTrace();

            closeConnect();
            return null;
        }

    }


    public Boolean createServico(Servicos servico){

        openconnect();

        /*
        "CREATE TABLE IF NOT EXISTS `Orcamentos` (\n" +
                "  `Id` INTEGER NOT NULL primary key  AUTOINCREMENT,\n" +
                "  `StatusCliente` int NOT NULL,\n" +
                "  `StatusTec` int NULL,\n" +
                "  `Cliente_Id` INT NOT NULL,\n" +
                "  `Funcionario_Id` INT NOT NULL,\n" +
                "  `Descricao` text NULL,\n" +
                "  `Data` text NULL);";
         */




        String sql = "INSERT INTO `Orcamentos` (`StatusCliente`, `StatusTec`, `Cliente_Id`, `Funcionario_Id`, `Descricao`, `Data`) VALUES ('"+ servico.getStatusCliente() +"', '"+ servico.getStatusTecnico() +"', '"+ servico.getClienteId() +"', '"+ servico.getFuncionario_id() +"', '"+ servico.getDescricao() +"', '"+ servico.getData() +"')";


        try {
            conn.createStatement().execute(sql);
            closeConnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            closeConnect();
            return false;
        }


    }


    public int createServico1(Servicos servico){

        openconnect();

        /*
        "CREATE TABLE IF NOT EXISTS `Orcamentos` (\n" +
                "  `Id` INTEGER NOT NULL primary key  AUTOINCREMENT,\n" +
                "  `StatusCliente` int NOT NULL,\n" +
                "  `StatusTec` int NULL,\n" +
                "  `Cliente_Id` INT NOT NULL,\n" +
                "  `Funcionario_Id` INT NOT NULL,\n" +
                "  `Descricao` text NULL,\n" +
                "  `Data` text NULL);";
         */




        String sql = "INSERT INTO `Orcamentos` ( `StatusTec`, `Cliente_Id`, `Funcionario_Id`, `Descricao`, `Data`, `StatusCliente`) VALUES ('"+ servico.getStatusTecnico() +"', '"+ servico.getClienteId() +"', '"+ servico.getFuncionario_id() +"', '"+ servico.getDescricao() +"', '"+ servico.getData() +"', 0)";
        String sql2 = "SELECT last_insert_rowid() FROM `Orcamentos`";

        try {
            conn.createStatement().execute(sql);
            ResultSet result = conn.createStatement().executeQuery(sql2);

            if(result.next()){
                int x = result.getInt(1);
                System.out.println("x = " + x);
                closeConnect();
                return x;
            }
            closeConnect();
            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            closeConnect();
            return -1;
        }

        //closeConnect();
        //return -1;

    }

    public boolean createOrcamentoEquipamento(OrcamentoEquipamento orc){
        openconnect();


        /**
         * "  `Id` INTEGER NOT NULL primary key  AUTOINCREMENT,\n" +
         *                 "  `Equipamento_Id` INT NOT NULL,\n" +
         *                 "  `Valor` REAL NULL,\n" +
         *                 "  `DiagnosticoTec` TEXT NULL,\n" +
         *                 "  `Observacoes` TEXT NULL,\n" +
         *                 "  `StatusTec`  INT NULL)";
          */

        String sql = "INSERT INTO `OrcamentoEquipamento`(`Orcamento_Id`, `Equipamento_Id`, `StatusTec`) VALUES ('"+ orc.getOrcamento_id() +"', '"+ orc.getEquipamento_id() +"', 1)";


        System.out.println("SQL = " + sql);

        try {
            conn.createStatement().execute(sql);

            closeConnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            closeConnect();
            return false;
        }

    }



    public List<OrcamentoEquipamento> getOrcamentoEquip(int id){
        openconnect();

        String sql = "SELECT * FROM `OrcamentoEquipamento` WHERE `Orcamento_Id` = '"+ id +"'";


        /**
         * "  `Id` INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
         *                 "  `` INTEGER NOT NULL ,\n" +
         *                 "  `` INT NOT NULL ,\n" +
         *                 "  `` REAL NULL,\n" +
         *                 "  `` TEXT NULL,\n" +
         *                 "  `` TEXT NULL,\n" +
         *                 "  ``  INT NULL)";
         */





        List<OrcamentoEquipamento> list = new ArrayList<>();
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(sql);

            while(resultSet.next()){
                OrcamentoEquipamento equi = new OrcamentoEquipamento();

                equi.setId(resultSet.getInt("Id"));
                equi.setOrcamento_id(resultSet.getInt("Orcamento_Id"));
                equi.setEquipamento_id(resultSet.getInt("Equipamento_Id"));
                equi.setValor(resultSet.getFloat("Valor"));
                equi.setDiagnosticoTec(resultSet.getString("DiagnosticoTec"));
                equi.setObservacoes(resultSet.getString("Observacoes"));
                equi.setStatusTec(resultSet.getInt("StatusTec"));


                list.add(equi);

            }

            closeConnect();
            return list;

        } catch (SQLException e) {
            e.printStackTrace();

            closeConnect();
            return null;
        }

    }

    public Clientes getCliente(int id){
        openconnect();

        String sql = "SELECT * FROM Clientes WHERE Id = '"+ id +"'";

        Clientes cli = null;

        try {
            ResultSet result = conn.createStatement().executeQuery(sql);

            if (result.next()){
                Clientes c = new Clientes();

                c.setId(result.getInt("Id"));
                c.setNome(result.getString("Nome"));
                c.setSobrenome(result.getString("Sobrenome"));
                c.setCPF(result.getString("CPF"));
                c.setEmail(result.getString("Email"));
                c.setCelular(result.getString("Celular"));

                closeConnect();
                return c;

            }

            closeConnect();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        closeConnect();
        return null;
    }

    public boolean updateOrcamentoEquip(OrcamentoEquipamento orc){
        openconnect();


        /**
         * "  `Id` INTEGER NOT NULL primary key  AUTOINCREMENT,\n" +
         *                 "  `Equipamento_Id` INT NOT NULL,\n" +
         *                 "  `Valor` REAL NULL,\n" +
         *                 "  `DiagnosticoTec` TEXT NULL,\n" +
         *                 "  `Observacoes` TEXT NULL,\n" +
         *                 "  `StatusTec`  INT NULL)";
         */

        String sql = "UPDATE `OrcamentoEquipamento` SET `Valor` = '"+ orc.getValor() +"', `DiagnosticoTec` = '"+ orc.getDiagnosticoTec() +"', `StatusTec` = '"+ orc.getStatusTec() +"' WHERE `Id` = "+ orc.getId() +"";


        System.out.println("SQL = " + sql);

        try {
            conn.createStatement().execute(sql);

            closeConnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            closeConnect();
            return false;
        }

    }


    public boolean updateOrcamento(int id){
        openconnect();


        /**
         * "  `Id` INTEGER NOT NULL primary key  AUTOINCREMENT,\n" +
         *                 "  `Equipamento_Id` INT NOT NULL,\n" +
         *                 "  `Valor` REAL NULL,\n" +
         *                 "  `DiagnosticoTec` TEXT NULL,\n" +
         *                 "  `Observacoes` TEXT NULL,\n" +
         *                 "  `StatusTec`  INT NULL)";
         */

        String sql = "UPDATE `Orcamentos` SET `StatusTec` = 2 WHERE `Id` = "+ id +"";


        System.out.println("SQL = " + sql);

        try {
            conn.createStatement().execute(sql);

            closeConnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            closeConnect();
            return false;
        }

    }

    public boolean updateOrcamentoStatusCli(int id, int status){
        openconnect();




        String sql = "UPDATE `Orcamentos` SET `StatusCliente` = '"+ status +"' WHERE `Id` = "+ id +"";


        System.out.println("SQL = " + sql);

        try {
            conn.createStatement().execute(sql);

            closeConnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            closeConnect();
            return false;
        }

    }


    public boolean finalizaOrcamento(int id){
        openconnect();


        /**
         * "  `Id` INTEGER NOT NULL primary key  AUTOINCREMENT,\n" +
         *                 "  `Equipamento_Id` INT NOT NULL,\n" +
         *                 "  `Valor` REAL NULL,\n" +
         *                 "  `DiagnosticoTec` TEXT NULL,\n" +
         *                 "  `Observacoes` TEXT NULL,\n" +
         *                 "  `StatusTec`  INT NULL)";
         */

        String sql = "UPDATE `Orcamentos` SET `StatusTec` = 3 WHERE `Id` = "+ id +"";


        System.out.println("SQL = " + sql);

        try {
            conn.createStatement().execute(sql);

            closeConnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            closeConnect();
            return false;
        }

    }

    public boolean finalizaOrcamento2(int id, String msg){
        openconnect();


        /**
         * "  `Id` INTEGER NOT NULL primary key  AUTOINCREMENT,\n" +
         *                 "  `Equipamento_Id` INT NOT NULL,\n" +
         *                 "  `Valor` REAL NULL,\n" +
         *                 "  `DiagnosticoTec` TEXT NULL,\n" +
         *                 "  `Observacoes` TEXT NULL,\n" +
         *                 "  `StatusTec`  INT NULL)";
         */

        String sql = "UPDATE `Orcamentos` SET `StatusTec` = 3, `Descricao_final` = '"+ msg +"'  WHERE `Id` = "+ id +"";


        System.out.println("SQL = " + sql);

        try {
            conn.createStatement().execute(sql);

            closeConnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            closeConnect();
            return false;
        }

    }


    public Servicos getServico(int id){
        openconnect();

        String sql = "SELECT * FROM Orcamentos WHERE Id = '"+ id +"'";
/*


        ``
        "  `` int NOT NULL,\n" +
                "  `` int NULL,\n" +
                "  `` INT NOT NULL,\n" +
                "  `` INT NOT NULL,\n" +
                "  `` text NULL,\n" +
                "  `` text NULL);";
        */




        try {
            ResultSet resultSet = conn.createStatement().executeQuery(sql);



            if (resultSet.next()){

                Servicos servicos = new Servicos();


                servicos.setId(resultSet.getInt("Id"));
                servicos.setClienteId(resultSet.getInt("Cliente_Id"));
                servicos.setData(resultSet.getString("Data"));
                servicos.setDescricao(resultSet.getString("Descricao"));
                servicos.setFuncionario_id(resultSet.getInt("Funcionario_Id"));
                servicos.setStatusCliente(resultSet.getInt("StatusCliente"));
                servicos.setStatusTecnico(resultSet.getInt("StatusTec"));

                closeConnect();
                return servicos;

            }
            closeConnect();

            return null;

        } catch (SQLException e) {
            e.printStackTrace();

            closeConnect();
            return null;
        }

    }
}
