package interfaces;

import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import models.TransporterTruck;

public class ResultsTransporterTruck extends JFrame{

	private ArrayList<Object[]> resultsBoard;
	private String[] columNames;
	private TransporterTruck transporterTruck;
	
	public ResultsTransporterTruck(ArrayList<Object[]> resultsBoard, TransporterTruck transporterTruck) {
		super ("Resultado de la simulacion del Camion Transportador");
		this.columNames = new String[]{"Nro. Corrida", "Nro. Item", "Num. Aleat(R)", "Peso del Item", "Peso Acumulado", "Se excedio la capacidad?"};
		this.resultsBoard = resultsBoard;
		this.transporterTruck = transporterTruck;
	}
	
	public void viewResults () {
		this.setSize(980, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		insertResultsBoard();
		initLabelInformation();
		this.setVisible(true);
	}
	
	private void insertResultsBoard() {
	    Object[][] tableDates = createTableWithSeparators(resultsBoard, columNames.length);
	    
	    JTable tableResult = new JTable(tableDates, columNames) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; 
	        }
	    };
	    
	    JScrollPane scrollPane = new JScrollPane(tableResult);
	    tableResult.setFillsViewportHeight(true);
	    
	    tableResult.getTableHeader().setReorderingAllowed(false);
	    scrollPane.setBounds(10, 140, 950, 500);
	    this.add(scrollPane);
	}
	
	private Object[][] createTableWithSeparators(ArrayList<Object[]> data, int columns) {
	    int extraRows = countSeparatorsNeeded(data);
	    
	    Object[][] tableData = new Object[data.size() + extraRows][columns];
	    
	    int rowIndex = 0;
	    for (int i = 0; i < data.size(); i++) {
	        Object[] currentRow = data.get(i);
	        
	        if (i > 0 && corridaChanged(data.get(i - 1), currentRow)) {
	            insertSeparatorRow(tableData, rowIndex, columns);
	            rowIndex++;
	        }
	        
	        for (int j = 0; j < columns; j++) {
	            tableData[rowIndex][j] = currentRow[j];
	        }
	        rowIndex++;
	    }
	    return tableData;
	}
	
	private int countSeparatorsNeeded(ArrayList<Object[]> data) {
	    int count = 0;
	    for (int i = 1; i < data.size(); i++) {
	        if (corridaChanged(data.get(i - 1), data.get(i))) {
	            count++;
	        }
	    }
	    return count;
	}
	
	private boolean corridaChanged(Object[] rowPrev, Object[] rowCurr) {
	    int corridaPrev = (int) rowPrev[0];
	    int corridaCurr = (int) rowCurr[0];
	    return corridaPrev != corridaCurr;
	}
	
	private void insertSeparatorRow(Object[][] tableData, int rowIndex, int columns) {
	    for (int j = 0; j < columns; j++) {
	        tableData[rowIndex][j] = (j == 0) ? "-" : "";
	    }
	}
	
	private void initLabelInformation () {
		JTextArea label = new JTextArea ("Los parametros usados en la simulacion fueron: " + "\n" + 
	                                     "Numero de corridas: " + String.valueOf(transporterTruck.getExecutions()) + "\n" +
				                         "Numero de items permitido por camion: " + String.valueOf(transporterTruck.getItemsPerTruck()) + "\n" + 
				                         "Capacidad de cada camion: " + String.valueOf(transporterTruck.getTruckCapacityTons()) + "Kg." + "\n" + 
				                         "Probabilidad Total de que la suma de los pesos sobrepase el limite: " + String.valueOf(transporterTruck.getOverloadProbability()) +"%");
		
		label.setBounds(this.getWidth() - 700, 10, 700, 100); 
		label.setLineWrap(true); 
		label.setWrapStyleWord(true); 
		label.setEditable(false); 
		label.setFocusable(false);
		label.setBackground(this.getBackground());
		label.setFont(new Font("Arial", Font.BOLD, 16));
		this.add(label);
	}
}
