package com.unlam.uno.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReaderFile {
	private File file = null;
	private FileReader fr = null;
	private BufferedReader br = null;

	public ReaderFile(String filePath) throws Exception {
		file = new File(filePath);
		fr = new FileReader(file);
		br = new BufferedReader(fr);
	}

	public String readLine() throws Exception {
		return br.readLine();
	}

	public void close() throws Exception {
		if (fr != null)
			fr.close();
	}
}