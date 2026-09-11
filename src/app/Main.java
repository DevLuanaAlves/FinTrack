
package app;

import java.util.Scanner;
import java.util.InputMismatchException;
import controller.FinTracker;
import model.Transacao;
import model.TransacaoMensal;
import exceptions.EntradaInvalidaException;
import utils.Formatador;

public class Main {
    public static void main (String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        FinTracker finTracker = new FinTracker();
        
        int opcao = 0;
        
        while (opcao !=5)
            
            try {
                
                System.out.println("******* FINTRACK ******");
                System.out.println("1. Adicionar transação");
                System.out.println("2. Listar transacoes");
                System.out.println("3. Mostrar saldo");
                System.out.println("4. Remover transacao");
                System.out.println("5. Sair");

                System.out.print ("Escolha uma opcao: ");
                opcao = scanner.nextInt(); // ler valores inteiros
                scanner.nextLine(); // limpar o buffer do teclado

                switch (opcao) {

                    case 1:

                        System.out.print("Descricao: ");
                        String descricao = scanner.nextLine();

                        System.out.print("Valor: ");
                        double valor = scanner.nextDouble();
                        if(valor <= 0) {
                            throw new EntradaInvalidaException("O valor deve ser maior que ZERO");
                        }
                        scanner.nextLine(); // limpar o buffer

                        System.out.print("Tipo (receita/despesa): ");
                        String tipo = scanner.nextLine();

                        System.out.print("É uma transação mensal? (s/n): ");
                        String ehMensal = scanner.nextLine();

                        Transacao transacao;

                        if (ehMensal.equalsIgnoreCase("s")) {
                            System.out.print("Mes (1-12): ");
                            int mes = scanner.nextInt();
                            scanner.nextLine(); // limpar o buffer
                            transacao = new TransacaoMensal(descricao, valor, tipo, mes);
                        } else {
                            transacao = new Transacao(descricao, valor, tipo);
                        }

                        finTracker.adicionarTransacao(transacao);

                        System.out.println("Transacao adicionada com sucesso!");
                        break;

                    case 2:
                        finTracker.listarTrasacoes();
                        break;

                    case 3:
                        System.out.println("Saldo atual: " + Formatador.formatarMoeda(finTracker.calcularSaldoTotal()));
                        break;
                        
                    case 4:
                        System.out.println("Digite o indice da transacao: ");
                        int indice = scanner.nextInt();
                        
                        finTracker.removerTransacao(indice);
                        
                        System.out.println("Transacao removida!");
                        break;
                        
                    case 5:
                        System.out.println("Encerrar sistema...");
                        break;
                        
                    default: 
                        System.out.println("Opcao invalida!");
                        
                }
                
        }catch(EntradaInvalidaException e){ 
            
            System.out.println(e.getMessage());
            scanner.nextLine(); // limpar buffer teclado
            
            
            System.out.println("Entrada invalida! Digite novamente");
            scanner.nextLine(); // limpar buffer teclado
            
        }catch(InputMismatchException e){
            
            System.out.println("Entrada invalida! Digite um numero.");
            scanner.nextLine(); // limpar buffer teclado, senão entra em loop infinito
            
        }
            
    }
       
}
