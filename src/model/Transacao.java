
package model;

/**
 *
 * @author ADMIN
 */
public class Transacao {
    private String descricao;
    private double valor;
    private String tipo;
    
    public Transacao(String descricao, double valor, String tipo){
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
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

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
    
    @Override
    public String toString() {
        return "Tipo: " + tipo + 
                "| Descricao " + descricao + 
                "| Valor: R$ " + valor;
    }
    
}
