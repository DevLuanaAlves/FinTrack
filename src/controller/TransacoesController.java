package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyStringWrapper;
import model.Transacao;
import DAO.TransacaoDAO;

public class TransacoesController {

    @FXML
    private TableView<Transacao> tabelaTransacoes;

    @FXML
    private TableColumn<Transacao, String> colData;

    @FXML
    private TableColumn<Transacao, String> colDescricao;

    @FXML
    private TableColumn<Transacao, String> colTipo;

    @FXML
    private TableColumn<Transacao, Double> colValor;

    @FXML
    private Button btnRemover;

    @FXML
    private Button btnVoltar;

    private final TransacaoDAO repository = new TransacaoDAO();
    private static final DateTimeFormatter FORMATO_DATA =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final ObservableList<Transacao> listaTransacoes =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        configurarColunas();

        carregarTransacoes();
    }

    private void configurarColunas() {

        colData.setCellValueFactory(celula -> {
            LocalDate data = celula.getValue().getData();
            return new ReadOnlyStringWrapper(
                data == null ? "—" : data.format(FORMATO_DATA)
            );
        });

        colDescricao.setCellValueFactory(
                new PropertyValueFactory<>("descricao")
        );

        colTipo.setCellValueFactory(
                new PropertyValueFactory<>("tipo")
        );

        colValor.setCellValueFactory(
                new PropertyValueFactory<>("valor")
        );
    }

    private void carregarTransacoes() {

        try {

            listaTransacoes.clear();

            listaTransacoes.addAll(repository.listar());

            tabelaTransacoes.setItems(listaTransacoes);

        } catch (SQLException e) {

            mostrarErro(
                    "Erro ao carregar transações",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void onRemover() {

        Transacao selecionada =
                tabelaTransacoes.getSelectionModel().getSelectedItem();

        if (selecionada == null) {

            mostrarAviso(
                    "Nenhuma transação selecionada",
                    "Selecione uma transação para remover."
            );

            return;
        }

        try {

            repository.excluir(selecionada.getId());

            listaTransacoes.remove(selecionada);

        } catch (SQLException e) {

            mostrarErro(
                    "Erro ao remover transação",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void onVoltar() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/controller/Dashboard.fxml")
            );

            Stage stage =
                    (Stage) btnVoltar.getScene().getWindow();

            // Troca apenas o conteúdo da janela
            stage.getScene().setRoot(root);

            stage.setTitle("FinTrack");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarAviso(String titulo, String mensagem) {

        Alert alerta = new Alert(Alert.AlertType.WARNING);

        alerta.setTitle("FinTrack");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensagem);

        alerta.showAndWait();
    }

    private void mostrarErro(String titulo, String mensagem) {

        Alert alerta = new Alert(Alert.AlertType.ERROR);

        alerta.setTitle("FinTrack");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensagem);

        alerta.showAndWait();
    }
}