package testingTool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;

public class Analizer {
	private String file;
	//private FileReaderHelper fileHelper;
	private int cantFiles = 0;	
	ArrayList<MethodAnalizer> allResults = new ArrayList<MethodAnalizer>();
	public Analizer(String file) {
		this.file = file;				
	}
	/*
	 * Metodo que recorre la carpeta seleccionada en busca de archivos para analizar
	 * Se puede seleccionar solo un archivo
	 */
	public float fileSearch() {
		//fileHelper = ;
		int cantFiles = 0;
		float prcentTotalComments = 0;
		String extAccepted = "java";
		MethodAnalizer res;
		if(FileReaderHelper.isDirectory(this.file)) { // En caso que sea directorio Los busco y los meto en un array a todos los files .java
			ArrayList<String> filesPath = new ArrayList<String>();
			filesPath = FileReaderHelper.listAllFilesFromFolder(this.file);
			for (int i = 0; i < filesPath.size(); i++) {
			    String extension = filesPath.get(i).substring(filesPath.get(i).lastIndexOf(".") + 1, filesPath.get(i).length());
			    if(extAccepted.equalsIgnoreCase(extension)) {
			    	cantFiles ++;
			    	allResults.add(new MethodAnalizer(new FileReaderHelper(filesPath.get(i))));
			    }			    
			}
		} else { // Caso en el que es solo un archivo
			cantFiles = 1;
			allResults.add(new MethodAnalizer(new FileReaderHelper(this.file)));			
		}
		// metodo que comienza el analisis
		analize();
		//calculateHalstead();
		mostrar();
		return prcentTotalComments / cantFiles;
	}
	
	public void analize() {		
		String line = "";
		MethodAnalizer codigoAnalizador = new MethodAnalizer();
		//Por los metodos que se agregan
		ArrayList<MethodAnalizer> newResults = new ArrayList<MethodAnalizer>();
		for(int i = 0 ; i < allResults.size(); i++) {
			FileReaderHelper fileHelper;
			fileHelper = allResults.get(i).getFile();			
			while((line = fileHelper.getLine()) != null) {				
				allResults.get(i).calcComments(line);
				allResults.get(i).complejidadCiclomatica(line);
				allResults.get(i).halstead(line);
				if(allResults.get(i).isMethodString(line)) { // Linea en donde muestra el comienzo del metodo
					codigoAnalizador = new MethodAnalizer();					
				} else if(allResults.get(i).isInsideMethod()) { // Adentro del metodo
					codigoAnalizador.calcComments(line);
					codigoAnalizador.complejidadCiclomatica(line);
					codigoAnalizador.getCantCommentLines();
					if(codigoAnalizador.methodEnd()) { // Cuando termina el metodo guardo su analisis en el array						
						newResults.add(codigoAnalizador);
					}
				} 
			}
		}
		this.allResults.addAll(newResults);
	}
	public void mostrar() {
		System.out.println("Cantidad de registros: " + allResults.size());
		for(int i = 0 ; i < allResults.size(); i++) {
			System.out.println("Mostrando Cantidad de Comentarios: " + allResults.get(i).getCantCommentLines());
			System.out.println("Mostrando Complejidad ciclomatica: " + allResults.get(i).getCiclomaticComplexity());
		}
	}
	
	public float getCommentsPercentFromFile(FileReaderHelper fileHelper) {
		String line;
		boolean isMultipleComment = false;
		int cantCommentLines = 0;
		int cantLines = 0;
		fileHelper.resetBuffer();
		while((line = fileHelper.getLine()) != null) {
			cantLines ++;
			if(isMultipleComment) {
				cantCommentLines ++;
				if(line.indexOf("*/") != -1) {
					isMultipleComment = false;
				}
			} else {
				if(line.indexOf("/*") != -1) {
					isMultipleComment = true;
					cantCommentLines ++;
				}
				if(line.indexOf("//") != -1) {
					cantCommentLines ++;
				}
			}			
		}
		return (cantCommentLines * 100) / cantLines;
	}
}
