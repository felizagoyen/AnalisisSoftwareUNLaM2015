package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileReaderHelper {

    private static final String EXTENSION_JAVA = ".JAVA";
    
    private String file;
    private int lineNumber = 0;
    private BufferedReader br = null;

    public FileReaderHelper(String file) {
	this.file = file;
	try {
	    br = new BufferedReader(new FileReader(this.file));
	    br.mark(0);
	} catch (FileNotFoundException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public String getLine() {
	String sCurrentLine = null;
	try {
	    sCurrentLine = this.br.readLine();
	    lineNumber++;
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return sCurrentLine;
    }

    public void resetBuffer() {

	if (lineNumber > 0) {
	    System.out.println("acaa: " + lineNumber);
	    try {
		this.br.reset();
		this.br.mark(0);
		lineNumber = 0;
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    public boolean isDirectory() {
	return new File(this.file).isDirectory();
    }

    public static boolean isDirectory(String file) {
	return new File(file).isDirectory();
    }

    public static ArrayList<String> listAllFilesFromFolder(String folderStr) {
	File folder = new File(folderStr);
	ArrayList<String> filesPath = new ArrayList<String>();
	for (final File fileEntry : folder.listFiles()) {
	    if (fileEntry.isDirectory()) {
		filesPath.addAll(listAllFilesFromFolder(fileEntry.getPath()));
	    } else if (fileEntry.getAbsolutePath().toUpperCase().endsWith(EXTENSION_JAVA)) {
		filesPath.add(fileEntry.getAbsolutePath());
	    }
	}
	return filesPath;
    }

    public String getFile() {
	return file;
    }

    public String getTextFile() {
	byte[] encoded = null;
	try {
	    encoded = Files.readAllBytes(Paths.get(file));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return new String(encoded);
    }
}
