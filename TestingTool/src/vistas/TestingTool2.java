package vistas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import core.Analizer;
import core.MethodAnalizer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import java.awt.TextArea;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class TestingTool2 extends JFrame {

	private static final String VALUE_PATH_DEFAULT = "<path to file or folder>";
	private static final String TITLE_PORCENTAGE_COMMENTS = "Percentage of Comments";
	private static final String TITLE_CYCLOMATIC_COMPLEXITY = "Cyclomatic Complexity";
	private static final String TITLE_LENGTH = "Length";
	private static final String TITLE_VOLUME = "Volume";
	private static final String TITLE_FAN_IN = "Fan in";
	private static final String TITLE_FAN_OUT = "Fan out";

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPath;
	private JComboBox<String> comboClass;
	private JComboBox<String> comboMethods;
	private Analizer analyzer = null;
	private JLabel lblClass;
	private JLabel lblMethod;
	private JLabel lblCode;
	private TextArea txtCode;
	private JTextField txtPorcentajeDeComentarios;
	private JTextField txtComplejidadCiclomatica;
	private JTextField txtLongitud;
	private JTextField txtVolumen;
	private JTextField txtFanIn;
	private JTextField txtFanOut;
	private JTextField txtPercentageCommentsValue;
	private JTextField txtCyclomaticComplexityValue;
	private JTextField txtLengthValue;
	private JTextField txtVolumeValue;
	private JTextField txtFanInValue;
	private JTextField txtFanOutValue;
	/**
	 * Create the frame.
	 */
	public TestingTool2() {
		setTitle("TestingTool");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 810, 670);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(29, 26, 739, 27);
		contentPane.add(splitPane);

		txtCode = new TextArea();
		txtCode.setBackground(Color.WHITE);
		txtCode.setEditable(false);
		txtCode.setBounds(29, 154, 739, 282);
		contentPane.add(txtCode);

		comboClass = new JComboBox<String>();
		comboClass.setEnabled(false);
		comboClass.setBounds(29, 82, 345, 34);		
		comboClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				MethodAnalizer firstClass = analyzer.getAllResults().get(0);
				refreshView(firstClass.getCode(),
						firstClass.getComentedCodeLines(),
						firstClass.getCiclomaticComplexity(),
						firstClass.getHalsteadLength(),
						firstClass.getHalsteadVolume(),
						firstClass.getFanIn(),
						firstClass.getFanOut()
						);	
			}
		});
		contentPane.add(comboClass);

		comboMethods = new JComboBox<String>();
		comboMethods.setEnabled(false);
		comboMethods.setBounds(413, 82, 355, 34);
		comboClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				comboMethods.removeAllItems();
				comboMethods.addItem("All Methods");
			}
		});
		contentPane.add(comboMethods);

		JButton btnSelect = new JButton("Browser...");
		btnSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String path = openFileChooser();		

				if (path != null && !path.isEmpty()) {

					cleanScreen();

					txtPath.setText(path);
					analyzer = new Analizer(path);
					for (MethodAnalizer result : analyzer.getAllResults()) {
						comboClass.addItem(result.getFileName());
					}		    

					comboClass.setEnabled(true);
					comboMethods.setEnabled(true);
				}

			}
		});
		splitPane.setLeftComponent(btnSelect);

		txtPath = new JTextField();
		txtPath.setHorizontalAlignment(SwingConstants.CENTER);
		txtPath.setEditable(false);
		txtPath.setText(VALUE_PATH_DEFAULT);
		splitPane.setRightComponent(txtPath);
		txtPath.setColumns(10);

		lblClass = new JLabel("Class");
		lblClass.setBounds(29, 65, 70, 15);
		contentPane.add(lblClass);

		lblMethod = new JLabel("Method");
		lblMethod.setBounds(413, 65, 70, 15);
		contentPane.add(lblMethod);

		lblCode = new JLabel("Code");
		lblCode.setBounds(29, 133, 70, 15);
		contentPane.add(lblCode);

		JPanel panelStadistics = new JPanel();
		panelStadistics.setForeground(new Color(255, 255, 255));
		panelStadistics.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelStadistics.setBounds(29, 454, 739, 159);
		contentPane.add(panelStadistics);
		panelStadistics.setLayout(null);

		JPanel panelProperties = new JPanel();
		panelProperties.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelProperties.setBounds(0, 0, 369, 159);
		panelStadistics.add(panelProperties);
		panelProperties.setLayout(new MigLayout("", "[grow]", "[][][][][][]"));

		txtPorcentajeDeComentarios = new JTextField();
		txtPorcentajeDeComentarios.setEditable(false);
		txtPorcentajeDeComentarios.setHorizontalAlignment(SwingConstants.CENTER);
		txtPorcentajeDeComentarios.setText(TITLE_PORCENTAGE_COMMENTS);
		panelProperties.add(txtPorcentajeDeComentarios, "cell 0 0,growx");
		txtPorcentajeDeComentarios.setColumns(10);

		txtComplejidadCiclomatica = new JTextField();
		txtComplejidadCiclomatica.setEditable(false);
		txtComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
		txtComplejidadCiclomatica.setText(TITLE_CYCLOMATIC_COMPLEXITY);
		panelProperties.add(txtComplejidadCiclomatica, "cell 0 1,growx");
		txtComplejidadCiclomatica.setColumns(10);

		txtLongitud = new JTextField();
		txtLongitud.setEditable(false);
		txtLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		txtLongitud.setText(TITLE_LENGTH);
		panelProperties.add(txtLongitud, "cell 0 2,growx");
		txtLongitud.setColumns(10);

		txtVolumen = new JTextField();
		txtVolumen.setEditable(false);
		txtVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		txtVolumen.setText(TITLE_VOLUME);
		panelProperties.add(txtVolumen, "cell 0 3,growx");
		txtVolumen.setColumns(10);

		txtFanIn = new JTextField();
		txtFanIn.setEditable(false);
		txtFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		txtFanIn.setText(TITLE_FAN_IN);
		panelProperties.add(txtFanIn, "cell 0 4,growx");
		txtFanIn.setColumns(10);

		txtFanOut = new JTextField();
		txtFanOut.setEditable(false);
		txtFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		txtFanOut.setText(TITLE_FAN_OUT);
		panelProperties.add(txtFanOut, "cell 0 5,growx");
		txtFanOut.setColumns(10);

		JPanel panelValue = new JPanel();
		panelValue.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelValue.setBounds(370, 0, 369, 159);
		panelStadistics.add(panelValue);
		panelValue.setLayout(new MigLayout("", "[grow]", "[][][][][][]"));

		txtPercentageCommentsValue = new JTextField();
		txtPercentageCommentsValue.setEditable(false);
		txtPercentageCommentsValue.setHorizontalAlignment(SwingConstants.CENTER);
		panelValue.add(txtPercentageCommentsValue, "cell 0 0,growx");
		txtPercentageCommentsValue.setColumns(10);

		txtCyclomaticComplexityValue = new JTextField();
		txtCyclomaticComplexityValue.setEditable(false);
		txtCyclomaticComplexityValue.setHorizontalAlignment(SwingConstants.CENTER);
		txtCyclomaticComplexityValue.setColumns(10);
		panelValue.add(txtCyclomaticComplexityValue, "cell 0 1,growx");

		txtLengthValue = new JTextField();
		txtLengthValue.setEditable(false);
		txtLengthValue.setHorizontalAlignment(SwingConstants.CENTER);
		txtLengthValue.setColumns(10);
		panelValue.add(txtLengthValue, "cell 0 2,growx");

		txtVolumeValue = new JTextField();
		txtVolumeValue.setEditable(false);
		txtVolumeValue.setHorizontalAlignment(SwingConstants.CENTER);
		txtVolumeValue.setColumns(10);
		panelValue.add(txtVolumeValue, "cell 0 3,growx");

		txtFanInValue = new JTextField();
		txtFanInValue.setEditable(false);
		txtFanInValue.setHorizontalAlignment(SwingConstants.CENTER);
		txtFanInValue.setColumns(10);
		panelValue.add(txtFanInValue, "cell 0 4,growx");

		txtFanOutValue = new JTextField();
		txtFanOutValue.setEditable(false);
		txtFanOutValue.setHorizontalAlignment(SwingConstants.CENTER);
		txtFanOutValue.setColumns(10);
		panelValue.add(txtFanOutValue, "cell 0 5,growx");

		refreshView("", 0, 0, 0, 0.0, 0, 0);
	}

	private String openFileChooser() {
		// System.out.println("Abrir filechooser");
		JFileChooser chooser = new JFileChooser();
		String filePath = null;
		// /PARA SETEAR FILTROS
		// FileNameExtensionFilter filter = new
		// FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
		// chooser.setFileFilter(filter);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = chooser.showOpenDialog(getParent());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// System.out.println("You chose to open this file: " +
			// chooser.getSelectedFile().getName());
			// System.out.println("In this Path: " +
			// chooser.getSelectedFile().getPath());
			filePath = chooser.getSelectedFile().getPath();
		}

		return filePath;
	}

	private void cleanScreen() {
		txtPath.setText(VALUE_PATH_DEFAULT);
		comboClass.removeAllItems();
		comboMethods.removeAllItems();
		refreshView("", 0, 0, 0, 0.0, 0, 0);
	}

	private void refreshView(String code, int percentageCommentsValue, int cyclomaticComplexityValue, long lengthValue, double volumeValue, int fanInValue, int fanOutValue) {
		txtCode.setText(code);
		txtPercentageCommentsValue.setText(Integer.toString(percentageCommentsValue) + " %");
		txtCyclomaticComplexityValue.setText(Integer.toString(cyclomaticComplexityValue));
		txtLengthValue.setText(Long.toString(lengthValue));
		txtVolumeValue.setText(Double.toString(volumeValue));
		txtFanInValue.setText(Integer.toString(fanInValue));
		txtFanOutValue.setText(Integer.toString(fanOutValue));
	}
}
