package Models;

public class OrcamentoEquipamento {

    private int id;
    private int orcamento_id;
    private int equipamento_id;
    private float valor;
    private String diagnosticoTec;
    private String observacoes;
    private int statusTec;

    public OrcamentoEquipamento() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrcamento_id() {
        return orcamento_id;
    }

    public void setOrcamento_id(int orcamento_id) {
        this.orcamento_id = orcamento_id;
    }

    public int getEquipamento_id() {
        return equipamento_id;
    }

    public void setEquipamento_id(int equipamento_id) {
        this.equipamento_id = equipamento_id;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDiagnosticoTec() {
        return diagnosticoTec;
    }

    public void setDiagnosticoTec(String diagnosticoTec) {
        this.diagnosticoTec = diagnosticoTec;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public int getStatusTec() {
        return statusTec;
    }

    public void setStatusTec(int statusTec) {
        this.statusTec = statusTec;
    }
}



