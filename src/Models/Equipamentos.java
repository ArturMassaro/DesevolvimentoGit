package Models;

public class Equipamentos {

    private int id, Tipo_Id, Funcionario_Id, Cliente_Id;
    private String Nome, Descricao;


    public Equipamentos(int id, int tipo_Id, int funcionario_Id, int cliente_Id, String nome, String descricao) {
        this.id = id;
        Tipo_Id = tipo_Id;
        Funcionario_Id = funcionario_Id;
        Cliente_Id = cliente_Id;
        Nome = nome;
        Descricao = descricao;
    }

    public Equipamentos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo_Id() {
        return Tipo_Id;
    }

    public void setTipo_Id(int tipo_Id) {
        Tipo_Id = tipo_Id;
    }

    public int getFuncionario_Id() {
        return Funcionario_Id;
    }

    public void setFuncionario_Id(int funcionario_Id) {
        Funcionario_Id = funcionario_Id;
    }

    public int getCliente_Id() {
        return Cliente_Id;
    }

    public void setCliente_Id(int cliente_Id) {
        Cliente_Id = cliente_Id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }
}
