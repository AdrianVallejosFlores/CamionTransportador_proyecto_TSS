public class ServiceDistribution {

    public static int getServiceTime(double r) {

        if (r < 0.10) return 1;
        else if (r < 0.30) return 2;
        else if (r < 0.60) return 3;
        else if (r < 0.85) return 4;
        else return 5;
    }
}