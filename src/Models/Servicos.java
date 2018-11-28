package Models;

public class Servicos {

    private int id, statusCliente, statusTecnico, clienteId, funcionario_id;
    private String descricao, data;


    public Servicos() {
    }

    public Servicos(int id, int statusCliente, int statusTecnico, int clienteId, int funcionario_id, String descricao, String data) {
        this.id = id;
        this.statusCliente = statusCliente;
        this.statusTecnico = statusTecnico;
        this.clienteId = clienteId;
        this.funcionario_id = funcionario_id;
        this.descricao = descricao;
        this.data = data;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatusCliente() {
        return statusCliente;
    }

    public void setStatusCliente(int statusCliente) {
        this.statusCliente = statusCliente;
    }

    public int getStatusTecnico() {
        return statusTecnico;
    }

    public void setStatusTecnico(int statusTecnico) {
        this.statusTecnico = statusTecnico;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getFuncionario_id() {
        return funcionario_id;
    }

    public void setFuncionario_id(int funcionario_id) {
        this.funcionario_id = funcionario_id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
