package com.unlam.uno.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import com.unlam.uno.code.Logger;
import com.unlam.uno.code.NumberDescription;

public class Main {

	private JFrame frmUno;
	private JTextField txtNumber;
	private JTextField txtOutput;
    private JLabel lblOutput;
    private Logger logger = Logger.getInstance();
	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Se inicializa valores del frame principal
		frmUno = new JFrame();
		frmUno.setResizable(false);
		frmUno.setTitle("Uno");
		frmUno.setBounds(100, 100, 476, 390);
		frmUno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUno.getContentPane().setLayout(null);
		
		//Se cierra archivo de log cuando termina el programa
		frmUno.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	logger.info("El usuario ha cerrado el programa");
		    	logger.closeFile();
		    	System.exit(0);
		    }
		});
		
		//Se inicializan valores del panel que contiene los objetos
		JPanel panel = new JPanel();
		panel.setBounds(12, 90, 458, 104);
		frmUno.getContentPane().add(panel);
		panel.setLayout(null);

		//Se inicializan valores del Label descriptivo
		JLabel lblNumber = new JLabel("Ingrese nro entero:");
		lblNumber.setBounds(146, 24, 149, 15);
		panel.add(lblNumber);
		
		//Se inicializan valores del input para ingresar el numero a convertir
		txtNumber = new JTextField(8);
		txtNumber.setBounds(117, 51, 208, 28);
		txtNumber.setToolTipText("El numero debe ser entero mayor a 0 y menor a 20000000");  
		panel.add(txtNumber);
		
		PlainDocument doc = (PlainDocument) txtNumber.getDocument();
	    doc.setDocumentFilter(new IntegerFilter());
		
	    //Se inicializan valores del boton que realiza la accion de convertir
		JButton btnConvert = new JButton("Convertir");
		btnConvert.setToolTipText("Converte el numero ingresado en letras");  
		btnConvert.setVerticalTextPosition(AbstractButton.BOTTOM);
		btnConvert.setHorizontalTextPosition(AbstractButton.CENTER);

		//Accion de escucha del boton para realizar al conversion
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberToString();
			}
		});
		btnConvert.setBounds(111, 200, 240, 58);
		frmUno.getContentPane().add(btnConvert);
		
		//Se inicializan los valores de los componentes del texto de salida
		txtOutput = new JTextField();
		txtOutput.setEditable(false);
		txtOutput.setBounds(29, 302, 413, 28);
		frmUno.getContentPane().add(txtOutput);
		txtOutput.setColumns(10);
		
		lblOutput = new JLabel("");
		lblOutput.setEnabled(false);
		lblOutput.setBounds(29, 278, 413, 15);
		frmUno.getContentPane().add(lblOutput);
		
		//Se inicializan valores del componente que muestra informacion del software
		JButton btnAbout = new JButton("Acerca\n de..\n", GuiUtils.load("resources/about.png", 30, 30));
		btnAbout.setBounds(342, 9, 105, 69);
		frmUno.getContentPane().add(btnAbout);
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAboutUsWindow();
			}
		});
		btnAbout.setFont(new Font("", 1, 11));
		btnAbout.setVerticalTextPosition(AbstractButton.BOTTOM);
		btnAbout.setHorizontalTextPosition(AbstractButton.CENTER);
		btnAbout.setToolTipText("Acerca de ..");
	}
	
	/** Devuelve el frame principal
	 * 
	 */
	public JFrame getFrmUno() {
		return frmUno;
	}
	/** Convierte el numero ingresado por el usario en letras y lo muetra
	 * 
	 */
	private void numberToString() {
		String number = txtNumber.getText();
		
		// se controla que el numero exista
		if( number.isEmpty() )
		{
			logger.info("Campo vacio");
			JOptionPane.showMessageDialog(frmUno, "El campo no puede ser vacio");
			return;
		}

		// se controla el rango
		Integer num = Integer.parseInt(number);
		if( num > 20000000 )
		{
			logger.info("Se ha ingresado un numero que excede el rango. El numero es: " + number);
			JOptionPane.showMessageDialog(frmUno, "Error: el rango es 0-20000000");
			return;
		}

		// se procede a traducir el numero en letras
		NumberDescription n = new NumberDescription();
		String res = n.convertirLetras(num);
		
		// se informa al usuario el resultado
		lblOutput.setText("El numero " + number + " se escribe:");
		txtOutput.setText( res );
		
		logger.info("El numero " + number + " se ha convertido correctamente a: " + res);
	}
	
	/** Muestra la pantalla Acerca de ..
	 * 
	 */
	private void showAboutUsWindow() {
		try {
			logger.info("Accediendo a ventana Acerca de...");
			AboutUsFrame aboutus = new AboutUsFrame();
			aboutus.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			aboutus.setVisible(true);
		} catch (Exception e2) {
			logger.error("Ha ocurrido un error abriendo la ventana de Acerca de...");
			e2.printStackTrace();
		}
	}
}
