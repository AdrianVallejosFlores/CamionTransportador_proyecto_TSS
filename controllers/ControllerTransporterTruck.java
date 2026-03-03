package controllers;

import java.util.ArrayList;
import javax.swing.JTextField;
import interfaces.ResultsTransporterTruck;
import models.TransporterTruck;

public class ControllerTransporterTruck {

	private ArrayList<JTextField> listParameters;
	private TransporterTruck transporterTruck;
	
	public ControllerTransporterTruck (ArrayList<JTextField> listParameters) {
		this.listParameters = listParameters;
		this.transporterTruck = new TransporterTruck();
	}
	
	public void sendParameters () {
		for (int i = 0; i < listParameters.size(); i++) {
			switch (i) {
				case 0: transporterTruck.setExecutions(Integer.parseInt(listParameters.get(i).getText().trim()));break;
				case 1: transporterTruck.setItemsPerTruck(Integer.parseInt(listParameters.get(i).getText().trim()));break;
				case 2: transporterTruck.setTruckCapacityTons(Double.parseDouble(listParameters.get(i).getText().trim()));break;
			}
		}
		
		transporterTruck.startSimulation();
	} 
	
	public void sendResultsBoard () {
		ResultsTransporterTruck resultsTT = new ResultsTransporterTruck(transporterTruck.getResultsBoard(), transporterTruck);
		resultsTT.viewResults();
	}
}