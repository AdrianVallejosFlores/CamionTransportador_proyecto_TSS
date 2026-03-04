public class SimulationMetrics {

    double avgWaiting;
    double avgSystem;
    double serverUtilization;
    double avgQueueLength;

    public SimulationMetrics(double avgWaiting,
                             double avgSystem,
                             double serverUtilization,
                             double avgQueueLength) {

        this.avgWaiting = avgWaiting;
        this.avgSystem = avgSystem;
        this.serverUtilization = serverUtilization;
        this.avgQueueLength = avgQueueLength;
    }

    @Override
    public String toString() {
        return "Tiempo promedio de espera: " + String.format("%.2f", avgWaiting) +
                "\nTiempo promedio en sistema: " + String.format("%.2f", avgSystem) +
                "\nUtilización del servidor: " + String.format("%.2f", serverUtilization) +
                "\nLongitud promedio de cola: " + String.format("%.2f", avgQueueLength);
    }
}