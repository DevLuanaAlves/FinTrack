package dao;

import DAO.TransacaoDAO;
import database.Conexao;
import database.DatabaseInitializer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Transacao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransacaoDAOTest {

    private static final String URL_MEMORIA = "jdbc:sqlite:file::memory:?cache=shared";

    private Connection conexaoMantida;
    private TransacaoDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        Conexao.setUrl(URL_MEMORIA);

        conexaoMantida = DriverManager.getConnection(URL_MEMORIA);
        
        DatabaseInitializer.criarTabela(conexaoMantida);

        dao = new TransacaoDAO();
    }

    @AfterEach
    void tearDown() throws SQLException {
        conexaoMantida.close();
        Conexao.setUrl("jdbc:sqlite:fintrack.db");
    }

    @Test
    void deveSalvarEListarTransacao() throws SQLException {
        Transacao transacao = new Transacao("mercado", 100.0, "despesa", LocalDate.now());

        dao.salvar(transacao);
        ArrayList<Transacao> lista = dao.listar();

        assertEquals(1, lista.size());
        assertEquals("mercado", lista.get(0).getDescricao());
        assertEquals(100.0, lista.get(0).getValor());
    }

    @Test
    void deveExcluirTransacao() throws SQLException {
        Transacao transacao = new Transacao("luz", 60.0, "despesa", LocalDate.now());
        dao.salvar(transacao);

        int id = dao.listar().get(0).getId();
        dao.excluir(id);

        ArrayList<Transacao> lista = dao.listar();
        assertTrue(lista.isEmpty());
    }

    @Test
    void deveAtualizarTransacao() throws SQLException {
        Transacao transacao = new Transacao("internet", 80.0, "despesa", LocalDate.now());
        dao.salvar(transacao);

        Transacao salva = dao.listar().get(0);
        salva.setConcluida(true);
        dao.atualizar(salva);

        Transacao atualizada = dao.listar().get(0);
        assertTrue(atualizada.isConcluida());
    }

   
    @Test
    void deveAtualizarValorEDataDaTransacao() throws SQLException {
        dao.salvar(new Transacao("internet", 80.0, "despesa", LocalDate.of(2026, 1, 10)));

        Transacao salva = dao.listar().get(0);
        salva.setValor(95.5);
        salva.setData(LocalDate.of(2026, 2, 15));
        dao.atualizar(salva);

        Transacao atualizada = dao.listar().get(0);
        assertEquals(95.5, atualizada.getValor());
        assertEquals(LocalDate.of(2026, 2, 15), atualizada.getData());
    }
}