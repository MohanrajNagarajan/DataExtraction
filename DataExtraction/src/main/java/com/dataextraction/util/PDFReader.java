package com.dataextraction.util;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFReader {
	
	public String readPDF(File file) {
		String result = "";
		try {
		PDDocument document = PDDocument.load(file);

	      //Instantiate PDFTextStripper class
	    PDFTextStripper pdfStripper = new PDFTextStripper();
	    result = pdfStripper.getText(document);
		document.close();
		return result;
		} catch (IOException e) {
			return e.getMessage();
		}  		
	}

}
