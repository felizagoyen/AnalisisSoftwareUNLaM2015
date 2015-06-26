package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MethodAnalizer {
	
	private boolean isFunction; // Indica si es funcion o es un archivo
	

	private String reference; //Nombre del archivo o la funcion
	
	private int codeLines;
	private int cantCommentLines;
	private int comentedCodeLines;
	private int ciclomaticComplexity;
	private int fanIn;
	private int fanOut;	
	private int contadorLlaves;
	private int n1;
	private int N1;
	private int n2;
	private int N2;
	private long halsteadLength;
	private double halsteadVolume ;
	
	private boolean isMultipleComment = false;
	private boolean isInsideMethod = false;
	private boolean methodEnd = false;
	
	private ArrayList<String> operadores = new ArrayList<String>();
	private ArrayList<String> operandos = new ArrayList<String>();
	
	private String code = "";
	private String filePath;
	private String fileName;
	private String prevLine = "----1----";//La funcion general se debe ejecutar solo una vez por linea
	private FileReaderHelper file;
	
	
	public MethodAnalizer(FileReaderHelper file) {
		this.reference = file.getFile();
		this.file = file;
		this.reference = this.fileName = file.fileName();
		this.code = file.getTextFile();
		this.isFunction = false;
	}
	
	public MethodAnalizer(String line) {
		this.code += line + "\n";		
		line = sanitizeAndRemoveComments(line);
		line = line.replace("\t", "").replace("( )+", " ").trim();
		line = normalizeCodeLine(line);
		String[] splitedLine = line.split(" ");
		String cadAnterior = ""; // Se analiza como posible funcion, si en la siguiente cadena se encuentra un "(" se toma que cadAnterior es una funcion			
		for(String cad : splitedLine) {
			if(cad.equals("(") && !cadAnterior.equals("")) {					
				this.reference = cadAnterior;
				this.isFunction = true;		
				break;
			}
			cadAnterior = cad;
		}
	}
	/*
	 * Calcula la cantidad de comentarios
	 * Se le va pasando de a lineas y va haciendo el conteo
	 */
	public void calcComments(String line){		
		this.general(line);		
		prevLine = line;
		codeLines ++;		
		if(isMultipleComment) {
			cantCommentLines ++;
			if(line.indexOf("*/") != -1) {
				isMultipleComment = false;
			}
			
		} else {
			if(line.indexOf("/*") != -1) {
				isMultipleComment = true;
				cantCommentLines ++;
				if(line.indexOf("*/") != -1 && line.indexOf("*/") > line.indexOf("/*")) {
					isMultipleComment = false;
				}
			}
			
			if(line.indexOf("//") != -1) {
				cantCommentLines ++;
			}
		}	
	}
	/*
	 * Calcula la complejidad ciclomatica, se va pasando de a lineas, se va analizando el codigo, y se incrementa el contador de complejidad
	 */
	public void complejidadCiclomatica(String line) {
		this.general(line);
		prevLine = line;
		line = sanitizeAndRemoveComments(line);
		line = line.replace("\t", "").replace("( )+", " ").trim();
		int contador = 0;			
		int indice = 0;			
		
		//A esta altura le removi todos los comentarios al codigo, si tiene longitud hay codigo		
		if(line.length() > 0) {							
			if (line.indexOf("if(") != -1 || line.indexOf("if (") != -1)
				contador++;	
			
			if (line.indexOf("while(") != -1 || line.indexOf("while (") != -1)
				contador++;

			if (line.indexOf("for(") != -1 || line.indexOf("for (") != -1)
				contador++;

			if (line.matches("case(.+):"))
				contador++;
			
			indice = line.indexOf("&&");
			while (indice != -1) {
				contador++;
				if (indice + 2 <= line.length())
					indice += 2;
				if (line.substring(indice).contains("&&")) {
					indice += line.substring(indice).indexOf("&&");
				} else
					indice = -1;
			}

			indice = line.indexOf("||");
			while (indice != -1) {
				contador++;
				if (indice + 2 <= line.length())
					indice += 2;
				if (line.substring(indice).contains("||")) {
					indice += line.substring(indice).indexOf("||");
				} else
					indice = -1;
			}	
			this.ciclomaticComplexity += contador;	
		}
		
	}
	
	/*
	 * Funcion que calcula halstead, esta es la mas dificil porque tengo que distinguir entre un operador y un operando, para eso 
	 * tengo que analizar cada linea y separar bien lo que es operador u operando para, para hacer eso utilizo el metodo normalizeCodeLine 
	 * al normalizarla convierto esto:
	 * if(aa==bb){
	 * En esto
	 * if ( aa == bb ) {
	 * para poder tener bien separados los elementos y poder contar operadores y operandos
	 */
	public void halsteadLine(String line) {		
		String[] operadoresPosibles = {  "auto", "extern", "register", "static", "typedef", "virtual", "mutable", "inline", "const", "friend", "volatile", "transient", "final", "break", "case",
				"continue", "default", "do", "if", "else", "enum", "for", "goto", "if", "new", "return", "asm", "operator", "private", "protected", "public", "sizeof", "struct", "switch", "union",
				"while", "this", "namespace", "using", "try", "catch", "throw", "throws", "finally", "strictfp", "instanceof", "interface", "extends", "implements", "abstract", "concrete",
				"const_cast", "static_cast", "dynamic_cast", "reinterpret_cast", "typeid", "template", "explicit", "true", "false", "typename", "!=", "%=", "&&", "||", "&=",
				"*=", "++", "+=", "--", "-=->", "/=", "::", "<<", "<<=", "<=", "==", ">=", ">>", ">>>", ">>=>>>=", "?",
				 "^=", "|", "|=", "=&","~", "+",":","^", "&", ">", "/", ",", "-", "*", "%", "!", "<", "="};
		String[] simbolos = {  "auto", "extern", "register", "static", "typedef", "virtual", "mutable", "inline", "const", "friend", "volatile", "transient", "final", "break", "case",
				"continue", "default", "do", "if", "else", "enum", "for", "goto", "if", "new", "return", "asm", "operator", "private", "protected", "public", "sizeof", "struct", "switch", "union",
				"while", "this", "namespace", "using", "try", "catch", "throw", "throws", "finally", "strictfp", "instanceof", "interface", "extends", "implements", "abstract", "concrete",
				"const_cast", "static_cast", "dynamic_cast", "reinterpret_cast", "typeid", "template", "explicit", "true", "false", "typename", "!=", "%=", "&&", "||", "&=", "(", ")","/*","*/",
				"{", "}", "[", "]", "*=", "++", "+=", "--", "-=->", "...", "/=", "::", "<<", "<<=", "<=", "==", ">=", ">>", ">>>", ">>=>>>=", "?",
				 "^=", "|", "|=", "~", "=&", "#", "##", "~", "+", ".", ":","^", ";", "&", ">", "/", ",", "-", "*", "%", "!", "<", "="};
		this.general(line);
		prevLine = line;
		line = sanitizeAndRemoveComments(line);
		line = line.replace("\t", "").replace("( )+", " ").trim();
		
		if(line.length() != 0) {
			line = normalizeCodeLine(line);
			String[] splitedLine = line.split(" ");
			
			for(String cad : splitedLine) {	
				if(Arrays.asList(operadoresPosibles).contains(cad)) {
					operadores.add(cad);
				} else if(!Arrays.asList(simbolos).contains(cad)) {
					operandos.add(cad);
				}
			}			
		}				
	}
	
	public void calcularHalstead() {
		HashSet<String> operadoresSinRepetidos;
		HashSet<String> operandosSinRepetidos = new HashSet<String>();
		operadoresSinRepetidos = new HashSet<String>(operadores);
		operandosSinRepetidos = new HashSet<String>(operandos);

		n1 = operadoresSinRepetidos.size();
		N1 = operadores.size();
		n2 = operandosSinRepetidos.size();
		N2 = operandos.size();
		
		halsteadLength = N1 + N2;
		halsteadVolume = halsteadLength * log(n1 + n2, 2);
		
	}
	
	/*
	 * Devuelve las funciones que encuentra en la linea de codigo
	 */
	public ArrayList<String> getFunctions(String line) {
		this.general(line);
		prevLine = line;
		line = sanitizeAndRemoveComments(line);
		line = line.replace("\t", "").replace("( )+", " ").trim();
		line = normalizeCodeLine(line);
		String[] splitedLine = line.split(" ");
		String cadAnterior = ""; // Se analiza como posible funcion, si en la siguiente cadena se encuentra un "(" se toma que cadAnterior es una funcion
		ArrayList<String> funciones = new ArrayList<String>();
		for(String cad : splitedLine) {
			if(cad.equals("(") && !cadAnterior.equals("") && esOperador(cadAnterior) == -1) {
				fanOut ++;
				funciones.add(cadAnterior);
			}
			cadAnterior = cad;
		}		
		return funciones;
	}
	
/*
 * Esta funcion normaliza las lineas de codigo separando cada elemento con un espacio. Para luego poder
 * al normalizarla convierto esto:
 * if(aa==bb){
 * En esto
 * if ( aa == bb ) {
 */
	
	private String normalizeCodeLine(String line) {
		String[] simbolos = {  "auto", "extern", "register", "static", "typedef", "virtual", "mutable", "inline", "const", "friend", "volatile", "transient", "final", "break", "case",
				"continue", "default", "do", "if", "else", "enum", "for", "goto", "if", "new", "return", "asm", "operator", "private", "protected", "public", "sizeof", "struct", "switch", "union",
				"while", "this", "namespace", "using", "try", "catch", "throw", "throws", "finally", "strictfp", "instanceof", "interface", "extends", "implements", "abstract", "concrete",
				"const_cast", "static_cast", "dynamic_cast", "reinterpret_cast", "typeid", "template", "explicit", "true", "false", "typename", "!=", "%=", "&&", "||", "&=", "(", ")","/*","*/",
				"{", "}", "[", "]", "*=", "++", "+=", "--", "-=->", "...", "/=", "::", "<<", "<<=", "<=", "==", ">=", ">>", ">>>", ">>=>>>=", "?",
				 "^=", "|", "|=", "~", "=&", "#", "##", "~", "+", ".", ":","^", ";", "&", ">", "/", ",", "-", "*", "%", "!", "<", "="};
		String[] splitedLine = line.split(" ");
		String normalizedLine = "";
		ArrayList<String> cadAux = new ArrayList<String>();
		for(String cad : splitedLine) {		
			boolean hasOperator = false;
			while(cad.length() > 0) {
				hasOperator = false;
				String simboloSelected = "";
				int indexSimboloSelected = -1;
				for (String simbolo : simbolos){
					if ( (cad.indexOf(simbolo) >= 0 && cad.indexOf(simbolo) < indexSimboloSelected) || (indexSimboloSelected == -1 && cad.indexOf(simbolo) >= 0) ) {
						indexSimboloSelected = cad.indexOf(simbolo);
						simboloSelected = simbolo;
					}					
				}			
				if (indexSimboloSelected >= 0) {	
					if(indexSimboloSelected > 0) {
						cadAux.add(cad.substring(0, indexSimboloSelected));
						cadAux.add(simboloSelected);														
					} else {
						cadAux.add(simboloSelected);														
					}
					if((indexSimboloSelected + simboloSelected.length()) == cad.length()) {
						cad = "";
					} else {
						cad = cad.substring((indexSimboloSelected + simboloSelected.length()), (cad.length()));
					}
					hasOperator = true;					
				} 
				
				if(!hasOperator) {
					cadAux.add(cad);
					cad = "";
					hasOperator = false;
				}
			}
		}
		
		
		for (String s : cadAux)
		{
			normalizedLine += s + " ";
		}
		return normalizedLine;
	}
	
	private static int esOperador(String operador) {
		int index;
		final String[] simbolos = {  "auto", "extern", "register", "static", "typedef", "virtual", "mutable", "inline", "const", "friend", "volatile", "transient", "final", "break", "case",
				"continue", "default", "do", "if", "else", "enum", "for", "goto", "if", "new", "return", "asm", "operator", "private", "protected", "public", "sizeof", "struct", "switch", "union",
				"while", "this", "namespace", "using", "try", "catch", "throw", "throws", "finally", "strictfp", "instanceof", "interface", "extends", "implements", "abstract", "concrete",
				"const_cast", "static_cast", "dynamic_cast", "reinterpret_cast", "typeid", "template", "explicit", "true", "false", "typename", "!", "!=", "%", "%=", "&", "&&", "||", "&=", "(", ")",
				"{", "}", "[", "]", "*", "*=", "+", "++", "+=", ",", "-", "--", "-=->", ".", "...", "/", "/=", ":", "::", "<", "<<", "<<=", "<=", "=", "==", ">", ">=", ">>", ">>>", ">>=>>>=", "?",
				"^", "^=", "|", "|=", "~", ";", "=&", "#", "##", "~"};

		for (String simbolo : simbolos)
			if ((index = operador.indexOf(simbolo)) >= 0)
				return index;
		
		return -1;
	}
	
	public String sanitizeString(String line) {
		return line.replace("\t", "").replaceAll("( )+", " ").trim();
	}
	public String sanitizeAndRemoveComments(String line) {	
		if(this.isMultipleComment) {
			if(line.indexOf("*/") != -1) {
				line = line.substring(line.indexOf("*/"),line.length() - 1 );	
			} else {
				line = "";
			}
		}
		
		
		if(line.indexOf("/*") != -1) {
			line = line.substring(0, line.indexOf("/*"));			
		}
		if(line.indexOf("//") != -1) {	
			line = line.substring(0, line.indexOf("//"));							
		} 	
		return sanitizeString(line);
	}
	
	
	public boolean isMethodString(String linea) {		
		if ((linea.contains("public") || linea.contains("private") || linea.contains("protected")) && linea.contains("(")){
			this.isInsideMethod = true;
			return true;
		}	
		return false;
	}
	public boolean isInsideMethod() {
		return this.isInsideMethod;
	}
	public boolean methodEnd() {		
		if(this.methodEnd) {		
			//this.methodEndd = false;
			return true;
		} else {
			return false;
		}
		
	}
	
	public void general(String line) {	
		if(line != this.prevLine) {
			if(isMethodString(line)) {
				fanOut --;
			}
			if(this.isFunction) {
				this.code += line + "\n";
			}
			
			if (line.contains("{")) {
				contadorLlaves++;
			} else if (line.contains("}")) {
				contadorLlaves--;				
				if(contadorLlaves <= -1){
					this.isInsideMethod = false;
					this.methodEnd = true;
					
				}
			}
		}
		
	}	
	
	private static double log(double x, int base) {
		return Math.log(x) / Math.log(base);
	}
	

	public int getCodeLines() {
		return codeLines;
	}

	public void setCodeLines(int codeLines) {
		this.codeLines = codeLines;
	}

	public int getComentedCodeLines() {
		return comentedCodeLines;
	}

	public void setComentedCodeLines(int comentedCodeLines) {
		this.comentedCodeLines = comentedCodeLines;
	}

	public int getCiclomaticComplexity() {
		return ciclomaticComplexity == 0 ? 0 : ciclomaticComplexity+1;
	}

	public void setCiclomaticComplexity(int ciclomaticComplexity) {
		this.ciclomaticComplexity = ciclomaticComplexity;
	}

	public long getHalsteadLength() {
		return halsteadLength;
	}

	public void setHalsteadLength(int halsteadLength) {
		this.halsteadLength = halsteadLength;
	}

	public double getHalsteadVolume() {
		return halsteadVolume;
	}

	public void setHalsteadVolume(int halsteadVolume) {
		this.halsteadVolume = halsteadVolume;
	}

	public int getFanIn() {
		return fanIn;
	}

	public void setFanIn(int fanIn) {
		this.fanIn = fanIn;
	}

	public int getFanOut() {
		return fanOut;
	}

	public void setFanOut(int fanOut) {
		this.fanOut = fanOut;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public FileReaderHelper getFile() {
		return file;
	}

	public void setFile(FileReaderHelper file) {
		this.file = file;
	}

	public int getCantCommentLines() {
		return cantCommentLines;
	}

	public void setCantCommentLines(int cantCommentLines) {
		this.cantCommentLines = cantCommentLines;
	}

	public boolean isFunction() {
		return isFunction;
	}

	public void setFunction(boolean isFunction) {
		this.isFunction = isFunction;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public void setMethodEnd(Boolean end) {
		this.methodEnd = end;
	}
	
	public void setIsInsideMethod(Boolean isInside) {
		this.isInsideMethod = isInside;
	}
	
	public float getPercentCommentedLines() {
	    float percent = 0;
	    if (codeLines > 0) {
		percent = (float) cantCommentLines / codeLines * 100;
	    }
	    return percent;
	}
}
