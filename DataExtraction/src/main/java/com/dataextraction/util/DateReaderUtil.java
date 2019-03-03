package com.dataextraction.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import net.rationalminds.LocalDateModel;
//import net.rationalminds.Parser;

public class DateReaderUtil {
	
	public List<DateGroup> getDatesInString (String data) {
		 List<DateGroup> dates = new Parser().parse(data);
		 System.out.println("Date Group size"+dates.size());
		 return dates;
	}
	
//	public List<LocalDateModel> getDates(String data) {
//		Parser parser=new Parser();  
//        List<LocalDateModel> dates=parser.parse(data);  
//        System.out.println(dates);  
//        return dates;
//	}
}
