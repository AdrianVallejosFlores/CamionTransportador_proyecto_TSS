import javax.swing.table.DefaultTableModel;
import java.util.*;

public class InventorySimulator {

    private int months;
    private int Q;
    private int R;
    private int inventory;
    private int orderCount = 0;

    private List<Order> pendingOrders = new ArrayList<>();
    private Random random = new Random();

    public InventorySimulator(int months, int initialInventory, int Q, int R) {
        this.months = months;
        this.inventory = initialInventory;
        this.Q = Q;
        this.R = R;
    }

    public void runSimulation(DefaultTableModel model) {

        for (int month = 1; month <= months; month++) {

            // 1️⃣ Procesar pedidos pendientes
            Iterator<Order> iterator = pendingOrders.iterator();
            while (iterator.hasNext()) {
                Order order = iterator.next();
                order.monthsRemaining--;

                if (order.monthsRemaining == 0) {
                    inventory += order.quantity;
                    iterator.remove();
                }
            }

            int initialInventory = inventory;

            // 2️⃣ Generar demanda
            double rDemand = random.nextDouble();
            int demand = DemandDistribution.getDemand(rDemand);

            int finalInventory = initialInventory - demand;
            int shortage = 0;

            if (finalInventory < 0) {
                shortage = Math.abs(finalInventory);
                finalInventory = 0;
            }

            // 3️⃣ Política (Q,R)
            if (finalInventory <= R) {

                double rLead = random.nextDouble();
                int leadTime = LeadTimeDistribution.getLeadTime(rLead);

                pendingOrders.add(new Order(Q, leadTime));
                orderCount++;
            }

            // 4️⃣ Inventario promedio mensual
            double averageInventory;

            if (shortage == 0) {
                averageInventory = (initialInventory + finalInventory) / 2.0;
            } else {
                averageInventory = initialInventory / 2.0;
            }

            model.addRow(new Object[]{
                    month,
                    initialInventory,
                    String.format("%.5f", rDemand),
                    demand,
                    finalInventory,
                    shortage,
                    orderCount,
                    String.format("%.2f", averageInventory)
            });

            inventory = finalInventory;
        }
    }
}