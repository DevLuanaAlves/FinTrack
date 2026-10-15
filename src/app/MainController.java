package app;

import java.time.LocalDate;
import controller.FinTracker;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Transacao;

public class MainController implements Initializable {

    @FXML private ComboBox<String> comboTipo;
    @FXML private TextField campoDescricao;
    @FXML private TextField campoValor;
    @FXML private CheckBox checkConcluida;
    @FXML private TableView<Transacao> tabela;
    @FXML private TableColumn<Transacao, Integer> colId;
    @FXML private TableColumn<Transacao, String> colTipo;
    @FXML private TableColumn<Transacao, String> colDescricao;
    @FXML private TableColumn<Transacao, Double> colValor;
    @FXML private TableColumn<Transacao, Boolean> colConcluida;
    @FXML private Label labelSaldo;

    private final FinTracker finTracker = new FinTracker();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboTipo.setItems(FXCollections.observableArrayList("receita", "despesa"));
        comboTipo.setValue("receita");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colConcluida.setCellValueFactory(new PropertyValueFactory<>("concluida"));

        atualizarTabela();
        atualizarSaldo();
    }

    @FXML
    private void adicionarTransacao() {
        try {
            String descricao = campoDescricao.getText().trim();
            String valorTexto = campoValor.getText().trim().replace(",", ".");

            if (descricao.isEmpty() || valorTexto.isEmpty()) {
                mostrarAlerta("Preencha todos os campos.");
                return;
            }

            double valor = Double.parseDouble(valorTexto);
            if (valor <= 0) {
                mostrarAlerta("O valor deve ser maior que zero.");
                return;
            }

            Transacao transacao = new Transacao(descricao, valor, comboTipo.getValue(), LocalDate.now());
            transacao.setConcluida(checkConcluida.isSelected());

            finTracker.adicionarTransacao(transacao);

            campoDescricao.clear();
            campoValor.clear();
            checkConcluida.setSelected(false);

            atualizarTabela();
            atualizarSaldo();

        } catch (NumberFormatException ex) {
            mostrarAlerta("Valor inválido. Digite um número.");
        }
    }

    @FXML
    private void removerSelecionada() {
        Transacao selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Selecione uma transação para remover.");
            return;
        }
        try {
            finTracker.removerTransacao(selecionada.getId());
            atualizarTabela();
            atualizarSaldo();
        } catch (SQLException ex) {
            mostrarAlerta("Erro ao remover transação.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void atualizar() {
        atualizarTabela();
        atualizarSaldo();
    }

    private void atualizarTabela() {
        try {
            ArrayList<Transacao> lista = finTracker.obterTransacoes();
            ObservableList<Transacao> dados = FXCollections.observableArrayList(lista);
            tabela.setItems(dados);
        } catch (SQLException ex) {
            mostrarAlerta("Erro ao carregar transações.");
            ex.printStackTrace();
        }
    }

    private void atualizarSaldo() {
        try {
            double saldo = finTracker.calcularSaldoTotal();
            labelSaldo.setText("Saldo atual: R$ " + String.format("%.2f", saldo));
        } catch (SQLException ex) {
            mostrarAlerta("Erro ao calcular saldo.");
            ex.printStackTrace();
        }
    }

    private void mostrarAlerta(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING, mensagem);
        alerta.showAndWait();
    }
}