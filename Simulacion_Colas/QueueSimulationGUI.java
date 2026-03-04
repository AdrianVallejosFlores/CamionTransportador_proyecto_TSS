import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QueueSimulationGUI extends JFrame {

    private JTextField txtCustomers;
    private JTable table;
    private DefaultTableModel model;
    private JTextArea metricsArea;

    public QueueSimulationGUI() {

        setTitle("Simulación Sistema de Colas (Modelo Libro Coss Bu)");
        setSize(1200, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 3));

        panel.add(new JLabel("Número de Clientes:"));
        txtCustomers = new JTextField();
        panel.add(txtCustomers);

        JButton btnSimulate = new JButton("Simular");
        panel.add(btnSimulate);

        JButton btnHelp = new JButton("¿Qué significa cada columna?");
        panel.add(btnHelp);

        add(panel, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("Cliente");
        model.addColumn("R Llegada");
        model.addColumn("T Entre Llegadas");
        model.addColumn("Hora Llegada");
        model.addColumn("R Servicio");
        model.addColumn("T Servicio");
        model.addColumn("Inicio Servicio");
        model.addColumn("Fin Servicio");
        model.addColumn("Espera");
        model.addColumn("Tiempo Sistema");

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        metricsArea = new JTextArea(5, 20);
        metricsArea.setEditable(false);
        add(metricsArea, BorderLayout.SOUTH);

        btnSimulate.addActionListener(e -> simulate());
        btnHelp.addActionListener(e -> showColumnInfo());
    }

    private void simulate() {

        model.setRowCount(0);
        metricsArea.setText("");

        int customers = Integer.parseInt(txtCustomers.getText());

        QueueSimulator simulator = new QueueSimulator(customers);
        SimulationMetrics metrics = simulator.runSimulation(model);

        metricsArea.setText(metrics.toString());
    }

    private void showColumnInfo() {

        String message =
                "SIGNIFICADO DE CADA COLUMNA:\n\n" +
                "Cliente:\n" +
                "Número del cliente en orden de llegada.\n\n" +

                "R Llegada:\n" +
                "Número aleatorio entre 0 y 1 usado para determinar\n" +
                "el tiempo entre llegadas mediante la tabla de probabilidades.\n\n" +

                "T Entre Llegadas:\n" +
                "Tiempo que pasa entre la llegada de este cliente\n" +
                "y el cliente anterior.\n\n" +

                "Hora Llegada:\n" +
                "Momento exacto (tiempo acumulado) en que el cliente\n" +
                "llega al sistema.\n\n" +

                "R Servicio:\n" +
                "Número aleatorio entre 0 y 1 usado para determinar\n" +
                "el tiempo de servicio.\n\n" +

                "T Servicio:\n" +
                "Tiempo que dura la atención del cliente.\n\n" +

                "Inicio Servicio:\n" +
                "Momento en que el cliente comienza a ser atendido.\n" +
                "Se calcula como el máximo entre su llegada y el\n" +
                "fin del servicio anterior.\n\n" +

                "Fin Servicio:\n" +
                "Momento en que termina la atención del cliente.\n\n" +

                "Espera:\n" +
                "Tiempo que el cliente permanece en la cola antes\n" +
                "de ser atendido.\n\n" +

                "Tiempo Sistema:\n" +
                "Tiempo total que el cliente pasa en el sistema\n" +
                "(espera + servicio).";

        JOptionPane.showMessageDialog(
                this,
                message,
                "Información de las Columnas",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}