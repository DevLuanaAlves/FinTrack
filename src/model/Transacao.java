package model;

import java.time.LocalDate;

public class Transacao {
    private int id;
    private boolean concluida;
    private String descricao;
    private double valor;
    private String tipo;
    private LocalDate data;

    public Transacao(String descricao, double valor, String tipo, LocalDate data){
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.data = data;
        this.concluida = false;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    @Override
    public String toString() {
        return "ID: " + id +
            "| Tipo: " + tipo +
            "| Descricao " + descricao +
            "| Valor: R$ " + valor +
            "| Data: " + data +
            "| Concluida: " + (concluida ? "Sim" : "Não");
    }
}