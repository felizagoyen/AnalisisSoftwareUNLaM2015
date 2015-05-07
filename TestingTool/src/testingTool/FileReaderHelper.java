package testingTool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileReaderHelper {
	
	private String file;
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
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (this.br != null && sCurrentLine == null)this.br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return sCurrentLine;
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
	        	filesPath.addAll(listAllFilesFromFolder(fileEntry.getPath())) ;
	        } else {	        	
	        	filesPath.add(fileEntry.getAbsolutePath());	           
	        }
	    }
	    return filesPath;
	}
}
