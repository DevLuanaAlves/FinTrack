
package controller;

import java.time.LocalDate;
import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import model.Transacao;
import DAO.TransacaoDAO;

public class AdicionarTransacaoController {

    @FXML
    private Label lblTitulo;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtValor;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    private String tipo;

    private final TransacaoDAO repository =
            new TransacaoDAO();

    public void configurarTipo(String tipo) {

        this.tipo = tipo;

        btnSalvar.getStyleClass().removeAll(
            "form-save-button",
            "form-save-receita",
            "form-save-despesa"
        );

        if (tipo.equalsIgnoreCase("Receita")) {
            lblTitulo.setText("Adicionar Receita");
            btnSalvar.setText("Salvar Receita");
            btnSalvar.getStyleClass().add("form-save-receita");

        } else {
            lblTitulo.setText("Adicionar Despesa");
            btnSalvar.setText("Salvar Despesa");
            btnSalvar.getStyleClass().add("form-save-despesa");
        }
    }

    @FXML
    private void onSalvar() {

        String descricao = txtDescricao.getText().trim();
        String valorTexto = txtValor.getText().trim();

        if (descricao.isEmpty() || valorTexto.isEmpty()) {
            mostrarAviso(
                    "Campos obrigatórios",
                    "Preencha a descrição e o valor."
            );
            return;
        }

        double valor;

        try {
            valorTexto = valorTexto.replace(",", ".");
            valor = Double.parseDouble(valorTexto);
        } catch (NumberFormatException e) {
            mostrarAviso(
                    "Valor inválido",
                    "Digite um valor válido. Exemplo: 150,00"
            );
            return;
        }

        if (valor <= 0) {
            mostrarAviso(
                    "Valor inválido",
                    "O valor deve ser maior que zero."
            );
            return;
        }

        try {

            Transacao transacao = new Transacao(descricao, valor, tipo, LocalDate.now());

            repository.salvar(transacao);

            

            mostrarSucesso(
                    "Transação salva",
                    "A " + tipo.toLowerCase()
                    + " foi adicionada com sucesso."
            );

            voltarParaDashboard();

        } catch (SQLException e) {

            mostrarErro(
                    "Erro ao salvar",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void onCancelar() {
        voltarParaDashboard();
    }

    private void voltarParaDashboard() {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource(
                            "/controller/Dashboard.fxml"
                    )
            );

            Stage stage =
                    (Stage) btnCancelar.getScene().getWindow();

            Scene scene =
                    new Scene(root, 1000, 650);

            stage.setScene(scene);
            stage.setTitle("FinTrack");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAviso(
            String titulo,
            String mensagem) {

        Alert alerta =
                new Alert(Alert.AlertType.WARNING);

        alerta.setTitle("FinTrack");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private void mostrarSucesso(
            String titulo,
            String mensagem) {

        Alert alerta =
                new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("FinTrack");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private void mostrarErro(
            String titulo,
            String mensagem) {

        Alert alerta =
                new Alert(Alert.AlertType.ERROR);

        alerta.setTitle("FinTrack");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}