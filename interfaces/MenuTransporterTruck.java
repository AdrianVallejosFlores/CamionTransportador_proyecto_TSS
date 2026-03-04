package interfaces;

import controllers.ControllerTransporterTruck;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuTransporterTruck extends JFrame implements ActionListener {
	
	final private int NUM_PARAMETERS = 3;
	public ArrayList<JTextField> listParameters;
	
	public MenuTransporterTruck (String title) {
		super (title);
		this.setSize(700, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		initLabelTittle();
		initLabelParameters();
		initTextsField();
		initButton();
		this.setVisible(true);
	}
	
	private void initLabelTittle () {
		JTextArea label = new JTextArea ("Ingrese los parametros correspondientes para realizar la simulacion." + "\n" +
                                         "Debe ingresar el numero de corridas, el numero de items permitidos "+ "\n" + 
                                         "y la capacidad del camion (Kilogramos)");
		label.setBounds(this.getWidth() - 650, 10, 700, 60); 
	    label.setLineWrap(true); 
	    label.setWrapStyleWord(true); 
	    label.setEditable(false); 
	    label.setFocusable(false);
	    label.setBackground(this.getBackground());
	    label.setFont(new Font("Arial", Font.BOLD, 16));
	    this.add(label);
	}
	
	
	private void initLabelParameters() {
	    String[] texts = {
	        "                            Numero de corridas:",
	        "               Numero de items permitidos:",
	        "                         Capacidad del camion:"
	    };

	    int posX = this.getWidth() - 650;
	    int posY = 100;
	    int width = 300;
	    int height = 25;
	    int spacing = 30;

	    Font labelFont = new Font("Arial", Font.BOLD, 16);

	    for (int i = 0; i < texts.length; i++) {
	        JLabel label = new JLabel(texts[i]);
	        label.setFont(labelFont);
	        label.setBounds(posX, posY + (i * spacing), width + 50, height);
	        this.add(label);
	    }
	}
	
	private void initButton () {
		JButton button = new JButton("Empezar Simulacion");
		button.setFont(new Font("Arial", Font.BOLD, 16));
		button.addActionListener(this);
		button.setBounds(((this.getWidth())/2) - 100 , this.getHeight() - 95, 200, 50);
     	this.add(button);
	}
	
	private void initTextsField() {
	    this.listParameters = new ArrayList<>();

	    int labelPosX = this.getWidth() - 690;   
	    int fieldPosX = labelPosX + 330;        
	    int posY = 100;                          
	    int spacing = 30;                        
	    int width = 100;
	    int height = 25;

	    Font fieldFont = new Font("Arial", Font.PLAIN, 14);

	    for (int i = 0; i < NUM_PARAMETERS; i++) {
	        JTextField field = new JTextField(4);
	        field.setFont(fieldFont);
	        field.setBorder(new LineBorder(Color.BLACK, 1));
	        field.setBounds(fieldPosX, posY + (i * spacing), width, height);
	        this.add(field);
	        this.listParameters.add(field);
	    }
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    try {
	        int numCorridas = Integer.parseInt(listParameters.get(0).getText().trim());
	        double montoInicial = Integer.parseInt(listParameters.get(1).getText().trim());
	        double meta = Double.parseDouble(listParameters.get(2).getText().trim());

	        if (numCorridas <= 0 || montoInicial < 0 || meta < 0) {
	            JOptionPane.showMessageDialog(this, "Todos los valores deben ser positivos.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        ControllerTransporterTruck controllerTT = new ControllerTransporterTruck(listParameters);
	        controllerTT.sendParameters();	
	        this.dispose();
	        controllerTT.sendResultsBoard();

	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Ingrese valores validos: entero para 'numero de corridas', y numeros decimales para los demas campos.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}	
}
