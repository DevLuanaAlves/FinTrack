package repository;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepositorioGenericoTest {

    private RepositorioGenerico<String> repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new RepositorioGenerico<>();
    }

    @Test
    void deveAdicionarItem() {
        repositorio.adicionar("item1");

        assertEquals(1, repositorio.total());
    }

    @Test
    void deveRemoverItem() {
        repositorio.adicionar("item1");

        boolean removido = repositorio.remover("item1");

        assertTrue(removido);
        assertEquals(0, repositorio.total());
    }

    @Test
    void deveListarTodosOsItens() {
        repositorio.adicionar("a");
        repositorio.adicionar("b");

        List<String> lista = repositorio.listarTodos();

        assertEquals(2, lista.size());
        assertTrue(lista.contains("a"));
        assertTrue(lista.contains("b"));
    }

    @Test
    void deveAdicionarTodosComWildcardExtends() {
        List<String> novos = List.of("x", "y", "z");

        repositorio.adicionarTodos(novos);

        assertEquals(3, repositorio.total());
    }

    @Test
    void deveCopiarParaComWildcardSuper() {
        repositorio.adicionar("item1");
        List<Object> destino = new java.util.ArrayList<>();

        repositorio.copiarPara(destino);

        assertEquals(1, destino.size());
        assertEquals("item1", destino.get(0));
    }
    @Test
    void deveContarItensComWildcardInterrogacao() {
        List<Object> mista = java.util.Arrays.asList("a", 1, null, 2.5);

        assertEquals(3, RepositorioGenerico.contarItens(mista));
        assertEquals(2, RepositorioGenerico.contarItens(List.of("x", "y")));
    }
}
