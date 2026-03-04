public class DemandDistribution {

    public static int getDemand(double r) {

        if (r < 0.010) return 35;
        else if (r < 0.025) return 36;
        else if (r < 0.045) return 37;
        else if (r < 0.065) return 38;
        else if (r < 0.087) return 39;
        else if (r < 0.110) return 40;
        else if (r < 0.135) return 41;
        else if (r < 0.162) return 42;
        else if (r < 0.190) return 43;
        else if (r < 0.219) return 44;
        else if (r < 0.254) return 45;
        else if (r < 0.299) return 46;
        else if (r < 0.359) return 47;
        else if (r < 0.424) return 48;
        else if (r < 0.494) return 49;
        else if (r < 0.574) return 50;
        else if (r < 0.649) return 51;
        else if (r < 0.719) return 52;
        else if (r < 0.784) return 53;
        else if (r < 0.844) return 54;
        else if (r < 0.894) return 55;
        else if (r < 0.934) return 56;
        else if (r < 0.964) return 57;
        else if (r < 0.980) return 58;
        else if (r < 0.995) return 59;
        else return 60;
    }
}