package Models;

public class Usuarios {

    private int Id;
    private String Login;
    private String Senha;
    private String Nome;
    private String CPF;
    //private int Tipo;
    private int Atual;

    private int verfCli, verfOrc, verfRelatorio, verfAdmin;

    public int getVerfAdmin() {
        return verfAdmin;
    }

    public void setVerfAdmin(int verfAdmin) {
        this.verfAdmin = verfAdmin;
    }

    public int getVerfCli() {
        return verfCli;
    }

    public void setVerfCli(int verfCli) {
        this.verfCli = verfCli;
    }

    public int getVerfOrc() {
        return verfOrc;
    }

    public void setVerfOrc(int verfOrc) {
        this.verfOrc = verfOrc;
    }

    public int getVerfRelatorio() {
        return verfRelatorio;
    }

    public void setVerfRelatorio(int verfRelatorio) {
        this.verfRelatorio = verfRelatorio;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public int getAtual() {
        return Atual;
    }

    public void setAtual(int atual) {
        Atual = atual;
    }
}
