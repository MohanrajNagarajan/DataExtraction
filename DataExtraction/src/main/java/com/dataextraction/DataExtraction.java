package com.dataextraction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.dataextraction.util.DateReaderUtil;
import com.dataextraction.util.PDFReader;
import com.joestelmach.natty.DateGroup;

public class DataExtraction {
	
	
	public static void main(String[] args) {
		
		try {
			
			PDFReader pdfReader = new PDFReader();
			DateReaderUtil readerUtil = new DateReaderUtil();
			Path currentDir = Paths.get("");
			System.out.println("Absoulte Path "+currentDir.toAbsolutePath());
			File file ;		
			File[] files = new File(currentDir+"src/main/resources/").listFiles();
			int noOfFiles = files.length;
			String fileContent;
			FileOutputStream fos;
			String falseString = "sunday monday tuesday wednesday thursday friday saturday january february march april may june july august september october november december";
			System.out.println("SIZE "+noOfFiles);
			String fileName = "";
			String sentence ="";
			String pdfFileName="";
			List<String> sentences;
			BufferedWriter bw ;
			for (int i =0 ; i<noOfFiles; i++ ) {
				sentences = new ArrayList<String>();
				sentence = "";
				file = files[i];
				pdfFileName = file.getName();
				System.out.println("PDF File Name : "+pdfFileName);
				fileName = file.getName().replace(".pdf", ".txt");
				fos = new FileOutputStream(new File(currentDir+"src/test/resources/"+fileName));
				bw = new BufferedWriter(new OutputStreamWriter(fos));
				fileContent = pdfReader.readPDF(file);
				String lines[] = fileContent.split("\\r?\\n");
				System.out.println("TOTAL LINES "+lines.length);
				List<DateGroup> dates = readerUtil.getDatesInString(fileContent);
				List<DateGroup> removefalse = new ArrayList<DateGroup>();
				int j =0;
				for (DateGroup date : dates) {
					sentence = "";
					if (!date.getText().matches("[0-9 ]+") && date.getText().length() > 6 && !falseString.contains(date.getText().toLowerCase()) && ! (date.getText().toLowerCase().matches("(.*) days")) 
							&& (!date.getText().toLowerCase().matches("(.*) months")) && !(date.getText().toLowerCase().matches("(.*) years"))) {
						removefalse.add(date);
						bw.newLine();
						bw.write("Date number "+(j++));
						bw.newLine();
						bw.write("Date : "+date.getText());
						bw.newLine();
						bw.write("Line Number "+date.getLine());
						bw.newLine();

						if ( date.getLine() < lines.length-1 && (date.getLine()-2 != -1)) {
							
						
							sentence = lines[date.getLine()-2]+lines[date.getLine()-1]+lines[date.getLine()]; 
						}
						else {
							sentence = lines[date.getLine()-1]+lines[date.getLine()];
						}
						sentences.add(sentence);
						bw.write("Sentence "+sentence);
						bw.newLine();
						bw.newLine();
						
					}

				}
				
				bw.write("--------------------------------------------------------------------------------------------------------------------------");
				bw.newLine();
				bw.newLine();
				bw.write("----------------Keyword Matching Data------------------");
				bw.newLine();
				bw.newLine();
				System.out.println("After removing false positive "+removefalse.size());
				j=0;
				int matchCount =0;
				for (DateGroup date : removefalse) {
					String line = sentences.get(j++).toLowerCase();
					if (line.contains("maturity") || line.contains("closing")) {
						bw.write("Date : "+date.getText());
						bw.newLine();
						bw.write("Line Number "+date.getLine());
						bw.newLine();
						bw.write(line);
						bw.newLine();
						bw.write("KEYWORD TYPE : MATURITY AMOUNT");
						bw.newLine();
						bw.newLine();
						matchCount++;
					}
					
					if (line.contains("issue") || line.contains("open")) {
						bw.write("Date : "+date.getText());
						bw.newLine();
						bw.write("Line Number "+date.getLine());
						bw.newLine();
						bw.write(line);
						bw.newLine();
						bw.write("KEYWORD TYPE : ISSUE");
						bw.newLine();
						bw.newLine();
						matchCount++;
					} 
				}
				System.out.println("Total number of matches "+matchCount);
				bw.close();				
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
}
