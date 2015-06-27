package com.unlam.uno.gui;

import java.awt.Image;

import javax.swing.ImageIcon;


public class GuiUtils {

	public static ImageIcon load(String path){
		return load(path, 25, 25); 
	}
	
	public static ImageIcon load(String path, int w, int h) {
		
		ImageIcon tempIcon = new ImageIcon(Main.class.getResource(path));
		Image tempImg = tempIcon.getImage();  
		Image img = tempImg.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH);  
		ImageIcon icon = new ImageIcon(img);
		
		return icon; 
	}
}
