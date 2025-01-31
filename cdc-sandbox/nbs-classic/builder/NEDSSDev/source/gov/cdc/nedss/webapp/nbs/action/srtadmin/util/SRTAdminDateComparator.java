package gov.cdc.nedss.webapp.nbs.action.srtadmin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class SRTAdminDateComparator implements  Comparator<String>{

	@Override
	public int compare(String o1, String o2) {
		// TODO Auto-generated method stub
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
		int val =0;
		
		if(o1.equalsIgnoreCase("")){
			return 1;	
		}
		if(o2.equalsIgnoreCase("")){
			return -1;	
		}
		try {
			Date current  = formatter.parse(o1);
			Date nextDate  = formatter.parse(o2);
			val  = current.compareTo(nextDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
	}
}
