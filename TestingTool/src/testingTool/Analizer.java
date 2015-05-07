package testingTool;

import java.util.ArrayList;

public class Analizer {
	private String file;
	//private FileReaderHelper fileHelper;
	private int cantFiles = 0;
	
	public Analizer(String file) {
		this.file = file;				
	}
	
	public float getCommentsPercent() {
		//fileHelper = ;
		int cantFiles = 0;
		float prcentTotalComments = 0;
		String extAccepted = "java";
		if(FileReaderHelper.isDirectory(this.file)) {
			ArrayList<String> filesPath = new ArrayList<String>();
			filesPath = FileReaderHelper.listAllFilesFromFolder(this.file);
			for (int i = 0; i < filesPath.size(); i++) {				
			    System.out.println("La Posta: " + filesPath.get(i));
			    String extension = filesPath.get(i).substring(filesPath.get(i).lastIndexOf(".") + 1, filesPath.get(i).length());
			    if(extAccepted.equalsIgnoreCase(extension)) {
			    	cantFiles ++;
			    	prcentTotalComments += (float)getCommentsPercentFromFile(new FileReaderHelper(filesPath.get(i)));
			    }			    
			}
		} else {
			cantFiles = 1;
			prcentTotalComments = (float)getCommentsPercentFromFile(new FileReaderHelper(this.file));
		}
		return prcentTotalComments / cantFiles;
	}
	
	public float getCommentsPercentFromFile(FileReaderHelper fileHelper) {
		String line;
		boolean isMultipleComment = false;
		int cantCommentLines = 0;
		int cantLines = 0;
		while((line = fileHelper.getLine()) != null) {
			cantLines ++;
			System.out.println(line);
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
