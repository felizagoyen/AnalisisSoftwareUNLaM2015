package core;

import java.util.ArrayList;

public class Analizer {

    private String path;
    // private FileReaderHelper fileHelper;
    private int cantFiles = 0;
    private ArrayList<MethodAnalizer> allResults = new ArrayList<MethodAnalizer>();

    public Analizer(String path) {
	this.path = path;

	init();
    }

    /*
     * Metodo que recorre la carpeta seleccionada en busca de archivos para
     * analizar Se puede seleccionar solo un archivo
     */
    private float init() {
	// fileHelper = ;
	int cantFiles = 0;
	float prcentTotalComments = 0;
	String extAccepted = "java";
	MethodAnalizer res;
	if (FileReaderHelper.isDirectory(this.path)) { // En caso que sea
						       // directorio Los busco
						       // y los meto en un
						       // array a todos los
						       // files .java
	    ArrayList<String> filesPath = new ArrayList<String>();
	    filesPath = FileReaderHelper.listAllFilesFromFolder(this.path);
	    for (int i = 0; i < filesPath.size(); i++) {
		String extension = filesPath.get(i).substring(filesPath.get(i).lastIndexOf(".") + 1, filesPath.get(i).length());
		if (extAccepted.equalsIgnoreCase(extension)) {
		    cantFiles++;
		    allResults.add(new MethodAnalizer(new FileReaderHelper(filesPath.get(i))));
		}
	    }
	} else { // Caso en el que es solo un archivo
	    cantFiles = 1;
	    allResults.add(new MethodAnalizer(new FileReaderHelper(this.path)));
	}
	// metodo que comienza el analisis
	analize();
	// calculateHalstead();
	mostrar();
	return prcentTotalComments / cantFiles;
    }

    private void analize() {
	String line = "";
	MethodAnalizer codigoAnalizador = new MethodAnalizer();
	// Por los metodos que se agregan
	ArrayList<MethodAnalizer> newResults = new ArrayList<MethodAnalizer>();
	for (int i = 0; i < allResults.size(); i++) {
	    FileReaderHelper fileHelper;
	    fileHelper = allResults.get(i).getFile();
	    while ((line = fileHelper.getLine()) != null) {
		allResults.get(i).calcComments(line);
		allResults.get(i).complejidadCiclomatica(line);
		allResults.get(i).halsteadLine(line);
		if (allResults.get(i).isMethodString(line)) { // Linea en donde
							      // muestra el
							      // comienzo del
							      // metodo
		    codigoAnalizador = new MethodAnalizer();
		} else if (allResults.get(i).isInsideMethod()) { // Adentro del
								 // metodo
		    codigoAnalizador.calcComments(line);
		    codigoAnalizador.complejidadCiclomatica(line);
		    codigoAnalizador.halsteadLine(line);
		    codigoAnalizador.getCantCommentLines();
		    if (codigoAnalizador.methodEnd()) { // Cuando termina el
							// metodo guardo su
							// analisis en el array
			newResults.add(codigoAnalizador);
		    }
		}
	    }
	}
	this.allResults.addAll(newResults);
	for (int i = 0; i < allResults.size(); i++) {
	    allResults.get(i).calcularHalstead();
	}

    }

    /*
     * Esta es la funcion en donde van a estar todos los datos calculados
     */
    public void mostrar() {
	System.out.println("Cantidad de registros: " + allResults.size());
	for (int i = 0; i < allResults.size(); i++) {
	    System.out.println("Mostrando Cantidad de Comentarios: " + allResults.get(i).getCantCommentLines());
	    System.out.println("Mostrando Complejidad ciclomatica: " + allResults.get(i).getCiclomaticComplexity());
	    System.out.println("Longitud de halstead: " + allResults.get(i).getHalsteadLength());
	    System.out.println("Volumen de halstead: " + allResults.get(i).getHalsteadVolume());
	}

    }

    public ArrayList<MethodAnalizer> getAllResults() {
	return allResults;
    }

}