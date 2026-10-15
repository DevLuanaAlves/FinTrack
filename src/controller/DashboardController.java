package controller;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.chart.BarChart;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Transacao;
import DAO.TransacaoDAO;

public class DashboardController {

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblVisaoGeral;

    @FXML
    private Label lblTituloSaldo;

    @FXML
    private Label lblSaldo;

    @FXML
    private Label lblTituloReceitas;

    @FXML
    private Label lblReceitas;

    @FXML
    private Label lblTituloDespesas;

    @FXML
    private Label lblDespesas;

    @FXML
    private Label lblMovimentacoes;
    
    @FXML
    private BarChart<String, Number> graficoMovimentacoes;

    @FXML
    private PieChart graficoTipos;

    

    @FXML
    private Button btnReceita;

    @FXML
    private Button btnDespesa;

    @FXML
    private Button btnTransacoes;

    private final TransacaoDAO repository =
            new TransacaoDAO();

    @FXML
    public void initialize() {

        carregarResumo();
    }

    // =========================================================
    // CARREGAR VALORES DO DASHBOARD
    // =========================================================
   private void carregarGraficoTipos(
        double receitas,
        double despesas) {

        ObservableList<PieChart.Data> dados =
                FXCollections.observableArrayList();

        if (receitas > 0) {
            dados.add(
                new PieChart.Data(
                    "Receitas",
                    receitas
                )
            );
        }

        if (despesas > 0) {
            dados.add(
                new PieChart.Data(
                    "Despesas",
                    despesas
                )
            );
        }

        graficoTipos.setData(dados);
        graficoTipos.setTitle("");

        // Mostra os valores nas fatias
        graficoTipos.setLabelsVisible(true);
    }
    private void carregarGraficoResumo() throws SQLException {
        
        ArrayList<Transacao> transacoes = repository.listar();

        
        

        Map<YearMonth, Double> receitasPorMes = new TreeMap<>();
        Map<YearMonth, Double> despesasPorMes = new TreeMap<>();

        for (Transacao transacao : transacoes) {

            if (transacao.getData() == null) {
                continue; // ignora transações sem data válida
            }

            YearMonth mes = YearMonth.from(transacao.getData());

            if (transacao.getTipo().equalsIgnoreCase("Receita")) {
                receitasPorMes.merge(mes, transacao.getValor(), Double::sum);
            } else if (transacao.getTipo().equalsIgnoreCase("Despesa")) {
                despesasPorMes.merge(mes, transacao.getValor(), Double::sum);
            }
        }

        TreeSet<YearMonth> todosMeses = new TreeSet<>();
        todosMeses.addAll(receitasPorMes.keySet());
        todosMeses.addAll(despesasPorMes.keySet());

        DateTimeFormatter formatoMes =
                DateTimeFormatter.ofPattern("MMM/yy", new Locale("pt", "BR"));

        XYChart.Series<String, Number> serieReceita = new XYChart.Series<>();
        serieReceita.setName("Receitas");

        XYChart.Series<String, Number> serieDespesa = new XYChart.Series<>();
        serieDespesa.setName("Despesas");

        for (YearMonth mes : todosMeses) {
            String label = formatoMes.format(mes);

            serieReceita.getData().add(
                    new XYChart.Data<>(label, receitasPorMes.getOrDefault(mes, 0.0))
            );

            serieDespesa.getData().add(
                    new XYChart.Data<>(label, despesasPorMes.getOrDefault(mes, 0.0))
            );
        }

        graficoMovimentacoes.getData().clear();
        graficoMovimentacoes.getData().addAll(serieReceita, serieDespesa);

        graficoMovimentacoes.setLegendVisible(true);
    }
    
    private void carregarResumo() {
        try {
            ArrayList<Transacao> transacoes =
                    repository.listar();

            double totalReceitas = 0;
            double totalDespesas = 0;

            for (Transacao transacao : transacoes) {

                if (transacao.getTipo().equalsIgnoreCase("Receita")) {
                    totalReceitas += transacao.getValor();

                } else if (transacao.getTipo().equalsIgnoreCase("Despesa")) {
                    totalDespesas += transacao.getValor();
                }
            }

            double saldo = totalReceitas - totalDespesas;

            lblReceitas.setText(formatarMoeda(totalReceitas));
            lblDespesas.setText(formatarMoeda(totalDespesas));
            lblSaldo.setText(formatarMoeda(saldo));

            // Gráficos
            // Gráficos
            carregarGraficoResumo();
            carregarGraficoTipos(totalReceitas, totalDespesas);

        } catch (SQLException e) {

            e.printStackTrace();

            lblSaldo.setText("R$ 0,00");
            lblReceitas.setText("R$ 0,00");
            lblDespesas.setText("R$ 0,00");
        }
    }

    // =========================================================
    // FORMATAR VALOR
    // =========================================================

    private String formatarMoeda(double valor) {

        return String.format(
                java.util.Locale.forLanguageTag("pt-BR"),
                "R$ %.2f",
                valor
        );
    }

    // =========================================================
    // ADICIONAR RECEITA
    // =========================================================

    @FXML
    private void onAdicionarReceita() {

        abrirTelaTransacao("Receita");
    }

    // =========================================================
    // ADICIONAR DESPESA
    // =========================================================

    @FXML
    private void onAdicionarDespesa() {

        abrirTelaTransacao("Despesa");
    }

    // =========================================================
    // ABRIR TELA DE TRANSAÇÃO
    // =========================================================

    private void abrirTelaTransacao(String tipo) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/controller/AdicionarTransacao.fxml"
                    )
            );

            Parent root = loader.load();

            AdicionarTransacaoController controller =
                    loader.getController();

            controller.configurarTipo(tipo);

            Stage stage =
                    (Stage) btnReceita.getScene().getWindow();

            Scene scene =
                    new Scene(root, 500, 400);

            stage.setScene(scene);

            stage.setTitle(
                    "FinTrack - " + tipo
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =========================================================
    // ABRIR TRANSAÇÕES
    // =========================================================

    
    @FXML
    private void onAbrirTransacoes() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/app/Transacoes.fxml")
            );

            Stage stage =
                    (Stage) btnTransacoes.getScene().getWindow();

            // Troca apenas o conteúdo da janela
            stage.getScene().setRoot(root);

            stage.setTitle("FinTrack - Transações");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}