package com.unlam.uno.gui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.unlam.uno.code.Logger;
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
	private Logger logger = Logger.getInstance();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		loginGui = new LoginGui();
		loginGui.setVisible(true);
	}
	
	public LoginGui() {
		//Se inicializa valores del Frame principal
		setResizable(false);
		setTitle("Uno");
		setBounds(400, 200, 400, 220);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		//Se cierra archivo de log cuando termina el programa
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	logger.info("El usuario ha cerrado el programa");
		    	System.exit(0);
		    }
		});
		
		//Se inicializan valores del label de usuario y contrasena
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(42, 24, 70, 15);
		getContentPane().add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contraseña");
		lblContrasea.setBounds(42, 62, 117, 15);
		getContentPane().add(lblContrasea);
		
		//Se inicializan valores del input de usuario
		textFieldUser = new JTextField();
		textFieldUser.setBounds(177, 22, 184, 19);
		getContentPane().add(textFieldUser);
		textFieldUser.setColumns(10);
		
		//Se inicializan valores del input de contrasena
		textFieldPass = new JPasswordField();
		textFieldPass.setBounds(177, 60, 186, 19);
		getContentPane().add(textFieldPass);
		
		//Se inicializan valores del label que informa el error
		final JLabel lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(42, 87, 308, 27);
		getContentPane().add(lblError);
		
		//Se inicializan valores y acciones del boton que valida el login
		JButton btnLogin = new JButton("Ingresar");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user = textFieldUser.getText();
				String pass = new String(textFieldPass.getPassword());
				if(user.isEmpty() || pass.isEmpty()) { //Verifica que no esten vacios los campos
					lblError.setText("Debe ingresar usuario y contrasena"); //Informa que estan vacioes
					lblError.setVisible(true);
				} else { //Si no estan vacios
					String password = users.get(user);
					if(password != null && password.equals(pass)) { //Valida que sean correctos
						logger.setUser(user);
						logger.info("El usuario " + user + " se ha conectado");
						Main window = new Main(); //Son correctos, entonces abro la ventana del UNO y cierro login
						window.getFrmUno().setVisible(true);
						loginGui.setVisible(false);
					} else {
						logger.warn("Intento invalido de acceso");
						lblError.setText("Usuario y contrasena incorrecto"); //Informo que los datos ingresados no son correctos
						lblError.setVisible(true);
					}
				}
			}
		});
		
		btnLogin.setBounds(142, 126, 117, 25);
		getContentPane().add(btnLogin);
	}
	
	/** Metodo que obtiene los usuarios y contrasenas del archivo
	 * @return HashMap<String, String> con usuario,contrasena
	 */
	private HashMap<String, String> getUsersFromFile() {
		HashMap<String,String> users = new HashMap<String,String>();
		ReaderFile usersFile = null;
		String line;
		String[] splitLine;
		
		try {
			usersFile = new ReaderFile("resources/users.uno"); //Se crea el archivo
			while((line = usersFile.readLine()) != null) { //Guardo los usuarios en el mapa
				splitLine = line.split(",");
				users.put(splitLine[0], splitLine[1]);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("Ha ocurrido un error al leer el archivo de usuarios");
		} finally {
			try {
				if(usersFile != null) 
					usersFile.close();
			} catch(Exception e2) {
				e2.printStackTrace();
				logger.error("Ha ocurrido un error al cerrar el archivo de usuarios");
			}
		}
		
		return users;
	}
}
