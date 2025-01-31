package gov.cdc.nedss.webapp.nbs.action.myinvestigation.util;

import gov.cdc.nedss.systemservice.util.DropDownCodeDT;

import java.util.Comparator;

public class StringComparator  implements Comparator<Object>{

	public int compare(Object a, Object b) {
  		String desc1 = ((DropDownCodeDT)a).getValue().toUpperCase();
	    String desc2 = ((DropDownCodeDT)b).getValue().toUpperCase();
	    if (!(desc1.equals(desc2)))
	        return desc1.compareTo(desc2);
        else
        	return desc2.compareTo(desc1);
		
	     // end 
	}

}
