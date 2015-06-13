package vistas;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class PanelFactory {
    
    private static final String UNNAMED_TITLE = "Sin nombre";
    private static final String UNNAMED_VALUE = "¿?";
    private static final String TITLE_PORCENTAJE_COMENTARIOS = "Porcentaje de comentarios";
    private static final String TITLE_COMP_CICLOMATICA = "Complejidad Ciclomatica";
    private static final String TITLE_LONGITUD = "Longitud";
    private static final String TITLE_VOLUMEN = "Volumen";
    private static final String TITLE_FAN_IN = "Fan in";
    private static final String TITLE_FAN_OUT = "Fan out";
    
    public static JPanel getPanel(String name, String code, String porcentComentarios, String complejidadCiclomatica, String longitud, String volumen) {
	
	String title = ((name != null && !name.isEmpty())?(name):(UNNAMED_TITLE));
	String codeFinal = ((code != null && !code.isEmpty())?(code):(UNNAMED_VALUE));
	String porcentComentariosFinal = ((porcentComentarios != null && !porcentComentarios.isEmpty())?(porcentComentarios):(UNNAMED_VALUE));
	String complejidadCiclomaticaFinal = ((complejidadCiclomatica != null && !complejidadCiclomatica.isEmpty())?(complejidadCiclomatica):(UNNAMED_VALUE));
	String longitudFinal = ((longitud != null && !longitud.isEmpty())?(longitud):(UNNAMED_VALUE));
	String volumenFinal = ((volumen != null && !volumen.isEmpty())?(volumen):(UNNAMED_VALUE));
	
	JTextField txtPorcentajeDeComentarios;
	JTextField txtComplejidadCiclomatica;
	JTextField txtLongitud;
	JTextField txtVolumen;
	JTextField txtFanIn;
	JTextField txtFanOut;
	JTextField txtValuePorcentajeDeComentarios;
	JTextField txtValueComplejidadCiclomatica;
	JTextField txtValueLongitud;
	JTextField txtValueVolumen;
	JTextField txtValueFanIn;
	JTextField txtValueFanOut;
	
	JPanel panel = new JPanel();
	panel.setToolTipText(title);
	panel.setLayout(new BorderLayout(0, 0));
	
	JSplitPane splitPane_1 = new JSplitPane();
	splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
	panel.add(splitPane_1, BorderLayout.CENTER);
	splitPane_1.setResizeWeight(0.9); 
		
	JTextPane txtCode = new JTextPane();
	txtCode.setEditable(false);
	txtCode.setText(codeFinal);
	
	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setViewportView(txtCode);
	splitPane_1.setLeftComponent(scrollPane);
	
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
	txtPorcentajeDeComentarios.setText(TITLE_PORCENTAJE_COMENTARIOS);
	panel_3.add(txtPorcentajeDeComentarios, "cell 0 0,growx");
	txtPorcentajeDeComentarios.setColumns(10);
	
	txtComplejidadCiclomatica = new JTextField();
	txtComplejidadCiclomatica.setEditable(false);
	txtComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
	txtComplejidadCiclomatica.setText(TITLE_COMP_CICLOMATICA);
	panel_3.add(txtComplejidadCiclomatica, "cell 0 1,growx");
	txtComplejidadCiclomatica.setColumns(10);
	
	txtLongitud = new JTextField();
	txtLongitud.setEditable(false);
	txtLongitud.setHorizontalAlignment(SwingConstants.CENTER);
	txtLongitud.setText(TITLE_LONGITUD);
	panel_3.add(txtLongitud, "cell 0 2,growx");
	txtLongitud.setColumns(10);
	
	txtVolumen = new JTextField();
	txtVolumen.setEditable(false);
	txtVolumen.setHorizontalAlignment(SwingConstants.CENTER);
	txtVolumen.setText(TITLE_VOLUMEN);
	panel_3.add(txtVolumen, "cell 0 3,growx");
	txtVolumen.setColumns(10);
	
//	txtFanIn = new JTextField();
//	txtFanIn.setEditable(false);
//	txtFanIn.setHorizontalAlignment(SwingConstants.CENTER);
//	txtFanIn.setText(TITLE_FAN_IN);
//	panel_3.add(txtFanIn, "cell 0 4,growx");
//	txtFanIn.setColumns(10);
//	
//	txtFanOut = new JTextField();
//	txtFanOut.setEditable(false);
//	txtFanOut.setHorizontalAlignment(SwingConstants.CENTER);
//	txtFanOut.setText(TITLE_FAN_OUT);
//	panel_3.add(txtFanOut, "cell 0 5,growx");
//	txtFanOut.setColumns(10);
	
	JPanel panel_4 = new JPanel();
	splitPane_2.setRightComponent(panel_4);
	panel_4.setLayout(new MigLayout("", "[grow]", "[][][][][][]"));
	
	txtValuePorcentajeDeComentarios = new JTextField();
	txtValuePorcentajeDeComentarios.setEditable(false);
	txtValuePorcentajeDeComentarios.setHorizontalAlignment(SwingConstants.CENTER);
	txtValuePorcentajeDeComentarios.setText(porcentComentariosFinal);
	panel_4.add(txtValuePorcentajeDeComentarios, "cell 0 0,growx");
	txtValuePorcentajeDeComentarios.setColumns(10);
	
	txtValueComplejidadCiclomatica = new JTextField();
	txtValueComplejidadCiclomatica.setEditable(false);
	txtValueComplejidadCiclomatica.setText(complejidadCiclomaticaFinal);
	txtValueComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
	txtValueComplejidadCiclomatica.setColumns(10);
	panel_4.add(txtValueComplejidadCiclomatica, "cell 0 1,growx");
	
	txtValueLongitud = new JTextField();
	txtValueLongitud.setEditable(false);
	txtValueLongitud.setText(longitudFinal);
	txtValueLongitud.setHorizontalAlignment(SwingConstants.CENTER);
	txtValueLongitud.setColumns(10);
	panel_4.add(txtValueLongitud, "cell 0 2,growx");
	
	txtValueVolumen = new JTextField();
	txtValueVolumen.setEditable(false);
	txtValueVolumen.setText(volumenFinal);
	txtValueVolumen.setHorizontalAlignment(SwingConstants.CENTER);
	txtValueVolumen.setColumns(10);
	panel_4.add(txtValueVolumen, "cell 0 3,growx");
	
//	txtValueFanIn = new JTextField();
//	txtValueFanIn.setEditable(false);
//	txtValueFanIn.setText(fanInFinal);
//	txtValueFanIn.setHorizontalAlignment(SwingConstants.CENTER);
//	txtValueFanIn.setColumns(10);
//	panel_4.add(txtValueFanIn, "cell 0 4,growx");
//	
//	txtValueFanOut = new JTextField();
//	txtValueFanOut.setEditable(false);
//	txtValueFanOut.setText(fanOutFinal);
//	txtValueFanOut.setHorizontalAlignment(SwingConstants.CENTER);
//	txtValueFanOut.setColumns(10);
//	panel_4.add(txtValueFanOut, "cell 0 5,growx");
	
	return panel;
    }
    
}
