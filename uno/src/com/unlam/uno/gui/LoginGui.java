package com.unlam.uno.gui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.unlam.uno.code.ReaderFile;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.HashMap;

public class LoginGui extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldUser;
	private JTextField textFieldPass;
	HashMap<String,String> users = getUsersFromFile();
	private LoginGui loginGui;
	
	public LoginGui() {
		
		getContentPane().setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(42, 24, 70, 15);
		getContentPane().add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contraseña");
		lblContrasea.setBounds(42, 62, 117, 15);
		getContentPane().add(lblContrasea);
		
		textFieldUser = new JTextField();
		textFieldUser.setBounds(177, 22, 184, 19);
		getContentPane().add(textFieldUser);
		textFieldUser.setColumns(10);
		
		textFieldPass = new JTextField();
		textFieldPass.setColumns(10);
		textFieldPass.setBounds(177, 61, 184, 19);
		getContentPane().add(textFieldPass);
		
		final JLabel lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(42, 87, 308, 27);
		getContentPane().add(lblError);
		
		JButton btnLogin = new JButton("Ingresar");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textFieldUser.getText() == "" || textFieldPass.getText() == "") {
					lblError.setText("Debe ingresar usuario y contrasena");
					lblError.setVisible(true);
				} else {
					String pepe = users.get("lala");
					System.out.println("l");
					Main window = new Main();
					window.frmUno.setVisible(true);
					
				}
			}
		});
		
		btnLogin.setBounds(133, 126, 117, 25);
		getContentPane().add(btnLogin);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGui loginGui = new LoginGui();
					loginGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private HashMap<String, String> getUsersFromFile() {
		HashMap<String,String> users = new HashMap<String,String>();
		ReaderFile usersFile = null;
		String line;
		String[] splitLine;
		
		try {
			usersFile = new ReaderFile("users.csv");
			while((line = usersFile.readLine()) != null) {
				splitLine = line.split(",");
				users.put(splitLine[0], splitLine[1]);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(usersFile != null) 
					usersFile.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return users;
	}
	
}
