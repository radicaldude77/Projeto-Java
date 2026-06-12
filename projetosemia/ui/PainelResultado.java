package ui;

import model.Fumante;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Painel de resultados que herda de JPanel.
 * Demonstra herança dentro da camada de UI.
 */
public class PainelResultado extends JPanel {

    private final JLabel lblDias;
    private final JLabel lblCigarros;
    private final JLabel lblDinheiro;
    private final JLabel lblMotivacao;

    public PainelResultado() {
        setLayout(new GridLayout(4, 1, 8, 8));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(34, 139, 34), 2),
                "📊 Seu Progresso",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 13),
                new Color(34, 139, 34)
        ));
        setBackground(new Color(240, 255, 240));

        lblDias      = criarLabel("⏱️ Dias sem fumar: —");
        lblCigarros  = criarLabel("🚬 Cigarros não fumados: —");
        lblDinheiro  = criarLabel("💰 Dinheiro economizado: —");
        lblMotivacao = criarLabel("💪 Preencha seus dados e clique em Calcular!");
        lblMotivacao.setForeground(new Color(0, 100, 0));

        add(lblDias);
        add(lblCigarros);
        add(lblDinheiro);
        add(lblMotivacao);
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        return label;
    }

    /**
     * Atualiza o painel com os dados calculados de um Fumante.
     * Polimorfismo: recebe o tipo base Fumante mas pode receber subclasses no futuro.
     */
    public void atualizarResultados(Fumante fumante) {
        long dias = fumante.getDiasSemFumar();
        long cigarros = fumante.getCigarrosNaoFumados();
        double dinheiro = fumante.getDinheiroEconomizado();

        lblDias.setText(String.format("⏱️ Dias sem fumar: %d dia(s)", dias));
        lblCigarros.setText(String.format("🚬 Cigarros não fumados: %d cigarro(s)", cigarros));
        lblDinheiro.setText(String.format("💰 Dinheiro economizado: R$ %.2f", dinheiro));
        lblMotivacao.setText(getMensagemMotivacional(dias));

        // Destaque visual conforme progresso
        if (dias >= 30) {
            setBackground(new Color(220, 255, 220));
        } else {
            setBackground(new Color(240, 255, 240));
        }
    }

    /**
     * Retorna uma mensagem motivacional de acordo com os dias sem fumar.
     */
    private String getMensagemMotivacional(long dias) {
        if (dias == 0)      return "💪 Hoje é o dia 1 — você consegue!";
        if (dias < 7)       return "🌱 Continue! Sua saúde já está melhorando.";
        if (dias < 30)      return "⭐ Incrível! Mais de uma semana sem fumar!";
        if (dias < 90)      return "🏆 Um mês ou mais! Seus pulmões agradecem!";
        if (dias < 365)     return "🎉 Mais de 3 meses! Você é uma inspiração!";
        return "🥇 Um ano ou mais! Você mudou sua vida!";
    }

    /**
     * Limpa os resultados exibidos.
     */
    public void limpar() {
        lblDias.setText("⏱️ Dias sem fumar: —");
        lblCigarros.setText("🚬 Cigarros não fumados: —");
        lblDinheiro.setText("💰 Dinheiro economizado: —");
        lblMotivacao.setText("💪 Preencha seus dados e clique em Calcular!");
        setBackground(new Color(240, 255, 240));
    }
}
