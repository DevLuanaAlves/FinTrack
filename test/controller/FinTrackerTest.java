package controller;

import database.Conexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import database.DatabaseInitializer;
import java.time.LocalDate;
import model.Transacao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FinTrackerTest {

    private static final String URL_MEMORIA = "jdbc:sqlite:file::memory:?cache=shared";

    private Connection conexaoMantida;
    private FinTracker finTracker;

    @BeforeEach
    void setUp() throws SQLException {
        Conexao.setUrl(URL_MEMORIA);
        conexaoMantida = DriverManager.getConnection(URL_MEMORIA);

        DatabaseInitializer.criarTabela(conexaoMantida);

        finTracker = new FinTracker();
    }

    @AfterEach
    void tearDown() throws SQLException {
        conexaoMantida.close();
        Conexao.setUrl("jdbc:sqlite:fintrack.db");
    }

    @Test
    void saldoDeveSerZeroSemTransacoes() throws SQLException {
        assertEquals(0.0, finTracker.calcularSaldoTotal());
    }

    @Test
    void saldoDeveSomarReceitasESubtrairDespesas() throws SQLException {
        finTracker.adicionarTransacao(new Transacao("salario", 1000.0, "receita", LocalDate.now()));
        finTracker.adicionarTransacao(new Transacao("aluguel", 400.0, "despesa", LocalDate.now()));

        double saldo = finTracker.calcularSaldoTotal();

        assertEquals(600.0, saldo);
    }

    @Test
    void saldoDeveAtualizarAposRemocao() throws SQLException {
        finTracker.adicionarTransacao(new Transacao("salario", 1000.0, "receita", LocalDate.now()));
        int id = finTracker.obterTransacoes().get(0).getId();

        finTracker.removerTransacao(id);

        assertEquals(0.0, finTracker.calcularSaldoTotal());
    }
    @Test
    void deveRegistrarTransacoesDaSessaoNoRepositorioGenerico() {
        finTracker.adicionarTransacao(new Transacao("salario", 1000.0, "receita", LocalDate.now()));

        java.util.List<Object> destino = new java.util.ArrayList<>();
        finTracker.copiarTransacoesDaSessao(destino);

        assertEquals(1, finTracker.obterTransacoesDaSessao().size());
        assertEquals(1, destino.size());
    }
}