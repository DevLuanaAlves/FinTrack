package model;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransacaoTest {

    @Test
    void deveCriarTransacaoComValoresCorretos() {
        Transacao transacao = new Transacao("mercado", 150.0, "despesa", LocalDate.now());

        assertEquals("mercado", transacao.getDescricao());
        assertEquals(150.0, transacao.getValor());
        assertEquals("despesa", transacao.getTipo());
        assertFalse(transacao.isConcluida());
    }

    @Test
    void deveAlterarConcluidaComSetter() {
        Transacao transacao = new Transacao("salario", 2000.0, "receita", LocalDate.now());

        transacao.setConcluida(true);

        assertTrue(transacao.isConcluida());
    }

    @Test
    void deveAlterarIdComSetter() {
        Transacao transacao = new Transacao("luz", 90.0, "despesa", LocalDate.now());

        transacao.setId(5);

        assertEquals(5, transacao.getId());
    }
}