package DAO;

import database.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Transacao;

public class TransacaoDAO {

    public void salvar(Transacao transacao) throws SQLException {
        String sql = """
                     INSERT INTO transacoes (tipo, descricao, valor, concluida, data)
                     VALUES (?, ?, ?, ?, ?)
                     """;
        try (Connection conexao = Conexao.conectar();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setString(1, transacao.getTipo());
            statement.setString(2, transacao.getDescricao());
            statement.setDouble(3, transacao.getValor());
            statement.setInt(4, transacao.isConcluida() ? 1 : 0);
            statement.setString(5, transacao.getData().toString()); // formato ISO: yyyy-MM-dd

            statement.executeUpdate();
        }
    }

    public ArrayList<Transacao> listar() throws SQLException {
        String sql = "SELECT id, tipo, descricao, valor, concluida, data FROM transacoes";
        ArrayList<Transacao> transacoes = new ArrayList<>();

        try (Connection conexao = Conexao.conectar();
             Statement statement = conexao.createStatement();
             ResultSet resultado = statement.executeQuery(sql)) {

            while (resultado.next()) {

                String dataTexto = resultado.getString("data");
                LocalDate data = (dataTexto != null) ? LocalDate.parse(dataTexto) : LocalDate.now();

                Transacao transacao = new Transacao(
                    resultado.getString("descricao"),
                    resultado.getDouble("valor"),
                    resultado.getString("tipo"),
                    data
                );
                transacao.setId(resultado.getInt("id"));
                transacao.setConcluida(resultado.getInt("concluida") == 1);

                transacoes.add(transacao);
            }
        }

        return transacoes;
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM transacoes WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public void atualizar(Transacao transacao) throws SQLException {
        String sql = """
             UPDATE transacoes
             SET tipo = ?, descricao = ?, valor = ?, concluida = ?, data = ?
             WHERE id = ?
             """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, transacao.getTipo());
            comando.setString(2, transacao.getDescricao());
            comando.setDouble(3, transacao.getValor());
            comando.setInt(4, transacao.isConcluida() ? 1 : 0);
            comando.setString(5, transacao.getData().toString());
            comando.setInt(6, transacao.getId());

            comando.executeUpdate();
        }
    }
}