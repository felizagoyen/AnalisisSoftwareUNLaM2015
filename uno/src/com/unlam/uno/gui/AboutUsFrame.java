package com.unlam.uno.gui;

import java.awt.Color;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class AboutUsFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	public AboutUsFrame() {
		setTitle("Acerca de ..");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 481, 453);
		 JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea txtProduction = new JTextArea();
		txtProduction.setBackground(new Color(255, 255, 255));
		txtProduction.setEditable(false);
		txtProduction.setText("Analisis de software\n" + 
				  "Trabajo Practico\n\n" +  
				  "Año: 2015 - 1er Cuatrimestre\n\n" +  
	              "Integrantes\n" +  
	              "\tCristian Gonzalez\n" +
	              "\tFernando Elizagoyen\n" +
	              "\tMatias Pascual\n" +
	              "\tBrian De Diego\n\n"+
	              "  El programa UNO toma valores enteros entre 0 y\n" +
	              "20.000.000, traduciendolo en letras. Este mismo\n" +
	              "programa como su codigo se utilizo para aplicar\n"+
	              "algoritmos de calidad con fines didacticos.\n" );

		txtProduction.setAlignmentX(AbstractButton.CENTER);
		txtProduction.setAlignmentY(AbstractButton.CENTER);
		txtProduction.setBounds(57, 24, 355, 259);
		contentPane.add(txtProduction);
		
		JLabel lblUnlamLogo = new JLabel( );
		lblUnlamLogo.setIcon(GuiUtils.load("resources/unlam.png", 350, 87));
		lblUnlamLogo.setBounds(57, 295, 451, 83);
		contentPane.add(lblUnlamLogo);
	}
}
