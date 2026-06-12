import javax.swing.*;
import ui.JanelaPrincipal;

/**
 * Ponto de entrada da aplicação "Pare de Fumar".
 * Garante que a UI seja criada na Event Dispatch Thread (EDT) do Swing.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Usa o Look and Feel nativo do sistema operacional
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Se não conseguir, mantém o padrão do Swing — não é crítico
                System.err.println("Look and Feel não disponível: " + e.getMessage());
            }

            JanelaPrincipal janela = new JanelaPrincipal();
            janela.setVisible(true);
        });
    }
}
