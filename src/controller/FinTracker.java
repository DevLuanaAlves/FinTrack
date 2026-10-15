
package controller;


import java.sql.SQLException;
import DAO.TransacaoDAO;
import java.util.ArrayList;
import model.Transacao;
import repository.RepositorioGenerico;
import java.util.List;

public class FinTracker {
    
    private RepositorioGenerico<Transacao> repositorioMemoria;
    private TransacaoDAO repository;
    //private ArrayList<Transacao> transacoes; 
    //private TransacaoRepository repository;
    
    public FinTracker() {
        
        repositorioMemoria = new RepositorioGenerico<>();
        repository = new TransacaoDAO();
    }
    
    public List<Transacao> obterTransacoesDaSessao() {
        return repositorioMemoria.listarTodos();
    }

    // Wildcard '? super Transacao'
    public void copiarTransacoesDaSessao(List<? super Transacao> destino) {
        repositorioMemoria.copiarPara(destino);
    }
    
    public ArrayList<Transacao> obterTransacoes() throws SQLException {
        return repository.listar();
    }
    
    public void adicionarTransacao(Transacao transacao){
        
        try {
        
            repository.salvar(transacao);
            repositorioMemoria.adicionar(transacao);
            
            
        }catch (SQLException erro) {
            
            System.out.println("Erro ao salvar transacao no banco de dados");
            erro.printStackTrace();
        }
    }
    
    public void listarTransacoes() {
        
        try {
            List<Transacao> transacoes = repository.listar();
            //ArrayList<Transacao> transacoes = repository.listar();
            
            if(transacoes.isEmpty()){
                System.out.println("Nenhuma transacao cadastrada");
            }else {
                for (Transacao transacao : transacoes) {
                System.out.println(transacao);
                System.out.println("-----------------------");
                
                }
            }
            
        }catch (SQLException erro) {
            System.out.println("Erro ao listar tarefas");
            erro.printStackTrace();
        }
        
        
    }
    
    public double calcularSaldoTotal() throws SQLException {
        
        double saldo = 0;
        List<Transacao> transacoes = repository.listar();
        
        for (Transacao transacao : transacoes) {
            
            if (transacao.getTipo().equalsIgnoreCase("receita")){
                saldo += transacao.getValor();
            }
            else {
                saldo -= transacao.getValor();
            }
        }
        return saldo;
    }
    
    public void removerTransacao(int id) throws SQLException {
        repository.excluir(id);
    }
    
    public void concluirTransacao(int id) {
        
        try {
            List<Transacao> transacoesBanco = repository.listar();
            for (Transacao transacao : transacoesBanco) {
                if(transacao.getId() == id) {
                    transacao.setConcluida(true);
                    repository.atualizar(transacao);
                    System.out.println("Transacao concluida com sucesso!");
                    return;
                }
            }
            System.out.println("Transacao nao encontrada");
        } catch (SQLException erro) {
            System.out.println("Erro ao concluir transacao");
            erro.printStackTrace();
        }
    }
    
    
    
    
    
    
   
}
