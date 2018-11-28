package Models;

public class Clientes {

    int id;
    private String Nome, Sobrenome, CPF, Celular, Email;


    public Clientes() {
    }

    public Clientes(int id, String nome, String sobrenome, String CPF, String celular, String email) {
        this.id = id;
        Nome = nome;
        Sobrenome = sobrenome;
        this.CPF = CPF;
        Celular = celular;
        Email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getSobrenome() {
        return Sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        Sobrenome = sobrenome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
