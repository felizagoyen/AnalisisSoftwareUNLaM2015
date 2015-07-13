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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import core.Analizer;
import core.MethodAnalizer;

public class TestingTool extends JFrame {

    private static final String VALUE_PATH_DEFAULT = "<path to file or folder>";
    
    /**
     * default serial
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtPath;
    private JTabbedPane tabbedPane;
    
    /**
     * Create the frame.
     */
    public TestingTool() {
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
		
		if (path != null && !path.isEmpty()) {
		    
		    cleanScreen();
		    
		    txtPath.setText(path);
		    
		    Analizer analyzer = new Analizer(path);
		    for (MethodAnalizer result : analyzer.getAllResults()) {
			String percentComments ;
			if (result.getComentedCodeLines() > 0) {
			    percentComments = String.valueOf((result.getComentedCodeLines() / result.getCodeLines()) * 100);
			} else {
			    percentComments = "0 %";
			}
			
			JPanel panel = PanelFactory.getPanel(result.getReference(), result.getCode(), percentComments, String.valueOf(result.getCiclomaticComplexity()), String.valueOf(result.getHalsteadLength()), String.valueOf(result.getHalsteadVolume()),String.valueOf(result.getFanIn()),String.valueOf(result.getFanOut()));
			tabbedPane.addTab(panel.getToolTipText(), null, panel, null);
		    }		    
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

	tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	contentPane.add(tabbedPane, BorderLayout.CENTER);

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
	tabbedPane.removeAll();
    }
    
}
