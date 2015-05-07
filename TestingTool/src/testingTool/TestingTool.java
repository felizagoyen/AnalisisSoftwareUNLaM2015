package testingTool;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

public class TestingTool extends JFrame {

	private JPanel contentPane;
	private JTextField lblSelectedFile;
	private JLabel labelPercent;
	//private Analizer analyzer;

	/**
	 * Launch the application.
	 */
	public void run() {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestingTool frame = new TestingTool();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestingTool() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{438, 0};
		gbl_contentPane.rowHeights = new int[]{27, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane.anchor = GridBagConstraints.NORTH;
		gbc_splitPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		contentPane.add(splitPane, gbc_splitPane);
		
		lblSelectedFile = new JTextField();
		splitPane.setRightComponent(lblSelectedFile);
		lblSelectedFile.setColumns(10);
		
		JButton btnSeleccionarProyecto = new JButton("Seleccionar proyecto");
		btnSeleccionarProyecto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String file = openFileChooser();
				if (file != null) {
					Analizer analyzer = new Analizer(file);
					labelPercent.setText("% " + analyzer.getCommentsPercent());
				} else {
					labelPercent.setText("%0");
				}				
			}
		});
		splitPane.setLeftComponent(btnSeleccionarProyecto);
		
		JSplitPane splitPane_1 = new JSplitPane();
		GridBagConstraints gbc_splitPane_1 = new GridBagConstraints();
		gbc_splitPane_1.fill = GridBagConstraints.BOTH;
		gbc_splitPane_1.gridx = 0;
		gbc_splitPane_1.gridy = 6;
		contentPane.add(splitPane_1, gbc_splitPane_1);
		
		labelPercent = new JLabel("%");
		splitPane_1.setRightComponent(labelPercent);
		
		JLabel lblPorcentajeDeComentarios = new JLabel("Porcentaje de comentarios");
		splitPane_1.setLeftComponent(lblPorcentajeDeComentarios);
	}
	
	public String openFileChooser() {
		System.out.println("Abrir filechooser");
		JFileChooser chooser = new JFileChooser();
		///PARA SETEAR FILTROS
	    //FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
	    //chooser.setFileFilter(filter);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    int returnVal = chooser.showOpenDialog(getParent());
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	//System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
	    	//System.out.println("In this Path: " + chooser.getSelectedFile().getPath());
	    	lblSelectedFile.setText(chooser.getSelectedFile().getPath());
	    	return chooser.getSelectedFile().getPath();
	    } 
	    return null;
	}

}
