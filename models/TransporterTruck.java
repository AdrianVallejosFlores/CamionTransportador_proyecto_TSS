package models;

import java.util.Random;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class TransporterTruck {

	final private String FORMULA = "{{(1/400)*(x - 190), 190 <= x && x < 210},{(-1/400)*(x - 230), 210 <= x && x <= 230}}";
	private Random random;
	private int executions;
    private int itemsPerTruck;
    private double truckCapacityTons;
	private ArrayList<Object[]> resultsBoard; 
	private int timesExceeded;
	private DecimalFormatSymbols symbols;
	protected DecimalFormat df;
	
	public TransporterTruck () {
		this.timesExceeded = 0;
		this.random = new Random();
		this.resultsBoard = new ArrayList<>();
		symbols = new DecimalFormatSymbols(Locale.US);
		df = new DecimalFormat("#.###", symbols);
	}
	
	
	
	public void startSimulation() {
        InverseTransformMethod inverseTransform = new InverseTransformMethod(FORMULA);

        for (int run = 1; run <= executions; run++) {
            double accumulatedWeight = 0.0;
            boolean exceeded = false;

            for (int item = 1; item <= itemsPerTruck; item++) {
                double rnd = random.nextDouble(); 
                double itemWeight = inverseTransform.generateValue(rnd); 
                accumulatedWeight += itemWeight;

                if (accumulatedWeight > truckCapacityTons) {
                    exceeded = true;
                }

                Object[] result = new Object[6];
                result[0] = run;
                result[1] = item;
                result[2] = df.format(rnd);
                result[3] = df.format(itemWeight);
                result[4] = df.format(accumulatedWeight);
                result[5] = exceeded ? "Si excedio" : "No";

                resultsBoard.add(result);
            }

            if (exceeded) {
            	timesExceeded++;
            }
        }
    }

	
	public int getExecutions() {
        return executions;
    }

    public void setExecutions(int executions) {
        this.executions = executions;
    }

    public int getItemsPerTruck() {
        return itemsPerTruck;
    }

    public void setItemsPerTruck(int itemsPerTruck) {
        this.itemsPerTruck = itemsPerTruck;
    }

    public double getTruckCapacityTons() {
        return truckCapacityTons;
    }

    public void setTruckCapacityTons(double truckCapacityTons) {
        this.truckCapacityTons = truckCapacityTons;
    }

    public ArrayList<Object[]> getResultsBoard() {
        return resultsBoard;
    }
	
    public double getOverloadProbability() {
        return ((double)this.timesExceeded / (double)this.executions) * 100.0; 
    }
}
