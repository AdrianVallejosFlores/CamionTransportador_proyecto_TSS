import javax.swing.table.DefaultTableModel;
import java.util.*;

public class QueueSimulator {

    private int totalCustomers;
    private Random random = new Random();

    public QueueSimulator(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public SimulationMetrics runSimulation(DefaultTableModel model) {

        List<Customer> customers = new ArrayList<>();

        int lastArrival = 0;
        int lastServiceEnd = 0;

        double totalWaiting = 0;
        double totalSystem = 0;
        double totalServiceTime = 0;

        for (int i = 1; i <= totalCustomers; i++) {

            Customer c = new Customer(i);

            // Llegadas
            c.rArrival = random.nextDouble();
            c.interArrival = ArrivalDistribution.getInterArrivalTime(c.rArrival);

            if (i == 1) {
                c.arrivalTime = c.interArrival;
            } else {
                c.arrivalTime = lastArrival + c.interArrival;
            }

            lastArrival = c.arrivalTime;

            // Servicio
            c.rService = random.nextDouble();
            c.serviceTime = ServiceDistribution.getServiceTime(c.rService);

            // Inicio servicio
            c.serviceStart = Math.max(c.arrivalTime, lastServiceEnd);

            // Fin servicio
            c.serviceEnd = c.serviceStart + c.serviceTime;

            // Espera
            c.waitingTime = c.serviceStart - c.arrivalTime;

            // Tiempo sistema
            c.systemTime = c.serviceEnd - c.arrivalTime;

            lastServiceEnd = c.serviceEnd;

            totalWaiting += c.waitingTime;
            totalSystem += c.systemTime;
            totalServiceTime += c.serviceTime;

            customers.add(c);

            model.addRow(new Object[]{
                    c.id,
                    String.format("%.5f", c.rArrival),
                    c.interArrival,
                    c.arrivalTime,
                    String.format("%.5f", c.rService),
                    c.serviceTime,
                    c.serviceStart,
                    c.serviceEnd,
                    c.waitingTime,
                    c.systemTime
            });
        }

        double avgWaiting = totalWaiting / totalCustomers;
        double avgSystem = totalSystem / totalCustomers;

        double totalTime = customers.get(customers.size() - 1).serviceEnd;
        double utilization = totalServiceTime / totalTime;

        double avgQueueLength = totalWaiting / totalTime;

        return new SimulationMetrics(
                avgWaiting,
                avgSystem,
                utilization,
                avgQueueLength
        );
    }
}