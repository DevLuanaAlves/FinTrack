package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String CRIAR_TABELA = """
            CREATE TABLE IF NOT EXISTS transacoes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                descricao VARCHAR(100) NOT NULL,
                valor DECIMAL(10,2) NOT NULL,
                tipo VARCHAR(10) NOT NULL,
                data DATE,
                concluida INTEGER NOT NULL DEFAULT 0
            )
            """;

    public static void inicializar() {
        try (Connection conexao = Conexao.conectar()) {
            criarTabela(conexao);
            System.out.println("Banco de dados inicializado com sucesso");
        } catch (SQLException erro) {
            System.out.println("Erro ao inicializar o banco de dados");
            erro.printStackTrace();
        }
    }

    // Também é usado pelos testes (SQLite em memória)
    public static void criarTabela(Connection conexao) throws SQLException {
        try (Statement statement = conexao.createStatement()) {
            statement.execute(CRIAR_TABELA);
        }
    }
}