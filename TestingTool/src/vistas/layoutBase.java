package vistas;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;

public class layoutBase extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPath;
	private JTextField txtPorcentajeDeComentarios;
	private JTextField txtComplejidadCiclomatica;
	private JTextField txtLongitud;
	private JTextField txtVolumen;
	private JTextField txtFanIn;
	private JTextField txtFanOut;
	private JTextField txtValuePorcentajeDeComentarios;
	private JTextField txtValueComplejidadCiclomatica;
	private JTextField txtValueLongitud;
	private JTextField txtValueVolumen;
	private JTextField txtValueFanIn;
	private JTextField txtValueFanOut;
	private JScrollPane scrollPane;
	private JTextPane textCode;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					layoutBase frame = new layoutBase();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public layoutBase() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.NORTH);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String path = openFileChooser();
				if (path != null) {
					//labelPercent.setText("% " + analyzer.fileSearch());
				} else {
					//labelPercent.setText("%0");
				}
			}
		});
		splitPane.setLeftComponent(btnSelect);
		
		txtPath = new JTextField();
		txtPath.setHorizontalAlignment(SwingConstants.CENTER);
		txtPath.setEditable(false);
		txtPath.setText("<path to file or folder>");
		splitPane.setRightComponent(txtPath);
		txtPath.setColumns(10);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("sad");
		tabbedPane.addTab("New tab", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel.add(splitPane_1, BorderLayout.CENTER);
		splitPane_1.setResizeWeight(0.9);
		
		JPanel panel_2 = new JPanel();
		splitPane_1.setRightComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setResizeWeight(0.2);
		panel_2.add(splitPane_2, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		splitPane_2.setLeftComponent(panel_3);
		panel_3.setLayout(new MigLayout("", "[grow]", "[][][][][][]"));
		
		txtPorcentajeDeComentarios = new JTextField();
		txtPorcentajeDeComentarios.setEditable(false);
		txtPorcentajeDeComentarios.setHorizontalAlignment(SwingConstants.CENTER);
		txtPorcentajeDeComentarios.setText("porcentaje de comentarios");
		panel_3.add(txtPorcentajeDeComentarios, "cell 0 0,growx");
		txtPorcentajeDeComentarios.setColumns(10);
		
		txtComplejidadCiclomatica = new JTextField();
		txtComplejidadCiclomatica.setEditable(false);
		txtComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
		txtComplejidadCiclomatica.setText("complejidad ciclomatica");
		panel_3.add(txtComplejidadCiclomatica, "cell 0 1,growx");
		txtComplejidadCiclomatica.setColumns(10);
		
		txtLongitud = new JTextField();
		txtLongitud.setEditable(false);
		txtLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		txtLongitud.setText("longitud");
		panel_3.add(txtLongitud, "cell 0 2,growx");
		txtLongitud.setColumns(10);
		
		txtVolumen = new JTextField();
		txtVolumen.setEditable(false);
		txtVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		txtVolumen.setText("volumen");
		panel_3.add(txtVolumen, "cell 0 3,growx");
		txtVolumen.setColumns(10);
		
		txtFanIn = new JTextField();
		txtFanIn.setEditable(false);
		txtFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		txtFanIn.setText("fan in");
		panel_3.add(txtFanIn, "cell 0 4,growx");
		txtFanIn.setColumns(10);
		
		txtFanOut = new JTextField();
		txtFanOut.setEditable(false);
		txtFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		txtFanOut.setText("fan out");
		panel_3.add(txtFanOut, "cell 0 5,growx");
		txtFanOut.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		splitPane_2.setRightComponent(panel_4);
		panel_4.setLayout(new MigLayout("", "[grow]", "[][][][][][]"));
		
		txtValuePorcentajeDeComentarios = new JTextField();
		txtValuePorcentajeDeComentarios.setEditable(false);
		txtValuePorcentajeDeComentarios.setHorizontalAlignment(SwingConstants.CENTER);
		txtValuePorcentajeDeComentarios.setText("<value>");
		panel_4.add(txtValuePorcentajeDeComentarios, "cell 0 0,growx");
		txtValuePorcentajeDeComentarios.setColumns(10);
		
		txtValueComplejidadCiclomatica = new JTextField();
		txtValueComplejidadCiclomatica.setEditable(false);
		txtValueComplejidadCiclomatica.setText("<value>");
		txtValueComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
		txtValueComplejidadCiclomatica.setColumns(10);
		panel_4.add(txtValueComplejidadCiclomatica, "cell 0 1,growx");
		
		txtValueLongitud = new JTextField();
		txtValueLongitud.setEditable(false);
		txtValueLongitud.setText("<value>");
		txtValueLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		txtValueLongitud.setColumns(10);
		panel_4.add(txtValueLongitud, "cell 0 2,growx");
		
		txtValueVolumen = new JTextField();
		txtValueVolumen.setEditable(false);
		txtValueVolumen.setText("<value>");
		txtValueVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		txtValueVolumen.setColumns(10);
		panel_4.add(txtValueVolumen, "cell 0 3,growx");
		
		txtValueFanIn = new JTextField();
		txtValueFanIn.setEditable(false);
		txtValueFanIn.setText("<value>");
		txtValueFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		txtValueFanIn.setColumns(10);
		panel_4.add(txtValueFanIn, "cell 0 4,growx");
		
		txtValueFanOut = new JTextField();
		txtValueFanOut.setEditable(false);
		txtValueFanOut.setText("<value>");
		txtValueFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		txtValueFanOut.setColumns(10);
		panel_4.add(txtValueFanOut, "cell 0 5,growx");
		
		scrollPane = new JScrollPane();
		splitPane_1.setLeftComponent(scrollPane);
		
		textCode = new JTextPane();
		textCode.setText("<code>");
		scrollPane.setViewportView(textCode);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
	}
	
	public String openFileChooser() {
		//System.out.println("Abrir filechooser");
		JFileChooser chooser = new JFileChooser();
		String filePath = null;
		///PARA SETEAR FILTROS
	    //FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
	    //chooser.setFileFilter(filter);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    int returnVal = chooser.showOpenDialog(getParent());
	    
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	//System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
	    	//System.out.println("In this Path: " + chooser.getSelectedFile().getPath());
	    	txtPath.setText(chooser.getSelectedFile().getPath());
	    	filePath = chooser.getSelectedFile().getPath();
	    } else {
	    	txtPath.setText("<path to file or folder>");
	    }
	    
	    
	    return filePath;
	}
}
