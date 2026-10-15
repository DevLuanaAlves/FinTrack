package repository;

import java.util.ArrayList;
import java.util.List;

public class RepositorioGenerico<T> {

    private final List<T> itens = new ArrayList<>();

    public void adicionar(T item) {
        itens.add(item);
    }

    public boolean remover(T item) {
        return itens.remove(item);
    }

    public List<T> listarTodos() {
        return new ArrayList<>(itens);
    }

    public int total() {
        return itens.size();
    }

    // Wildcard '? extends T': aceita adicionar itens de T ou de qualquer subtipo de T
    public void adicionarTodos(List<? extends T> novosItens) {
        itens.addAll(novosItens);
    }

    // Wildcard '? super T': permite copiar os itens para uma lista de T ou de qualquer supertipo de T
    public void copiarPara(List<? super T> destino) {
        destino.addAll(itens);
    }
    
    // Wildcard '?': aceita lista de qualquer tipo, pois só lê os elementos como Object
    public static int contarItens(List<?> lista) {
        int contador = 0;
        for (Object item : lista) {
            if (item != null) {
                contador++;
            }
        }
        return contador;
    }
}