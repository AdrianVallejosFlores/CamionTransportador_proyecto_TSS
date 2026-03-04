import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SimulationGUI extends JFrame {

    private JTextField txtMonths, txtInitialInventory, txtQ, txtR;
    private JTable table;
    private DefaultTableModel model;

    public SimulationGUI() {

        setTitle("Simulación Sistema de Inventarios (Modelo Q,R - Coss Bu)");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Número de Meses:"));
        txtMonths = new JTextField();
        panel.add(txtMonths);

        panel.add(new JLabel("Inventario Inicial:"));
        txtInitialInventory = new JTextField();
        panel.add(txtInitialInventory);

        panel.add(new JLabel("Cantidad fija Q:"));
        txtQ = new JTextField();
        panel.add(txtQ);

        panel.add(new JLabel("Punto de Reorden R:"));
        txtR = new JTextField();
        panel.add(txtR);

        JButton btnSimulate = new JButton("Simular");
        panel.add(btnSimulate);

        add(panel, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("Mes");
        model.addColumn("Inventario Inicial");
        model.addColumn("R Demanda");
        model.addColumn("Demanda Ajustada");
        model.addColumn("Inventario Final");
        model.addColumn("Faltante");
        model.addColumn("Órdenes Acum.");
        model.addColumn("Inventario Promedio");

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnSimulate.addActionListener(e -> simulate());
    }

    private void simulate() {

        model.setRowCount(0);

        int months = Integer.parseInt(txtMonths.getText());
        int initialInventory = Integer.parseInt(txtInitialInventory.getText());
        int Q = Integer.parseInt(txtQ.getText());
        int R = Integer.parseInt(txtR.getText());

        InventorySimulator simulator =
                new InventorySimulator(months, initialInventory, Q, R);

        simulator.runSimulation(model);
    }
}