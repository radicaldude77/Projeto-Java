package ui;

import exception.DadosInvalidosException;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.Fumante;
import service.FumanteService;

/**
 * Janela principal da aplicação "Pare de Fumar".
 * Herda de JFrame e compõe a interface com PainelResultado.
 */
public class JanelaPrincipal extends JFrame {

    private final FumanteService service = new FumanteService();
 
    // Campos de entrada
    private JTextField txtNome;
    private JTextField txtDataNascimento;
    private JTextField txtCigarrosPorDia;
    private JTextField txtPrecoPorMaco;
    private JTextField txtCigarrosPorMaco;
    private JTextField txtDataParada;

    // Painel de resultados (subclasse de JPanel)
    private PainelResultado painelResultado;

    public JanelaPrincipal() {
        super("🚭 Pare de Fumar — Contador de Progresso");
        configurarJanela();
        construirInterface();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 620);
        setLocationRelativeTo(null); // centraliza na tela
        setResizable(false);
    }

    private void construirInterface() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        painelPrincipal.setBackground(Color.WHITE);

        // --- Cabeçalho ---
        JLabel lblTitulo = new JLabel("🚭 Pare de Fumar", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(34, 139, 34));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // --- Formulário ---
        painelPrincipal.add(construirFormulario(), BorderLayout.CENTER);

        // --- Painel sul: resultado + botões ---
        JPanel painelSul = new JPanel(new BorderLayout(8, 8));
        painelSul.setBackground(Color.WHITE);

        painelResultado = new PainelResultado();
        painelSul.add(painelResultado, BorderLayout.CENTER);
        painelSul.add(construirBotoes(), BorderLayout.SOUTH);

        painelPrincipal.add(painelSul, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private JPanel construirFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "📋 Seus Dados",
                0, 0,
                new Font("SansSerif", Font.BOLD, 12)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNome            = new JTextField(20);
        txtDataNascimento  = new JTextField("dd/MM/yyyy", 20);
        txtCigarrosPorDia  = new JTextField(20);
        txtPrecoPorMaco    = new JTextField("12.50", 20);
        txtCigarrosPorMaco = new JTextField("20", 20);
        txtDataParada      = new JTextField(
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 20);

        adicionarCampo(painel, gbc, 0, "Nome:", txtNome,
                "Digite seu nome completo.");
        adicionarCampo(painel, gbc, 1, "Data de nascimento (dd/MM/yyyy):", txtDataNascimento,
                "Use o formato 21/02/1985.");
        adicionarCampo(painel, gbc, 2, "Cigarros por dia:", txtCigarrosPorDia,
                "Ex.: 10.");
        adicionarCampo(painel, gbc, 3, "Preço do maço (R$):", txtPrecoPorMaco,
                "Ex.: 12.50 ou 12,50.");
        adicionarCampo(painel, gbc, 4, "Cigarros por maço:", txtCigarrosPorMaco,
                "Ex.: 20.");
        adicionarCampo(painel, gbc, 5, "Data em que parou (dd/MM/yyyy):", txtDataParada,
                "Use o formato 21/02/2024.");

        return painel;
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc,
                                 int linha, String rotulo, JTextField campo) {
        adicionarCampo(painel, gbc, linha, rotulo, campo, null);
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc,
                                 int linha, String rotulo, JTextField campo,
                                 String dica) {
        gbc.gridx = 0; gbc.gridy = linha; gbc.weightx = 0.35;
        JLabel label = new JLabel(rotulo);
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        painel.add(label, gbc);

        gbc.gridx = 1; gbc.weightx = 0.65;
        campo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        if (dica != null && !dica.isBlank()) {
            campo.setToolTipText(dica);
        }
        painel.add(campo, gbc);
    }

    private JPanel construirBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        painel.setBackground(Color.WHITE);

        JButton btnCalcular = new JButton("✅ Calcular Progresso");
        btnCalcular.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnCalcular.setBackground(new Color(34, 139, 34));
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFocusPainted(false);
        btnCalcular.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCalcular.addActionListener(e -> calcular());

        JButton btnLimpar = new JButton("🗑️ Limpar");
        btnLimpar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnLimpar.setFocusPainted(false);
        btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLimpar.addActionListener(e -> limpar());

        painel.add(btnCalcular);
        painel.add(btnLimpar);
        return painel;
    }

    /**
     * Chama o serviço e trata as exceções de forma robusta.
     */
    private void calcular() {
        try {
            Fumante fumante = service.criarFumante(
                    txtNome.getText(),
                    txtDataNascimento.getText(),
                    txtCigarrosPorDia.getText(),
                    txtPrecoPorMaco.getText(),
                    txtCigarrosPorMaco.getText(),
                    txtDataParada.getText()
            );

            // Polimorfismo: painelResultado.atualizarResultados recebe Fumante (ou subclasse)
            painelResultado.atualizarResultados(fumante);

        } catch (DadosInvalidosException e) {
            mostrarDialogo(
                    "Dados Inválidos",
                    "⚠️ Campo inválido: " + e.getCampo() + "\n" + e.getMessage(),
                    JOptionPane.WARNING_MESSAGE
            );
        } catch (Exception e) {
            String mensagem = e.getMessage();
            if (mensagem == null || mensagem.isBlank()) {
                mensagem = "Ocorreu um erro inesperado. Verifique os dados e tente novamente.";
            }
            mostrarDialogo(
                    "Erro",
                    "❌ " + mensagem,
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void mostrarDialogo(String titulo, String mensagem, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }

    private void limpar() {
        txtNome.setText("");
        txtDataNascimento.setText("dd/MM/yyyy");
        txtCigarrosPorDia.setText("");
        txtPrecoPorMaco.setText("");
        txtCigarrosPorMaco.setText("20");
        txtDataParada.setText(
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        painelResultado.limpar();
    }
}
