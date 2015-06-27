package com.unlam.uno.gui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.unlam.uno.code.ReaderFile;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.HashMap;
import javax.swing.JPasswordField;

public class LoginGui extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textFieldUser;
	HashMap<String,String> users = getUsersFromFile();
	private static LoginGui loginGui;
	private JPasswordField textFieldPass;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		loginGui = new LoginGui();
		loginGui.setVisible(true);
	}
	
	public LoginGui() {
		setResizable(false);
		setTitle("Uno");
		setBounds(400, 200, 400, 220);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		final JLabel lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(42, 87, 308, 27);
		getContentPane().add(lblError);
		
		JButton btnLogin = new JButton("Ingresar");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user = textFieldUser.getText();
				String pass = new String(textFieldPass.getPassword());
				if(user.isEmpty() || pass.isEmpty()) {
					lblError.setText("Debe ingresar usuario y contrasena");
					lblError.setVisible(true);
				} else {
					String password = users.get(user);
					if(password != null && password.equals(pass)) {
						Main window = new Main();
						window.getFrmUno().setVisible(true);
						loginGui.setVisible(false);
					} else {
						lblError.setText("Usuario y contrasena incorrecto");
						lblError.setVisible(true);
					}
				}
			}
		});
		
		btnLogin.setBounds(142, 126, 117, 25);
		getContentPane().add(btnLogin);
		
		textFieldPass = new JPasswordField();
		textFieldPass.setBounds(177, 60, 186, 19);
		getContentPane().add(textFieldPass);
	}
	
	private HashMap<String, String> getUsersFromFile() {
		HashMap<String,String> users = new HashMap<String,String>();
		ReaderFile usersFile = null;
		String line;
		String[] splitLine;
		
		try {
			usersFile = new ReaderFile("resources/users.uno");
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
