package com.dataextraction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dataextraction.util.DateReaderUtil;
import com.dataextraction.util.PDFReader;
import com.joestelmach.natty.DateGroup;

public class DataExtraction {
	
	
	public static void main(String[] args) {
		
		PDFReader pdfReader = new PDFReader();
		DateReaderUtil readerUtil = new DateReaderUtil();
		
		File file ;
		int noOfFiles = new File("src/main/resources/").listFiles().length;
		String fileContent;
		String falseString = "sunday monday tuesday wednesday thursday friday saturday january february march april may june july august september october november december";
		System.out.println("SIZE "+noOfFiles);
		for (int i =0 ; i<noOfFiles; i++ ) {
			file = new File("src/main/resources/").listFiles()[0];
			fileContent = pdfReader.readPDF(file);
			String lines[] = fileContent.split("\\r?\\n");
			System.out.println("TOTAL LINES "+lines.length);
			List<DateGroup> dates = readerUtil.getDatesInString(fileContent);
			List<DateGroup> removefalse = new ArrayList<DateGroup>();
			for (DateGroup date : dates) {
				if (!date.getText().matches("[0-9 ]+") && date.getText().length() > 6 && !falseString.contains(date.getText().toLowerCase()) && ! (date.getText().toLowerCase().matches("(.*) days")) 
						&& (!date.getText().toLowerCase().matches("(.*) months")) && !(date.getText().toLowerCase().matches("(.*) years"))) {
					

					removefalse.add(date); 
				}
				else {
					System.out.println("Removed Text "+date.getText()); 
				}
				//System.out.println("size:"+date.getLine()+"  "+date.getText() + " "+date.getDates()+" date size :"+date.getDates().size());
				//System.out.println(lines[date.getLine()-1]);
			}
			
			System.out.println("After removing false positive "+removefalse.size());
			for (DateGroup date : removefalse) {
				System.out.println("size:"+date.getLine()+"  "+date.getText() + " "+date.getDates()+" date size :"+date.getDates().size());
				System.out.println(lines[date.getLine()-2]+" "+lines[date.getLine()-1]);
			}
			System.out.println(file.getName());
			//System.out.println(fileContent);
			break;
		}
	}
}
