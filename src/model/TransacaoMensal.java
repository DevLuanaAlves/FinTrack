package model;

import java.time.LocalDate;

public class TransacaoMensal extends Transacao {
    private int mes;

    public TransacaoMensal(String descricao, double valor, String tipo, int mes) {
        super(descricao, valor, tipo, LocalDate.now());
        this.mes = mes;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    @Override
    public String toString() {
        return super.toString() + "| Mes " + mes;
    }
}