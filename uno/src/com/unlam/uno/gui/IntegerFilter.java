package com.unlam.uno.gui;

import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class IntegerFilter extends DocumentFilter {
	   @Override
	   public void insertString(FilterBypass fb, int offset, String string,
	         AttributeSet attr) throws BadLocationException {

	      Document doc = fb.getDocument();
	      StringBuilder sb = new StringBuilder();
	      sb.append(doc.getText(0, doc.getLength()));
	      sb.insert(offset, string);

	      if (test(sb.toString()) && sb.toString().length() <= 8 )  {
	         super.insertString(fb, offset, string, attr);
	      }
	   }

	   private boolean test(String text) {
	      try {
	         Integer.parseInt(text);
	         return true;
	      } catch (NumberFormatException e) {
	         return false;
	      }
	   }

	   @Override
	   public void replace(FilterBypass fb, int offset, int length, String text,
	         AttributeSet attrs) throws BadLocationException {

	      Document doc = fb.getDocument();
	      StringBuilder sb = new StringBuilder();
	      sb.append(doc.getText(0, doc.getLength()));
	      sb.replace(offset, offset + length, text);

	      if (test(sb.toString()) && sb.toString().length() <= 8) {
	         super.replace(fb, offset, length, text, attrs);
	      }
	   }

	   @Override
	   public void remove(FilterBypass fb, int offset, int length)
	         throws BadLocationException {
	      Document doc = fb.getDocument();
	      StringBuilder sb = new StringBuilder();
	      sb.append(doc.getText(0, doc.getLength()));
	      sb.delete(offset, offset + length);

	      if ( test(sb.toString()) || sb.toString().isEmpty() ) {
	         super.remove(fb, offset, length);
	      } 
	   }
	}