public class ArrivalDistribution {

    public static int getInterArrivalTime(double r) {

        if (r < 0.125) return 1;
        else if (r < 0.375) return 2;
        else if (r < 0.625) return 3;
        else if (r < 0.875) return 4;
        else return 5;
    }
}