import javax.swing.SwingUtilities;

public class QueueSimulationApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QueueSimulationGUI gui = new QueueSimulationGUI();
            gui.setVisible(true);
        });
    }
}