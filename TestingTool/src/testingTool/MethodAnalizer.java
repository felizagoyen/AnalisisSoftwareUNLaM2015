package testingTool;

import java.util.ArrayList;

public class MethodAnalizer {
	private int codeLines;
	private int cantCommentLines;
	private int comentedCodeLines;
	private int ciclomaticComplexity;
	private int halsteadLength;
	private int halsteadVolume;
	private int fanIn;
	private int fanOut;	
	private int contadorLlaves;
	
	private boolean isMultipleComment = false;
	private boolean isInsideMethod = false;
	private boolean methodEnd = false;
	
	
	
	private String code;
	private String filePath;
	private String fileName;
	private String prevLine;//La funcion general se debe ejecutar solo una vez por linea
	private FileReaderHelper file;
	
	
	public MethodAnalizer(FileReaderHelper file) {
		this.file = file;
	}
	public MethodAnalizer() {
		
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
			//Ver si esto funciona
			if (line.indexOf("if(") != -1){
				contador++;	
			}
			if (line.indexOf("while(") != -1)
				contador++;

			if (line.indexOf("switch(") != -1)
				contador++;

			if (line.indexOf("for(") != -1)
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
	public void halstead(String line) {
		int index = 0;
		String analizar = "";
		this.general(line);
		prevLine = line;
		line = sanitizeAndRemoveComments(line);
		line = line.replace("\t", "").replace("( )+", " ").trim();
		System.out.println("HALSTEAD");
		System.out.println(normalizeCodeLine(line));		
	}
	
/*
 * Esta funcion normaliza las lineas de codigo separando cada elemento con un espacio. Para luego poder
 * al normalizarla convierto esto:
 * if(aa==bb){
 * En esto
 * if ( aa == bb ) {
 * Aun queda por corregir un par de boludeces aca
 */
	
	private String normalizeCodeLine(String line) {
		String[] simbolos = {  "auto", "extern", "register", "static", "typedef", "virtual", "mutable", "inline", "const", "friend", "volatile", "transient", "final", "break", "case",
				"continue", "default", "do", "if", "else", "enum", "for", "goto", "if", "new", "return", "asm", "operator", "private", "protected", "public", "sizeof", "struct", "switch", "union",
				"while", "this", "namespace", "using", "try", "catch", "throw", "throws", "finally", "strictfp", "instanceof", "interface", "extends", "implements", "abstract", "concrete",
				"const_cast", "static_cast", "dynamic_cast", "reinterpret_cast", "typeid", "template", "explicit", "true", "false", "typename", "!", "!=", "%", "%=", "&", "&&", "||", "&=", "(", ")",
				"{", "}", "[", "]", "*", "*=", "+", "++", "+=", ",", "-", "--", "-=->", ".", "...", "/", "/=", ":", "::", "<", "<<", "<<=", "<=", "=", "==", ">", ">=", ">>", ">>>", ">>=>>>=", "?",
				"^", "^=", "|", "|=", "~", ";", "=&", "#", "##", "~"};
		String[] splitedLine = line.split(" ");
		String normalizedLine = "";
		ArrayList<String> cadAux = new ArrayList<String>();
		System.out.println(line);
		for(String cad : splitedLine) {		
			//System.out.println(cad);
			int indexSimbolo;
			boolean cadModified = false;
			boolean hasOperator = false;
			int i = 0;
			while(cad.length() > 0) {
				//System.out.println(cad);
				String simboloSelected = "";
				int indexSimboloSelected = -1;
				for (String simbolo : simbolos){
					//System.out.println(cad.indexOf(simbolo) + " < " + indexSimboloSelected);
					if ( (cad.indexOf(simbolo) >= 0 && cad.indexOf(simbolo) < indexSimboloSelected) || (indexSimboloSelected == -1 && cad.indexOf(simbolo) >= 0) ) {
//						System.out.println("Entre");
						indexSimboloSelected = cad.indexOf(simbolo);
						simboloSelected = simbolo;
//						System.out.println("Simbolo encontrado en: " + indexSimboloSelected);
					}					
				}			
				//System.out.println("Este puto " + indexSimboloSelected);
				if (indexSimboloSelected >= 0) {	
//					System.out.println("ENTRE???");
//					System.out.println("Simbolo:" + simboloSelected);
//					System.out.println("Cadena a analizar:" + cad);
					if(indexSimboloSelected > 0) {
						cadAux.add(cad.substring(0, indexSimboloSelected));
						cadAux.add(simboloSelected);														
					} else {
						cadAux.add(simboloSelected);														
					}
//					System.out.println(simboloSelected.length());
//					System.out.println("Start: " + (indexSimboloSelected + simboloSelected.length()) + " END: " + (cad.length() -1));
					if((indexSimboloSelected + simboloSelected.length()) == cad.length()) {
						cad = "";
					} else {
						cad = cad.substring((indexSimboloSelected + simboloSelected.length()), (cad.length() -1));
					}
//					System.out.println("Resto cad: " + cad);					
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
		this.general(linea);
		prevLine = linea;
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
	
	/*
	 * Los metodos para calcular fanIn y fanOut son facile, esto es lo que saque de otro tp pero hay que terminarlos
	 */
	
	/*
	public static long calcularFanIn(String codigo, String nombreMetodo) {

		int contadorLlamadas = 0;
		String[] lineasCodigo = codigo.split("\n");

		for (String linea : lineasCodigo)
			if (esLlamadaAMetodo(linea))
				contadorLlamadas++;

		return contadorLlamadas;
	}

	
	public static long calcularFanOut(String metodo) {

		// List<String> metodosNoRepetidos = listaMetodos;
		long contador = 0;

		for (Resultado resultado : listaResultados) {

			String[] lineasCodigo = resultado.getCodigoMetodo().split("/n");

			for (String linea : lineasCodigo)
				if (linea.contains(metodo))
					contador++;

		}
		return contador;
	}
	public static boolean esLlamadaAMetodo(String linea) {
		if (linea.contains(".") && linea.contains("("))
			return true;
		return false;
	}*/
	
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
		return ciclomaticComplexity;
	}

	public void setCiclomaticComplexity(int ciclomaticComplexity) {
		this.ciclomaticComplexity = ciclomaticComplexity;
	}

	public int getHalsteadLength() {
		return halsteadLength;
	}

	public void setHalsteadLength(int halsteadLength) {
		this.halsteadLength = halsteadLength;
	}

	public int getHalsteadVolume() {
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

	
	
}
